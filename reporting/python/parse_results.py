#!/usr/bin/env python
# -*- coding: utf-8 -*-

import csv

results  = open(sys.argv[1], "rb")
reader = csv.reader(results, delimiter=';')

header = reader.next()
line_len = len(header)
w_col = 0

for col_num in range(0,line_len):
    w_col = col_num
    if(header[col_num] == "warmUp"):
        break

for row in reader:
    for col_num in range(0,line_len):
        if(col_num == w_col):
            continue
        print '%s: %s' % (header[col_num], row[col_num])

results.close()

