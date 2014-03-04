/**
 * This class defines an object topology - that sets the scene for a network.
 * @author Palak Shah
 */
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;


public class Topology {

	public int num_nodes, num_neighbors; 
	public int radius = 200; //radius of circle
	public String topology; //type of topology
	public ArrayList<Address> topoAddr = new ArrayList<Address>(); //address of every node in the topology is stored here
	public ArrayList<Node> all_nodes = new ArrayList<Node>(); //identifier of everynode in topology is stored here
	public ArrayList<Node> nodes = new ArrayList<Node>(); //temp list used by random to assign neighbors
	private static final Random rnd = new Random(); 
	
	public int[] sum = new int[51]; //sum of neighbors for every cycle from 0 - 50
	
	//constructor
	public Topology(int N, int k, String t){
		this.num_nodes = N;
		this.num_neighbors = k;
		this.topology = t;
		for(int i=0;i<=50;i++){
			sum[i] = 0;
		}
	}//constructor ends here
	/**
	 * this method sets identifiers of all nodes as a natural number between 0 and N 
	 * @param offset
	 */
	public void identifyNodes(int offset) {
		for(int i=1; i<=this.num_nodes; i++){
			Node temp = new Node (i, this.num_nodes, this.num_neighbors);
			temp.address = new Address();
			
			//set different addresses for different topologies
			if(this.topology.equals("O")) {
				temp.createAddrO(num_nodes, radius, offset);
				topoAddr.add(temp.address);
			}
			else if(this.topology.equals("b")) {
				if(i<num_nodes){
					temp.createAddrB(num_nodes, radius/2, offset);
				}
				else {
					temp.address.setX(offset+0);
					temp.address.setY(0);
				}
				topoAddr.add(temp.address);
			}
			else if(this.topology.equals("S")) {
				if(i<=num_nodes/4){
					temp.createAddrO(num_nodes/4, radius, offset);
					topoAddr.add(temp.address);
					nodes.add(temp);
				}
				else if((i>num_nodes/4)&&(i<=num_nodes/2)) {
					temp.createAddrS2(num_nodes/4, radius/4, radius/2, -1*radius/3,offset);
					topoAddr.add(temp.address);
					nodes.add(temp);
				}
				else if((i>num_nodes/2)&&(i<=3*num_nodes/4)) {
					temp.createAddrS1(num_nodes/4, radius/4, -1*radius/2, -1*radius/3,offset);
					topoAddr.add(temp.address);
					nodes.add(temp);
				}
				else if(i>3*num_nodes/4) {
					temp.createAddrS1(num_nodes/4, radius/4, 0, 2*radius/3,offset);
					topoAddr.add(temp.address);
					nodes.add(temp);
				}
			}
			
			all_nodes.add(temp);
			nodes.add(temp);
			
		}//for loop ends here
	}//Method identifyNodes ends here
	
	/**
	 * this method randomly assigns k neighbors to every node
	 */
	public void initialize(){
		if((this.topology.equals("O"))||(this.topology.equals("b"))){
			for(int j=0; j<this.num_nodes; j++){ //for all nodes from 0 to N-1
				Collections.shuffle(nodes, rnd);
				int k=0;
				int a=0;
			    while(k<this.num_neighbors){ //add neighbors from 0 - k-1
			    	if(nodes.get(a).getIdentifier() != all_nodes.get(j).getIdentifier()){
			    		all_nodes.get(j).View.add(nodes.get(a).getIdentifier());//is this necessary???
			    		all_nodes.get(j).setPartialView(k, nodes.get(a).getIdentifier());
			    		k++;
			    	}
			    	a++;
				}
			}//for loop ends here
		}
		else if(this.topology.equals("S")) {
			for(int j=0; j<this.num_nodes/4; j++){ //for all nodes from 0 to N-1
				Collections.shuffle(nodes, rnd);
				int k=0;
				int a=0;
			    while(k<this.num_neighbors){ //add neighbors from 0 - k-1
			    	if(nodes.get(a).getIdentifier() != all_nodes.get(j).getIdentifier()){
			    		all_nodes.get(j).View.add(nodes.get(a).getIdentifier());//is this necessary???
			    		all_nodes.get(j).setPartialView(k, nodes.get(a).getIdentifier());
			    		k++;
			    	}
			    	a++;
				}
			}
			for(int j=this.num_nodes/4; j<this.num_nodes/2; j++){ //for all nodes from 0 to N-1
				Collections.shuffle(nodes, rnd);
				int k=0;
				int a=0;
			    while(k<this.num_neighbors){ //add neighbors from 0 - k-1
			    	if(nodes.get(a).getIdentifier() != all_nodes.get(j).getIdentifier()){
			    		all_nodes.get(j).View.add(nodes.get(a).getIdentifier());//is this necessary???
			    		all_nodes.get(j).setPartialView(k, nodes.get(a).getIdentifier());
			    		k++;
			    	}
			    	a++;
				}
			}
			for(int j=this.num_nodes/2; j<3*this.num_nodes/4; j++){ //for all nodes from 0 to N-1
				Collections.shuffle(nodes, rnd);
				int k=0;
				int a=0;
			    while(k<this.num_neighbors){ //add neighbors from 0 - k-1
			    	if(nodes.get(a).getIdentifier() != all_nodes.get(j).getIdentifier()){
			    		all_nodes.get(j).View.add(nodes.get(a).getIdentifier());//is this necessary???
			    		all_nodes.get(j).setPartialView(k, nodes.get(a).getIdentifier());
			    		k++;
			    	}
			    	a++;
				}
			}
			for(int j=3*this.num_nodes/4; j<this.num_nodes; j++){ //for all nodes from 0 to N-1
				Collections.shuffle(nodes, rnd);
				int k=0;
				int a=0;
			    while(k<this.num_neighbors){ //add neighbors from 0 - k-1
			    	if(nodes.get(a).getIdentifier() != all_nodes.get(j).getIdentifier()){
			    		all_nodes.get(j).View.add(nodes.get(a).getIdentifier());//is this necessary???
			    		all_nodes.get(j).setPartialView(k, nodes.get(a).getIdentifier());
			    		k++;
			    	}
			    	a++;
				}
			}
		}
	}//method initialize ends here
	/**
	 * the following set of methods all rank the neighbord of a node according to the distance 
	 * equation defined for every topology
	 */
	public void rankO(){
		int rank, absolute, a, b, temp;
		for(int i=0; i<this.num_nodes; i++){ //for all nodes in system,
			for(int j=0; j<all_nodes.get(i).View.size(); j++) { //rank the nodes
				a=all_nodes.get(i).getIdentifier();
				b=all_nodes.get(i).getView(j);
				if((a-b)<0){
					absolute=b-a;
				}
				else absolute = a-b;
				temp = num_nodes - absolute;
				rank = Math.min(temp, absolute);
				all_nodes.get(i).setRanking(rank);
			}
		}
	}//method rankO ends here
	
	public void rankO(Node n){ //here velue of n is all_nodes.get(n-1).getIdentifier(), so make sure you enter that in main method
		int rank, absolute, a, b, temp;
		
			for(int j=0; j<n.View.size(); j++) { //rank the nodes
				a=n.getIdentifier();
				b=n.getView(j);
				if((a-b)<0){
					absolute=b-a;
				}
				else absolute = a-b;
				temp = num_nodes - absolute;
				rank = Math.min(temp, absolute);
				n.setRanking(rank);
			}
	}//method rankO ends here
	
	public void rankB(){
		int rank, absolute, a, b, temp;
		for(int i=0; i<this.num_nodes; i++){ //for all nodes in system,
			for(int j=0; j<all_nodes.get(i).View.size(); j++) { //rank the nodes
				a=all_nodes.get(i).getIdentifier();
				b=all_nodes.get(i).getView(j);
				if((a-b)<0){
					absolute=b-a;
				}
				else absolute = a-b;
				if((0<=a)&&(a<=this.num_neighbors)&&(b==this.num_nodes)){
					rank = 1;
				}
				else rank = absolute;
				all_nodes.get(i).setRanking(rank);
			}
		}
	}//method rankO ends here
	
	public void rankB(Node n){ //here velue of n is all_nodes.get(n-1).getIdentifier(), so make sure you enter that in main method
		int rank, absolute, a, b, temp;
		
			for(int j=0; j<n.View.size(); j++) { //rank the nodes
				a=n.getIdentifier();
				b=n.getView(j);
				if((a-b)<0){
					absolute=b-a;
				}
				else absolute = a-b;
				if((0<=a)&&(a<=this.num_neighbors)&&(b==this.num_nodes)){
					rank = 1;
				}
				else rank = absolute;
				n.setRanking(rank);
			}
		
	}//method rankO ends here
	
	
	public void rankS(){
		int rank, absolute, a, b, temp;
		for(int i=0; i<this.num_nodes; i++){
			for(int j=0; j<all_nodes.get(i).View.size(); j++) { //rank the nodes
				a=all_nodes.get(i).getIdentifier();
				b=all_nodes.get(i).getView(j);
				if((a-b)<0){
					absolute=b-a;
				}
				else absolute = a-b;
				if(all_nodes.get(i).getIdentifier()<=this.num_nodes/4){
					temp = num_nodes - absolute;
					rank = Math.min(temp, absolute);
					all_nodes.get(i).setRanking(rank);
				}
			
				else if((all_nodes.get(i).getIdentifier()>this.num_nodes/4)){
					rank=absolute;
					all_nodes.get(i).setRanking(rank);
				}
			}
		}
	}//method rankS ends here
	
	public void rankS(Node n){
		int rank, absolute, a, b, temp;
			for(int j=0; j<n.View.size(); j++) { //rank the nodes
				a=n.getIdentifier();
				b=n.getView(j);
				if((a-b)<0){
					absolute=b-a;
				}
				else absolute = a-b;
				if(n.getIdentifier()<=this.num_nodes/4){
					temp = num_nodes - absolute;
					rank = Math.min(temp, absolute);
					n.setRanking(rank);
				}
			
				else if((n.getIdentifier()>this.num_nodes/4)){
					rank=absolute;
					n.setRanking(rank);
				}
			}
	}//method rankS ends here
	
	/**
	 * the following methods sort the neighbors according to rank, adding the first k neighbors to partial list
	 */
	public void sort(){
		int maxRank = 10000;
		int maxIndex = 10000;
		int max = 10000;
		for(int i=0; i<this.num_nodes; i++){ //for all nodes in system,
			for(int j=0;j<this.num_neighbors;j++) {
				
				for(int k = 0; k<all_nodes.get(i).View.size(); k++) {
					if(maxRank > all_nodes.get(i).getRanking(k)){
						maxRank=all_nodes.get(i).getRanking(k);
						max = all_nodes.get(i).getView(k);
						maxIndex = k;
					}
				}
				sum[0]+=maxRank;
				all_nodes.get(i).setPartialView(j, max);
				all_nodes.get(i).View.remove(maxIndex);
				all_nodes.get(i).Ranking.remove(maxIndex);
				max = 10000;
				maxIndex = 10000;
				maxRank = 10000;
			}
			all_nodes.get(i).View.clear(); 
			for(int j=0; j<this.num_neighbors; j++){
				all_nodes.get(i).View.add(all_nodes.get(i).getPartialView(j));
			}
			all_nodes.get(i).Ranking.clear();
		}
	}//Method sort ends here
	
	public void sort(Node n, int itr){ ////here velue of n is all_nodes.get(n-1).getIdentifier(), so make sure you enter that in main method
		int maxRank = 10000;
		int maxIndex = 10000;
		int max = 10000;
		int j=0;
		while(j<this.num_neighbors) {
			for(int k = 0; k<n.View.size(); k++) {
				if(maxRank > n.getRanking(k)){
					maxRank=n.getRanking(k);
					max = n.getView(k);
					maxIndex = k;
				}
			}
			if(j==0){
				sum[itr]+=maxRank;
				n.setPartialView(j, max);
				j++;
			}
			if((j!=0)&&(n.getPartialView(j-1)!= max)){
				sum[itr]+=maxRank;
				n.setPartialView(j, max);
				j++;
			}
			n.View.remove(maxIndex);
			n.Ranking.remove(maxIndex);
			max = 10000;
			maxIndex = 10000;
			maxRank = 10000;
		} 
		for(int p=0; p<this.num_neighbors; p++){
			n.View.add(n.getPartialView(p));
		}
		n.Ranking.clear();
	}
	
	/**
	 * for each node, this method selects a neighbor randomly and exchanges neighbors in list called View. 
	 * Then both nodes rank and sort their own functions and save the first k neighbors in partialView 
	 * @param itr
	 */
	public void exchange(int itr){
		int friendIndex;
		int friend;
		boolean ispresent = false;
		int i=0;
		while(i<this.num_nodes){ //for all nodes in system,
			friendIndex = (int) (Math.random()*this.num_neighbors);
			if((friendIndex==this.num_neighbors)||(all_nodes.get(i).getPartialView(friendIndex) == all_nodes.get(i).getIdentifier())){
				continue;
			}//if the same node is selected, select another node
			
			friend = all_nodes.get(i).getPartialView(friendIndex);//gives identifier of neighbor selected
			if(Arrays.asList(all_nodes.get(friend-1).partialView).indexOf(all_nodes.get(i).getIdentifier())== -1){
				all_nodes.get(friend-1).View.add(all_nodes.get(i).identifier);
			}
			
			for(int a=0; a<this.num_neighbors; a++) { //main exchange happens in this loop
				//This part not working for duplicate values :(
				//other than that it's working fine
				if((Arrays.asList(all_nodes.get(i).partialView).indexOf(all_nodes.get(friend-1).getPartialView(a))== -1)&&(all_nodes.get(friend-1).getPartialView(a)!= all_nodes.get(i).getIdentifier())){
					all_nodes.get(i).View.add(all_nodes.get(friend-1).getPartialView(a));
				}
				
				if((Arrays.asList(all_nodes.get(friend-1).partialView).indexOf(all_nodes.get(i).getPartialView(a))== -1)&&(all_nodes.get(i).getPartialView(a) != all_nodes.get(friend-1).getIdentifier())){
					all_nodes.get(friend-1).View.add(all_nodes.get(i).getPartialView(a));
				}
			}
			if(this.topology.equals("O")){
				rankO(all_nodes.get(i));
				rankO(all_nodes.get(friend-1));
			}
			if(this.topology.equals("b")){
				rankB(all_nodes.get(i));
				rankB(all_nodes.get(friend-1));
			}
			if(this.topology.equals("S")){
				rankS(all_nodes.get(i));
				rankS(all_nodes.get(friend-1));
			}
			sort(all_nodes.get(i),itr);
			sort(all_nodes.get(friend-1),itr);
			
			all_nodes.get(i).View.clear();
			all_nodes.get(friend-1).View.clear();
			for(int n=0; n<num_neighbors; n++){
				all_nodes.get(i).View.add(all_nodes.get(i).getPartialView(n));
				all_nodes.get(friend-1).View.add(all_nodes.get(friend-1).getPartialView(n));
				
			}
			
			i++;
		}
	}//method exchange ends here
}//Class Topology ends here
