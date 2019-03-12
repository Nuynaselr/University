import functions.*;
import functions.basic.*;
import functions.meta.*;

import java.io.*;

class Lab4 {
    public static void main(String[] s) throws InappropriateFunctionPointException, IOException, ClassNotFoundException {
        LinkedListTabulatedFunction list = new LinkedListTabulatedFunction(1, 9, 3);
        System.out.println(list.getPointX(1) + "    " + list.getPointsCount());
        list.addNodeToTail(656);
        System.out.println(list.getPointsCount() + "    " + list.getPointX(3));

        ArrayTabulatedFunction valTabSin = new ArrayTabulatedFunction((ArrayTabulatedFunction) TabFunctions.tabulate(new Sin(), 0, 2 * Math.PI, 10));
        ArrayTabulatedFunction valTabCos = new ArrayTabulatedFunction((ArrayTabulatedFunction) TabFunctions.tabulate(new Cos(), 0, 2 * Math.PI, 10));

        System.out.println("Cos: + CosTab: ");
        Cos valueC = new Cos();
        System.out.println(valueC.getLeftDomainBorder());
        System.out.println(valueC.getRightDomainBorder());
        for (double i = 0; i < 2 * Math.PI; i += 0.1){
            System.out.println(valueC.getFunctionValue(i) + "   " + valTabCos.getFunctionValue(i));
        }

        System.out.println("Sin: + SinTab: ");
        Sin valueS = new Sin();
        System.out.println(valueS.getLeftDomainBorder());
        System.out.println(valueS.getRightDomainBorder());
        for (double i = 0; i < 2 * Math.PI; i += 0.1){
            System.out.println(valueS.getFunctionValue(i)  + "   " + valTabSin.getFunctionValue(i));
        }

        ArrayTabulatedFunction valSumPowSinCos = new ArrayTabulatedFunction((ArrayTabulatedFunction) TabFunctions.tabulate(new Sum(new Power(new Sin(), 2), new Power(new Cos(), 2)), 0, 2 * Math.PI, 11));
        ArrayTabulatedFunction valSumPowSinCos2 = new ArrayTabulatedFunction((ArrayTabulatedFunction) TabFunctions.tabulate(new Sum(new Power(new Sin(), 2), new Power(new Cos(), 2)), 0, 2 * Math.PI, 6));


        TabulatedFunction valueExp = TabFunctions.tabulate(new Exp(), 0, 11, 10);

        try (FileWriter fileWrite = new FileWriter("test.txt", false)){
            TabFunctions.writeTabulatedFunction(valueExp, fileWrite);
        }
        catch (IOException ex){
            System.out.println(ex.getMessage());
        }

        TabulatedFunction valueExp2 = new ArrayTabulatedFunction();

        try (FileReader fileRead = new FileReader("test.txt")){
            valueExp2 = TabFunctions.readTabulatedFunction(fileRead);
        }
        catch (IOException ex){
            System.out.println(ex.getMessage());
        }

        TabulatedFunction valueExp3 = TabFunctions.tabulate(new Exp(), 0, 11, 10);

        try (FileOutputStream fileWrite = new FileOutputStream("test2.txt", false)){
            TabFunctions.outputTabulatedFunction(valueExp3, fileWrite);
        }
        catch (IOException ex){
            System.out.println(ex.getMessage());
        }

        TabulatedFunction valueExp4 = new ArrayTabulatedFunction();

        try (FileInputStream fileRead = new FileInputStream("test2.txt")){
            valueExp4 = TabFunctions.inputTabulatedFunction(fileRead);
        }
        catch (IOException ex){
            System.out.println(ex.getMessage());
        }

        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("serial.txt"));
        TabulatedFunction func = new LinkedListTabulatedFunction(0,10,10);
        out.writeObject(func);
        out.close();

        ObjectInputStream in = new ObjectInputStream(new FileInputStream("serial.txt"));
        LinkedListTabulatedFunction func3 = null;
        func3 = (LinkedListTabulatedFunction) in.readObject();
        in.close();
    }


}