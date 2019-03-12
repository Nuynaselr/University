//
// Created by np on 09.03.19.
//
#import <iostream>

using namespace std;
/*
 * принятие сообщений
 * массив команд
 * проверка на правильность ввода
 * вывод ошибки, если ввод не верен
 * возвращение правильной команды
 */

#ifndef SERVER_LISTEN_H
#define SERVER_LISTEN_H
class Listen{
private:
    char[] message = new char[1024];
    char[] array_themes = ["Maia", "Breeze", "WhiteOnBlack", "BlackOnWhite"];

    char[] command_change_color = "konsoleprofile colors=";

    void delete_space(iostream fin){
        while (fin.peek() == CR || fin.peek() == EOL || fin.peek() == TAB || fin.peek() == SPACE) {
            fin.get();
        }
    }

    void check_command(){
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < sizeof(this->message); i++) {
                if (this->message[i] != )
            }
        }
    }

public:
    void enter_message(iostream cin){
        getline(this->delete_space(cin), message);
    }

};
#endif //SERVER_LISTEN_H
