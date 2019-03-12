//
// Created by NIkita on 20.05.2018.
//

#ifndef FRACTION_FRACTION_H
#define FRACTION_FRACTION_H
#include <iostream>

using namespace std;

class Fraction {
protected:
    int numerator;
    int denominator;

    int nod(int num1, int num2);
    void swap(Fraction &variable);
public:
    Fraction() {}
    Fraction(int num, int den);
    Fraction(const Fraction &variable);
    ~Fraction();

    //////////////////////////////////////////////////
    void setFraction(int numerator, int denominator);
    void setFtactionNumerator(int numerator);
    void setFtactionDenominator(int denominator);
    int getFractionNumerator();
    int getFractionDenomirator();
    void reduction();
    double decimal();

    friend ostream &operator<<(ostream & out, Fraction & variable);
    friend istream &operator>>(istream &in, Fraction &variable);
    friend Fraction operator+(Fraction &variable1, Fraction &variable2);
    friend Fraction operator-(Fraction &variable1, Fraction &variable2);
    friend Fraction operator*(Fraction &variable1, Fraction &variable2);
    friend Fraction operator/(Fraction &variable1, Fraction &variable2);
    Fraction &operator=(const Fraction &variable);
    Fraction &operator+=(Fraction &variable);
    Fraction &operator-=(Fraction &variable);
    Fraction &operator/=(Fraction &variable);
    Fraction &operator*=(Fraction &variable);

    friend const bool operator>(Fraction &variable1, Fraction &variable2);
    friend const bool operator<(Fraction &variable1, Fraction &variable2);
    friend const bool operator==(Fraction &variable1, Fraction &variable2);
    friend const bool operator!=(Fraction &variable1, Fraction &variable2);
    friend const bool operator>=(Fraction &variable1, Fraction &variable2);
    friend const bool operator<=(Fraction &variable1, Fraction &variable2);

    Fraction &operator[](int size);
};

#endif //FRACTION_FRACTION_H
