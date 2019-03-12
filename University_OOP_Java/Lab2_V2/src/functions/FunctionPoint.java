package functions;

public class FunctionPoint{
            private double x, y;

            public FunctionPoint(){
                this.x = 0;
                this.y = 0;
            }
            public FunctionPoint(double x, double y){
                this.x = x;
                this.y = y;
            }
            public FunctionPoint(FunctionPoint point ){
                this.x = point.x;
                this.y = point.y;
            }

            public void setX(double x){
                    this.x = x;
            }
            public void setY(double y){
                    this.y = y;
            }
            public void setXY(double x, double y){
                    this.x = x;
                    this.y = y;
            }

            public double getX(){
                return this.x;
            }
            public double getY(){
                return this.y;
            }
}