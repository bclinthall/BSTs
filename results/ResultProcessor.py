import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import os

def make_csv():
    tree_types = ['Splay', 'Tango', 'Treap', 'Vanilla']
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

labels = {'Splay':'Splay', 'Tango': 'Tango', 'Treap': 'Treap', 'Vanilla': 'Perfect Static'}

def plot_access_type(access_type='bitReversal', maxLgN=25, treeTypes=['Splay', 'Vanilla', 'Tango', 'Treap'], plot_what='OpsPerAccess', scale=-1, perloglog=False):
    n = 1 << maxLgN
    access_df = df.loc[df.sequenceType == access_type]
    access_df = access_df.loc[access_df.index < n]
    tree_dfs = {treeType: pd.DataFrame(pd.DataFrame(access_df.loc[access_df.treeType == treeType])) for treeType in ['Splay', 'Vanilla', 'Tango', 'Treap']}


    fig, ax = plt.subplots()
    # plt.plot(tango.reset_index().OpsPerAccess, "ro")
    # plt.plot(splay.reset_index().OpsPerAccess, "bo")

    for treeType in treeTypes:
        tree_df = tree_dfs[treeType]
        ax.plot(tree_df.index, tree_df[plot_what], label=labels[treeType])
    ax.set_xlabel('nodes')
    ax.set_ylabel('BST operations per access')
    if scale != -1:
        plot_scaled(tree_dfs['Tango'], scale, ax, 'Tango')
    if perloglog:
        plot_perloglog(tree_dfs['Tango'], scale, ax, 'Tango')
    ax.legend()


def plot_perloglog(df, scale, ax, tree_type):
    df['ops_scaled'] = scale * df['OpsPerAccess'] / np.log(np.log(df.index));
    ax.plot(df.index, df['ops_scaled'], label=tree_type + ' x ' + str(scale) + '/ log log n' )


def plot_scaled(df, scale, ax, tree_type, plot_what='OpsPerAccess'):
    df[plot_what + '_scaled'] = df[plot_what] * scale;
    ax.plot(df.index, df[plot_what + '_scaled'], label=tree_type + ' x ' + str(scale))



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
