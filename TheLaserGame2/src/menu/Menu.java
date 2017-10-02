package menu;

import java.awt.Graphics;
import java.util.ArrayList;

/**
 * Represents a menu, or a list of individual options on a page
 * @author Daniel McCrystal
 */
public abstract class Menu {
	
	private ArrayList<Option> options;
	
	public Menu() {
		options = new ArrayList<Option>();
	}
	
	public Option getOption(int index) {
		return options.get(index);
	}
	
	public int optionsLength() {
		return options.size();
	}
	
	public abstract void drawMenuBG(Graphics page);
	
	public void addOption(Option o) {
		options.add(o);
	}
	
	public void loadMenu(Graphics page) {
		drawMenuBG(page);
		for(Option o: options) {
			o.load(page);
		}
	}
}
