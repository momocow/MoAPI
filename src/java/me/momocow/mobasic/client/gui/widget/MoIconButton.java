package me.momocow.mobasic.client.gui.widget;

import me.momocow.mobasic.client.gui.MoGuiScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class MoIconButton extends MoButton
{
	private int imageWidth;
	private int imageHeight;
	private int hoveredX;
	private int hoveredY;
	private int normalX;
	private int normalY;
	private int textureWidth;
	private int textureHeight;
	
	public MoIconButton(int buttonId, int x, int y, int hover_x, int hover_y, int normal_x, int normal_y, int guiWidth, int guiHeight, int txtWidth, int txtHeight, int imgWidth, int imgHeight, ResourceLocation texture) 
	{
		super(buttonId, x, y, guiWidth, guiHeight, "", texture);
		
		this.imageWidth = imgWidth;
		this.imageHeight = imgHeight;
		this.hoveredX = hover_x;
		this.hoveredY = hover_y;
		this.normalX = normal_x;
		this.normalY = normal_y;
		this.textureWidth = txtWidth;
		this.textureHeight = txtHeight;
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		if (this.visible)
        {
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            if(this.hovered)
            {
            	MoGuiScreen.drawProportionTexturedRect(this.TEXTURE, this.xPosition, this.yPosition, this.zLevel, this.hoveredX, this.hoveredY, this.textureWidth, this.textureHeight, this.imageWidth, this.imageHeight, this.width, this.height);
            }
            else
            {
            	MoGuiScreen.drawProportionTexturedRect(this.TEXTURE, this.xPosition, this.yPosition, this.zLevel, this.normalX, this.normalY, this.textureWidth, this.textureHeight, this.imageWidth, this.imageHeight, this.width, this.height);
            }
            this.mouseDragged(mc, mouseX, mouseY);
        }
	}
}
