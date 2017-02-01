package me.momocow.mobasic.tileentity;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public abstract class MoTileEntityInventory extends MoTileEntity implements IInventory
{
	protected ItemStack[] inventory;
	protected String customName;
	
	public MoTileEntityInventory(String modid)
	{
		super(modid);
		this.inventory = new ItemStack[this.getSizeInventory()];
	}
	
	public void setCustomName(String name)
	{
		this.customName = name;
	}
	
	public String getCustomName()
	{
		return this.customName;
	}
	
	public abstract String getUnlocalizedName();
	
	@Override
	public String getName() {
		return (this.hasCustomName())? this.customName: "container." + this.getUnlocalizedName();
	}
	
	@Override
	public boolean hasCustomName() {
		return (this.customName != null) && !this.customName.isEmpty();
	}
	
	@Override
	public ITextComponent getDisplayName() {
		return (this.hasCustomName())? new TextComponentString(this.getName()): new TextComponentTranslation(this.getName());
	}
	
}
