import os
import cv2
import numpy
import math

rotate_matrix = [[0, 0],
                 [0, 0]]

video = cv2.VideoCapture('D.mp4')
extension_image = ('.jpeg', '.png')
dir_saved_img_win = 'C:\\Users\\Nikita\\Documents\\GitHub\\University\\SummerPractice\\SaveImage'
dir_saved_img_unix = '/home/np/University/SummerPractice/SaveImage'

# Directory for saved image
os.chdir(dir_saved_img_unix)

# extension image
extension_image = extension_image[0]


def create_rotate_matrix(angle):
    rotate_matrix[0][0] = numpy.cos(angle)
    rotate_matrix[0][1] = -numpy.sin(angle)

    rotate_matrix[1][0] = numpy.sin(angle)
    rotate_matrix[1][1] = numpy.cos(angle)


def save_image():
    number_image = 1
    while video.isOpened():
        ret, frame = video.read()

        # show video
        cv2.imshow('frame', frame)

        # save image in dir_saved_img
        cv2.imwrite(str(number_image) + extension_image, frame)
        number_image += 1

        if cv2.waitKey(1) & 0xFF == ord('q'):
            break

    video.release()
    cv2.destroyAllWindows()


def cubic_polynomial(p, p2, p3, p4, x):
    answer = 0.5*(p4 - p + 3*(p2 - p3)) * (x**3) + (p - 2.5*p2 + 2*p3 - 0.5*p4) * (x**2) + 0.5 * (p3 - p) * x + p2
    return answer


def rotate_bicubic(angle):
    source_image = cv2.imread('/home/np/University/SummerPractice/Rick.jpg')

    angle_rad = math.radians(angle)
    height, width, chanel = source_image.shape

    dest_image = numpy.zeros((height, width, 3), numpy.uint8)

    half_width = width // 2
    half_height = height // 2

    radius = math.sqrt(half_height ** 2 + half_width ** 2)
    const_b = math.atan2(half_height, half_width)

    x, y, x_fract, y_fract, c0, c1, c2, c3, c = 0.0
    c00, c10, c20, c30, c01, c11, c21, c31, c02, c12, c22, c32, c03, c13, c23, c33 = []


def rotate_image(angle):
    source_image = cv2.imread('/home/np/University/SummerPractice/Rick.jpg')

    angle_rad = math.radians(angle)
    height, width, chanel = source_image.shape

    dest_image = numpy.zeros((height, width, 3), numpy.uint8)

    half_width = width // 2
    half_height = height // 2

    radius = math.sqrt(half_height ** 2 + half_width ** 2)
    const_b = math.atan2(half_height, half_width)

    for i in range(height):
        for j in range(width):
            big_i = int(
                (i - half_height) * math.cos(angle_rad) - (j - half_width) * math.sin(angle_rad)
                + radius * math.cos(const_b))
            big_j = int(
                (i - half_height) * math.sin(angle_rad) + (j - half_width) * math.cos(angle_rad)
                + radius * math.cos(const_b))

            if height > big_i >= 0 and width > big_j >= 0:
                dest_image[i][j] = source_image[big_i][big_j]

    cv2.imshow("Image", dest_image)
    cv2.waitKey(0)
    cv2.destroyAllWindows()
    return True


if __name__ == '__main__':

    # source_image = cv2.imread('C:\\Users\\Nikita\\Documents\\GitHub\\University\\SummerPractice\\Rick.jpg')

    rotate_image(45)

    # (h, w, d) = aaa.shape
    # center = (w // 2, h // 2)
    # M = cv2.getRotationMatrix2D(center, 90, 1.0)
    # rotated = cv2.warpAffine(aaa, M, (w, h))
    # cv2.imshow("Image", rotated)
    # cv2.waitKey(0)
    # cv2.destroyAllWindows()

    # aaa = cv2.imread('C:\\Users\\Nikita\\Documents\\GitHub\\University\\SummerPractice\\Rick.jpg')
    # # cv2.imshow("Image", aaa)
    # # cv2.waitKey(0)
    # # cv2.destroyAllWindows()
    #
    # print(aaa[0])
