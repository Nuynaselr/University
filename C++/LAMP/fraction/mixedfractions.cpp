//
// Created by NIkita on 20.05.2018.
//
#include "mixedfractions.h"

void MixedFraction::swap(MixedFraction &variable) {
    MixedFraction frac(this->whole, this->getFractionNumerator(), this->getFractionDenomirator());
    this->setMixedFraction(whole, variable.getFractionNumerator(), variable.getFractionDenomirator());
    variable.setMixedFraction(whole, frac.getFractionNumerator(), frac.getFractionDenomirator());
}

MixedFraction::MixedFraction(int num, int den) {
    this->whole = int(num / den);
    this->setFtactionNumerator(num % den);
    this->setFtactionDenominator(den);
}
MixedFraction::MixedFraction(int whole, int num, int den) {
    this->whole = whole;
    this->setFtactionNumerator(num);
    this->setFtactionDenominator(den);
}
MixedFraction::MixedFraction(Fraction &variable){
    this->whole = int(variable.getFractionNumerator() / variable.getFractionDenomirator());
    this->setFtactionNumerator(variable.getFractionNumerator() % variable.getFractionDenomirator());
    this->setFtactionDenominator(variable.getFractionDenomirator());
}
MixedFraction::MixedFraction(const MixedFraction &variable){
    this->setMixedFraction(variable.whole, variable.numerator, variable.denominator);
}
MixedFraction::~MixedFraction() = default;

int MixedFraction::getWhole(){
    return this->whole;
}
void MixedFraction::setMixedFraction(int whole, int numerator, int denominator){
    this->whole = whole;
    this->setFtactionNumerator(numerator);
    this->setFtactionDenominator(denominator);
}
double MixedFraction::decimalMF() {
    return whole + this->decimal();
}

ostream &operator<<(ostream & out, MixedFraction & variable){
    out << variable.getWhole() << "|" << variable.getFractionNumerator() << '/' << variable.getFractionDenomirator();
    return out;
}
istream &operator>>(istream &in, MixedFraction &variable){
    cout << "Example input: 2 | 4 / 5, 0 | 6 / 9 (Spaces are required)" << endl;
    int whole, numerator, denominator;
    char sign1, sign2;
    in >> whole >> sign1 >> numerator >> sign2 >> denominator;
    while(sign2 != '/' || denominator == 0 || sign1 != '|'){
        cout << "Incorrect data enter" << endl;
        in >> whole >> sign1 >> numerator >> sign2 >> denominator;
    }
    variable.whole = whole;
    variable.setFtactionNumerator(numerator);
    variable.setFtactionDenominator(denominator);
}
MixedFraction operator+(MixedFraction &variable1, MixedFraction &variable2){
    MixedFraction frac;
    if (variable1.getFractionDenomirator() == variable2.getFractionDenomirator()){
        frac.setFtactionDenominator(variable1.getFractionDenomirator());
        frac.setFtactionNumerator(variable1.getFractionNumerator() + variable2.getFractionNumerator());
    } else {
        frac.setFtactionDenominator(variable1.getFractionDenomirator() * variable2.getFractionDenomirator());
        frac.setFtactionNumerator(variable1.getFractionNumerator() * variable2.getFractionDenomirator() + variable2.getFractionNumerator() * variable1.getFractionDenomirator());
    }
    frac.reduction();
    return frac;
}
MixedFraction operator-(MixedFraction &variable1, MixedFraction &variable2){
    MixedFraction frac;
    if (variable1.getFractionDenomirator() == variable2.getFractionDenomirator()){
        frac.setFtactionDenominator(variable1.getFractionDenomirator());
        frac.setFtactionNumerator(variable1.getFractionNumerator() - variable2.getFractionNumerator());
    } else {
        frac.setFtactionDenominator(variable1.getFractionDenomirator() * variable2.getFractionDenomirator());
        frac.setFtactionNumerator(variable1.getFractionNumerator() * variable2.getFractionDenomirator() - variable2.getFractionNumerator() * variable1.getFractionDenomirator());
    }
    frac.reduction();
    return frac;
}
MixedFraction operator*(MixedFraction &variable1, MixedFraction &variable2){
    MixedFraction frac;
    frac.setFtactionNumerator(variable1.getFractionNumerator() * variable2.getFractionNumerator());
    frac.setFtactionDenominator(variable1.getFractionDenomirator() * variable2.getFractionDenomirator());
    return frac;
}
MixedFraction operator/(MixedFraction &variable1, MixedFraction &variable2){
    MixedFraction frac;
    frac.setFtactionNumerator(variable1.getFractionNumerator() * variable2.getFractionDenomirator());
    frac.setFtactionDenominator(variable1.getFractionDenomirator() * variable2.getFractionNumerator());
    return frac;
}
MixedFraction &MixedFraction::operator=(const MixedFraction &variable){
    if (&variable != this){
        MixedFraction frac(variable);
        frac.swap(*this);
    }
    return *this;
}
MixedFraction &MixedFraction::operator+=(MixedFraction &variable){
    *this = *this + variable;
    return *this;
}
MixedFraction &MixedFraction::operator-=(MixedFraction &variable){
    *this = *this - variable;
    return *this;
}
MixedFraction &MixedFraction::operator/=(MixedFraction &variable){
    *this = *this / variable;
    return *this;
}
MixedFraction &MixedFraction::operator*=(MixedFraction &variable){
    *this = *this * variable;
    return *this;
}

const bool operator>(MixedFraction &variable1, MixedFraction &variable2){
    if (variable1.whole > variable2.whole) return true;
    else if(variable1.getFractionDenomirator() == variable2.getFractionDenomirator() && variable1.whole == variable2.whole){
        return variable1.getFractionNumerator() < variable2.getFractionNumerator();
    }else if (variable1.getFractionDenomirator() != variable2.getFractionDenomirator() && variable1.whole == variable2.whole){
        return variable1.getFractionNumerator() * variable2.getFractionDenomirator() > variable2.getFractionNumerator() * variable1.getFractionDenomirator();
    }
    return false;
}
const bool operator<(MixedFraction &variable1, MixedFraction &variable2){
    if (variable1.whole < variable2.whole) return true;
    if(variable1.getFractionDenomirator() == variable2.getFractionDenomirator() && variable1.whole == variable2.whole){
        return variable1.getFractionNumerator() < variable2.getFractionNumerator();
    }else if(variable1.getFractionDenomirator() != variable2.getFractionDenomirator() && variable1.whole == variable2.whole){
        return variable1.getFractionNumerator() * variable2.getFractionDenomirator() < variable2.getFractionNumerator() * variable1.getFractionDenomirator();
    }
    return false;
}
const bool operator==(MixedFraction &variable1, MixedFraction &variable2){
    if(variable1.getFractionDenomirator() == variable2.getFractionDenomirator() && variable1.getFractionNumerator() == variable2.getFractionNumerator() && variable1.whole == variable2.whole){
        return true;
    }
    return false;
}
const bool operator!=(MixedFraction &variable1, MixedFraction &variable2){
    if(variable1 == variable2){
        return false;
    }
    return true;
}
const bool operator>=(MixedFraction &variable1, MixedFraction &variable2){
    if(variable1 == variable2 || variable1 > variable2){
        return true;
    }
    return false;
}
const bool operator<=(MixedFraction &variable1, MixedFraction &variable2){
    if(variable1 == variable2 || variable1 < variable2){
        return true;
    }
    return false;
}

MixedFraction &MixedFraction::operator[](int size){
    MixedFraction * frac = (MixedFraction*) malloc(size);
    return *frac;
}
