#!/usr/bin/env python3
"""errs.py <log> <path-substring>...: print compact javac errors for matching files."""
import re, sys
log = sys.argv[1]
subs = sys.argv[2:]
lines = open(log, encoding='utf-8', errors='replace').read().split('\n')
pat = re.compile(r'^(/.*\.java):(\d+): error: (.*)$')
for i, l in enumerate(lines):
    m = pat.match(l)
    if not m or not any(s in m.group(1) for s in subs):
        continue
    extra = []
    for j in range(i + 1, min(i + 8, len(lines))):
        if pat.match(lines[j]) or lines[j].strip() == '^':
            break
        if lines[j].strip().startswith(('symbol:', 'location:', 'required:', 'found:', 'reason:')):
            extra.append(lines[j].strip())
    src = lines[i + 1].strip() if i + 1 < len(lines) else ''
    print(f"{m.group(1).rsplit('/',1)[1]}:{m.group(2)}: {m.group(3)} | {' '.join(extra)} || {src[:170]}")
