package me.momocow.moapi.block;

import java.util.List;

import me.momocow.moapi.tileentity.MoTileEntity;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class MoBlockContainerHorizontalFacing extends MoBlockHorizontalFacing implements ITileEntityProvider
{	
	public MoBlockContainerHorizontalFacing()
	{
		this(Material.ROCK);
	}
	
	public MoBlockContainerHorizontalFacing(Material material)
	{
		this(Material.ROCK, Material.ROCK.getMaterialMapColor());
	}

	public MoBlockContainerHorizontalFacing(Material material, MapColor color)
    {
        super(material, color);
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
	
	/**
	 * Resume the TileEntity data from the NBT of the item, or initial the TileEntity with the bank controller
	 */
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		//init Facing
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		
		if(!worldIn.isRemote)
		{
			TileEntity tile = worldIn.getTileEntity(pos);
			if(tile instanceof MoTileEntity && placer instanceof EntityPlayer)
			{				
				((MoTileEntity)tile).onBlockPlacedBy((EntityPlayer)placer, stack);
			}
			else
			{
				worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
			}
		}
	}
	
	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
		TileEntity tile = worldIn.getTileEntity(pos);
		
		if(tile instanceof MoTileEntity)
		{
			((MoTileEntity) tile).onBlockHarvested(worldIn, pos, state, player);
		}
	}
	
	/**
	 * write the tile entity data to the item stack
	 */
	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) 
	{
		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof MoTileEntity)
		{
			return ((MoTileEntity) tile).getDrops();
		}
		
		return super.getDrops(world, pos, state, fortune);
	}
}
