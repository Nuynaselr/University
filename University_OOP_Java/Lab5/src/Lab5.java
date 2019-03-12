import functions.*;
import functions.basic.*;
import functions.meta.*;

import java.io.*;

class Lab5 {
    public static void main(String[] s) throws InappropriateFunctionPointException, IOException, ClassNotFoundException, CloneNotSupportedException {
        ArrayTabulatedFunction list = new ArrayTabulatedFunction(1, 9, 6);
        LinkedListTabulatedFunction list3 = new LinkedListTabulatedFunction(1, 10, 8);
        FunctionPoint point = new FunctionPoint(10, 15);

        System.out.println(point.toString());
        System.out.println(list.toString());

        System.out.println(list.equals(list));
        System.out.println(list.hashCode());

        TabulatedFunction list2 = (ArrayTabulatedFunction) list.clone();
        list2.deletePoint(1);

        LinkedListTabulatedFunction list4 = (LinkedListTabulatedFunction) list3.clone();
        list4.deleteNodeByIndex(1);

        System.out.println(point.equals(list2));

    }


}