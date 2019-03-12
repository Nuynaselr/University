package functions.meta;

import functions.Function;

// класс реализующий возврат значения по значению второй функции
public class Composition implements Function{
    private Function firstFunc;
    private Function secondFunc;

    public Composition(Function newFirstFunc, Function newSecondFunc){
        this.firstFunc = newFirstFunc;
        this.secondFunc = newSecondFunc;
    }

    @Override
    public double getLeftDomainBorder() {
        return firstFunc.getLeftDomainBorder();
    }

    @Override
    public double getRightDomainBorder() {
        return firstFunc.getRightDomainBorder();
    }

    @Override
    public double getFunctionValue(double x) {
        return firstFunc.getFunctionValue(secondFunc.getFunctionValue(x));
    }
}
