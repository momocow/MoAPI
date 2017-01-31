package me.momocow.moapi.item;

import me.momocow.moapi.client.render.MoCustomModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

abstract public class MoItem extends Item implements MoCustomModel{
	public MoItem(){
		super();
	}
	
	@Override
	public void initModel(){
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}
}
