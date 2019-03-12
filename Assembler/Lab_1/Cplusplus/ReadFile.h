//
// Created by np on 03.03.19.
//
#include <iostream>
#include <fstream>
#include <string>

#ifndef CPLUSPLUS_READFILE_H
#define CPLUSPLUS_READFILE_H

using namespace std;

class ReadFile {
private:
    string count_number = "";
    string row_data = "";

    bool checkNumber(char el)
    {
        if (int(el) >= 48 && int(el) <= 57 || el == '.' || el == '-') {
            return true;
        }
        return false;
    }

public:
    ReadFile(){
    }

    void create_file(char * file_name){
        ifstream file (file_name, ios_base::in);
        if (file.is_open()) {
            getline(file, this->count_number);
            getline(file, this->row_data);
        }else{
            printf("%s", "Error: file is not found");
        }
        /*
        while (!fin.is_open()) {
        cout << "Error: Please enter correct name file" << endl << "Enter name file: ";
        getline(cin, fileName);
        fin.open(fileName.c_str());
        }
         */
    }

    void ret_array_integer(int * & array_int){
        int count_array = atoi(this->count_number.c_str());
        array_int = new int[count_array];
        string number;
        int j = 0;
        for (int i = 0; i < row_data.length(); i++) {
            if (checkNumber(row_data[i])) {
                number += row_data[i];
                if (!checkNumber(row_data[i + 1]))
                {
                    array_int[j] = atoi(number.c_str());
                    number = "";
                    ++j;
                }
            }
        }
    }

    void output_data(){
        cout << "Data in file: \n" << this->count_number << "\n" << this->row_data << endl;
    }
};


#endif //CPLUSPLUS_READFILE_H
