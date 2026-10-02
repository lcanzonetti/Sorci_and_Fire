#!/usr/bin/env python3
"""Rewrite com.mojang.math (1.18) usages to JOML + com.mojang.math.Axis (1.19.3+)."""
import os, re, sys
sys.path.insert(0, os.path.dirname(__file__))
from rl_rewrite import split_args

def find_close(src, k):
    depth = 1; m = k; instr = False; esc = False
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
    return m

def rewrite_quat(src):
    key = 'new Quaternion('
    out = ''; i = 0
    while True:
        j = src.find(key, i)
        if j < 0:
            return out + src[i:]
        out += src[i:j]
        k = j + len(key); m = find_close(src, k)
        inner = rewrite_quat(src[k:m-1])
        args = [a.strip() for a in split_args(inner)]
        if len(args) == 3 and re.match(r'^Vector3f\.[XYZ][PN]$', args[0]):
            axis = args[0].replace('Vector3f', 'Axis')
            fn = 'rotationDegrees' if args[2] == 'true' else 'rotation'
            out += f'{axis}.{fn}({args[1]})'
        elif len(args) == 4 and args[3] in ('true', 'false'):
            x, y, z = args[:3]
            if args[3] == 'true':
                out += f'new Quaternionf().rotationXYZ(({x}) * ((float) Math.PI / 180F), ({y}) * ((float) Math.PI / 180F), ({z}) * ((float) Math.PI / 180F))'
            else:
                out += f'new Quaternionf().rotationXYZ({x}, {y}, {z})'
        else:
            out += 'new Quaternionf(' + inner + ')'
        i = m

for root, _, files in os.walk(sys.argv[1]):
    for f in files:
        if not f.endswith('.java'):
            continue
        p = os.path.join(root, f)
        s = open(p, encoding='utf-8').read()
        o = s
        if 'com.mojang.math' not in s and 'Quaternion' not in s:
            continue
        s = rewrite_quat(s)
        s = re.sub(r'\bVector3f\.([XYZ][PN])\b', r'Axis.\1', s)
        s = re.sub(r'\bQuaternion\b', 'Quaternionf', s)
        s = s.replace('import com.mojang.math.Quaternionf;', 'import org.joml.Quaternionf;')
        for c in ('Vector3f', 'Vector4f', 'Matrix4f', 'Matrix3f'):
            s = s.replace(f'import com.mojang.math.{c};', f'import org.joml.{c};')
        if 'Quaternionf' in s and 'import org.joml.Quaternionf;' not in s:
            s = re.sub(r'(\nimport )', r'\nimport org.joml.Quaternionf;\1', s, count=1)
        if re.search(r'\bAxis\.[XYZ][PN]', s) and 'import com.mojang.math.Axis;' not in s:
            s = re.sub(r'(\nimport )', r'\nimport com.mojang.math.Axis;\1', s, count=1)
        if s != o:
            open(p, 'w', encoding='utf-8').write(s)
