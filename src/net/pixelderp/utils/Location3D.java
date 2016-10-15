package net.pixelderp.utils;

import org.bukkit.Location;
import org.bukkit.World;

public class Location3D extends Location{

	public Location3D(World world, double x, double y, double z) {
		super(world, x, y, z);		
	}
	
	public Location3D(World world, double x, double y, double z, float yaw,	float pitch) {
		super(world, x, y, z, yaw, pitch);
	}

	public static Location lerp(Location loc1, Location loc2, double alpha){
		return loc1.clone().multiply((1 - alpha)).add(loc2.clone().multiply(alpha));
	}
	
	public static Location toWholeBlock(Location loc){
		return new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
	}
	
	public static double distance(Location loc1, Location loc2){
		return Math.sqrt(Math.pow(loc1.getX() - loc2.getX(), 2) + Math.pow(loc1.getY() - loc2.getY(), 2) + Math.pow(loc1.getZ() - loc2.getZ(), 2));
	}
	
	
}
