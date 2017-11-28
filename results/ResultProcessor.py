import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

df = pd.DataFrame.from_csv("log.csv")

df['OpsPerAccess'] = df['operations'] / df['accesses']
df['perLog'] = df['OpsPerAccess']  / np.log2(df['nodes'])
df['perLogSq'] = df['perLog']  / np.log2(df['nodes'])

df['OpsPerAccess'].plot()
plt.plot(df['nodes'], df['OpsPerAccess'])
plt.plot(df['nodes'], 2.89 * np.log2(df['nodes']) - 7)
graphPerLog(df.nodes, df.OpsPerAccess, 7)



plt.plot(df['nodes'], df['perLog'])
plt.plot(df['nodes'], df['perLogSq'])


def graphPerLog(xs, series, constTerm):
    plt.plot(xs, (series + constTerm) / np.log2(xs), label=constTerm)


for c in np.arange(-10, 10, 1):
    graphPerLog(df.nodes, df.OpsPerAccess, c)

graphPerLog(df.nodes, df.OpsPerAccess, 7)



nums = np.arange(1, 1000, 1)
log = np.log2(nums)
plt.plot(nums, (log - 17) / log)

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

for sigma in np.linspace(1, 5, 10):
    plotNormal(sigma)
