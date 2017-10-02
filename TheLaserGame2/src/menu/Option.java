package menu;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Represents an option that a user can interact with
 * @author Daniel McCrystal
 */
public abstract class Option {
	
	protected String title;
	protected int posX, posY;
	public HitBox hitbox;
	
	public Option(String title, int x, int y, int width, int height){
		this.title = title;
		posX = x;
		posY = y;
		
		hitbox = new HitBox(posX, posY, width, height);
	}
	
	public void load(Graphics page){
		hitbox.load(page);
		
		if(hitbox.hovered)
			page.setColor(Color.green);
		else
			page.setColor(Color.white);
		
		page.drawString(title, posX + 5, posY + 22);
	}
	
	public void changeTitle(String str){
		title = str;
	}
	
	/**
	 * Must be implemented by either a child class or an anonymous class
	 */
	public abstract void doFunction();
}
