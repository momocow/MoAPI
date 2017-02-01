package me.momocow.mobasic.client.render;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** interface for textures/models init */
public interface MoCustomModel
{	
	@SideOnly(Side.CLIENT)
	public void initModel();
}