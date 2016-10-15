package net.pixelderp.pathfinding3d;

import java.util.ArrayList;
import java.util.Collections;

import net.md_5.bungee.api.ChatColor;
import net.pixelderp.pathplugin.PathPlugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Pathfinding extends JavaPlugin{


	public static ArrayList<Location> getPath(Location start, Location end){
		ArrayList<Location> locs = new ArrayList<Location>();
		long time = System.currentTimeMillis();
		for(Node node : getNodePath(start, end)){
			locs.add(node.getLoc());
		}
		//Bukkit.broadcastMessage(ChatColor.GREEN + "Time Taken To Find Path: " + ChatColor.GOLD + (System.currentTimeMillis() - time) + "ms");
		return locs;
	}
	
	public static ArrayList<Node> getNodePath(Location start, Location end){
		ArrayList<Node> nodes = new ArrayList<Node>();
		ArrayList<Node> openNodes = new ArrayList<Node>();

		ArrayList<Node> closedNodes = new ArrayList<Node>();
		
		World world = start.getWorld();
		
		for(int x = Math.min(start.getBlockX(), end.getBlockX())- 10; x <= Math.max(start.getBlockX(), end.getBlockX()) + 10; x++){
			for(int y = Math.min(start.getBlockY(), end.getBlockY()) - 10; y <= Math.max(start.getBlockY(), end.getBlockY()) + 10; y++)
				for(int z = Math.min(start.getBlockZ(), end.getBlockZ()) - 10; z <= Math.max(start.getBlockZ(), end.getBlockZ()) + 10; z++){
					if(new Location(world, x, y, z).getBlock().getType().equals(Material.AIR)
							&& !(new Location(world, x, y-1, z).getBlock().getType().equals(Material.AIR))
							&& (new Location(world, x, y+1, z).getBlock().getType().equals(Material.AIR)))
						nodes.add(new Node(new Location(world, x, y, z)));
			}
		}
		
		Node startNode = Node.getNode(start, nodes);
		Node endNode = Node.getNode(end, nodes);

		for(Node node : nodes){
			//ArmorStand stand = (ArmorStand) PathPlugin.world.spawnEntity(node.getLoc().clone().add(0.5,0,0.5), EntityType.ARMOR_STAND);
			//stand.setGravity(false);
			//stand.setVisible(false);
			//stand.setHelmet(new ItemStack(Material.GLASS));					
		}
		
		
		openNodes.add(startNode);
		
		while(openNodes.size() > 0){
			
			if(Node.getLowestFCostNode(openNodes) == null) return null;
			Node currentNode = Node.getLowestFCostNode(openNodes); 		
			
			openNodes.remove(currentNode);
			closedNodes.add(currentNode);
			
			if(currentNode.equals(endNode)) break;
			
			for(Node node : currentNode.getNeighbors(nodes)){
				if(closedNodes.contains(node)) continue;
				
				int newMovementCostToneighbor = currentNode.getGCost() + getDistance(currentNode, node);
				
				if(newMovementCostToneighbor < node.getGCost() || !openNodes.contains(node)){
					node.setGCost(newMovementCostToneighbor);
					node.setHCost(getDistance(node, endNode));
					node.setParent(currentNode);
					if(!openNodes.contains(node)){
						openNodes.add(node);
						
						//node.getLoc().clone().add(0,-1,0).getBlock().setType(Material.EMERALD_BLOCK);					
						
					}
				}
				
			}
			
			
		}
		
		
		
		
		return getPath(startNode, endNode);
		
	}
	
	private static ArrayList<Node> getPath(Node start, Node end){
		ArrayList<Node> path = new ArrayList<Node>();
		Node currentNode = end;
		
		while(!currentNode.equals(start)){
			path.add(currentNode);
			currentNode = currentNode.getParent();			
		}
		path.add(start);
		Collections.reverse(path);
		return path;
	}
	
	private static int getDistance(Node nodeA, Node nodeB){
		int distX = Math.abs(nodeA.getLoc().getBlockX() - nodeB.getLoc().getBlockX());
		int distY = Math.abs(nodeA.getLoc().getBlockY() - nodeB.getLoc().getBlockY());
		int distZ = Math.abs(nodeA.getLoc().getBlockZ() - nodeB.getLoc().getBlockZ());

		return distX + distY + distZ;
		
	}
	
	
}
