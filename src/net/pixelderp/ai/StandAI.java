package net.pixelderp.ai;

import java.util.ArrayList;

import net.pixelderp.pathfinding3d.Pathfinding;
import net.pixelderp.utils.Location3D;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

public class StandAI {

	public static ArrayList<StandAI> stands = new ArrayList<StandAI>();
	
	private ArmorStand stand;
	private Player target;
	private ArrayList<Location> path;
	
	public StandAI(Location loc){
		stand = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
		stand.setArms(true);
		stand.setBasePlate(false);		
		stand.setHelmet(new ItemStack(Material.SKULL_ITEM));
		stand.setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
		stand.setLeggings(new ItemStack(Material.IRON_LEGGINGS));
		stand.setBoots(new ItemStack(Material.IRON_BOOTS));

		stand.setItemInHand(new ItemStack(Material.GOLD_SPADE));
		
		stand.setRightArmPose(new EulerAngle(0,0,0));
		stand.setLeftArmPose(new EulerAngle(0,0,0));
		stand.setRightLegPose(new EulerAngle(0,0,0));
		stand.setLeftLegPose(new EulerAngle(0,0,0));

		stands.add(this);
	}
	
	public static void disable(){
		for(StandAI ai : stands){
			ai.stand.remove();
		}		
	}
	
	
	private int ticks = 1;
	
	private void update(){
		if(ticks > 20) ticks = 1;
		else ticks++;

		//		if(target != null && path == null && ticks % 10 == 0){		

		if(ticks % 20 == 0){		
			try{
				//Bukkit.broadcastMessage("Stand X: " + stand.getLocation().getX() + " Y: " + stand.getLocation().getY() + " Z: " + stand.getLocation().getZ());
				//Bukkit.broadcastMessage("Target X: " + target.getLocation().getX() + " Y: " + target.getLocation().getY() + " Z: " + target.getLocation().getZ());

			path = Pathfinding.getPath(Location3D.toWholeBlock(stand.getLocation()), Location3D.toWholeBlock(target.getLocation()));
			path.remove(0);
			lerpLoc = path.get(0);
			}catch(Exception e){
				//path = null;
				//Bukkit.broadcastMessage("Path not found");
				return;
			}
		}
		
		updateJoints();

		updatePosition();
	}
	
	
	private float speed = 0.1f; //between 0 and 1

	private float lerpValue = 0;
	private Location lerpLoc;
	
	
	private void updatePosition(){
		if(path == null || path.size() == 0) return;	
		
		
		
		if(lerpValue >= 1){
			path.remove(0);	
			lerpValue = 0;
			if(path.size() == 0){
				path = null;
				return;
			}
			else
				lerpLoc = path.get(0);
		}
			
		double distance = Location3D.distance(stand.getLocation(), lerpLoc.clone().add(0.5,0,0.5));
		lerpValue += speed/distance;
		Location newPos = Location3D.lerp(stand.getLocation(), lerpLoc.clone().add(0.5,0,0.5), lerpValue);
		
		updateHead(newPos, distance);		
		stand.teleport(newPos); //.clone().add(0.5,0,0.5)
			
		
	}
	
	private void updateHead(Location loc, double distance){		
		Vector v = new Vector(loc.getBlockX() - stand.getLocation().getBlockX(), loc.getBlockY() - stand.getLocation().getBlockY(), loc.getBlockZ() - stand.getLocation().getBlockZ());

		loc.setDirection(v);		
	}
	
	private void updateJoints(){
		
		int maxDegree = 40;
		int speed = 10;
		
		
		
		stand.setRightArmPose(setEulerPositions(stand.getRightArmPose(), rightArmForward, speed, maxDegree));
		stand.setLeftArmPose(setEulerPositions(stand.getLeftArmPose(), leftArmForward, speed, maxDegree));
		
		stand.setLeftLegPose(setEulerPositions(stand.getLeftLegPose(), rightArmForward, speed, maxDegree));
		stand.setRightLegPose(setEulerPositions(stand.getRightLegPose(), leftArmForward, speed, maxDegree));

		rightArmForward = checkEulerPositionBoolean(stand.getRightArmPose(), rightArmForward, maxDegree);
		leftArmForward = checkEulerPositionBoolean(stand.getLeftArmPose(), leftArmForward, maxDegree);
		
	}
	
	private boolean rightArmForward = true;
	private boolean leftArmForward = false;
	
	private EulerAngle setEulerPositions(EulerAngle angle, boolean forward, int speed, int maxDegree){
		if(forward){
			angle = angle.add(Math.toRadians(-speed), 0, 0);
			if(angle.getX() < Math.toRadians(-maxDegree)) angle = angle.setX(Math.toRadians(-maxDegree));
			return angle;
		}else{
			angle = angle.add(Math.toRadians(speed), 0, 0);
			if(angle.getX() > Math.toRadians(maxDegree)) angle = angle.setX(Math.toRadians(maxDegree));

			return angle;
		}		
	}
	
	private boolean checkEulerPositionBoolean(EulerAngle angle, boolean forward, int maxDegree){
		if(forward){
			if(angle.getX() <= Math.toRadians(-maxDegree)) return false;
			return true;
		}else{
			if(angle.getX() >= Math.toRadians(maxDegree)) return true;
			return false;
		}
	}
	
	
	
	public static void updateStands(){
		for(StandAI ai : stands){
			ai.update();
		}
	}
	
	
	public void setTarget(Player player){
		this.target = player;
	}
	
	
	
}
