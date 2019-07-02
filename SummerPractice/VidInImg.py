import cv2
import os

video = cv2.VideoCapture('D.mp4')
extension_image = ('.jpeg', '.png')
dir_saved_img = 'C:\\Users\\Nikita\\Documents\\GitHub\\University\\SummerPractice\\SaveImage'

# Directory for saved image
os.chdir(dir_saved_img)

# extension image
extension_image = extension_image[0]

def save_image():
    number_image = 1
    while (video.isOpened()):
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

if __name__ == '__main__':
    # aaa = cv2.imread('C:\\Users\\Nikita\\Documents\\GitHub\\University\\SummerPractice\\Rick.jpg')
    # # cv2.imshow("Image", aaa)
    # # cv2.waitKey(0)
    # # cv2.destroyAllWindows()
    #
    # print(aaa[0])

    save_image()