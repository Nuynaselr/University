#include <iostream>
#include <vector>
#include <ctime>

using  namespace std;

void createPyramid(vector<int> & number, int parent, int limit)
{
    int maxChild;
    bool done = false;
    while ((parent * 2 <= limit) && (!done))
    {
        if (parent * 2 == limit)
        {
            maxChild = parent * 2;
        }
        else if (number[parent * 2] > number[parent * 2 + 1])
        {
            maxChild = parent * 2;
        }
        else
        {
            maxChild = parent * 2 + 1;
        }

        if(number[parent] < number[maxChild])
        {
            int swapNum;
            swapNum = number[parent];
            number[parent] = number[maxChild];
            number[maxChild] = swapNum;

            parent = maxChild;
        }
        else
        {
            done = true;
        }
    }
}

void jsort(vector<int> & number, int size)
{
    for(int i  = (size / 2) - 1; i >= 0; i--)
    {
        createPyramid(number, i, size);
    }
    for(int i = size - 1; i >= 1; i--)
    {
        int swapNum;
        swapNum = number[0];
        number[0] = number[i];
        number[i] = swapNum;

        createPyramid(number, 0, i - 1);
    }
}

void printAll(vector<int> & number)
{
    for(int i = 0; i < number.size(); i++)
    {
        cout << number[i] << ' ';
    }
    cout << endl;

    jsort(number,  number.size());

    for(int i = 0; i < number.size(); i++)
    {
        cout << number[i] << ' ';
    }
    cout << endl;
}

int main() {

    vector<int> number;

    srand(time(0));
    int max = 1000;
    for (int i = 0; i < 100; i++)
    {
        number.push_back(rand() % max + 1);
    }

    printAll(number);

    return 0;
}