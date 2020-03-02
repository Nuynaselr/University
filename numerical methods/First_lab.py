# x^3 - 4.4x^2 + 4.7x - 1.1 = 0
import numpy as np
import matplotlib.pyplot as plt

x = 0


def search_function(x):
    return (x*x*x) - 4.4*x*x + 4.7*x - 1.1


def methods_half_division(left_border, right_border, eps):
    number_of_iteration = 0
    midpoint = 0
    while (right_border - left_border) > 2*eps:
        number_of_iteration += 1
        midpoint = (right_border + left_border)/2
        if search_function(left_border) * search_function(midpoint) > 0:
            left_border = midpoint
        else:
            right_border = midpoint

    print('Number of iteration: ', number_of_iteration)
    print('f(x) = ', search_function(midpoint))

    return midpoint


def derivative_of_function(x):
    return (3 * x * x) - 4.4 * 2 * x + 4.7


def hybrid_methods(left_border, right_border, eps):
    number_of_iteration = 0
    x = left_border
    x_ = x - search_function(x) / derivative_of_function(x)
    while np.abs(x-x_) >= eps:
        number_of_iteration += 1
        while search_function(x_) < search_function(x):
            x_ = 0.5 * (x + x_)
        else:
            x = x_
            x_ = x - search_function(x) / derivative_of_function(x)

    print('Number of iteration: ', number_of_iteration)
    print('f(x) = ', search_function(x))
    return x





if __name__ == '__main__':
    eps = 0.0001

    left_border = float(input('Enter left boarder: '))
    right_border = float(input('Enter right border: '))

    print('Method half division: ')
    answer = methods_half_division(left_border, right_border, eps)
    print('Value x: ', answer)

    print('-----------------------------------------\nHybrid_method: ')
    answer = 0
    answer = hybrid_methods(left_border, right_border, eps)
    print('Value x: ', answer)

    plt.plot(np.linspace(0, 3, 50), [search_function(x) for x in np.linspace(0, 3, 50)])
    plt.grid(True)
    plt.show()