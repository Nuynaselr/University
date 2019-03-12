#include <iostream>
//#include "mixedfractions.cpp"
#include "mixedfractions.h"

using namespace std;

int main() {
    Fraction frac(45,10);
   /* MixedFraction frac1(14,9);
    MixedFraction frac2(56,16);
    MixedFraction frac3(79,17);
    MixedFraction frac4(45,10);*/


    MixedFraction frac4(45,10);
    cout << frac.decimal();

    return 0;
}