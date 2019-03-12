#include <vector>
#include <fstream>
#include <iostream>


using namespace std;

int matrix[4][4] = // матрица переходов
        {                   //  А..я  0..9 ' '      other
                1, 4, 3, 4, //S  G     E    F        E
                1, 2, 4, 4, //G  G     X    E        E
                1, 2, 3, 4, //X  G     X    F        E
                4, 4, 4, 4  //E  E     E    E        E
        };

enum Astate {S, A, G, F, E}; //состояние автоматов

struct Lex
{
    bool type;
    wchar_t * stringLex;
};

int GetMatrixCol(wchar_t currentChar) //Функция преобразования символа в соответствующий номер столбца матрицы переходов
{
    if (isdigit(currentChar)) return 1;
    if (currentChar >= 1040 && currentChar <= 1103 || isalpha(currentChar)) return 0;
    if (currentChar == ' ' || currentChar == '\r' || currentChar == '\n') return 2;

    return 3;
}

void addword(vector<Lex> &lexema, Lex lex, int firstPosition, int position, wchar_t * str)
{
    int length = position - firstPosition;
    lex.stringLex = (wchar_t *) calloc(length + 1, sizeof(wchar_t));
    for (int i = 0; i < length; i++, firstPosition++) {
        lex.stringLex[i] = str[firstPosition];
    }
    lexema.emplace_back(lex);
}

void addword(vector<Lex> &lexema, Lex lex, int firstPosition, int position, wchar_t * str, bool type)
{
    int length = position - firstPosition;
    lex.stringLex = (wchar_t *) calloc(length + 1, sizeof(wchar_t));
    for (int i = 0; i < length; i++, firstPosition++) {
        lex.stringLex[i] = str[firstPosition];
    }
    lex.type = type;
    lexema.emplace_back(lex);
}

void processed(vector<Lex> &lexema, wchar_t * str)
{
    int position = 0;
    int firstPosition = 0;
    Astate state = Astate :: S;
    Lex lex;
    while (str[position] != '\0'){
        wchar_t currentChar = str[position];
        if (state == Astate::S && currentChar != ' ') {
            firstPosition = position;
            lex.type = true;
        }
        state = (Astate) matrix[state][GetMatrixCol(currentChar)];
        if (state == Astate::E) {
            while (GetMatrixCol(currentChar) != 2) {
                currentChar = str[++position];
            }
            addword(lexema, lex, firstPosition, position, str, false);
            state = Astate::S;
            firstPosition = position;
        }
        if (state == Astate::F) {
            addword(lexema, lex, firstPosition, position, str);
            state = Astate :: S;
        }
        position++;
    }
    if (state == Astate::G || state == Astate::A)
    {
        addword(lexema, lex, firstPosition, position, str);
        if (state == Astate::G)lex.type = true;
        else lex.type = false;
    }
}

void printAll(vector<Lex> &lexema, wchar_t * str, int size)
{
    wofstream fout("output.txt");
    fout << "Original text:" << endl;
    fout << str << endl;

    fout << "\nTrue elements: " << endl;
    for(int i = 0; i < lexema.size(); i++) {
        if (lexema[i].type)
        {
            fout << lexema[i].stringLex << endl;
        }
    }
    fout << "\nFalse elements: " << endl;
    for(int i = 0; i < lexema.size(); i++) {
        if (!lexema[i].type)
        {
            fout << lexema[i].stringLex << endl;
        }
    }
    fout.close();
}

int main() {
    setlocale(LC_ALL, "ru_RU.UTF-8");
    wifstream fin;
    vector<Lex> lexema;

    char fileName[50];
    cout << "Enter file name: ";
    gets(fileName);

    fin.open(fileName);
    //--target lr1ot -- -j 2
    fin.seekg(0, ios::end);
    int length = (int) fin.tellg() + 1;
    wchar_t *stringProcessed = (wchar_t *) calloc(length, sizeof(wchar_t));
    fin.seekg(0, ios::beg);
    fin.getline(stringProcessed, length, EOF);
    processed(lexema, stringProcessed);
    printAll(lexema, stringProcessed, length);

    lexema.clear();
    fin.close();
    return 0;
}