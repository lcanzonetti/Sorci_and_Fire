#!/usr/bin/env python3
"""addimport.py <fqcn> <files...>: add an import to each file if missing."""
import sys, re
cls = sys.argv[1]
simple = cls.rsplit('.', 1)[1]
for p in sys.argv[2:]:
    s = open(p, encoding='utf-8').read()
    if re.search(r'^import ' + re.escape(cls) + ';', s, re.M):
        continue
    if re.search(r'^import [\w.]+\.' + re.escape(simple) + ';', s, re.M):
        continue
    s = re.sub(r'^(package [\w.]+;\n)', r'\1\nimport ' + cls + ';', s, count=1, flags=re.M)
    open(p, 'w', encoding='utf-8').write(s)
