import numpy as np
import matplotlib.pyplot as plt


"""
cos (x + 0.5) - y - 2 = 0
sin y - 2x - 1 = 0
"""

zero = np.zeros(2)


def function(x):
    return np.array([(np.cos(x[0] + 0.5) - x[1] - 2), (np.sin(x[1]) - 2 * x[0] - 1)])


def f1_dx(x):
    return -np.sin(x + 0.5)


def f1_dy(y):
    return -1


def f2_dx(x):
    return -2


def f2_dy(y):
    return np.cos(y)


def matrix_jacobi(x):
    # x, y = x[0], x[1]
    return np.array([[f1_dx(x[0]), f1_dy(x[1])], [f2_dx(x[0]), f2_dy(x[1])], ])


def vector_length(x1, x2):
    return np.linalg.norm(x1 - x2)


def newton(x0, eps):
    i = 0
    x = x0
    x0 = 100000
    while vector_length(x, x0) > eps:
        i += 1
        x0 = x
        x = x - np.linalg.inv(matrix_jacobi(x)).dot(function(x))
        if i > 400:
            print("Error: Number of iterations above the limit")
            print("!!Diverge!!")
            return 0

    print('x_k - x_k1:', vector_length(x, x0))
    print('||F(x_k) - F(x)||:', vector_length(function(x), zero))
    print('Point:', x[0], x[1])
    print('Iterations:', i)


def mod_newton(x_init, eps):
    i = 0
    x_prev = 100000
    x = x_init
    while vector_length(x, x_prev) > eps:
        i += 1
        x_prev = x
        x = x - np.linalg.inv(matrix_jacobi(x_init)).dot(function(x))

        if i > 400:
            print("Error: Number of iterations above the limit")
            print("!!Diverge!!")
            break

    print('x_k - x_k1:', vector_length(x, x_prev))
    print('||F(x_k) - F(x)||:', vector_length(function(x), zero))
    print('Point:', x[0], x[1])
    print('Iterations:', i)


def display_graph():
    def f1(x):
        return np.cos(x + 0.5) - 2

    def f2(y):
        return (np.sin(y) - 1) / 2

    x = np.linspace(-1, -0.75, 100)
    y = np.linspace(-1, -1.25, 100)

    plt.plot(x, f1(x), c='g')
    plt.plot(f2(y), y, c='r')
    plt.grid()
    plt.show()


if __name__ == '__main__':
    # display_graph()

    x = float(input('Enter x: '))
    y = float(input('Enter y: '))
    eps = 0.00001
    x0 = np.array([x, y], dtype=np.float64)

    print('Newton')
    newton(x0, eps)

    print('---------------------------------------\nModified Newton')
    mod_newton(x0, eps)
