package net.pixelderp.pathfinding3d;

import java.util.ArrayList;







import net.pixelderp.utils.Location3D;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

public class Node {
	private Node parent;
	private Location loc;
	private int gCost; //Distance from starting node
	private int hCost; //Distance from end node
	
	public Node(Location loc){
		this.loc = loc;
	}
	
	public Node getParent(){
		return parent;
	}
	
	public boolean hasParent(){
		if(parent == null) return false;
		return true;
	}
	
	public void setParent(Node node){
		this.parent = node;
	}
	
	public Location getLoc(){
		return loc;		
	}
	
	public int getGCost(){
		return gCost;
	}
	
	public void setGCost(int gCost){
		this.gCost = gCost;
	}
	
	public int getHCost(){
		return hCost;
	}
	
	public void setHCost(int hCost){
		this.hCost = hCost;
	}
	
	public int getFCost(){
		return gCost + hCost;
	}
	
	public ArrayList<Node> getNeighbors(ArrayList<Node> nodes){
		ArrayList<Node> neighbors = new ArrayList<Node>();
		
		for(int x = loc.getBlockX()-1; x <= loc.getBlockX() + 1; x++){
			for(int y = loc.getBlockY()-1; y <= loc.getBlockY() + 1; y++){
				for(int z = loc.getBlockZ()-1; z <= loc.getBlockZ() + 1; z++){
					//if(x == loc.getBlockX() && z == loc.getBlockZ()) continue;
					if(getNode(new Location(loc.getWorld(), x, y, z), nodes) != null){
						if(new Location(loc.getWorld(), x, y-1, z).getBlock().getType().equals(Material.AIR)) continue;
						neighbors.add(getNode(new Location(loc.getWorld(), x, y, z), nodes));
					}					
				}
			}

		}
		
		return neighbors;		
	}
	
	

	public static Node getNode(Location loc, ArrayList<Node> nodes){
		for(Node node : nodes){
			if(node.getLoc().equals(Location3D.toWholeBlock(loc))) return node;
		}
		return null;
	}
	
	public static Node getLowestFCostNode(ArrayList<Node> nodes){
		Node lowest = nodes.get(0);
		for(Node node : nodes){
			if(node.equals(lowest)) continue;
			if(node.getFCost() < lowest.getFCost() || (node.getFCost() == lowest.getFCost() && node.getHCost() < lowest.getHCost())){
				lowest = node;
			}
		}
		return lowest;		
	}
	
}
