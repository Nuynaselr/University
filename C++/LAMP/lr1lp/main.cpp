#include <iostream>
#include <fstream>
#include <vector>
#include <cmath>

using namespace std;

bool checkNumber(char el) // проверка является ли символ числом
{
    if (int(el) >= 48 && int(el) <= 57 || el == '.' || el == '-') {
        return true;
    }
    return false;
}

void transform(string s, vector<double> & vecNum) // преобразования числа из string в int
{
    string number;
    int j = 0;
    for (int i = 0; i < s.length(); i++) {
        if (checkNumber(s[i])) {
            number += s[i];
            if (!checkNumber(s[i + 1]))
            {
                vecNum.push_back(strtod(number.c_str(),NULL));
                number = "";
                j++;
            }
        }
    }
}

void calc(vector<double> &arrA, vector<double>& arrB, vector<double> &result, double eps) // расчет по формуле
{
    for (int i = 0; i < arrA.size(); i++) {
        if (arrA[i] > arrB[i]) {
            if (abs(arrB[i]) < eps)
            {
                result.push_back(-9223372036854775808.0);
            }
            else {
                result.push_back(arrA[i] / arrB[i] + 10);
            }
        }
        else if (abs(arrA[i] - arrB[i]) < eps) {
            result.push_back(51);
        }
        else if (arrA[i] < arrB[i]) {
            if (abs(arrA[i]) < eps)
            {
                result.push_back(-9223372036854775808.0);
            }
            else {
                result.push_back((arrA[i] * arrB[i] - 4) / arrA[i]);
            }
        }
    }
}

void op(vector<double> &arrA, vector<double>& arrB, vector<double>& result) // вывод информации и сохранение в файл
{
    ofstream fout("OUTPUT.txt");
    for (int i = 0; i < result.size(); i++) {
        if (result[i] == -9223372036854775808.0)
        {
            fout << "Error: DivisionByZero" << endl;
            cout << "a = " << arrA[i] << " | b = " << arrB[i] << " | Error: DivisionByZero" << endl;
        }
        else
        {
            fout << result[i] << endl;
            cout << "a = " << arrA[i] << " | b = " << arrB[i] << " | result = " << result[i] << endl;
        }
    }
    fout.close();
}

double machineEps(double eps) {
    while (1 + eps != 1) {
        eps /= 2;
    }
    return eps;
} // eps для сравнивания с нулем

void fileOpen(ifstream &fin) {
    string fileName;
    cout << "Enter name file: ";
    getline(cin, fileName);

    fin.open(fileName);
    while (!fin.is_open()) {
        cout << "Error: Please enter correct name file" << endl << "Enter name file: ";
        getline(cin, fileName);
        fin.open(fileName.c_str());
    }

} //открытие файла

string repeat(string rep) //вопрос повтора
{
    if (rep == "Y" || rep == "y") {
        return rep;
    }
    else if (rep == "N" || rep == "n") {
        return rep;
    }
    else
    {
        cout << "Error: incorrect data\nRepeat program?(Y/N): ";
        getline(cin, rep);
        rep = repeat(rep);
    }
}

int main()
{
    double eps = 1.0;
    string rep;
    ifstream fin;
    string arrA;
    string arrB;
    vector<double> numA;
    vector<double> numB;
    vector<double> result;

    do {
        fileOpen(fin);
        if (!fin.eof()) {

            getline(fin, arrA);
            getline(fin, arrB);

            transform(arrA, numA);
            transform(arrB, numB);

            eps = machineEps(eps);

            calc(numA, numB, result, eps);
            op(numA, numB, result);

            numA.clear();
            numB.clear();
            result.clear();
        }
        else
        {
            cout << "File is empty" << endl;
        }
        fin.close();

        cout << "Repeat program?(Y/N): ";
        getline(cin, rep);
        rep = repeat(rep);
    }
    while (rep == "Y" || rep == "y");
    return 0;
}