package functions;

import java.io.Serializable;
import java.util.Objects;

public class ArrayTabulatedFunction implements TabulatedFunction, Serializable, Cloneable{
    private FunctionPoint[] arrayPoint; // массив точек
    private int length; //длинна данного массива(переменная создана, чтобы не вызывать функцию расчета длины массива)

    // конструктор построения массива, через количество точек
    public ArrayTabulatedFunction(double leftX, double rightX, int pointsCount) throws InappropriateFunctionPointException {
        if (leftX > rightX) {
            throw new FunctionPointIndexOutOfBoundsException("Right value less than left value");
        }
        if (pointsCount < 2){
            throw new InappropriateFunctionPointException("Not enough points (min 2 points)");
        }
        double lengthBePoint = (rightX - leftX)/(double)pointsCount;
        this.arrayPoint = new FunctionPoint[pointsCount];
        this.length = pointsCount;
        for (int i = 0; i < pointsCount; i++){
            this.arrayPoint[i] = new FunctionPoint(leftX + i * lengthBePoint, 0);
        }
    }
    // конструктор потсроения массива, через массив занчений(минимальное еоличество значений: )
    public ArrayTabulatedFunction(double leftX, double rightX, double[] values) throws InappropriateFunctionPointException {
        int countPoint = values.length;
        if (countPoint < 2){
            throw new InappropriateFunctionPointException("Not enough points (min 2 points)");
        }
        if (leftX > rightX) {
            throw new FunctionPointIndexOutOfBoundsException("Right value less than left value");
        }
        double lengthBePoint = (rightX - leftX)/(double)countPoint;
        this.arrayPoint = new FunctionPoint[countPoint];
        for (int i = 0; i < countPoint; i++){
            this.arrayPoint[i].setXY(leftX + i * lengthBePoint, values[i]);
        }
    }
    // конструктор построения массива, через массив точек
    public ArrayTabulatedFunction(FunctionPoint[] array){
        this.arrayPoint = array;
        this.length = array.length;
    }
    public ArrayTabulatedFunction(ArrayTabulatedFunction func){
        this.arrayPoint = func.arrayPoint;
        this.length = func.length;
    }

    public double getLeftDomainBorder(){
        return this.arrayPoint[0].getX();
    } // значение первого/самого левого элемента
    public double getRightDomainBorder(){
        return this.arrayPoint[this.length - 1].getX();
    } // значение последнего/самого правого элемента

    //расчет значения функции, с помощью рядом лежащей точкиы
    public double getFunctionValue(double x){
        if (x < this.arrayPoint[0].getX() && x > this.arrayPoint[this.length - 1].getX()) { throw new FunctionPointIndexOutOfBoundsException("Index out of bounds"); }
        int i;
        if (this.arrayPoint[0].getX() > x || this.arrayPoint[this.length - 1].getX() < x) return Double.NaN;
        for (i = 1; i < this.length && this.arrayPoint[i].getX() < x; ++i) ;
        double leftX = this.arrayPoint[i - 1].getX();
        double leftY = this.arrayPoint[i - 1].getY();
        double rightX = this.arrayPoint[i].getX();
        double rightY = this.arrayPoint[i].getY();
        return ((rightY - leftY) * (x - leftX)) / (rightX - leftX) + leftY;
    }
    public int getPointsCount(){
        return this.length;
    } //возвращение длины массива

    // задание значение точки по индексу
    public void setPoint(int index, FunctionPoint point){
        if (Math.abs(point.getX() - this.arrayPoint[0].getX()) < SpecialTools.esp || point.getX() < this.arrayPoint[this.length - 1].getX() || point.getX() > this.arrayPoint[0].getX()) {
            if (index < this.length && index >= 0) {
                this.arrayPoint[index].setXY(point.getX(), point.getY());
            }
            else {
                throw new FunctionPointIndexOutOfBoundsException("Index out of bounds");
            }
        }
        else {
            throw new FunctionPointIndexOutOfBoundsException("Index out of bounds");
        }
    }
    // задание значения x по индексу
    public void setPointX(int index, double x){
        if (x > this.arrayPoint[0].getX() || x < this.arrayPoint[this.length - 1].getX() || Math.abs(x - this.arrayPoint[0].getX()) < SpecialTools.esp){
            if (index < this.length && index >= 0) this.arrayPoint[index].setX(x);
            else { throw new FunctionPointIndexOutOfBoundsException("Index out of bounds"); }
        } else throw new FunctionPointIndexOutOfBoundsException("Index out of bounds");
    }
    // задание значения y по индексу
    public void setPointY(int index, double y){
        if (index < this.length && index >= 0) this.arrayPoint[index].setY(y);
        else throw new FunctionPointIndexOutOfBoundsException("Index out of bounds");

    }

    // возвращение точки, по индексу
    public FunctionPoint getPoint(int index){
        if (index > this.length || index < 0) throw new FunctionPointIndexOutOfBoundsException("Index out of bounds");
        return this.arrayPoint[index];
    }
    // возвращение значения x по индексу
    public double getPointX(int index){
        if (index < this.length && index >= 0) {
            return this.arrayPoint[index].getX();
        } else throw new FunctionPointIndexOutOfBoundsException("Index out of bounds");
    }
    // возвращений значения y по индексу
    public double getPointY(int index) {
        if (index < this.length && index >= 0) {
            return this.arrayPoint[index].getY();
        } else throw new FunctionPointIndexOutOfBoundsException("Index out of bounds");
    }

    // удаление точки
    public void deletePoint(int index) throws InappropriateFunctionPointException {
        if (this.length - 1 <= 2) throw new InappropriateFunctionPointException("minimal point: 3");
        this.arrayPoint[index] = null;
        FunctionPoint[] newArrayPoint = new FunctionPoint[this.length - 1];
        System.arraycopy(this.arrayPoint, 0, newArrayPoint, 0, index);
        for (int i = index; i < this.length - 1; i++){
            newArrayPoint[i] = new FunctionPoint(this.arrayPoint[i + 1]);
        }
        this.length --;
        arrayPoint = newArrayPoint;
    }

    // добавление точки(добавляется в то место, чтобы массив сразу был отсортированный)
    public void addPoint(FunctionPoint point){
        int index = 0;
        for (int i = 0; i < this.length; i++){
            if (point.getX() < this.arrayPoint[i].getX()) {
                index = i;
                break;
            }
        }
        FunctionPoint[] newArrayPoint = new FunctionPoint[this.length + 1];
        System.arraycopy(this.arrayPoint, 0, newArrayPoint, 0, index);
        newArrayPoint[index] = new FunctionPoint(point);
        for (int i = index; i < this.length; i++){
            newArrayPoint[i + 1] = new FunctionPoint(this.arrayPoint[i]);
        }
        this.length ++;
        arrayPoint = newArrayPoint;
    }

    public String toString(){
        String str = new String();
        str += "{ ";
        for (int i = 0; i < this.length - 1; i++){
            str += this.arrayPoint[i].toString() + ", ";
        }
        str += this.arrayPoint[this.length - 1].toString() + "}";
        return str;
    }

    public boolean equals(Object o){
        return o instanceof ArrayTabulatedFunction && ((ArrayTabulatedFunction) o).arrayPoint == this.arrayPoint && ((ArrayTabulatedFunction) o).length == this.length;
    }

    public int hashCode(){
        return Objects.hash(this.length) + Objects.hash(this.arrayPoint);
    }

    public Object clone() throws CloneNotSupportedException {
        ArrayTabulatedFunction res = (ArrayTabulatedFunction) super.clone();
        res.arrayPoint = (FunctionPoint[]) res.arrayPoint.clone();
        return res;
    }
}
