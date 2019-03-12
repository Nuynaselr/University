//
// Created by NIkita on 18.05.2018.
//
#include "fraction.h"

int Fraction::nod(int num1, int num2){
    while (num1 != 0 && num2 !=0){
        if (num1 > num2) num1 %= num2;
        else num2 %= num1;
    }
    return num1 + num2;
}
void Fraction::swap(Fraction &variable){
    Fraction frac(this->numerator, this->denominator);
    this->numerator = variable.numerator;
    this->denominator = variable.denominator;
    variable.numerator = frac.numerator;
    variable.denominator = frac.denominator;
}

Fraction::Fraction(int num, int den) {
    this->numerator = num;
    this->denominator = den;
}
Fraction::Fraction(const Fraction &variable) {
    this->numerator = variable.numerator;
    this->denominator = variable.denominator;
}
Fraction::~Fraction() = default;

//////////////////////////////////////////////////
void Fraction::setFraction(int numerator, int denominator) {
    this->numerator = numerator;
    this->denominator = denominator;
}
void Fraction::setFtactionNumerator(int numerator){
    this->numerator = numerator;
}
void Fraction::setFtactionDenominator(int denominator){
    this->denominator = denominator;
}
int Fraction::getFractionNumerator(){
    return this->numerator;
}
int Fraction::getFractionDenomirator(){
    return this->denominator;
}
void Fraction::reduction(){
    int reduction = nod(this->numerator, this->denominator);
    this->numerator /= reduction;
    this->denominator /= reduction;
}
double Fraction::decimal(){
    return double(this->numerator) / this->denominator;
}


ostream &operator<<(ostream & out, Fraction & variable){
    out << variable.getFractionNumerator() << '/' << variable.getFractionDenomirator();
    return out;
}
istream &operator>>(istream &in, Fraction &variable){
    cout << "Example input: 4 / 5, 6 / 9 (Spaces are required)" << endl;
    int numerator, denominator;
    char sign;
    in >> numerator >> sign >> denominator;
    while(sign != '/' || denominator == 0){
        cout << "Incorrect data enter" << endl;
        cout << "Example input: 4 / 5, 6 / 9 (Spaces are required)" << endl;
        int numerator, denominator;
        char sign;
        in >> numerator >> sign >> denominator;
    }
    variable.numerator = numerator;
    variable.denominator = denominator;
}
Fraction operator+(Fraction &variable1, Fraction &variable2){
    Fraction frac;
    if (variable1.denominator == variable2.denominator){
        frac.denominator = variable1.denominator;
        frac.numerator = variable1.numerator + variable2.numerator;
    } else {
        frac.denominator = variable1.denominator * variable2.denominator;
        frac.numerator = variable1.numerator * variable2.denominator + variable2.numerator * variable1.denominator;
    }
    frac.reduction();
    return frac;
}
Fraction operator-(Fraction &variable1, Fraction &variable2){
    Fraction frac;
    if (variable1.denominator == variable2.denominator){
        frac.denominator = variable1.denominator;
        frac.numerator = variable1.numerator - variable2.numerator;
    } else {
        frac.denominator = variable1.denominator * variable2.denominator;
        frac.numerator = variable1.numerator * variable2.denominator - variable2.numerator * variable1.denominator;
    }
    frac.reduction();
    return frac;
}
Fraction operator*(Fraction &variable1, Fraction &variable2){
    Fraction frac;
    frac.numerator = variable1.numerator * variable2.numerator;
    frac.denominator = variable1.denominator * variable2.denominator;
    return frac;
}
Fraction operator/(Fraction &variable1, Fraction &variable2){
    Fraction frac;
    frac.numerator = variable1.numerator * variable2.denominator;
    frac.denominator = variable1.denominator * variable2.numerator;
    return frac;
}
Fraction & Fraction::operator=(const Fraction &variable){
    if (&variable != this){
        Fraction frac(variable);
        frac.swap(*this);
    }
    return *this;
}
Fraction & Fraction::operator+=(Fraction &variable){
    *this = *this + variable;
    return *this;
}
Fraction & Fraction::operator-=(Fraction &variable){
    *this = *this - variable;
    return *this;
}
Fraction & Fraction::operator/=(Fraction &variable){
    *this = *this / variable;
    return *this;
}
Fraction & Fraction::operator*=(Fraction &variable){
    *this = *this * variable;
    return *this;
}

const bool operator>(Fraction &variable1, Fraction &variable2){
    if(variable1.denominator == variable2.denominator){
        return variable1.numerator > variable2.numerator;
    }else {
        return variable1.numerator * variable2.denominator > variable2.numerator * variable1.denominator;
    }
}
const bool operator<(Fraction &variable1, Fraction &variable2){
    if(variable1.denominator == variable2.denominator){
        return variable1.numerator < variable2.numerator;
    }else {
        return variable1.numerator * variable2.denominator < variable2.numerator * variable1.denominator;
    }
}
const bool operator==(Fraction &variable1, Fraction &variable2){
    if(variable1.denominator == variable2.denominator && variable1.numerator == variable2.numerator){
        return true;
    }
    return false;
}
const bool operator!=(Fraction &variable1, Fraction &variable2){
    if(variable1 == variable2){
        return false;
    }
    return true;
}
const bool operator>=(Fraction &variable1, Fraction &variable2){
    if(variable1 == variable2 || variable1 > variable2){
        return true;
    }
    return false;
}
const bool operator<=(Fraction &variable1, Fraction &variable2){
    if(variable1 == variable2 || variable1 < variable2){
        return true;
    }
    return false;
}

Fraction &Fraction::operator[](int size){
    Fraction * frac = (Fraction*) malloc(size);
    return *frac;
}