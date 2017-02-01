package me.momocow.mobasic.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class MoBlockContainerFacing extends MoBlockFacing implements ITileEntityProvider
{
	public MoBlockContainerFacing()
	{
		this(Material.ROCK);
	}
	
	public MoBlockContainerFacing(Material material)
	{
		this(Material.ROCK, Material.ROCK.getMaterialMapColor());
	}

	public MoBlockContainerFacing(Material material, MapColor color)
    {
        super(material, color);
        this.isBlockContainer = true;
    }
	
	@Override
	public abstract TileEntity createNewTileEntity(World worldIn, int meta);
	
	
	public boolean canDropInventory(IBlockState state)
	{
		return true;
	}
	
	public boolean canAlertBlockChange()
	{
		return true;
	}
		
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		TileEntity tile = worldIn.getTileEntity(pos);
		
		if(this.canDropInventory(state) && tile instanceof IInventory)
		{
			InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tile);
		}
		
		if(this.canAlertBlockChange())
		{
			worldIn.updateComparatorOutputLevel(pos, this);
		}
		
		super.breakBlock(worldIn, pos, state);
	}
}
