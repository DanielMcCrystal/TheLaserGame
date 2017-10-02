package menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

/**
 * Option that a user can type into to input a String
 * @author Daniel McCrystal
 */
public class TextBox extends Option {
	
	private String text;
	public boolean selected = false;
	
	public TextBox(String startText, int x, int y, int width, int height) {
		super("", x, y, width, height);
		text = startText;
	}
	
	public void doFunction() {
		selected = true;
	}
	
	public String getText() {
		return text;
	}
	
	public void addLetter(char c) {
		if(text.length() <= 21) {
			text += c;
		}
	}
	
	public void backspace() {
		if(text.length() > 0) {
			text = text.substring(0, text.length()-1);
		}
	}
	
	public void clear() {
		text = "";
	}
	
	public void load(Graphics page) {
		super.load(page);
		String underscore = "";
		if(selected) {
			page.setColor(Color.blue);
			page.drawRect(posX, posY, hitbox.getWidth(), hitbox.getHeight());
			page.setColor(Color.cyan);
			underscore = "_";
		}
		else {
			page.setColor(Color.yellow);
		}
		page.drawString(text + underscore, posX + 5, posY + 22);
	}
}
