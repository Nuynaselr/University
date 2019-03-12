#include <iostream>
#include <fstream>

using namespace std;

bool checkNumber(char el)
{
    if (int(el) >= 48 && int(el) <= 57 || el == '.' || el == '-')
    {
        return true;
    }
    return false;
}

int searchNumber(string s)
{
    int size = 0;
    for(int i = 0; i < s.length(); i++)
    {
        if (checkNumber(s[i]) && !checkNumber(s[i + 1]))
        {
            size ++;
        }
    }
    return size;
}

void transform(string s, double *vecNum)
{
    string number;
    int size = 0;
    for(int i = 0; i < s.length(); i++)
    {
        if (checkNumber(s[i]))
        {
            number += s[i];
            if(!checkNumber(s[i + 1]))
            {
                vecNum[size] = atof(number.c_str());
                size ++;
                number = "";
            }
        }
    }
}

void op(double *arr, int result, int d,int size)
{
    ofstream fout("OUTPUT.txt");
    cout << "Original: ";
    for(int i = 0; i < size; i++)
    {
        cout << arr[i] << " ";
    }
    cout << "\nElement d: " << d << endl;
    cout << "Number of elements: " << result << endl;

    fout << result << endl;
    fout.close();
}

int res(double *arr, int d, double eps, int size)
{
    int result = 0;
    for (int i = 0; i < size; i++)
    {
        if (arr[i] + eps < d && arr[i] < 0)
        {
            result++;
        }
    }
    return result;
}

void fileOpen(ifstream & fin)
{
    string fileName;
    cout << "Enter name file: ";
    getline(cin, fileName);

    fin.open(fileName);
    while(!fin.is_open())
    {
        cout << "Error: Please enter correct name file" << endl << "Enter name file: ";
        getline(cin, fileName);
        fin.open(fileName.c_str());
    }

}

string repeat(string rep)
{
    while(!(rep == "Y" || rep == "y" || rep == "N" || rep == "n"))
    {
        cout << "Error: incorrect data\nRepeat program?(Y/N): ";
        getline(cin, rep);
    }
    return rep;
}

double machineEps(double eps)
{
    while(1 + eps != 1)
    {
        eps /= 2;
    }
    return eps;
}

int main() {
    double eps = 1.0;
    ifstream fin;
    string fileName, rep;
    string arrNum;
    int d, result;
    string arrS;
    int size;

    do {
        fileOpen(fin);

        if (!fin.eof()) {
            eps = machineEps(eps);
            getline(fin, arrS);
            fin >> d;
            size = searchNumber(arrS);
            double *arr = new double[size];
            transform(arrS, arr);

            result = res(arr, d, eps, size);
            op(arr, result, d, size);

        } else {
            cout << "File is empty" << endl;
        }
        fin.close();
        cout << "Repeat program?(Y/N): ";

        getline(cin, rep);
        rep = repeat(rep);
    } while (rep == "Y" || rep == "y");
    return 0;
}