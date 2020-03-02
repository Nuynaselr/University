import numpy as np
import matplotlib.pyplot as plt


"""
cos (x + 0.5) - y = 2
sin y - 2x = 1
"""


def function(x):
    return np.array([(np.cos(x[0] + 0.5) - x[1] - 2), (np.sin(x[1]) - 2 * x[0] - 1)])


def f1(x):
    return np.cos(x + 0.5) - y - 2


def f2(y):
    return np.sin(y) - 2 * x - 1


def display_graph():
    x = np.linspace(-3, 3, 100)
    y = np.linspace(-3, 3, 100)

    plt.plot(x, f1(x), c='g')
    plt.plot(f2(y), y, c='r')
    plt.grid()
    plt.show()


if __name__ == '__main__':
    a = float(input('Enter x: '))
    b = float(input('Enter y: '))
    eps = 0.001
    x0 = np.array([a, b], dtype=np.float64)

    display_graph()
    print(function(x0))