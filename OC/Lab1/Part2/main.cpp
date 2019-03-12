#include <iostream>
#include <iomanip>

using namespace std;

double function(double x, double y){
    double answer = x*x - 2*y;
    return answer;
}

double searchK(int numberIteration, double x, double y, double h, double & deltaY, double * arrayY){
    if (numberIteration == 1){
        double k1 = function(x, y);
        arrayY[2] = k1;
        deltaY += k1;
        return k1;
    }else if (numberIteration == 4){
        double k4 = function(x + h, y + h * searchK(numberIteration - 1, x, y, h, deltaY, arrayY));
        arrayY[5] = k4;
        deltaY += k4;
        return deltaY;
    }else {
        double k2_3 = function(x + h/2, y + h*searchK(numberIteration - 1, x, y, h, deltaY,arrayY)/2);
        if (numberIteration == 2){
           arrayY[3] = k2_3;
        }else {
            arrayY[4] = k2_3;
        }
        deltaY += k2_3 * 2;
        return k2_3;
    }
}

void solutionOfTheEquation(double x, double y, double h, double ** arrayValueY){
    double deltaY = 0;

    arrayValueY[0][0] = x;
    arrayValueY[0][1] = y;
    deltaY = h * searchK(4, x, y, h, deltaY, arrayValueY[0]) / 6;
    arrayValueY[0][6] = deltaY;
    y += deltaY;

    for (int i = 1; i < int(1/h) + 1; i++){
        x += h;
        arrayValueY[i][0] = x;
        arrayValueY[i][1] = y;
        deltaY = h * searchK(4, arrayValueY[i][0], arrayValueY[i][1], h, deltaY, arrayValueY[i]) / 6;
        arrayValueY[i][6] = deltaY;
        y += deltaY;
        deltaY = 0;
    }
}

void enterZeroTask(double & y, double & h){
    string row;

    while(true) {
        cout << "Enter zero task: y " << endl;
        getline(cin, row);
        if (atof(row.c_str())) {
            y = atof(row.c_str());
            break;
        } else {
            cout << "Error: incorrect enter" << endl;
        }
    }

    while(true) {
        cout << "Enter step: h " << endl;
        getline(cin, row);
        if (atof(row.c_str())) {
            h = atof(row.c_str());
            break;
        } else {
            cout << "Error: incorrect enter" << endl;
        }
    }
}

void print(double * arrayY){
    int valueNumber = 4;
   // cout << "x  y  k1  k2  k3  k4  deltaY" << endl;
    cout << arrayY[0] << " " << setprecision(valueNumber) << arrayY[1] << " " << setprecision(valueNumber) << arrayY[2] << " " << setprecision(valueNumber) << arrayY[3] << " " << setprecision(valueNumber) << arrayY[4] << " " << setprecision(valueNumber) << arrayY[5] << " "  << setprecision(valueNumber) << arrayY[6] << endl;
}

int main() {
    //0 1 2  3  4  5  6
    //x y k1 k2 k3 k4 deltaY
    double h;
    double x = 0;
    double y;

    enterZeroTask(y, h);

    int countRow = int(1/h) + 1;
    int countArray = 7;

    double ** arrayValueY = new double*[countRow];
    for (int i = 0; i < countRow; i++){
        arrayValueY[i] = new double[countArray];
    }

    solutionOfTheEquation(x, y, h, arrayValueY);

    cout << "x y k1 k2 k3 k4 deltaY" << endl;
    for (int i = 0; i < countRow; i++){
        print(arrayValueY[i]);
    }

    for (int i = 0; i < countRow; i++){
        delete [] arrayValueY[i];
    }
    delete[] arrayValueY;

    return 0;
}