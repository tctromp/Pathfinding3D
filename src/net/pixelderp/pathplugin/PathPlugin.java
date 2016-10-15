package net.pixelderp.pathplugin;

import net.md_5.bungee.api.ChatColor;
import net.pixelderp.ai.StandAI;
import net.pixelderp.pathfinding3d.Node;
import net.pixelderp.pathfinding3d.Pathfinding;
import net.pixelderp.utils.Location3D;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class PathPlugin{

	
	//public static World world;
	/*
	@Override
	public void onEnable(){
		world = Bukkit.getWorld("world");
		 
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            public void run() {            	
            	StandAI.updateStands();           	
            }
        }, 0L, 1L);
		
		
		
	}
	
	@Override
	public void onDisable(){
		for(Entity e : world.getEntities()){
			if(e instanceof ArmorStand) e.remove();
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		Player player = (Player) sender;
		Location start = new Location(world, -128, 70, 272);
		if (cmd.getName().equalsIgnoreCase("p")) {	
			
			StandAI ai = new StandAI(start.clone().add(0.5,0,0.5));
			ai.setTarget(player);
			
		}
		
		return false;
	}
	
	
	/**
	 * for(Entity e : world.getEntities()){
				if(e instanceof ArmorStand) e.remove();
			}
			
			 try{
				 	Location pLoc = player.getLocation();
				 	Location loc = new Location(world, pLoc.getBlockX(), pLoc.getBlockY(), pLoc.getBlockZ());
					for(Node node : Pathfinding.getNodePath(start, loc)){
						ArmorStand stand = (ArmorStand) world.spawnEntity(node.getLoc().clone().add(0.5,-1,0.5), EntityType.ARMOR_STAND);
						stand.setGravity(false);
						stand.setVisible(false);
						stand.setHelmet(new ItemStack(Material.LAPIS_BLOCK));						
					}
				}catch(NullPointerException e){
					Bukkit.broadcastMessage(ChatColor.RED + "ERROR: No path found");
				}
	 */
	
	
	
}
