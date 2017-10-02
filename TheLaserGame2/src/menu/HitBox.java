package menu;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Represents the area that the mouse has to hover to select a certain Option
 * @author Daniel McCrystal
 */
public class HitBox {
	private int posX, posY, width, height;
	public boolean hovered;
	
	public HitBox(int x, int y, int width, int height){
		posX = x;
		posY = y;
		this.width = width;
		this.height = height;
	}
	
	public void load(Graphics page){
		
		if(hovered)
			page.setColor(Color.green);
		else
			page.setColor(Color.white);
		
		page.drawRect(posX, posY, width, height);
	}
	
	public int getX(){
		return posX;
	}
	public int getY(){
		return posY;
	}
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}
}
