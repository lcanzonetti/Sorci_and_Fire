#!/usr/bin/env python3
"""Rewrite `new ResourceLocation(a, b)` -> fromNamespaceAndPath, `new ResourceLocation(a)` -> parse."""
import os, sys

def split_args(s):
    depth = 0; args = []; cur = ''; instr = False; esc = False
    for ch in s:
        if instr:
            cur += ch
            if esc: esc = False
            elif ch == '\\': esc = True
            elif ch == '"': instr = False
            continue
        if ch == '"': instr = True; cur += ch; continue
        if ch in '([{<': depth += 1
        if ch in ')]}>': depth -= 1
        if ch == ',' and depth == 0:
            args.append(cur); cur = ''; continue
        cur += ch
    args.append(cur)
    return args

def rewrite(src):
    key = 'new ResourceLocation('
    out = ''; i = 0
    while True:
        j = src.find(key, i)
        if j < 0:
            out += src[i:]; break
        out += src[i:j]
        k = j + len(key); depth = 1; instr = False; esc = False; m = k
        while depth:
            ch = src[m]
            if instr:
                if esc: esc = False
                elif ch == '\\': esc = True
                elif ch == '"': instr = False
            elif ch == '"': instr = True
            elif ch == '(': depth += 1
            elif ch == ')': depth -= 1
            m += 1
        inner = src[k:m-1]
        inner = rewrite(inner)
        n = len(split_args(inner))
        out += ('ResourceLocation.fromNamespaceAndPath(' if n == 2 else 'ResourceLocation.parse(') + inner + ')'
        i = m
    return out

for root, _, files in os.walk(sys.argv[1]):
    for f in files:
        if f.endswith('.java'):
            p = os.path.join(root, f)
            s = open(p, encoding='utf-8').read()
            if 'new ResourceLocation(' in s:
                open(p, 'w', encoding='utf-8').write(rewrite(s))
