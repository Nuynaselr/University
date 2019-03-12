package functions.meta;

import functions.Function;

// класс реализующий сумму значений функций
public class Sum implements Function{
    private Function firstFun;
    private Function secondFun;

    public Sum(Function firstFun, Function secondFun){
        this.firstFun = firstFun;
        this.secondFun = secondFun;
    }

    @Override
    public double getLeftDomainBorder() {
        return this.firstFun.getLeftDomainBorder() <= this.secondFun.getLeftDomainBorder() ? this.firstFun.getLeftDomainBorder() : this.secondFun.getLeftDomainBorder() ;
    }

    @Override
    public double getRightDomainBorder() {
        return this.firstFun.getRightDomainBorder() <= this.secondFun.getRightDomainBorder() ? this.firstFun.getRightDomainBorder() : this.secondFun.getRightDomainBorder();
    }

    @Override
    public double getFunctionValue(double x) {
        return this.firstFun.getFunctionValue(x) + this.secondFun.getFunctionValue(x);
    }
}
