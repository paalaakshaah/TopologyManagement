import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;

public class TMAN {

	public static Topology scene;
	public static int iterations = 50;
	public static int N; 
	public static int k;
	public static String topology; 
	
	public static int w = 500;
	public static int h = 500;
	
	public TMAN(){
		
	}
	
	/**
	 * Main function
	 * 
	 */
	public static void main(String args[])throws IOException{
		N = Integer.parseInt(args[0]);
		k = Integer.parseInt(args[1]); 
		topology = args[2];
		
		scene = new Topology(N, k, topology);
		scene.identifyNodes(250);
		scene.initialize();
		rankTopology(topology);
		scene.sort();
		clearRank(N);
		//run the loop 50 times
		for(int i=0; i< iterations; i++) {
			scene.exchange(i+1);
			if((i == 0)||(i == 4)||(i == 9)||(i == 14)||(i == 49)){
				printnewResults(i);
			}
		}//for loop ends here*/
		String fileName = topology+"_N"+N+"_k"+k+".txt";
		File f = new File(fileName);
		writeResults(f);
        
	}//Main method ends here
	
	public static void rankTopology(String topology){
		if(topology.equals("O")){
			scene.rankO();
		}
		else if(topology.equals("b")){
			scene.rankB();
		}
		else if(topology.equals("S")){
			scene.rankS();
		}
	}
	
	public static void clearRank(int N){
		for(int i=0; i<N; i++){
			scene.all_nodes.get(i).clearRanking();
		}
	}
	
	/*
	 * these functions generates the node plots
	 * 
	 */
	public static void printResults() throws IOException{
		// Constructs a BufferedImage of one of the predefined image types.
        BufferedImage buff = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        // Create a graphics which can be used to draw into the buffered image
        Graphics2D g = buff.createGraphics();
        
     // fill all the image with black
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, w, h);
        
     // create a circle with white
        g.setColor(Color.WHITE);
        
     //plot the topology
        for(int i=0; i<N; i++){
        	g.draw(new Ellipse2D.Double(scene.topoAddr.get(i).getX(), scene.topoAddr.get(i).getY(), 2.0, 2.0));
        }
        
        
     // Disposes of this graphics context and releases any system resources that it is using.
        g.dispose();
        
     // Save as PNG
        File f = new File("palak.png");
        ImageIO.write(buff, "png", f);
	}
	
	public static void printnewResults(int itrNum) throws IOException{
		
        BufferedImage buff = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        // Create a graphics for drawing buffered image
        Graphics2D g = buff.createGraphics();
        
     // fill background with black
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, w, h);
        
     // create topology of nodes with white
        g.setColor(Color.WHITE);
        
     // plot the topology
        for(int i=0; i<N; i++){
        	g.draw(new Ellipse2D.Double(scene.topoAddr.get(i).getX(), scene.topoAddr.get(i).getY(), 2.0, 2.0));
        	for(int j=0; j<scene.num_neighbors; j++){
        		double xNeighbor = scene.topoAddr.get(scene.all_nodes.get(i).getPartialView(j)-1).getX();
        		double yNeighbor = scene.topoAddr.get(scene.all_nodes.get(i).getPartialView(j)-1).getY();
        		g.setColor(Color.WHITE);
        		g.draw(new Line2D.Double(scene.topoAddr.get(i).getX(), scene.topoAddr.get(i).getY(),xNeighbor,yNeighbor));
        	}
        }
        
        
     // Disposes of g
        g.dispose();
        
     // Save as PNG file
        String name = topology+"_N"+N+"_k"+k+"_"+(itrNum+1)+".png";
        File f = new File(name);
        ImageIO.write(buff, "png", f);
	}
	
	/**
	 * this function prints the sum of neighbors into a txt file
	 * @param sumFile
	 */
	public static void writeResults(File sumFile) {
		String comp = null;
		double compRatio = 0;
		try{
			FileWriter fw = new FileWriter(sumFile.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			for(int i=0; i<=50; i++){
				String s = Integer.toString(scene.sum[i]);
				bw.write(s);
				bw.write('\n');
			}
			bw.close();
		} catch (IOException e) {
	        e.printStackTrace();
	    }
	} //method ends here
	
}// class ends here
