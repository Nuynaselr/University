package functions;

import java.io.Serializable;
import java.util.Objects;

// класс описывающий табулированную функцию, как двусвязный циклический список
public class LinkedListTabulatedFunction implements TabulatedFunction, Serializable, Cloneable {
    // класс описывающий ноду
    private class FunctionNode implements Serializable{
        private FunctionPoint value; // создание ноды
        private FunctionNode next; // ссылка на след. ноду
        private FunctionNode prev; // ссылка на предыдущию ноду

        // конструктор по умолчанию
        public FunctionNode() {
            this.value = null;
            this.next = null;
            this.prev = null;
        }

        // конструктор создания ноды с помощью значения, ссылки на след. ноду, ссылки на предыдущую ноду
        public FunctionNode(double value, FunctionNode prev, FunctionNode next) {
            this.value = new FunctionPoint(value, 0);
            this.prev = prev;
            this.next = next;
        }
        // конструктор создания ноды по значению
        public FunctionNode(double value) {
            this.value = new FunctionPoint(value, 0);
            this.next = null;
            this.prev = null;
        }
        // конструктор создания ноды с помощью точки
        public FunctionNode(FunctionPoint point) {
            this.value = point;
            this.next = null;
            this.prev = null;
        }
    }

    private FunctionNode head; // начало списка
    private FunctionNode tail; // конец списка
    private int countNode; // длина списка

    // конструктор по умолчанию
    public LinkedListTabulatedFunction() {
        this.head = null;
        this.tail = null;
        this.countNode = 0;
    }

    // конструктор создания списка по количеству точек
    public LinkedListTabulatedFunction(double left, double right, int count) throws InappropriateFunctionPointException {
        if (left > right || Math.abs(left - right) < SpecialTools.esp || count == 0) {
            throw new InappropriateFunctionPointException("Illegal arguments()");
        } else {
            double length = (right - left) / (count - 1);
            this.head = new FunctionNode(left);
            this.tail = new FunctionNode(right);
            connectionHeadTail(this.head, this.tail);
            for (int i = 1; i < count - 1; i++) {
                addNodeByIndex(i, new FunctionNode(left + length * i, getNodeByIndex(i - 1), getNodeByIndex(i)));
            }
            this.countNode = count;
        }
    }
    // конструктор создания по массиву значений
    public LinkedListTabulatedFunction(double left, double right, double[] array) throws InappropriateFunctionPointException {
        int count = array.length;
        if (left > right || Math.abs(left - right) < SpecialTools.esp || count == 0) {
            throw new InappropriateFunctionPointException("Illegal arguments()");
        } else {
            this.head = new FunctionNode(left);
            this.tail = new FunctionNode(right);
            connectionHeadTail(this.head, this.tail);
            for (int i = 1; i < count; i++) {
                addNodeByIndex(i, new FunctionNode(array[i], getNodeByIndex(i - 1), getNodeByIndex(i)));
            }
            this.countNode = count;
        }
    }
    // конструктор создания списка по массиву точек
    public LinkedListTabulatedFunction(FunctionPoint[] array) throws InappropriateFunctionPointException {
        if (array.length <= 2) {throw new InappropriateFunctionPointException("Illegal arguments(min length array = 2)");}
        FunctionNode newHead = new FunctionNode(array[0]);
        FunctionNode newTail = new FunctionNode(array[array.length - 1]);
        connectionHeadTail(newHead, newTail);
        FunctionNode newNode;
        for (int i = 1; i < array.length - 1; i++){
            newNode = new FunctionNode(array[i]);
            addNodeByIndex(i, newNode);
        }
    }

    // соединение двух нод
    private void connectionNode(FunctionNode left, FunctionNode right){
        left.next = right;
        right.prev = left;
    }
    // соединение начала и конца списка
    private void connectionHeadTail(FunctionNode head, FunctionNode tail){
        head.prev = tail;
        head.next = tail;
        tail.prev = head;
        tail.next = head;
    }
    // добавления точки между двумя другими
    private void addNode(FunctionNode left, FunctionNode newNode, FunctionNode right){
        left.next = newNode;
        right.prev = newNode;
        newNode.prev = left;
        newNode.next = right;
        this.countNode++;
    }
    //поиск по значению
    private int searchByValue(double value){
        FunctionNode timeNode = this.head;
        int i;
        for (i = 0; value < timeNode.value.getX(); i++){
            timeNode = timeNode.next;
        }
        return i;
    }

    // возвращение первого значния в спике
    public double getLeftDomainBorder(){
        return this.head.value.getX();
    }
    // возвращения второго значения в списке
    public double getRightDomainBorder(){
        return this.tail.value.getX();
    }

    // кахождения значения точки по ближайшей точки
    public double getFunctionValue(double x) {
        if (x < this.head.value.getX() && x > this.tail.value.getX()) { throw new FunctionPointIndexOutOfBoundsException("Index out of bounds"); }
        int i;
        if (this.head.value.getX() > x || this.tail.value.getX() < x) return Double.NaN;
        for (i = 1; i < this.countNode && this.getNodeByIndex(i).value.getX() < x; ++i) ;
        double leftX = this.getNodeByIndex(i - 1).value.getX();
        double leftY = this.getNodeByIndex(i - 1).value.getY();
        double rightX = this.getNodeByIndex(i).value.getX();
        double rightY = this.getNodeByIndex(i).value.getY();
        return ((rightY - leftY) * (x - leftX)) / (rightX - leftX) + leftY;
    }

    // возвращает длину списка
    public int getPointsCount(){
        return this.countNode;
    }

    // задание значения точки по индексу
    public void setPoint(int index, FunctionPoint point){
        if (Math.abs(point.getX() - this.head.value.getX()) < SpecialTools.esp || point.getX() < this.tail.value.getX() || point.getX() > this.head.value.getX()) {
            if (index < this.countNode && index >= 0) {
                this.getNodeByIndex(index).value.setXY(point.getX(), point.getY());
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
        if (x > this.head.value.getX() || x < this.tail.value.getX() || Math.abs(x - this.head.value.getX()) < SpecialTools.esp){
            if (index < this.countNode && index >= 0) this.getNodeByIndex(index).value.setX(x);
            else { throw new FunctionPointIndexOutOfBoundsException("Index out of bounds"); }
        } else throw new FunctionPointIndexOutOfBoundsException("Index out of bounds");
    }
    // задание значения y по индексу
    public void setPointY(int index, double y){
        if (index < this.countNode && index >= 0) this.getNodeByIndex(index).value.setY(y);
        else throw new FunctionPointIndexOutOfBoundsException("Index out of bounds");

    }

    // возвращение точки
    public FunctionPoint getPoint(int index){
        if (index > this.countNode || index < 0) throw new FunctionPointIndexOutOfBoundsException("Index out of bounds");
        return this.getNodeByIndex(index).value;
    }
    // возвращения значения x по индексу
    public double getPointX(int index){
        if (index < this.countNode && index >= 0) {
            return this.getNodeByIndex(index).value.getX();
        } else throw new FunctionPointIndexOutOfBoundsException("Index out of bounds");
    }
    // возвращения значения y по индексу
    public double getPointY(int index) {
        if (index < this.countNode && index >= 0) {
            return this.getNodeByIndex(index).value.getY();
        } else throw new FunctionPointIndexOutOfBoundsException("Index out of bounds");
    }

    // удаление точки по индексу
    public void deletePoint(int index) throws InappropriateFunctionPointException {
        connectionNode(getNodeByIndex(index - 1), getNodeByIndex(index + 1));
        this.countNode --;
    }

    // добавление точки
    public void addPoint(FunctionPoint point) {
        FunctionNode newNode = new FunctionNode(point);
        addNodeByIndex(searchByValue(point.getX()), newNode);
    }

    // возвращение точки по индексу
    public FunctionNode getNodeByIndex(int index) {
        FunctionNode timeValue = this.head;
        for (int i = 0; i != index; i++) {
            timeValue = timeValue.next;
        }
        return timeValue;
    }

    // добавление точки в конец
    public FunctionNode addNodeToTail(double x) {
        FunctionNode newPoint = new FunctionNode(x);
        newPoint.prev = this.tail;
        newPoint.next = this.head;
        this.head.prev = newPoint;
        this.tail.next = newPoint;
        this.tail = newPoint;
        this.countNode++;
        return newPoint;
    }
    // добавление точки по индексу через ноду
    private FunctionNode addNodeByIndex(int index, FunctionNode newNode){
        addNode(getNodeByIndex(index - 1), newNode, getNodeByIndex(index));
        return newNode;
    }
    // добавление точки по индексу через значение
    public FunctionNode addNodeByIndex(int index, double x){
        FunctionNode newNode = new FunctionNode(x);
        addNode(getNodeByIndex(index - 1), newNode, getNodeByIndex(index));
        return newNode;
    }

    // удаление ноды
    public void deleteNodeByIndex(int index){
        connectionNode(getNodeByIndex(index - 1), getNodeByIndex(index + 1));
        this.countNode --;
    }

    public String toString(){
        String str = new String();
        str += "{ ";
        for (int i = 0; i < this.countNode - 1; i++){
            str += this.getPoint(i).toString() + ", ";
        }
        str += this.getPoint(this.countNode - 1).toString() + "}";
        return str;
    }

    public boolean equals(Object o){
        boolean bo = true;
        for (int i = 0; i < this.countNode - 1; i++){
            bo = bo && this.getPoint(i) == ((LinkedListTabulatedFunction) o).getPoint(i);
        }
        return o instanceof LinkedListTabulatedFunction && bo && ((LinkedListTabulatedFunction) o).countNode == this.countNode;
    }

    public int hashCode(){
        int sum = 0;
        for (int i = 0; i < this.countNode - 1; i++){
            sum += Objects.hash(this.getPoint(i));
        }
        return Objects.hash(this.countNode) + sum;
    }

    public Object clone() throws CloneNotSupportedException {
        LinkedListTabulatedFunction res = new LinkedListTabulatedFunction();
        res.head = new FunctionNode(this.head.value);
        res.tail = new FunctionNode(this.tail.value);
        res.connectionHeadTail(res.head, res.tail);
        FunctionNode newNode;
        for (int i = 1; i < this.countNode - 1; i++){
            newNode = new FunctionNode(this.getPoint(i));
            res.addNodeByIndex(i, newNode);
        }
        return res;
    }
}