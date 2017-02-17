package me.momocow.mobasic.client.gui;

/**
 * MUST call {@link MoCenteredGuiScreen#initGui()} to draw the centeredScreen at the correct position
 * @author MomoCow
 */
public class MoCenteredGuiScreen extends MoGuiScreen
{
	public MoCenteredGuiScreen(int w, int h)
	{
		super(w, h);
	}
	
	/**
	 * Gui initial size 100x100
	 */
	public MoCenteredGuiScreen()
	{
		this(100, 100);
	}
	
	/**
	 * MUST call {@link MoCenteredGuiScreen#initGui()} to draw the centeredScreen at the correct position
	 */
	@Override
	public void initGui() {
		this.setCenter(width / 2, height / 2);	//init the offset of the Gui
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
	}
}
