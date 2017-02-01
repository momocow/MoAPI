package me.momocow.mobasic.tileentity;

import java.util.ArrayList;
import java.util.List;

import me.momocow.mobasic.util.NBTHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class MoTileEntity extends TileEntity
{
	private String MODID;
	
	public MoTileEntity(String modid)
	{
		this.MODID = modid;
	}
	
	public abstract int getGuiId();
	
	public int getRenderMetadata()
	{
		if (this.getWorld() == null || this.pos == BlockPos.ORIGIN)
		{
			return -1;
		}
		else
		{
			return this.getBlockMetadata();
		}
	}
	
	public void onBlockPlacedBy(EntityPlayer placer, ItemStack stack)
	{
		if(stack.hasTagCompound())
		{
			NBTTagCompound data = stack.getTagCompound().getCompoundTag(this.MODID);
			if(data != null)
			{
				this.importFromNBT(data);
			}
		}
	}
	
	public List<ItemStack> getDrops()
	{
		List<ItemStack> list = new ArrayList<ItemStack>();
		ItemStack stack = new ItemStack(Item.getItemFromBlock(this.worldObj.getBlockState(this.getPos()).getBlock()), 1);
		this.exportToNBT(NBTHelper.getDataTag(stack, MODID));
		list.add(stack);
		
		return list;
	}
	
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {}
	
	/**
	 * the NBT decoder of data in Mod's data tag for the tile
	 * @param data
	 */
	public abstract void importFromNBT(NBTTagCompound data);
	/**
	 * the NBT encoder of data in Mod's data tag for the tile
	 * @param data
	 */
	public abstract NBTTagCompound exportToNBT(NBTTagCompound data);
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		if(compound.hasKey(this.MODID))
		{
			this.importFromNBT(compound.getCompoundTag(this.MODID));
		}
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag(this.MODID, this.exportToNBT(new NBTTagCompound()));
		return super.writeToNBT(compound);
	}
}
