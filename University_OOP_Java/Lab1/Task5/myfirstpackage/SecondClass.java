package myfirstpackage;

public class SecondClass{
	private int  firstValue, secondValue;

	public SecondClass(int firstValue, int seconValue){
		this.firstValue = firstValue;
		this.secondValue = secondValue;
	}
	public int getFisrtValue(){
		return firstValue;
	}
	public int getSecondValue(){
		return secondValue;
	}
	public void setFirstValue(int value){
		this.firstValue = value;
	}
	public void setSecondValue(int value){
		this.secondValue = value;
	}
	public int calc(){
		return firstValue - secondValue;
	}
}
