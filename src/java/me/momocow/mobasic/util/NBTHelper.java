package me.momocow.mobasic.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class NBTHelper
{
	public static boolean hasKey(NBTTagCompound nbt, String key)
	{
		//if nbt.hasKey(key)==false
		//check if it is the key of uuid
		return (nbt.hasKey(key))? true: (nbt.hasKey(key + "Most") && nbt.hasKey(key + "Least"));
	}
	
	public static NBTTagCompound getDataTag(ItemStack stack, String modid)
	{
		if(stack != null)
		{
			return stack.getSubCompound(modid, true);
		}
		
		return null;
	}
	
	public static boolean hasDataTag(ItemStack stack, String modid)
	{
		return stack != null  && stack.hasTagCompound() && stack.getTagCompound().hasKey(modid);
	}
}
