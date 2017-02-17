package me.momocow.mobasic.client.gui.widget;

import joptsimple.internal.Strings;
import me.momocow.mobasic.client.gui.MoGuiScreen;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoTextFieldPassword extends MoTextField
{	
	public MoTextFieldPassword(int componentId, FontRenderer fontrendererObj, 
    		int x, int y, 
    		int disable_x, int disable_y, 
    		int normal_x, int normal_y, 
    		int txtWidth, int txtHeight, 
    		int imgWidth, int imgHeight, 
    		int guiWidth, int guiHeight,
    		ResourceLocation txt) 
	{
		super(componentId, fontrendererObj, x, y, disable_x, disable_y, normal_x, normal_y, txtWidth, txtHeight, imgWidth, imgHeight, guiWidth, guiHeight, txt);
	}
	
	/**
     * Draws the textbox
     */
    public void drawTextBox()
    {
        if (this.getVisible())
        {
        	if(this.isEnabled)
        	{
        		MoGuiScreen.drawPartialScaleTexturedRect(this.texture, this.xPosition, this.yPosition, this.zLevel, this.enabledX, this.enabledY, this.textureWidth, this.textureHeight, this.imageWidth, this.imageHeight, this.width, this.height);
        	}
        	else
        	{
        		MoGuiScreen.drawPartialScaleTexturedRect(this.texture, this.xPosition, this.yPosition, this.zLevel, this.disabledX, this.disabledY, this.textureWidth, this.textureHeight, this.imageWidth, this.imageHeight, this.width, this.height);
        	}

            int i = this.isEnabled ? this.enabledColor : this.disabledColor;
            int j = this.cursorPosition - this.lineScrollOffset;
            int k = this.selectionEnd - this.lineScrollOffset;
            String s = this.fontRendererInstance.trimStringToWidth(Strings.repeat('*', this.text.substring(this.lineScrollOffset).length()), this.getWidth());
            boolean flag = j >= 0 && j <= s.length();
            boolean flag1 = this.isFocused && this.cursorCounter / 6 % 2 == 0 && flag;
            int l = this.enableBackgroundDrawing ? this.xPosition + 4 : this.xPosition;
            int i1 = this.enableBackgroundDrawing ? this.yPosition + (this.height - 8) / 2 : this.yPosition;
            int j1 = l;

            if (k > s.length())
            {
                k = s.length();
            }

            if (!s.isEmpty())
            {
                String s1 = flag ? s.substring(0, j) : s;
                j1 = this.fontRendererInstance.drawString(s1, l, i1, i);
            }

            boolean flag2 = this.cursorPosition < this.text.length() || this.text.length() >= this.getMaxStringLength();
            int k1 = j1;

            if (!flag)
            {
                k1 = j > 0 ? l + this.width : l;
            }
            else if (flag2)
            {
                k1 = j1 - 1;
                --j1;
            }

            if (!s.isEmpty() && flag && j < s.length())
            {
                j1 = this.fontRendererInstance.drawString(s.substring(j), j1, i1, i);
            }

            if (flag1)
            {
                if (flag2)
                {
                    Gui.drawRect(k1, i1 - 1, k1 + 1, i1 + 1 + this.fontRendererInstance.FONT_HEIGHT, -3092272);
                }
                else
                {
                    this.fontRendererInstance.drawString("_", k1, i1, i);
                }
            }

            if (k != j)
            {
                int l1 = l + this.fontRendererInstance.getStringWidth(s.substring(0, k));
                this.drawCursorVertical(k1, i1 - 1, l1 - 1, i1 + 1 + this.fontRendererInstance.FONT_HEIGHT);
            }
        }
    }
}
