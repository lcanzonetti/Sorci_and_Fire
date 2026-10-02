#!/usr/bin/env python3
"""One-shot migration of 1.18.2 datapack JSON to the 1.21.1 formats."""
import json, os, sys, re

ROOT = sys.argv[1] if len(sys.argv) > 1 else "src/main/resources/data"

TAG_RENAMES = {
    "forge:glass/colorless": "c:glass_blocks/colorless",
    "forge:obsidian": "c:obsidians",
    "forge:slimeballs": "c:slime_balls",
    "forge:string": "c:strings",
    "forge:is_overworld": "c:is_overworld",
}


def fix_tag_ref(s):
    if not isinstance(s, str):
        return s
    pre = "#" if s.startswith("#") else ""
    body = s[1:] if pre else s
    if body in TAG_RENAMES:
        return pre + TAG_RENAMES[body]
    if body.startswith("forge:"):
        return pre + "c:" + body[len("forge:"):]
    return s


def walk_strings(o):
    if isinstance(o, dict):
        return {k: walk_strings(v) for k, v in o.items()}
    if isinstance(o, list):
        return [walk_strings(v) for v in o]
    if isinstance(o, str) and ("forge:" in o):
        if o.startswith("forge:") or o.startswith("#forge:"):
            return fix_tag_ref(o)
    return o


def result_stack(r):
    if isinstance(r, str):
        return {"id": r}
    if isinstance(r, dict) and "item" in r:
        r = dict(r)
        r["id"] = r.pop("item")
        if "nbt" in r:
            print("WARN: result nbt dropped", r, file=sys.stderr)
            r.pop("nbt")
    return r


def migrate_recipe(d):
    if "result" in d:
        d["result"] = result_stack(d["result"])
    if d.get("type") == "minecraft:smithing":
        d["type"] = "minecraft:smithing_transform"
        d = {"type": d["type"], "template": [], "base": d["base"], "addition": d["addition"], "result": d["result"]}
    if "conditions" in d:
        d["neoforge:conditions"] = d.pop("conditions")
    return d


def item_predicate(p):
    p = dict(p)
    if "item" in p:
        p["items"] = p.pop("item")
    if "tag" in p:
        p["items"] = "#" + p.pop("tag")
    if isinstance(p.get("items"), list) and len(p["items"]) == 1:
        p["items"] = p["items"][0]
    if "nbt" in p:
        print("WARN: predicate nbt dropped", p, file=sys.stderr)
        p.pop("nbt")
    return p


def migrate_advancement(d):
    disp = d.get("display")
    if disp and "icon" in disp:
        icon = dict(disp["icon"])
        if "item" in icon:
            icon["id"] = icon.pop("item")
        icon.pop("nbt", None)
        disp["icon"] = icon
    for crit in d.get("criteria", {}).values():
        cond = crit.get("conditions", {})
        if crit.get("trigger") == "minecraft:inventory_changed" and "items" in cond:
            cond["items"] = [item_predicate(p) for p in cond["items"]]
    return d


def looting_count(fn):
    out = {"function": "minecraft:enchanted_count_increase", "enchantment": "minecraft:looting", "count": fn.get("count", 1)}
    if "limit" in fn:
        out["limit"] = fn["limit"]
    return out


def migrate_loot_node(o):
    if isinstance(o, list):
        return [migrate_loot_node(v) for v in o]
    if not isinstance(o, dict):
        return o
    o = {k: migrate_loot_node(v) for k, v in o.items()}
    fn = o.get("function", "").replace("minecraft:", "")
    if fn == "looting_enchant":
        return looting_count(o)
    if fn == "enchant_with_levels":
        treasure = o.pop("treasure", False)
        if treasure:
            o["options"] = "#minecraft:on_random_loot"
    cond = o.get("condition", "").replace("minecraft:", "")
    if cond == "random_chance_with_looting":
        c = o["chance"]
        m = o["looting_multiplier"]
        return {
            "condition": "minecraft:random_chance_with_enchanted_bonus",
            "enchantment": "minecraft:looting",
            "unenchanted_chance": c,
            "enchanted_chance": {"type": "minecraft:linear", "base": c + m, "per_level_above_first": m},
        }
    if cond == "alternative":
        o["condition"] = "minecraft:any_of"
    if cond == "match_tool":
        pred = o.get("predicate", {})
        if "enchantments" in pred:
            ench = []
            for e in pred.pop("enchantments"):
                e = dict(e)
                if "enchantment" in e:
                    e["enchantments"] = e.pop("enchantment")
                ench.append(e)
            pred.setdefault("predicates", {})["minecraft:enchantments"] = ench
        if "item" in pred:
            pred["items"] = pred.pop("item")
        if "tag" in pred:
            pred["items"] = "#" + pred.pop("tag")
    return o


def main():
    changed = 0
    for dirpath, _, files in os.walk(ROOT):
        for f in files:
            if not f.endswith(".json"):
                continue
            path = os.path.join(dirpath, f)
            with open(path, encoding="utf-8") as fh:
                raw = fh.read()
            try:
                d = json.loads(raw)
            except Exception as e:
                print("ERR parse", path, e, file=sys.stderr)
                continue
            d = walk_strings(d)
            rel = path.replace("\\", "/")
            if "/recipe/" in rel:
                d = migrate_recipe(d)
            elif "/advancement/" in rel:
                d = migrate_advancement(d)
            elif "/loot_table/" in rel:
                d = migrate_loot_node(d)
            new = json.dumps(d, indent=2, ensure_ascii=False) + "\n"
            if new != raw:
                with open(path, "w", encoding="utf-8") as fh:
                    fh.write(new)
                changed += 1
    print("changed", changed)


if __name__ == "__main__":
    main()
