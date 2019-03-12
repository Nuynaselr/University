package functions.meta;

import functions.Function;

// класс реализующий масштабирование
public class Scale implements Function{
    private Function func;
    private double xVal;
    private double yVal;

    public Scale(Function newFunc, double newXVal, double newYVal){
        this.func = newFunc;
        this.xVal = newXVal;
        this.yVal = newYVal;
    }

    @Override
    public double getLeftDomainBorder() {
        return this.func.getLeftDomainBorder() * this.xVal;
    }

    @Override
    public double getRightDomainBorder() {
        return this.func.getRightDomainBorder() * this.xVal;
    }

    @Override
    public double getFunctionValue(double x) {
        return this.func.getFunctionValue(x * this.xVal) * this.yVal;
    }
}
