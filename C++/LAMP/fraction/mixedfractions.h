//
// Created by NIkita on 20.05.2018.
//

#ifndef FRACTION_MIXEDFRACTIONS_H
#define FRACTION_MIXEDFRACTIONS_H

#include "fraction.h"

using namespace std;

class MixedFraction: public Fraction {
private:
    int whole = 0;

    void swap(MixedFraction &variable);

public:
    MixedFraction() {}
    MixedFraction(int num, int den);
    MixedFraction(int whole, int num, int den);
    MixedFraction(Fraction &variable);
    MixedFraction(const MixedFraction &variable);
    ~MixedFraction();

    int getWhole();
    void setMixedFraction(int whole, int numerator, int denominator);
    double decimalMF();

    friend ostream &operator<<(ostream &out, MixedFraction &variable);
    friend istream &operator>>(istream &in, MixedFraction &variable);
    friend MixedFraction operator+(MixedFraction &variable1, MixedFraction &variable2);
    friend MixedFraction operator-(MixedFraction &variable1, MixedFraction &variable2);
    friend MixedFraction operator*(MixedFraction &variable1, MixedFraction &variable2);
    friend MixedFraction operator/(MixedFraction &variable1, MixedFraction &variable2);
    MixedFraction &operator=(const MixedFraction &variable);
    MixedFraction &operator+=(MixedFraction &variable);
    MixedFraction &operator-=(MixedFraction &variable);
    MixedFraction &operator/=(MixedFraction &variable);
    MixedFraction &operator*=(MixedFraction &variable);

    friend const bool operator>(MixedFraction &variable1, MixedFraction &variable2);
    friend const bool operator<(MixedFraction &variable1, MixedFraction &variable2);
    friend const bool operator==(MixedFraction &variable1, MixedFraction &variable2);
    friend const bool operator!=(MixedFraction &variable1, MixedFraction &variable2);
    friend const bool operator>=(MixedFraction &variable1, MixedFraction &variable2);
    friend const bool operator<=(MixedFraction &variable1, MixedFraction &variable2);

    MixedFraction &operator[](int size);
};
#endif //FRACTION_MIXEDFRACTIONS_H
