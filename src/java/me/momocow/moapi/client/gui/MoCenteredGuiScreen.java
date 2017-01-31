package me.momocow.moapi.client.gui;

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
	
	@Override
	public void initGui() {
		this.setCenter(width / 2, height / 2);	//init the offset of the Gui
	}
}
