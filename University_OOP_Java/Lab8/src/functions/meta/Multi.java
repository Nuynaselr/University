package functions.meta;

import functions.Function;

// класс реализующий перемножение значений функций
public class Multi implements Function{
    private Function firstFun;
    private Function secondFun;

    public Multi(Function firstFun, Function secondFun){
        this.firstFun = firstFun;
        this.secondFun = secondFun;
    }

    @Override
    public double getLeftDomainBorder() {
        return this.firstFun.getLeftDomainBorder() <= this.secondFun.getLeftDomainBorder() ? this.firstFun.getLeftDomainBorder() : this.secondFun.getLeftDomainBorder();
    }

    @Override
    public double getRightDomainBorder() {
        return this.firstFun.getRightDomainBorder() <= this.secondFun.getRightDomainBorder() ? this.firstFun.getRightDomainBorder() : this.secondFun.getRightDomainBorder();
    }

    @Override
    public double getFunctionValue(double x) {
        return this.firstFun.getFunctionValue(x) * this.secondFun.getFunctionValue(x);
    }
}
