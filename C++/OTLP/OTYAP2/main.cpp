#include <vector>
#include <iostream>
#include <fstream>

using namespace std;

enum EState { S, SDoD, SDoO, SLoopL, SLoopO, SLoopOo, SLoopP, SWhileW, SWhileH, SWhileI, SWhileL, SWhileE, SAndA, SAndN, SAndD, SOrO, SOrR, SInd, SConst, SM, F, E};
enum LexType{ lWhile, lDo, lLoop, lAnd, lOr, lRel, lAs, lAo, lVar, lConst, Error };

void getSteta(ofstream & fout, LexType st){
    if (st ==  lWhile)
    {
        fout << "lWhile\t";
    }
    else if (st ==  lDo) {
        fout << "lDo\t";
    }
    else if (st ==  lLoop) {
        fout << "lLoop\t";
    }
    else if (st ==  lAnd) {
        fout << "lAnd\t";
    }
    else if (st ==  lOr) {
        fout << "LOr\t";
    }
    else if (st ==  lRel) {
        fout << "LRel\t";
    }
    else if (st ==  lAs) {
        fout << "lAs\t";
    }
    else if (st ==  lAo) {
        fout << "lAo\t";
    }
    else if (st ==  lVar) {
        fout << "lVar\t";
    }
    else if (st ==  lConst) {
        fout << "lConst\t";
    }
    else if (st ==  Error) {
        fout << "Wrong\t";
    }
}

struct Lex{
    LexType type;
    char* str;
}

        *pFirst = NULL, *pLast = NULL;

Lex CreatLex(LexType ltype, const char* start, int num){
    Lex lexem;
    lexem.str = new char[num + 1];
    for (int i = 0; i < num; i++) {
        lexem.str[i] = start[i];
    }
    lexem.str[num] = 0;
    if (ltype == LexType::lConst)
        if (atoi(lexem.str) >= -32768 && atoi(lexem.str) <= 327767)
        {
            lexem.type = ltype;
        }
        else
        {
            lexem.type = LexType ::Error;
        }
    else
    {
        lexem.type = ltype;
    }
    return lexem;
}

EState fun(const char *str, vector<Lex> &result,const char *lexstart, EState state)
{
    if (isspace(*str)) return S;
    else if (*str == 0) return F;
    else {
        lexstart = str;
        if (*str == '<'){
            return SM;
        }
        else {
            if (*str == '='){
                result.push_back(CreatLex(LexType::lAs, lexstart, 1));
            }
            else if (*str == '>'){
                result.push_back(CreatLex(LexType::lRel, lexstart, 1));
            }
            else if ((*str == '+') || (*str == '-') || (*str == '*') || (*str == '/')){
                result.push_back(CreatLex(LexType::lAo, lexstart, 1));
            }
            return S;
        }
    }

}

vector<Lex> LexAnalysis(const char* text)
{
    const char *str = text;
    const char *lexstart = text;
    vector<Lex> result;
    EState state = S, prevState;

    while ((state != F))
    {
        prevState = state;
        switch (state) {
            case S: {
                lexstart = str;
                if (isspace(*str));
                else if ((*str) == 'd')	{
                    state = SDoD;
                }
                else if ((*str) == 'l'){
                    state = SLoopL;
                }
                else if ((*str) == 'w'){
                    state = SWhileW;
                }
                else if ((*str) == 'a'){
                    state = SAndA;
                }
                else if ((*str) == 'o'){
                    state = SOrO;
                }
                else if (isalpha(*str)){
                    state = SInd;
                }
                else if (isdigit(*str)){
                    state = SConst;
                }
                else if (*str == '<') state = SM;
                else if (*str == '=') result.push_back(CreatLex(LexType::lAs, lexstart, 1));
                else if (*str == '>') result.push_back(CreatLex(LexType::lRel, lexstart, 1));
                else if ((*str == '+') || (*str == '-') || (*str == '*') || (*str == '/'))
                {
                    result.push_back(CreatLex(LexType::lAo, lexstart, 1));
                    //state = E;
                }
                else if (*str == 0)	 state = F;
                else state = E;
                break;
            }
            case SDoD: {
                if ((*str) == 'o')	{
                    state = SDoO;
                }
                else if (isalpha(*str) || isdigit(*str)){
                    state = SInd;
                }
                else {
                    result.push_back(CreatLex(LexType::lVar, lexstart, str - lexstart));
                    state = fun(str, result, lexstart, state);
                }
                break;
            }
            case SDoO: {
                if (isalpha(*str) || isdigit(*str)){
                    state = SInd;
                }
                else {
                    result.push_back(CreatLex(LexType::lDo, lexstart, str - lexstart));
                    state = fun(str, result, lexstart, state);
                }
                break;
            }
            case SLoopL: {
                if ((*str) == 'o')	{
                    state = SLoopO;
                }
                else if (isalpha(*str) || isdigit(*str)){
                    state = SInd;
                }
                else {
                    result.push_back(CreatLex(LexType::lVar, lexstart, str - lexstart));
                    state = fun(str, result, lexstart, state);
                }
                break;
            }
            case SLoopO: {
                if ((*str) == 'o')	{
                    state = SLoopOo;
                }
                else if (isalpha(*str) || isdigit(*str)){
                    state = SInd;
                }
                else {
                    result.push_back(CreatLex(LexType::lVar, lexstart, str - lexstart));
                    state = fun(str, result, lexstart, state);
                }
                break;
            }
            case SLoopOo: {
                if ((*str) == 'p')	{
                    state = SLoopP;
                }
                else if (isalpha(*str) || isdigit(*str)){
                    state = SInd;
                }
                else {
                    result.push_back(CreatLex(LexType::lVar, lexstart, str - lexstart));
                    state = fun(str, result, lexstart, state);
                }
                break;
            }
            case SLoopP: {
                if (isalpha(*str) || isdigit(*str)){
                    state = SInd;
                }
                else {
                    result.push_back(CreatLex(LexType::lLoop, lexstart, str - lexstart));
                    state = fun(str, result, lexstart, state);
                }
                break;
            }
            case SWhileW: {
                if ((*str) == 'h')	{
                    state = SWhileH;
                }
                else if (isalpha(*str) || isdigit(*str)){
                    state = SInd;
                }
                else {
                    result.push_back(CreatLex(LexType::lVar, lexstart, str - lexstart));
                    state = fun(str, result, lexstart, state);
                }
                break;
            }
            case SWhileH: {
                if ((*str) == 'i')	{
                    state = SWhileI;
                }
                else if (isalpha(*str) || isdigit(*str)){
                    state = SInd;
                }
                else {
                    result.push_back(CreatLex(LexType::lVar, lexstart, str - lexstart));
                    state = fun(str, result, lexstart, state);
                }
                break;
            }
            case SWhileI: {
                if ((*str) == 'l')	{
                    state = SWhileL;
                }
                else if (isalpha(*str) || isdigit(*str)){
                    state = SInd;
                }
                else {
                    result.push_back(CreatLex(LexType::lVar, lexstart, str - lexstart));
                    state = fun(str, result, lexstart, state);
                }
                break;
            }
            case SWhileL: {
                if ((*str) == 'e')	{
                    state = SWhileE;
                }
                else if (isalpha(*str) || isdigit(*str)){
                    state = SInd;
                }
                else {
                    result.push_back(CreatLex(LexType::lVar, lexstart, str - lexstart));
                    state = fun(str, result, lexstart, state);
                }
                break;
            }
            case SWhileE: {
                if (isalpha(*str) || isdigit(*str)){
                    state = SInd;
                }
                else {
                    result.push_back(CreatLex(LexType::lWhile, lexstart, str - lexstart));
                    state = fun(str, result, lexstart, state);
                }
                break;
            }
            case SAndA: {
                if ((*str) == 'n')	{
                    state = SAndN;
                }
                else if (isalpha(*str) || isdigit(*str)){
                    state = SInd;
                }
                else {
                    result.push_back(CreatLex(LexType::lVar, lexstart, str - lexstart));
                    state = fun(str, result, lexstart, state);
                }
                break;
            }
            case SAndN: {
                if ((*str) == 'd')	{
                    state = SAndD;
                }
                else if (isalpha(*str) || isdigit(*str)){
                    state = SInd;
                }
                else {
                    result.push_back(CreatLex(LexType::lVar, lexstart, str - lexstart));
                    state = fun(str, result, lexstart, state);
                }
                break;
            }
            case SAndD: {
                if (isalpha(*str) || isdigit(*str)){
                    state = SInd;
                }
                else {
                    result.push_back(CreatLex(LexType::lAnd, lexstart, str - lexstart));
                    state = fun(str, result, lexstart, state);
                }
                break;
            }
            case SOrO: {
                if ((*str) == 'r')	{
                    state = SOrR;
                }
                else if (isalpha(*str) || isdigit(*str)){
                    state = SInd;
                }
                else {
                    result.push_back(CreatLex(LexType::lVar, lexstart, str - lexstart));
                    state = fun(str, result, lexstart, state);
                }
                break;
            }
            case SOrR: {
                if (isalpha(*str) || isdigit(*str)){
                    state = SInd;
                }
                else {
                    result.push_back(CreatLex(LexType::lOr, lexstart, str - lexstart));
                    state = fun(str, result, lexstart, state);
                }
                break;
            }
            case SInd: {
                if (isalpha(*str) || isdigit(*str)){
                    state = SInd;
                }
                else {
                    result.push_back(CreatLex(LexType::lVar, lexstart, str - lexstart));
                    state = fun(str, result, lexstart, state);
                }
                break;
            }
            case SConst: {
                if (isdigit(*str)){
                    state = SConst;
                }
                else if (isalpha(*str)){
                    state = E;
                }
                else {
                    result.push_back(CreatLex(LexType::lConst, lexstart, str - lexstart));
                    state = fun(str, result, lexstart, state);
                }
                break;
            }
            case SM: {
                if (*str == '>'){
                    result.push_back(CreatLex(LexType::lRel, lexstart, 2));
                    state = S;
                }
                else if (isalpha(*str)){
                    state = E;
                }
                else {
                    result.push_back(CreatLex(LexType::lRel, lexstart, str - lexstart));
                    str--;
                    state = S;
                }
                break;
            }
            case E: {
                if (isalpha(*str) || isdigit(*str)){
                    state = E;
                }
                else {
                    result.push_back(CreatLex(LexType::Error, lexstart, str - lexstart));
                    state = fun(str, result, lexstart, state);
                }
                break;
            }
        }
        str++;
    }
    return result;
}

char * fileRead(ifstream & fin, char * text)
{
    fin.seekg(0, ios::end);
    int length = fin.tellg();
    text = new char[length + 1];
    fin.seekg(0, ios::beg);
    fin.read(text, length);
    text[length] = '\0';
    return text;
}

void printAll(ofstream & fout, vector<Lex> result)
{
    int j;

    fout << "Index | Symbol\t" << endl;
    fout << "--------------" << endl;

    for (unsigned int i = 0; i < result.size(); i++){
        if (result[i].type == lDo)
        {
            j = i;
            fout << i - j << "\t|  " << result[i].str << " -> ";
        } else
        {
            fout << i - j << "\t|  " << result[i].str << " -> ";
        }
        getSteta(fout, result[i].type);
        fout << endl;
    }
}

//////////////////////////////////////////////////////////////////////////////

const int matrix[10][13] = {
        1,	1,	12,	12,	12,	12,	12,	12,	12,	12,	12,	1,	1,
        12,	2,	12,	4,	12,	6,	12,	12,	9,	12,	12,	12,	2,
        12,	12,	3,	12,	12,	12,	12,	12,	12,	12,	12,	12,	3,
        12,	12,	12,	4,	12,	6,	12,	12,	12,	12,	11,	12,	6,
        12,	12,	12,	12,	5,	12,	12,	12,	12,	12,	12,	12,	5,
        12,	12,	12,	12,	12,	12,	7,	12,	12,	12,	12,	7,	7,
        12,	12,	12,	12,	12,	12,	12,	8,	12,	12,	12,	12,	8,
        12,	12,	12,	12,	12,	12,	12,	12,	12,	10,	12,	12,	10,
        12,	12,	12,	12,	12,	12,	12,	12,	12,	12,	12,	8, 12,
        12,	12,	12,	12,	12,	12,	12,	12,	12,	12,	12,	12,
};

enum State{ St, Do, In, Eq, Co, Op, Co2, Lo, Wh, Co3, Com, AO, Er};

int getGroup(LexType & tag)
{
    if (tag == lDo) return 0;
    else if (tag == lVar) return 1;
    else if (tag == lAs) return 2;
    else if (tag == lConst) return 3;
    else if (tag == lAo) return 4;
    else if (tag == lLoop) return 5;
    else if (tag == lWhile) return 6;
    else if (tag == lRel) return 7;
    else if (tag == lOr || tag == lAnd) return 8;
    else if (tag == Error) return 9;
}

void processing(ofstream & fout, vector<Lex> & result)
{
    int error = 0;
    State lastState = State::St;
    State pos = State::St;
    int position = 0;
    while(position < result.size()) {
        int tt = getGroup(result[position].type);
        int rr = pos;
        pos = (State) matrix[getGroup(result[position].type)][pos];
        if (pos != State::Er) {
            if (pos == State::Do) error = 0;
            if (lastState == State::AO && error == 0 && pos == State::Do) fout << "Correct construction" << endl;
            if (result[position].type == lDo && position != 0) fout << endl;
            if (lastState == State::AO && pos == State::Do) fout << endl;
            fout << result[position].str << ' ';
            lastState = pos;
        } else {
            error++;
            fout << "\nError: Expects ";
            if (lastState == State::St){ //enum State{ St, Do, In, Eq, Co, Op, Co2, Lo, Wh, Co3, Com, AO, Er};
                fout << "do" << endl;
                lastState = State::Do;
            } else if (lastState == State::Do || lastState == State::Wh){
                fout << "variable" << endl;
            } else if (lastState == State::In){
                fout << "a sign assignment" << endl;
                pos = State::Eq;
            } else if (lastState == State::Eq || lastState == State::Op){
                fout << "variable or constant" << endl;
            } else if (lastState == State::Co ){
                fout << "operator" << endl;
            } else if (lastState == State::Com){
                fout << "constant" << endl;
            } else if (lastState == State::Co2){
                fout << "loop" << endl;
            } else if (lastState == State::Lo){
                fout << "while" << endl;
                pos = State::Wh;
            } else if (lastState == State::Co3){
                fout << "comparison operator" << endl;
            } else if (lastState == State::AO) {
                fout << "and/or or new structure" << endl;
            }
        }
        position++;
    }
    if (lastState == State::AO && error == 0) fout << "Correct construction" << endl;
}

int main()
{
    /* for (int index = 1; index < 4; index++) {
         ifstream fin("input" + to_string(index) + ".txt");
         ofstream foutSec("outputSA" + to_string(index) + ".txt");
         ofstream fout("output" + to_string(index) + ".txt");*/


    ifstream fin("input.txt");
    ofstream foutSec("outputSA.txt");
    ofstream fout("output.txt");
    char *str;
    str = fileRead(fin, str);

    vector<Lex> result = LexAnalysis(str);

    printAll(fout, result);

    processing(foutSec, result);

    for (int i = 0; i < result.size(); i++) {
        delete[] result[i].str;
    }
    delete[] str;
    fin.close();
    foutSec.close();
    fout.close();

    return 0;
}