package functions.meta;

import functions.Function;

// класс реализующий возведение значение функции в степень pow
public class Power implements Function{
    private Function func;
    private double pow;

    public Power(Function newFunc, double pow){
        this.func = newFunc;
        this.pow = pow;
    }

    @Override
    public double getLeftDomainBorder() {
        return this.func.getLeftDomainBorder();
    }

    @Override
    public double getRightDomainBorder() {
        return this.func.getRightDomainBorder();
    }

    @Override
    public double getFunctionValue(double x) {
        return Math.pow(this.func.getFunctionValue(x), this.pow);
    }
}
