package functions;

public class TabulatedFunction{
    private FunctionPoint[] arrayPoint;
    private double machineEsp;
    private int length;

    private void machineEps() {
        double eps = 1.0;
        while (1.0 + 0.5 * eps != 1.0) {
            eps *= 0.5;
        }
        this.machineEsp = eps;
    }

    public TabulatedFunction(double leftX, double rightX, int pointsCount){
        double lengthBePoint = (rightX - leftX)/(double)pointsCount;
        this.arrayPoint = new FunctionPoint[pointsCount];
        this.length = pointsCount;
        for (int i = 0; i < pointsCount; i++){
            this.arrayPoint[i] = new FunctionPoint(leftX + i * lengthBePoint, 0);
        }
    }
    public TabulatedFunction(double leftX, double rightX, double[] values){
        int countPoint = values.length;
        double lengthBePoint = (rightX - leftX)/(double)countPoint;
        this.arrayPoint = new FunctionPoint[countPoint];
        for (int i = 0; i < countPoint; i++){
            this.arrayPoint[i].setXY(leftX + i * lengthBePoint, values[i]);
        }
    }

    public double getLeftDomainBorder(){
        return this.arrayPoint[0].getX();
    }
    public double getRightDomainBorder(){
        return this.arrayPoint[this.length - 1].getX();
    }
    public double getFunctionValue(double x){
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
    }
    public FunctionPoint getPoint(int index){
        if (index > this.length || index < 0) return new FunctionPoint(0,0);
        return this.arrayPoint[index];
    }
    public void setPoint(int index, FunctionPoint point){
        machineEps();
        if (Math.abs(point.getX() - this.arrayPoint[0].getX()) < machineEsp || point.getX() < this.arrayPoint[this.length - 1].getX() || point.getX() > this.arrayPoint[0].getX())
            if (index < this.length && index >= 0) {
                this.arrayPoint[index].setXY(point.getX(), point.getY());
            }
    }
    public double getPointX(int index){
        if (index < this.length && index >= 0) {
            return this.arrayPoint[index].getX();
        }
        return 0;
    }
    public void setPointX(int index, double x){
        if (x > this.arrayPoint[0].getX() || x < this.arrayPoint[this.length - 1].getX() || Math.abs(x - this.arrayPoint[0].getX()) < machineEsp){
            if (index < this.length && index >= 0) this.arrayPoint[index].setX(x);
        }
    }
    public double getPointY(int index){
        if (index < this.length && index >= 0) {
            return this.arrayPoint[index].getY();
        }
        return 0;
    }
    public void setPointY(int index, double y){

        if (index < this.length && index >= 0) this.arrayPoint[index].setX(y);

    }

    public void deletePoint(int index){
        this.arrayPoint[index] = null;
        FunctionPoint[] newArrayPoint = new FunctionPoint[this.length - 1];
        System.arraycopy(this.arrayPoint, 0, newArrayPoint, 0, index);
        for (int i = index; i < this.length - 1; i++){
            newArrayPoint[i] = new FunctionPoint(this.arrayPoint[i + 1]);
        }
        this.length --;
        arrayPoint = newArrayPoint;
    }
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
}