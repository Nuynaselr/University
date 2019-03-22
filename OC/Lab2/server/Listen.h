//
// Created by np on 09.03.19.
//
#include <iostream>
#include <fstream>
#include <string>

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
    bool flag_check_true_enter = false;

    string message;
    string array_themes[4] = { "Maia", "Breeze", "WhiteOnBlack", "BlackOnWhite"};

    string list_check_command[7] = {"1", "2", "3", "4", "start_color", "close", "close_server"};

    string command_change_color = "konsoleprofile colors=";

    bool check_true_command(string mess){
        for (int i = 0; i < 7; i++){
            if (mess == this->list_check_command[i]){
                this->flag_check_true_enter = true;
                return true;
            } else if (i == 6  && !this->flag_check_true_enter){
                return false;
            }
        }
    }

public:
    Listen(){}

    void enter_message(){
        string mess;
        cin >> mess;

        this->message = mess;
        while(!this->check_true_command(this->message)){
            cout << "Error: incorrect enter. Check entered command.\n"
                    "Available command:\n"
                    " help - command to call for help\n"
                    " star_color - command for making initial color\n"
                    " close - close client\n"
                    " close_server - close server\n"
                    " available colors (only a number is required):\n "
                    "   1 Maia\n"
                    "   2 Breeze\n"
                    "   3 WhiteOnBlack\n"
                    "   4 BlackOnWhite" << endl;
            enter_message();
        }
    }

    int get_size_message(){
        return sizeof(this->message.c_str());
    }

    void get_list_color(){
        cout << "List color: " << endl;
        for(int i = 0; i < 4; i++){
            cout << i + 1 << " " + this->array_themes[i] << endl;
        }
    }

    string get_message(){
       return this->message;
    }

    string get_command_color(){
        string command = this->command_change_color + array_themes[atoi(this->message.c_str()) - 1];
        return command;
    }

    void set_color(string row_color){
        this->message = row_color;
    }

    void help(){
        cout << " help - command to call for help\n"
                " star_color - command for making initial color\n"
                " close - close client\n"
                " close_server - close server\n"
                " available colors (only a number is required):\n "
                "   1 Maia\n"
                "   2 Breeze\n"
                "   3 WhiteOnBlack\n"
                "   4 BlackOnWhite" << endl;
    }


};
#endif //SERVER_LISTEN_H
