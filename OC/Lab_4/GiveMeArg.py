from subprocess import call
from sys import argv
import os


class CheckArg:
    def __init__(self, arg):
        self.buffer_size = 256
        self.arg = arg

    def start(self):
        if len(self.arg) == 2 and self.arg[1] == '--help':
            self.help()

        elif self.arg[1] == 'info':
            word = self.arg[2] if len(self.arg) == 3 else '.'
            if self.chekcMe(str(self.arg[2])):
                self.info(self.arg[2], word)
            else:
                print('Unknown command. Write "python GiveMeArg.py --help"')

        elif self.arg[1] == 'chmod':
            self.chmod()

        elif self.arg[1] == 'cp':
            self.cp()

        else:
            print('Unknown command. Write "python GiveMeArg.py --help"')

    def cp(self):
        src = self.arg[2]
        dst = self.arg[3]
        with open(src, 'r') as sender:
            with open(dst, 'w') as receiver:
                sender.seek(0, os.SEEK_END)
                file_length = sender.tell()
                sender.seek(0, os.SEEK_SET)
                while sender.tell() < file_length:
                    small_buff = sender.read(self.buffer_size)
                    receiver.write(small_buff)

    def info(self, arguments, file):
        try:
            call(['ls', arguments, file])
        except Exception as ex:
            print(ex)

    def chmod(self):
        rights = int(self.arg[3], 8)
        os.chmod(self.arg[2], rights)

    def help(self):
        with open('help.txt', 'r') as file:
            print(file.read())

    def chekcMe(self, argument):
        row = '-lahf'
        for word in argument:
            if word not in row:
                return False
        return True


if __name__ == '__main__':
    test = CheckArg(argv)
    test.start()
