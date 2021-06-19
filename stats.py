#!/usr/bin/python3

import csv

i = -1


def get(line, token, arr):
    if line.startswith(token):
        arr.append(line[len(token) + 2:len(line) - 1])


def reset():
    global i
    i = -1


def increment():
    global i
    i += 1
    return i


STATS = 15

data = [[] for x in range(STATS)]
with open('Stats/latencyStats500_1', 'r') as f:
    line = 1
    while line:
        line = f.readline()
        reset()
        # get(line, 'StorageMode', data[increment()])
        get(line, 'offload', data[increment()])
        get(line, 'HGW_Storage_Min_Threshold', data[increment()])
        get(line, 'HGW_Storage_Max_Threshold', data[increment()])
        get(line, 'HGW_Storage_Compression', data[increment()])
        get(line, 'HGW_Compression_Selection', data[increment()])
        get(line, 'HGW_Critical_Selection', data[increment()])
        get(line, 'Write latency', data[increment()])
        get(line, 'Read latency', data[increment()])
        get(line, 'Overall latency', data[increment()])
        get(line, 'Read Count', data[increment()])
        get(line, 'Write Count', data[increment()])
        get(line, 'Average Write latency', data[increment()])
        get(line, 'Average Read latency', data[increment()])
        get(line, 'Average Overall latency', data[increment()])
# Average Write latency: 161
# Average Read latency: 164
# Average Overall latency: 325
with open('Stats/SimulationTime500_1', 'r') as f:
    line = 1
    j = increment()
    while line:
        line = f.readline()
        if line != '':
            get(line, line[0:line.index('-')], data[j])

with open("stats.csv", "w+") as f:
    w = csv.writer(f, delimiter=';')
    w.writerows(zip(*data))
