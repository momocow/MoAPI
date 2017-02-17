package me.momocow.mobasic.client.gui.widget;

import me.momocow.mobasic.client.gui.MoGuiScreen;
import net.minecraft.util.ResourceLocation;

public class MoVanillaScrollBar implements MoScrollable
{
	private ResourceLocation TEXTURE;
	
	private boolean isEnabled = false;
	private boolean isDragged = false;
	private int width;
	private int height;
	private int posX;
	private int posY;
	private float zLevel;
	private int minY;
	private int maxY;
	private int stageNum = 1;
	private int stage = 0;
	private double stageUnit = 0;
	/**
	 * local position corresponding to the scrollbar gui which is clicked by the mouse
	 * It is only meaningful when the gui is dragged
	 */
	private int clickedGuiY = 0;
	
	public MoVanillaScrollBar(int x, int y, float z, int maxY, int width, int height, int stageCount, ResourceLocation texture)
	{
		this(x, y, z, maxY, width, height, stageCount, 0, texture);
	}
	
	public MoVanillaScrollBar(int x, int y, float z, int maxY, int width, int height, int stageCount, int initialStage, ResourceLocation texture)
	{
		this.posX = x;
		this.posY = y;
		this.zLevel = z;
		this.minY = y;
		this.maxY = maxY;
		this.width = width;
		this.height = height;
		this.stageNum = Math.max(stageCount, 1);
		this.isEnabled = (this.stageNum > 1);
		this.stage = initialStage;
		this.TEXTURE = texture;

		if(this.stageNum > 1)
		{
			this.stageUnit = (double)(this.maxY - this.height - this.minY) / (double)(this.stageNum - 1);
		}
	}
	
	public void drawScrollBar()
	{
		int textureX = (this.isEnabled)? 0: 12;
		MoGuiScreen.drawPartialScaleTexturedRect(TEXTURE, this.posX, this.posY, this.zLevel, textureX, 0, 12, 15, 24, 15, this.width, this.height);
	}
	
	public boolean isDragged()
	{
		return this.isDragged;
	}
	
	public boolean isEnabled()
	{
		return this.isEnabled;
	}
	
	public boolean isLastStage(int s)
	{
		return this.getStageNum() - 1 == s;
	}
	
	public void setEnabled(boolean enable)
	{
		this.isEnabled = enable;
	}
	
	public void setStage(int s)
	{
		this.stage = s;
		this.compute();
	}
	
	public void moveNextStage()
	{
		this.setStage(this.stage + 1);
	}
	
	public void moveBackStage()
	{
		this.setStage(this.stage - 1);
	}
	
	@Override
	public boolean isScrollBarClicked(int mouseX, int mouseY)
	{
		return this.isEnabled && mouseX >= this.posX && mouseX <= this.posX + this.width && mouseY >= this.posY && mouseY <= this.posY + this.height;
	}
	
	@Override
	public boolean isScrollFieldClicked(int mouseX, int mouseY)
	{
		return this.isEnabled && !this.isScrollBarClicked(mouseX, mouseY) && mouseX >= this.posX && mouseX <= this.posX + this.width && mouseY >= this.minY && mouseY <= this.maxY;
	}
	 
	@Override
	public void mouseClicked(int mouseX, int mouseY)
	{
		this.isDragged = true;
		this.clickedGuiY = mouseY - this.posY;
    }
	
	public void scrollFieldClicked(int mouseX, int mouseY)
	{
		if(mouseY < this.posY)
		{
			this.moveBackStage();
		}
		else if(mouseY > this.posY + this.height)
		{
			this.moveNextStage();
		}
	}
    
    @Override
	public void mouseClickMove(int mouseX, int mouseY) 
	{
    	this.posY = mouseY - this.clickedGuiY;
    	if(this.posY > this.maxY - this.height)
    	{
    		this.posY = this.maxY - this.height;
    	}
    	else if(this.posY < this.minY)
    	{
    		this.posY = this.minY;
    	}
    	
    	//derive the stage from pos
    	this.stage = getStageFromPos(this.posY);
    }
    
    @Override
	public void mouseReleased(int mouseX, int mouseY) 
    {
    	this.isDragged = false;
    	
    	this.posY = mouseY - this.clickedGuiY;
    	if(this.posY > this.maxY - this.height)
    	{
    		this.posY = this.maxY - this.height;
    	}
    	else if(this.posY < this.minY)
    	{
    		this.posY = this.minY;
    	}
    	
    	//derive the stage from pos
    	this.stage = getStageFromPos(this.posY);
    	this.compute();
    }
    
    @Override
    public void mouseWheelMove(int wheelMove) 
    {
    	if(wheelMove < 0)
    	{
    		this.moveNextStage();
    	}
    	else if(wheelMove > 0)
    	{
    		this.moveBackStage();
    	}
    }
    
    private int getStageFromPos(int pos)
    {
    	int deltaY = pos - this.minY;
    	return (int)Math.ceil((double)deltaY / this.stageUnit);
    }
    
    public int getStage()
    {
    	return this.stage;
    }
    
    public int getStageNum()
    {
    	return this.stageNum;
    }
    
    /**
     * compute the valid position with the given stage
     */
    private void compute()
    {
		//stage validation
		if(this.stage < 0)
		{
			this.stage = 0;
		}
		else if(this.stage > this.stageNum - 1)
		{
			this.stage = this.stageNum - 1;
		}
		
		//derive the position from stage
		if(this.stage == 0)
		{
			this.posY = this.minY;
		}
		else if(this.stage == this.stageNum - 1)
		{
			this.posY = this.maxY - this.height;
		}
		else
		{
			this.posY = this.minY + (int)(this.stageUnit * this.stage);
		}
    }
}
