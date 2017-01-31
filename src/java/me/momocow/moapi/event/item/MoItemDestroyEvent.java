package me.momocow.moapi.event.item;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.item.ItemEvent;

/**
 * Fired when an EntityItem is dying
 * @author MomoCow
 */
public class MoItemDestroyEvent extends ItemEvent
{
	DamageSource source;
	
	public MoItemDestroyEvent(EntityItem itemEntity, DamageSource src) {
		super(itemEntity);
		
		this.source = src;
	}
	
	public DamageSource getSource()
	{
		return this.source;
	}
}
