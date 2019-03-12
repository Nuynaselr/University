#include <sys/types.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <cstdio>
#include <zconf.h>
#include <cstdlib>
#include <unistd.h>
#include <fcntl.h>

char message[] = "Hello gggghere!\n";
char buf[sizeof(message)];

int main() {
    int sock, listener; // сокет, слушатель
    struct sockaddr_in addr;
    char buf[1024]; // буфер для сообщения
    int bytes_read;

    listener = socket(AF_INET, SOCK_STREAM, 0); // слушатель клиентов
    if (listener < 0) {
        perror("socket");
        exit(1);
    }

    fcntl(listener, F_SETFL, O_NONBLOCK);

    addr.sin_family = AF_INET;
    addr.sin_port = htons(3425);
    addr.sin_addr.s_addr = htonl(INADDR_ANY);



    //наайден ли клиент
    if (bind(listener, (struct sockaddr *) &addr, sizeof(addr)) < 0) {
        perror("bind");
        exit(2);
    }

    listen(listener, 1);

    while (1) {
        sock = accept(listener, NULL, NULL);
        if (sock < 0) {
            perror("accept");
            exit(3);
        }

        while (1) {
            //bytes_read = recv(sock, buf, 1024, 0);
            if (bytes_read <= 0) break;
            send(sock, message, 1024, 0);
        }

        close(sock);
    }



    return 0;
}