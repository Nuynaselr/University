package functions;

public class LinkedListTabulatedFunction implements TabulatedFunction{
    private class FunctionNode {
        private FunctionPoint value;
        private FunctionNode next;
        private FunctionNode prev;

        public FunctionNode() {
            this.value = null;
            this.next = null;
            this.prev = null;
        }

        public FunctionNode(double value, FunctionNode prev, FunctionNode next) {
            this.value = new FunctionPoint(value, 0);
            this.prev = prev;
            this.next = next;
        }
        public FunctionNode(double value) {
            this.value = new FunctionPoint(value, 0);
            this.next = null;
            this.prev = null;
        }
        public FunctionNode(FunctionPoint point) {
            this.value = point;
            this.next = null;
            this.prev = null;
        }
    }

    private FunctionNode head;
    private FunctionNode tail;
    private int countNode;

    public LinkedListTabulatedFunction() {
        this.head = null;
        this.tail = null;
        this.countNode = 0;
    }

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


    private void connectionNode(FunctionNode left, FunctionNode right){
        left.next = right;
        right.prev = left;
    }
    private void connectionHeadTail(FunctionNode head, FunctionNode tail){
        head.prev = tail;
        head.next = tail;
        tail.prev = head;
        tail.next = head;
    }
    private void addNode(FunctionNode left, FunctionNode newNode, FunctionNode right){
        left.next = newNode;
        right.prev = newNode;
        newNode.prev = left;
        newNode.next = right;
        this.countNode++;
    }
    private int searchByValue(double value){
        FunctionNode timeNode = this.head;
        int i;
        for (i = 0; value < timeNode.value.getX(); i++){
            timeNode = timeNode.next;
        }
        return i;
    }

    public double getLeftDomainBorder(){
        return this.head.value.getX();
    }
    public double getRightDomainBorder(){
        return this.tail.value.getX();
    }

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

    public int getPointsCount(){
        return this.countNode;
    }

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
    public void setPointX(int index, double x){
        if (x > this.head.value.getX() || x < this.tail.value.getX() || Math.abs(x - this.head.value.getX()) < SpecialTools.esp){
            if (index < this.countNode && index >= 0) this.getNodeByIndex(index).value.setX(x);
            else { throw new FunctionPointIndexOutOfBoundsException("Index out of bounds"); }
        } else throw new FunctionPointIndexOutOfBoundsException("Index out of bounds");
    }
    public void setPointY(int index, double y){
        if (index < this.countNode && index >= 0) this.getNodeByIndex(index).value.setY(y);
        else throw new FunctionPointIndexOutOfBoundsException("Index out of bounds");

    }

    public FunctionPoint getPoint(int index){
        if (index > this.countNode || index < 0) throw new FunctionPointIndexOutOfBoundsException("Index out of bounds");
        return this.getNodeByIndex(index).value;
    }
    public double getPointX(int index){
        if (index < this.countNode && index >= 0) {
            return this.getNodeByIndex(index).value.getX();
        } else throw new FunctionPointIndexOutOfBoundsException("Index out of bounds");
    }
    public double getPointY(int index) {
        if (index < this.countNode && index >= 0) {
            return this.getNodeByIndex(index).value.getY();
        } else throw new FunctionPointIndexOutOfBoundsException("Index out of bounds");
    }

    public void deletePoint(int index) throws InappropriateFunctionPointException {
        connectionNode(getNodeByIndex(index - 1), getNodeByIndex(index + 1));
        this.countNode --;
    }

    public void addPoint(FunctionPoint point) {
        FunctionNode newNode = new FunctionNode(point);
        addNodeByIndex(searchByValue(point.getX()), newNode);
    }

    public FunctionNode getNodeByIndex(int index) {
        FunctionNode timeValue = this.head;
        for (int i = 0; i != index; i++) {
            timeValue = timeValue.next;
        }
        return timeValue;
    }

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
    private FunctionNode addNodeByIndex(int index, FunctionNode newNode){
        addNode(getNodeByIndex(index - 1), newNode, getNodeByIndex(index));
        return newNode;
    }
    public FunctionNode addNodeByIndex(int index, double x){
        FunctionNode newNode = new FunctionNode(x);
        addNode(getNodeByIndex(index - 1), newNode, getNodeByIndex(index));
        return newNode;
    }

    public void deleteNodeByIndex(int index){
        connectionNode(getNodeByIndex(index - 1), getNodeByIndex(index + 1));
        this.countNode --;
    }
}