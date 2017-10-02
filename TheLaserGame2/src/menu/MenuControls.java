package menu;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import engine.Controls;

/**
 * Holds the controls for menu navigation
 * @author Daniel McCrystal
 */
public class MenuControls extends Controls {
	
	public static Menu activeMenu;
	public static Option selectedOption;
	public static TextBox selectedTextBox;
	
	public static BufferedImage menuBG;

	@Override
	public void mouseMoved(MouseEvent e) {
		if (selectedOption != null)
			selectedOption.hitbox.hovered = false;
		try {
			selectedOption = optionHovered(e.getX(), e.getY());
			selectedOption.hitbox.hovered = true;
		} catch (NullPointerException npe) {
		}
		;

		p.repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// System.out.println("x: " + e.getX() + ", y: " + e.getY());
		
		if (selectedOption != null) {
			selectedOption.doFunction();
		}
		
		Option potential = optionHovered(e.getX(), e.getY());
		if(potential instanceof TextBox) {
			selectedTextBox = (TextBox) potential;
		}
		else {
			if(selectedTextBox != null) {
				selectedTextBox.selected = false;
			}
			selectedTextBox = null;
		}
		p.repaint();
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		if(selectedTextBox != null) {
			if((int)e.getKeyChar() == 8) {
				selectedTextBox.backspace();
			}
			else if(e.getKeyChar() == KeyEvent.VK_ENTER || e.getKeyChar() == KeyEvent.VK_ESCAPE) {
				selectedTextBox.selected = false;
				selectedTextBox = null;
			}
			else {
				selectedTextBox.addLetter(e.getKeyChar());
			}
			p.repaint();
		}
	}
	
	/**
	 * 
	 * @param x the x position of the mouse
	 * @param y the y position of the mouse
	 * @return the option that the mouse hovers
	 */
	public Option optionHovered(int x, int y) {
		for (int i = 0; i < activeMenu.optionsLength(); i++) {
			if (x > activeMenu.getOption(i).hitbox.getX()
					&& x < activeMenu.getOption(i).hitbox.getX()
							+ activeMenu.getOption(i).hitbox.getWidth()
					&& y > activeMenu.getOption(i).hitbox.getY()
					&& y < activeMenu.getOption(i).hitbox.getY()
							+ activeMenu.getOption(i).hitbox.getHeight()) {
				return activeMenu.getOption(i);
			}
		}
		return null;
	}

}
