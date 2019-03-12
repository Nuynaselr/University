package functions.meta;

import functions.Function;

// класс реализующий сдвиг по осям
public class Shift implements Function{
    private Function func;
    private double xVal;
    private double yVal;

    public Shift(Function newFunc, double newXVal, double newYVal){
        this.func = newFunc;
        this.xVal = newXVal;
        this.yVal = newYVal;
    }

    @Override
    public double getLeftDomainBorder() {
        return this.func.getLeftDomainBorder() + this.xVal;
    }

    @Override
    public double getRightDomainBorder() {
        return this.func.getRightDomainBorder() + this.xVal;
    }

    @Override
    public double getFunctionValue(double x) {
        return this.func.getFunctionValue(x + this.xVal) + this.yVal;
    }
}
