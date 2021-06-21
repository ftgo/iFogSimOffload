#!/usr/bin/python3

import csv
import os.path
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

i = -1


def get_value(line, token, arr):
    if line.startswith(token):
        arr.append(line[len(token) + 2:len(line) - 1])


def reset():
    global i
    i = -1


def increment():
    global i
    i += 1
    return i


def current():
    global i
    return i


def get_id(values):
    values_1 = values[1]
    values_2 = values[2]
    return 'T_' + values_1[0:values_1.index('.')] + '_' + values_2[0:values_2.index('.')]


def column(data, i):
    return [row[i] for row in data]


def get_stats(dir=''):
    data = [[] for x in range(16)]
    with open(dir + 'latencyStats500_1', 'r') as f:
        line = 1
        while line:
            line = f.readline()
            reset()
            increment()
            get_value(line, 'offload', data[increment()])
            get_value(line, 'HGW_Storage_Min_Threshold', data[increment()])
            get_value(line, 'HGW_Storage_Max_Threshold', data[increment()])
            get_value(line, 'HGW_Storage_Compression', data[increment()])
            get_value(line, 'HGW_Compression_Selection', data[increment()])
            get_value(line, 'HGW_Critical_Selection', data[increment()])
            get_value(line, 'Write latency', data[increment()])
            get_value(line, 'Read latency', data[increment()])
            get_value(line, 'Overall latency', data[increment()])
            get_value(line, 'Read Count', data[increment()])
            get_value(line, 'Write Count', data[increment()])
            get_value(line, 'Average Write latency', data[increment()])
            get_value(line, 'Average Read latency', data[increment()])
            get_value(line, 'Average Overall latency', data[increment()])

    with open(dir + 'SimulationTime500_1', 'r') as f:
        line = 1
        j = increment()
        while line:
            line = f.readline()
            if line != '':
                get_value(line, line[0:line.index('-')], data[j])

    sim = []
    for r in range(len(data[1])):
        sim.append(get_id(column(data[1:], r)))

    data[0] = sim

    reset()
    increment()
    increment()
    data[current()] = [float(v) for v in data[increment()]]
    data[current()] = [float(v) for v in data[increment()]]
    data[current()] = [float(v) for v in data[increment()]]
    data[current()] = [float(v) for v in data[increment()]]
    data[current()] = [float(v) for v in data[increment()]]
    data[current()] = [int(v) for v in data[increment()]]
    data[current()] = [int(v) for v in data[increment()]]
    data[current()] = [int(v) for v in data[increment()]]
    data[current()] = [int(v) for v in data[increment()]]
    data[current()] = [int(v) for v in data[increment()]]
    data[current()] = [int(v) for v in data[increment()]]
    data[current()] = [int(v) for v in data[increment()]]
    data[current()] = [int(v) for v in data[increment()]]
    data[current()] = [int(v) for v in data[increment()]]

    data = list(zip(*data))

    return data


def to_csv(dir=''):
    data = get_stats(dir)
    with open(dir + 'stats.csv', 'w+') as f:
        w = csv.writer(f, delimiter=';', quoting=csv.QUOTE_NONNUMERIC)
        w.writerows(data)

    return data


def from_csv(dir=''):
    data_ = []
    with open(dir + 'stats.csv') as file:
        reader = csv.reader(file, delimiter=';', quoting=csv.QUOTE_NONNUMERIC)
        for line in reader:
            data_.append(line)

    return data_


def filter(data, column, value):
    f_data = []
    for row in data:
        if row[column] == value:
            f_data.append(row)
    return f_data


def plot_test():
    N = 5
    men_means = (20, 35, 30, 35, 27)
    women_means = (25, 32, 34, 20, 25)

    ind = np.arange(N)
    width = 0.35
    plt.bar(ind, men_means, width, label='Men')
    plt.bar(ind + width, women_means, width,
            label='Women')

    plt.ylabel('Scores')
    plt.title('Scores by group and gender')

    plt.xticks(ind + width / 2, ('G1', 'G2', 'G3', 'G4', 'G5'))
    plt.legend(loc='best')
    plt.show()


def plot_line(data, x, y):
    vsims = list(set(column(data, 0)))
    vsims.sort()

    vs = list(set(column(data, x)))
    vs.sort()

    # fdata = filter(filter(data, 0, vsims[0]), 5, vs[0])
    for vsim in vsims:
        fdata = filter(data, 0, vsim)
        xs = column(fdata, x)
        ys = column(fdata, y)

        df = pd.DataFrame({'xs': xs, 'ys': ys})
        plt.plot('xs', 'ys', data=df, marker='o', markerfacecolor='grey', markersize=12, color='lightgrey')

        break

    # plt.legend()
    plt.show()


def plot_bar(data, y):
    f_data = data
    f_data = filter(f_data, 1, 'true')  # only offload

    vs_id = list(set(column(f_data, 0)))
    # vs_id.sort()

    vs2 = list(set(column(f_data, 2)))
    vs2.sort()
    vs3 = list(set(column(f_data, 3)))
    vs3.sort()
    vs4 = list(set(column(f_data, 4)))
    vs4.sort()
    vs5 = list(set(column(f_data, 5)))
    vs5.sort()
    vs6 = list(set(column(f_data, 6)))
    vs6.sort()

    f_data = filter(f_data, 4, vs4[0])
    f_data = filter(f_data, 5, vs5[0])
    f_data = filter(f_data, 6, vs6[0])
    f_data = column(f_data, y)

    fig = plt.figure()
    ax = fig.add_axes([0, 0, 1, 1])
    ax.bar(vs_id, f_data)
    plt.show()


def plot_bars(data, x, y, title, series, dir=''):
    f_data = filter(data, 1, 'true')  # only offload

    vs_0 = list(set(column(f_data, 0)))
    vs_0.sort()
    vs_2 = list(set(column(f_data, 2)))
    vs_2.sort()
    vs_3 = list(set(column(f_data, 3)))
    vs_3.sort()
    vs_4 = list(set(column(f_data, 4)))
    vs_4.sort()
    vs_5 = list(set(column(f_data, 5)))
    vs_5.sort()
    vs_6 = list(set(column(f_data, 6)))
    vs_6.sort()

    vs_switch = {
        4: vs_4,
        5: vs_5,
        6: vs_6,
    }

    f_vs_switch = {
        4: (5, 6),
        5: (4, 6),
        6: (4, 5),
    }

    vs = vs_switch.get(x)

    if vs is None:
        raise ValueError("invalid x")

    f_vs = f_vs_switch.get(x)
    # f_data = filter(f_data, 4, vs4[0])
    f_data = filter(f_data, f_vs[0], vs_switch.get(f_vs[0])[0])  # fix on the smallest value
    f_data = filter(f_data, f_vs[1], vs_switch.get(f_vs[1])[0])  # fix on the smallest value

    colors = ['lightgray', 'gray', 'darkgray', 'dimgray']
    patterns = ['.', '\\', '+', '|', '/', '-', '*', 'x', 'o', 'O']

    i = 0
    n = 0.0
    id = np.arange(len(vs_0))
    width = 0.25

    ticks = []
    for v in vs:
        xs = filter(f_data, x, v)
        ys = column(xs, y)
        ys = tuple(ys)

        # plt.bar(id + n, ys, width, label=v)
        # plt.bar(id + n, ys, width, label=v, color='white', edgecolor='black', hatch=patterns[i])
        plt.bar(id + n, ys, width, label=v, color=colors[i], edgecolor='black')

        ticks.append(n)
        n += width
        i += 1

    plt.title(title)
    plt.xticks(id + width / 2, vs_0)
    plt.ylabel(series)
    plt.legend(loc='best')
    plt.savefig('{}{} - {}.png'.format(dir, title, series))
    # plt.show()
    plt.clf()
    plt.cla()
    plt.close()


def plot_all(data, dir):
    plot_bars(data, 4, 7, 'HGW_Storage_Compression', 'Write latency', dir)
    plot_bars(data, 5, 7, 'HGW_Compression_Selection', 'Write latency', dir)
    plot_bars(data, 6, 7, 'HGW_Critical_Selection', 'Write latency', dir)
    plot_bars(data, 4, 8, 'HGW_Storage_Compression', 'Read latency', dir)
    plot_bars(data, 5, 8, 'HGW_Compression_Selection', 'Read latency', dir)
    plot_bars(data, 6, 8, 'HGW_Critical_Selection', 'Read latency', dir)
    plot_bars(data, 4, 9, 'HGW_Storage_Compression', 'Overall latency', dir)
    plot_bars(data, 5, 9, 'HGW_Compression_Selection', 'Overall latency', dir)
    plot_bars(data, 6, 9, 'HGW_Critical_Selection', 'Overall latency', dir)
    plot_bars(data, 4, 10, 'HGW_Storage_Compression', 'Read Count', dir)
    plot_bars(data, 5, 10, 'HGW_Compression_Selection', 'Read Count', dir)
    plot_bars(data, 6, 10, 'HGW_Critical_Selection', 'Read Count', dir)
    plot_bars(data, 4, 11, 'HGW_Storage_Compression', 'Write Count', dir)
    plot_bars(data, 5, 11, 'HGW_Compression_Selection', 'Write Count', dir)
    plot_bars(data, 6, 11, 'HGW_Critical_Selection', 'Write Count', dir)
    plot_bars(data, 4, 12, 'HGW_Storage_Compression', 'Average Write latency', dir)
    plot_bars(data, 5, 12, 'HGW_Compression_Selection', 'Average Write latency', dir)
    plot_bars(data, 6, 12, 'HGW_Critical_Selection', 'Average Write latency', dir)
    plot_bars(data, 4, 13, 'HGW_Storage_Compression', 'Average Read latency', dir)
    plot_bars(data, 5, 13, 'HGW_Compression_Selection', 'Average Read latency', dir)
    plot_bars(data, 6, 13, 'HGW_Critical_Selection', 'Average Read latency', dir)
    plot_bars(data, 4, 14, 'HGW_Storage_Compression', 'Average Overall latency', dir)
    plot_bars(data, 5, 14, 'HGW_Compression_Selection', 'Average Overall latency', dir)
    plot_bars(data, 6, 14, 'HGW_Critical_Selection', 'Average Overall latency', dir)
    plot_bars(data, 4, 15, 'HGW_Storage_Compression', 'Execution Time', dir)
    plot_bars(data, 5, 15, 'HGW_Compression_Selection', 'Execution Time', dir)
    plot_bars(data, 6, 15, 'HGW_Critical_Selection', 'Execution Time', dir)


def stats(dir='Stats/'):
    data = from_csv(dir) if os.path.isfile(dir + 'stats.csv') else to_csv(dir)


def get_pk(row):
    # "T_20_30";"false";20.0;30.0;60.0;0.1;0.9;875940;897240;1773180;5440;5440;161;165;163;71349
    return (row[1], row[2], row[3], row[4], row[5], row[6])


def get_data_avg(data_all):
    data_dict: dict = {}

    for data in data_all:
        for row in data:
            pk = get_pk(row)

            if pk in data_dict:
                data_dict[pk].append(row)
            else:
                data_dict[pk] = [row]

    data_avg = []
    for key in data_dict:
        rows = data_dict[key]

        row_avg = rows[0]
        for row in rows[1:]:
            row_avg[7] += row[7]
            row_avg[8] += row[8]
            row_avg[9] += row[9]
            row_avg[10] += row[10]
            row_avg[11] += row[11]
            row_avg[12] += row[12]
            row_avg[13] += row[13]
            row_avg[14] += row[14]
            row_avg[15] += row[15]

        rowCount = len(rows)
        row_avg[7] /= rowCount
        row_avg[8] /= rowCount
        row_avg[9] /= rowCount
        row_avg[10] /= rowCount
        row_avg[11] /= rowCount
        row_avg[12] /= rowCount
        row_avg[13] /= rowCount
        row_avg[14] /= rowCount
        row_avg[15] /= rowCount

        data_avg.append(row_avg)

    return data_avg


def get_data_all(dir='Stats/'):
    data_all = []
    for n in range(99):
        basedir = '{}{}/'.format(dir, n)
        if not os.path.isdir(basedir):
            break

        data = from_csv(basedir) if os.path.isfile(basedir + 'stats.csv') else to_csv(basedir)
        data_all.append(data)

    return data_all


def stats_avg(dir='Stats/'):
    data_all = get_data_all(dir)
    data = get_data_avg(data_all)
    plot_all(data, dir)


stats_avg()
