/**
 * This class provides an object address - that stores the x and y co-ordinates for address of every node.
 * @author Palak Shah
 *
 */
public class Address {

	public double x;
	public double y;
	public Address(){
		
	}
	public Address(double x1, double y1){
		x=x1;
		y=y1;
	}
	public void setX(double x2){
		x=x2;
	}
	public void setY(double y2){
		y=y2;
	}
	public double getX(){
		return this.x;
	}
	public double getY(){
		return this.y;
	}
	
}
