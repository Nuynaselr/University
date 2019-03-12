package functions;

import java.io.Serializable;
import java.util.Objects;

public class FunctionPoint implements Serializable, Cloneable {
            private double x, y;

            public FunctionPoint(int pointCounts){
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

            public String toString(){
                return "( " + this.x + ", " + this.y + ")";
            }

            public boolean equals(Object o){
                return o instanceof FunctionPoint && ((FunctionPoint) o).getX() == this.x && ((FunctionPoint) o).getY() == this.y;
            }

            public int hashCode(){
                return Objects.hash(this.x, this.y);
            }

            public Object clone() throws CloneNotSupportedException {
                return super.clone();
            }

}