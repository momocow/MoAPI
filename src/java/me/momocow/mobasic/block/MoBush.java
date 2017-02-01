package me.momocow.mobasic.block;

import java.util.HashSet;
import java.util.Set;

import me.momocow.mobasic.client.render.MoCustomModel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class MoBush extends BlockBush implements MoCustomModel
{
	public static Set<Integer> soilList = new HashSet<Integer>();
	
	public MoBush()
	{
		this(Material.PLANTS);
	}
	
	protected MoBush(Material materialIn)
    {
		this(materialIn, materialIn.getMaterialMapColor());
    }

    protected MoBush(Material materialIn, MapColor mapColorIn)
    {
    	super(materialIn, mapColorIn);
    	this.setTickRandomly(true);
    }
    
    public void init() {}
	
    @Override
	protected boolean canSustainBush(IBlockState soilState)
    {
        return this.getSuitableSoilList().contains(Block.getIdFromBlock((soilState.getBlock())));
    }
	
	@Override
	public boolean canBlockStay(World worldIn, BlockPos plantablePos, IBlockState soilState)
	{
		if(soilState.getBlock() == this){
			soilState = worldIn.getBlockState(plantablePos.down());
		}
        return worldIn.getLight(plantablePos) <= this.getMaxGrowthLightness() && worldIn.getLight(plantablePos) >= this.getMinGrowthLightness() && this.canSustainBush(soilState);
    }
	
	/**
	 * Called when the player tries to put the plantable onto a block
	 */
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos plantablePos)
    {
		return this.canBlockStay(worldIn, plantablePos, worldIn.getBlockState(plantablePos.down()));
    }
	
	public void initModel() {}
	
	/**
	 * Override it to change the setting
	 * @return max lightness value to grow
	 */
	public int getMaxGrowthLightness()
	{
		return 15;
	}
	
	/**
	 * Override it to change the setting
	 * @return min lightness value to grow
	 */
	public int getMinGrowthLightness()
	{
		return 8;
	}
	
	public void setSuitableSoilList(Block b)
	{
		setSuitableSoilList(Block.getIdFromBlock(b));
	}
	
	public void setSuitableSoilList(Integer i)
	{
		soilList.add(i);
	}
	
	/**
	 * @return a set of blocks for the Plantable to be planted on
	 */
	public Set<Integer> getSuitableSoilList()
	{
		return soilList;
	}
	
	/**
	 * Planted by Player
	 * @param stack ItemStack held by player
	 * @param playerIn planter
	 * @param worldIn the world
	 * @param pos soil position
	 * @param facing the side to plant the Plantable
	 * @return
	 */
	public EnumActionResult plantToSoilByPlayer(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing facing)
	{
		if (facing == EnumFacing.UP && playerIn.canPlayerEdit(pos.offset(facing), facing, stack))
        {
			if (this.plant(worldIn, pos.up(), 1.0f))
			{
				--stack.stackSize;
				return EnumActionResult.SUCCESS;
			}
        }
        return EnumActionResult.FAIL;
	}
	
	/**
	 * Planted to the soil at the certain position
	 * @param worldIn the world
	 * @param pos the position of the plantable
	 * @param chance ranges from 0 to 1 for bushes to spawn
	 * @return true if it is planted successfully; false if it does not
	 */
	public boolean plant(World worldIn, BlockPos plantPos, float chance){
		if(this.canPlaceBlockAt(worldIn, plantPos) && worldIn.isAirBlock(plantPos))
		{
			if(worldIn.rand.nextFloat() <= chance)
			{
				worldIn.setBlockState(plantPos, this.getDefaultState(), 3);
			}
			return true;
		}
		
		return false;
	}
}
