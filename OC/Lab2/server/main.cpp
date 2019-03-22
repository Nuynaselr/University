#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <cstdio>
#include <zconf.h>
#include <cstdlib>
#include <unistd.h>
#include <fcntl.h>

#include "Listen.h"
#include <string>
#include <set>
#include <algorithm>

using namespace std;

//char message[] = "Hello gggghere!\n";
//char buf[sizeof(message)];

int main() {

    string message;
    Listen * test = new Listen();
    test->get_list_color();

    int sock, listener; // сокет, слушатель
    struct sockaddr_in addr;
    char buf[1024]; // буфер для сообщения
    int bytes_read;

    listener = socket(AF_INET, SOCK_STREAM, 0); // слушатель клиентов
    if (listener < 0) {
        perror("socket");
        exit(1);
    }

    addr.sin_family = AF_INET;
    addr.sin_port = htons(3425);
    addr.sin_addr.s_addr = htonl(INADDR_ANY);



    //наайден ли клиент
    if (bind(listener, (struct sockaddr *) &addr, sizeof(addr)) < 0) {
        perror("bind");
        exit(2);
    }

    listen(listener, 1);

    bool flag_close = false;

    while (!flag_close) {
        sock = accept(listener, NULL, NULL);



        if (sock < 0) {
            perror("accept");
            exit(3);
        }

        while (!flag_close) {
            //bytes_read = recv(sock, buf, 1024, 0);
            if (bytes_read <= 0) break;
            cout << "Well done!! Enter your sms" << endl;

            test->enter_message();

            cout << test->get_command_color() << endl;
            if(test->get_message() == "close"){
                send(sock, test->get_message().c_str(), 1024, 0);
            } else if(test->get_message() == "start_color"){
                test->set_color("1");
                send(sock, test->get_command_color().c_str(), 1024, 0);
            } else if (test->get_message() == "close_server") {
                flag_close = true;
                close(sock);
            } else {
                send(sock, test->get_command_color().c_str(), 1024, 0);
            }

        }
        close(sock);
    }

    delete test;

    return 0;
}