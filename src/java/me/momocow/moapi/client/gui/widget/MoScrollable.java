package me.momocow.moapi.client.gui.widget;

public interface MoScrollable 
{
	void drawScrollBar();
	void mouseClicked(int mouseX, int mouseY);
	void mouseClickMove(int mouseX, int mouseY) ;
	void mouseReleased(int mouseX, int mouseY);
	boolean isScrollBarClicked(int mouseX, int mouseY);
	boolean isScrollFieldClicked(int mouseX, int mouseY);
	void mouseWheelMove(int wheelMove);
}
