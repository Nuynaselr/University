#include <iostream>
#include <vector>
#include <fstream>
#include <ctime>
#include <windows.h>

using namespace std;
struct Node {
    double value; //Элемент содержащий значение, ссыслку на следующий и индекс
    Node *pointer;
    int index;
};

class MyList
{
    /*
     * класс один болььшой список содержащий в себе элементы(Node)
     */
private:
    int size;
    Node *head, *tail;
public:
    MyList(): head(NULL), tail(NULL), size(0){};
    ~MyList(){
        while (size != 0)
        {
            Node *temp = head->pointer;
            delete head;
            head = temp;
            size--;
        }
    };

    void printAll(vector<double> number, MyList &list, double timeWork, int typeSort)
    {
        /*
         * Вывод списка в файл
         */
        ofstream fout("output.txt", ios_base::app); // открытие файла для дозаписи
        Node *temph = list.head;
        int temp = size;

        switch (typeSort)
        {
            case 1:
                fout << "Random number: " << endl;
                cout << "Random number: " << endl;
                break;
            case 2:
                fout << "NearleSorted array: " << endl;
                cout << "NearleSorted array: " << endl;
                break;
            case 3:
                fout << "Reverse array: " << endl;
                cout << "Reverse array: " << endl;
                break;
        }
        fout << "Original array:" << endl;
        for(int i = 0; i < number.size(); i++)
        {
            fout << number[i] << ' ';
        }

        fout << "\nResult array: " << endl;
        while (temp)
        {
            fout << temph->value << " ";
            temph = temph->pointer;
            temp--;
        }
        fout << "\n";
        fout << "Time work sort: " << timeWork << endl;
        fout << endl;
        fout.close();
    }

    void addElement(double value)
    {
        /*
         * Функция для добавления элементов в список
         */
        Node *temp = new Node;
        temp->value = value;
        temp->pointer = head;
        temp->index = size;
        size++;
        if (!head)
        {
            head = temp;
            tail = head;
        }
        else {
            tail->pointer = temp;
            tail = temp;
        }
    }

    Node *get(const int &index) {
        /*
         * возвращение элемента списка по индексу
         */
        Node *x = head;
        for (int i = 0;; i++) {
            if (x->index == index)
            {
                return x;
            }
            x = x->pointer;
        }
    }

    void set(const int &index,const double value) {
        /*
         * присваивание значение элементу по индексу
         */
        Node *target = this->get(index);
        target->value = value;
    }

    void createPyramid(MyList & list, int parent, int limit)
    {
        /*
         * создание кучи для пирамидальной сортировки
         */
        int maxChild;
        bool done = false;
        while ((parent * 2 <= limit) && (!done))
        {
            if (parent * 2 == limit)
            {
                maxChild = parent * 2;
            }
            else if (list.get(parent * 2)->value > list.get(parent * 2 + 1)->value)
            {
                maxChild = parent * 2;
            }
            else
            {
                maxChild = parent * 2 + 1;
            }

            if(list.get(parent)->value < list.get(maxChild)->value)
            {
                double swapNum;
                swapNum = list.get(parent)->value;
                list.set(parent, list.get(maxChild)->value);
                list.set(maxChild, swapNum);

                parent = maxChild;
            }
            else
            {
                done = true;
            }
        }
    }

    double jsort(MyList & sortNode)
    {
        /*
         * сортировка пирамидой
         */
        LARGE_INTEGER start, finish, freq; // переменные для расчета времени
        QueryPerformanceFrequency(&freq); // для перевода в секунды
        QueryPerformanceCounter(&start); // начело отсчета

        int spSize = size;
        for(int i  = (size / 2) - 1; i >= 0; i--)
        {
            createPyramid(sortNode, i, spSize);
        }
        for(int i = size - 1; i >= 1; i--)
        {
            double swapNum;
            swapNum = sortNode.get(0)->value;
            sortNode.set(0, sortNode.get(i)->value);
            sortNode.set(i, swapNum);

            createPyramid(sortNode, 0, i - 1);
        }

        QueryPerformanceCounter(&finish); // конец отсчета
        return (finish.QuadPart - start.QuadPart) / (double) freq.QuadPart; // возвращение времени в секундах
    }
};

void randomNumber(vector<double> numArr, int number)
{
/*
 * генерирование рандомного числа вещественного типа
 * генерируется целая часть
 * после генерируется дробная часть и прибавляется
 */
    double timeWork;
    MyList list;
    double timeNum;
    for(int i = 0; i < number; i++)
    {
        timeNum = (rand() % number + 1) + double(rand() % 1000 + 1) / 1000;
        numArr.push_back(timeNum);
        list.addElement(timeNum);
    }
    timeWork = list.jsort(list);
    list.printAll(numArr, list, timeWork, 1);
    cout << "Time work: " << timeWork << endl;
    numArr.clear();
}

void nearleSorted(vector<double> numArr, int number)
{
    /*
     * генерируется почти отсортированный список
     * последовательно берутся числа
     * к ним прибавляются рандомно генерирующиеся дробные части
     * каждый 20 полностью рандомный
     */
    MyList list;
    double timeWork;
    double timeNum;
    for(int i = 0; i <= number; i++)
    {
        if (i % 20 == 0)
        {
            timeNum = (rand() % number + 1) + double(rand() % 1000 + 1) / 1000;
            numArr.push_back(timeNum);
            list.addElement(timeNum);
        }
        else
        {
            timeNum = i + double(rand() % 1000 + 1) / 1000;
            numArr.push_back(timeNum);
            list.addElement(timeNum);
        }
    }
    timeWork = list.jsort(list);
    list.printAll(numArr,list, timeWork, 2);
    cout << "Time work: " << timeWork << endl;
    numArr.clear();
}

void reversedArray(vector<double> numArr, int number)
{
    /*
     * отсортированный в обратном порядке массив
     * проходим по значению с number до 0 и прибавляем
     * рандомно генерирующуюся дробную часть
     */
    MyList list;
    double timeWork;
    double timeNum;
    for(int i = number; i >= 0; i--)
    {
        timeNum = i + double(rand() % 1000 + 1) / 1000;
        numArr.push_back(timeNum);
        list.addElement(timeNum);
    }
    timeWork = list.jsort(list);
    list.printAll(numArr, list, timeWork, 3);
    cout << "Time work: " << timeWork << endl;
    numArr.clear();
}

void clearFile()
{
    /*
     * очистка файла
     */
    ofstream f("output.txt");
    f.close();
}

int main()
{
    srand(time(0));

    int cardinality;
    vector<double> number;
    cout << "1 - Random number\n2 - NearleSorted\n3 - Reversed array" << endl;
    cout << "Number: ";
    cin >> cardinality;
    cin.get();

    clearFile();

    randomNumber(number, cardinality);
    nearleSorted(number, cardinality);
    reversedArray(number, cardinality);

    number.clear();

    return 0;
}