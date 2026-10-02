#!/usr/bin/env python3
"""Apply token replacements at exact javac error positions.

usage: fix_at_errors.py <javac log> <message regex> <old token> <new token>
Replaces <old token> starting at the caret column of each matching error.
"""
import re, sys
from collections import defaultdict

log, msg_re, old, new = sys.argv[1:5]
lines = open(log, encoding='utf-8', errors='replace').read().split('\n')
pat = re.compile(r'^(/.*\.java):(\d+): error: (.*)$')
edits = defaultdict(set)
for i, l in enumerate(lines):
    m = pat.match(l)
    if not m or not re.search(msg_re, m.group(3)):
        continue
    # find caret line
    for j in range(i + 1, min(i + 6, len(lines))):
        if lines[j].strip() == '^':
            edits[m.group(1)].add((int(m.group(2)), lines[j].index('^')))
            break
count = 0
for path, pos in edits.items():
    src = open(path, encoding='utf-8').read().split('\n')
    for ln, col in sorted(pos, key=lambda p: (p[0], -p[1])):
        s = src[ln - 1]
        if s[col:col + len(old)] == old:
            src[ln - 1] = s[:col] + new + s[col + len(old):]
            count += 1
        else:
            # caret may point at the '.' before the member
            k = s.find(old, col)
            if k >= 0 and k - col <= 2:
                src[ln - 1] = s[:k] + new + s[k + len(old):]
                count += 1
    open(path, 'w', encoding='utf-8').write('\n'.join(src))
print('applied', count)
