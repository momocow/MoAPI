package me.momocow.mobasic.client.gui.widget;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import me.momocow.mobasic.client.gui.MoGuiScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class MoButton extends GuiButton
{
	protected ResourceLocation TEXTURE;
	
	private List<String> tooltip = new ArrayList<String>();

    public MoButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, ResourceLocation textures)
    {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        
        this.TEXTURE = textures;
    }
    
    public boolean isHovered(int mouseX, int mouseY)
    {
    	return mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
    }
    
    public List<String> getTooltip()
    {
    	return this.tooltip;
    }
    
    public void setTootip(List<String> ttp)
    {
    	this.tooltip = Lists.newArrayList(ttp);
    }
    
    public void addTootip(List<String> ttp)
    {
    	this.tooltip.addAll(ttp);
    }
    
    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
    	if (this.visible)
        {
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            MoGuiScreen.drawProportionTexturedRect(this.TEXTURE, this.xPosition, this.yPosition, this.zLevel, 0, 0, this.width, this.height, this.width, this.height, this.width, this.height);
            this.mouseDragged(mc, mouseX, mouseY);
            int j = 14737632;

            if (packedFGColour != 0)
            {
                j = packedFGColour;
            }
            else
            if (!this.enabled)
            {
                j = 10526880;
            }
            else if (this.hovered)
            {
                j = 16777120;
            }
            
            this.drawCenteredString(mc.fontRendererObj, mc.fontRendererObj.trimStringToWidth(this.displayString, this.width - 10), this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, j);
        }
    }
    
    public boolean mouseClick(Minecraft mc, int mouseX, int mouseY, int mouseButton)
    {
    	if(this.mousePressed(mc, mouseX, mouseY))
    	{
    		this.playPressSound(mc.getSoundHandler());
    		return true;
    	}
    	return false;
    }
    
    public void setPosition(int x, int y)
    {
    	this.xPosition = x;
    	this.yPosition = y;
    }
}
