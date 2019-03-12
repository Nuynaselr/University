#include <iostream>
#include "ReadFile.h"

using namespace std;

/*double machineEps(double eps)
{
    while(1 + eps != 1)
    {
        eps /= 2;
    }
    return eps;
}*/

bool check_div_by_zero(int number){
    if (number == 0){
        printf("%s", "Error: division on zero");
        return true;
    }
}

int search_number(int * array_integer){
    // (-53/a + d-4*a)/(1 +a*b);
    if(check_div_by_zero(array_integer[0]) && check_div_by_zero(1 + array_integer[0]*array_integer[1])) {
        return (-53 / array_integer[0] + array_integer[2] - 4 * array_integer[0]) /
               (1 + array_integer[0] * array_integer[1]);
    }

}

int main() {

    char * file_name = "/home/np/Рабочий стол/LAB_SISPRO/Lab_1/Cplusplus/data.txt";
    ReadFile * re_file = new ReadFile();
    re_file->create_file(file_name);

    re_file->output_data();

    int * array_number;

    re_file->ret_array_integer(array_number);

    printf("%s", "Array numbers\n");
    for(int i = 0; i < 3; i++){
        cout << array_number[i] << " ";
    }
    printf("%s", "\n");

    cout << "Result: " << search_number(array_number) << endl;

    delete [] array_number;


    return 0;
}