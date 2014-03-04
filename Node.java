/**
 * This class defines an object node - that is the fundamental element of a network.
 * @author Palak Shah
 */
import java.util.ArrayList;

public class Node {

	public int identifier, total_nodes, total_neighbors;
	public Address address; //address to identify nodes in S topology
	public int[] partialView; //list of neighbors
	public ArrayList<Integer> View = new ArrayList<Integer>(); //extended list of neighbors
	public ArrayList<Integer> Ranking  = new ArrayList<Integer>(); //rank of every neighbor in View
	
	public Node(int i, int N, int k) {
		this.identifier = i;
		this.total_nodes = N;
		this.total_neighbors = k;
		this.partialView = new int[k];
	}//constructor ends here
	
	public int getIdentifier(){
		return this.identifier;
	}//method getIdentifier ends here
	
	public void setPartialView(int i, int k) {
		this.partialView[i] = k;
	}//method setPartialView ends here
	
	public int getPartialView(int i){
		return this.partialView[i];
	}//method getPartialView ends here
	
	public int getView(int i){
		return this.View.get(i);
	}//method getView ends here
	
	public void setRanking(int k) {
		this.Ranking.add(k);
	}//method setRanking ends here
	
	public int getRanking(int i){
		return this.Ranking.get(i);
	}//method getRanking ends here
	
	public void clearRanking(){
		this.Ranking.clear();
	}
	
	//create an address for O topology. Nodes are identified as natural numbers
	public Address createAddrO(int total, int r, int offset) {
			double thetaO = (this.identifier - 1)*Math.PI*2/(total);
			this.address.setX(offset+Math.cos(thetaO)*r);
			this.address.setY(offset+Math.sin(thetaO)*r);
			return this.address;
	}//method createAddrO ends here
	
	//create an address for B topology. Nodes are identified as (cos(theta),sin(theta))
	public Address createAddrB(int total, int r, int offset) {
		double thetaB = Math.PI/2 - (this.identifier - 1)*Math.PI/(total - 2);
		this.address.setX(offset+Math.cos(thetaB)*r);
		this.address.setY(offset+Math.sin(thetaB)*r);
		return this.address; 
	}//method createAddrB ends here
	
	//create an address for B topology. Nodes are identified as (cos(theta),sin(theta))
		public Address createAddrS1(int total, int r, int offsetX, int offsetY, int offset) {
			double thetaS1 = Math.PI - (this.identifier - 1)*Math.PI/(total - 1);
			this.address.setX(offset+offsetX+Math.cos(thetaS1)*r);
			this.address.setY(offset+offsetY+Math.sin(thetaS1)*r);
			return this.address; 
		}//method createAddrB ends here
		
		public Address createAddrS2(int total, int r, int offsetX, int offsetY, int offset) {
			double thetaS2 = 2*Math.PI - (this.identifier - 1)*Math.PI/(total - 1);
			this.address.setX(offset+offsetX+Math.cos(thetaS2)*r);
			this.address.setY(offset+offsetY+Math.sin(thetaS2)*r);
			return this.address; 
		}//method createAddrB ends here
	
}//Class Node ends here
