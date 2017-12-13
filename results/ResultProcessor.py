import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import os


def make_csv():
    tree_types = ['Splay', 'Tango', 'Treap']
    files = os.listdir()
    lines = ['"treeType","sequenceType","nodes","accesses","operations","time"\n']
    for tree_type in tree_types:
        for file in files:
            if file.startswith(tree_type):
                with open(file, 'r') as fp:
                    lines.append(fp.read() + '\n')
    with open("log.csv", "w") as fp:
        fp.writelines(lines)

make_csv()

df = pd.DataFrame.from_csv("log.csv")

df = df.reset_index()
df = df.set_index("nodes")
df = df.sort_index()

df['OpsPerAccess'] = df['operations'] / df['accesses']
df['perLog'] = df['OpsPerAccess']  / np.log2(df.index)
df['perLogLog'] = df['OpsPerAccess']  / np.log2(np.log2(df.index))


def plot_access_type(access_type):
    access_df = df.loc[df.sequenceType == access_type]
    splay = access_df.loc[access_df.treeType == "Splay"]
    treap = pd.DataFrame(access_df.loc[access_df.treeType == "Treap"])
    tango = pd.DataFrame(access_df.loc[access_df.treeType == "Tango"])
    tango['overSplay'] = tango['OpsPerAccess'] / splay['OpsPerAccess']

    # plt.plot(tango.reset_index().OpsPerAccess, "ro")
    # plt.plot(splay.reset_index().OpsPerAccess, "bo")

    plt.plot(tango.index, tango.OpsPerAccess)
    plt.plot(splay.index, splay.OpsPerAccess)
    plt.plot(treap.index, treap.OpsPerAccess)
    # plt.plot(tango.index, tango.perLog)
    # plt.plot(splay.index, splay.perLog)
    # plt.plot(tango.index, tango.perLogLog)
    # plt.plot(splay.index, splay.perLogLog)
    # plt.plot(tango.index, tango.overSplay)
    # plt.plot(splay.index, splay.time, "ro")
    # plt.plot(tango.index, tango.time, "bo")

def graphPerLog(xs, series, constTerm):
    plt.plot(xs, (series + constTerm) / np.log2(xs), label=constTerm)


# for c in np.arange(-10, 10, 1):
#     graphPerLog(df.nodes, df.OpsPerAccess, c)
#
# graphPerLog(df.nodes, df.OpsPerAccess, 7)



#nums = np.arange(1, 1000, 1)
#log = np.log2(nums)
#plt.plot(nums, (log - 17) / log)

def plotNormal(sigma):
    """
    :param sigma: standard deviation
    """
    xs = np.linspace(-10, 10, 11)
    print(xs)
    mu = 0 # mean
    ys = np.floor(1000/(sigma * np.sqrt(2 * np.pi)) \
         * np.exp(-(xs - mu)**2 / (2 * sigma**2)))
    print(sum(ys))
    plt.plot(xs, ys, linewidth=2)
    plt.show()

#for sigma in np.linspace(1, 5, 10):
#    plotNormal(sigma)
