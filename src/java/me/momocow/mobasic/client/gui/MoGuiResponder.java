package me.momocow.mobasic.client.gui;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface MoGuiResponder<T>
{
    void setEntryValue(int id, T value);
}
