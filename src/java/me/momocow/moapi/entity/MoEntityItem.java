package me.momocow.moapi.entity;

import me.momocow.moapi.event.item.MoItemDestroyEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class MoEntityItem extends EntityItem
{
	public static final String RegistryName = "MoEntityItem";
    
	/**
	 * Required constructor by {@linkplain net.minecraftforge.fml.common.network.internal.EntitySpawnHandler#spawnEntity spawnEntity(EntitySpawnMessage spawnMsg)}
	 * @param world
	 */
	public MoEntityItem(World world)
	{
		super(world);
		this.setPickupDelay(40);
	}
	
    public MoEntityItem(World worldIn, Entity vanilla, ItemStack stack) 
    {
    	super(worldIn, vanilla.posX, vanilla.posY, vanilla.posZ, stack);
    	this.setPickupDelay(40);
    	this.setThrower(((EntityItem)vanilla).getThrower());
    	this.setOwner(((EntityItem)vanilla).getOwner());
    	this.motionX = vanilla.motionX; 
    	this.motionY = vanilla.motionY;
    	this.motionZ = vanilla.motionZ;
	}
    
    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
    	boolean ret = super.attackEntityFrom(source, amount);
    	
    	if(this.isDead)
    	{
    		MinecraftForge.EVENT_BUS.post(new MoItemDestroyEvent(this, source));
    	}
    	
    	return ret;
    }
}
