#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <zconf.h>
#include <cstdlib>
#include <cstdio>

char buf[1024];

int main()
{
    int sock;
    struct sockaddr_in addr;

    int counter = 10;
    int data;

    // созднаие сокета
    sock = socket(AF_INET, SOCK_STREAM, 0);
    //проверка создан ли сокет
    if(sock < 0)
    {
        perror("socket");
        exit(1);
    }
    //задание параметров сокету
    addr.sin_family = AF_INET;
    addr.sin_port = htons(3425); // или любой другой порт...
    addr.sin_addr.s_addr = htonl(INADDR_LOOPBACK);

    //есть ли соединение
    if(connect(sock, (struct sockaddr *)&addr, sizeof(addr)) < 0)
    {
        perror("connect");
        exit(2);
    }

    data = recv(sock, buf, 1024, 0);
    printf("%d", data);
    printf(buf);




    close(sock);

    return 0;
}