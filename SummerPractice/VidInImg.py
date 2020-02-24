import os
import cv2
import numpy
import math


extension_image = ('.jpeg', '.png')
dir_saved_img_win = '\\SaveImage'
dir_saved_img_unix = '/SaveImage'

# Directory for saved image

os.chdir(os.getcwd() + dir_saved_img_win)

# extension image
extension_image = extension_image[0]

number_frame = 1


def cut_video_with_corner(path_file, angle):
    video = cv2.VideoCapture(path_file)

    while video.isOpened():
        ret, frame = video.read()
        rotate_bicubic(frame, angle)

    video.release()
    cv2.destroyAllWindows()


def cubic_polynomial(a, b, c, d, x):
    a = float(a)
    b = float(b)
    c = float(c)
    d = float(d)
    return (d - a + 3.0 * (b - c))/2.0 * (x**3) + (a - 2.5 * b + 2 * c - d/2) * (x * x) + (c - a)/2 * x + b


def rotate_bicubic(image, angle):
    source_image = image
    
    angle_rad = math.radians(angle)
    height, width, chanel = source_image.shape
    dest_image = numpy.ones((height, width, 3), numpy.uint8)
    half_width = width // 2
    half_height = height // 2
    
    radius = math.sqrt(half_height * half_height + half_width * half_width)
    const_b = math.atan2(half_height, half_width)

    cos = math.cos(angle_rad)
    sin = math.sin(angle_rad)
    rcos = radius * math.cos(const_b)
    rsin = radius * math.sin(const_b)
    for i in range(-half_height, half_height):
        for j in range(-half_width, half_width):
            x = i * cos - j * sin + rsin
            y = i * sin + j * cos + rcos
            x_fract = x - int(x)
            y_fract = y - int(y)
            x = int(x)
            y = int(y)

            pixel = numpy.ones(3, numpy.uint8)
            if (height > x > 0) and (width > y > 0):
                c00 = source_image[max(x - 1, 0), max(y - 1, 0)]
                c10 = source_image[x, max(y - 1, 0)]
                c20 = source_image[min(x + 1, height - 1), max(y - 1, 0)]
                c30 = source_image[min(x + 2, height - 1), max(y - 1, 0)]
                c01 = source_image[max(x - 1, 0), y]
                c11 = source_image[x, y]
                c21 = source_image[min(x + 1, height - 1), y]
                c31 = source_image[min(x + 2, height - 1), y]
                c02 = source_image[max(x - 1, 0), min(y + 1, width - 1)]
                c12 = source_image[x, min(y + 1, width - 1)]
                c22 = source_image[min(x + 1, height - 1), min(y + 1, width - 1)]
                c32 = source_image[min(x + 2, height - 1), min(y + 1, width - 1)]
                c03 = source_image[max(x - 1, 0), min(y + 2, width - 1)]
                c13 = source_image[x, min(y + 2, width - 1)]
                c23 = source_image[min(x + 1, height - 1), min(y + 2, width - 1)]
                c33 = source_image[min(x + 2, height - 1), min(y + 2, width - 1)]
                for coordinate in range(3):
                    c0 = cubic_polynomial(c00[coordinate], c10[coordinate], c20[coordinate], c30[coordinate], x_fract)
                    c1 = cubic_polynomial(c01[coordinate], c11[coordinate], c21[coordinate], c31[coordinate], x_fract)
                    c2 = cubic_polynomial(c02[coordinate], c12[coordinate], c22[coordinate], c32[coordinate], x_fract)
                    c3 = cubic_polynomial(c03[coordinate], c13[coordinate], c23[coordinate], c33[coordinate], x_fract)
                    c = cubic_polynomial(c0, c1, c2, c3, y_fract)
                    pixel[coordinate] = c
                    if pixel[coordinate] > 255:
                        pixel[coordinate] = 255
                    elif pixel[coordinate] < 0:
                        pixel[coordinate] = 0
                dest_image[i + half_height][j + half_width] = pixel
    cv2.imwrite('frame' + str(number_frame) + extension_image, dest_image)


def rotate_image(image, angle):
    source_image = angle

    angle_rad = math.radians(angle)
    height, width, chanel = source_image.shape

    dest_image = numpy.zeros((height, width, 3), numpy.uint8)

    half_width = width // 2
    half_height = height // 2

    radius = math.sqrt(half_height * half_height + half_width * half_width)
    const_b = math.atan2(half_height, half_width)

    cos = math.cos(angle_rad)
    sin = math.sin(angle_rad)

    rcos = radius * math.cos(const_b)
    rsin = radius * math.sin(const_b)

    for i in range(height):
        for j in range(width):
            big_i = int((i - half_width) * cos - (j - half_height) * sin + rcos)
            big_j = int((i - half_width) * sin + (j - half_height) * cos + rsin)

            if height > big_i >= 0 and width > big_j >= 0:
                dest_image[i][j] = source_image[big_i][big_j]

    cv2.imwrite('Image.jpg', dest_image)

    cv2.waitKey(360)
    cv2.destroyAllWindows()
    return True


if __name__ == '__main__':
    path_file = 'D.mp4'
    angle = 45
    # cut_video_with_corner(path_file, angle)

    image = cv2.imread('C:\\Users\\Nikita\\Documents\\GitHub\\University\\SummerPractice\\Rick.jpg')
    rotate_bicubic(image, angle)
