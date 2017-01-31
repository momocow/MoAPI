package me.momocow.moapi.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class MultiStateItem extends MoItem{
	public static List<ModelResourceLocation> models = new ArrayList<ModelResourceLocation>();
	
	public MultiStateItem(){
		super();
		this.setHasSubtypes(true);
	}

	public void initState(int n){
		for(int i = 0; i< n; i++){
			models.add(new ModelResourceLocation(this.getRegistryName().toString() + i, "inventory"));
		}
	}
	
	public void initState(){
		initState(1);
	}
	
	@Override
	public void initModel(){
		for(int m = 0; m< models.size(); m++){
			ModelBakery.registerItemVariants((Item)this, models.get(m));
			ModelLoader.setCustomModelResourceLocation(this, m, models.get(m));
		}
	}
}
