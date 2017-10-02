package board;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import pieces.Piece;
import engine.Controls;
import engine.Game;

/**
 * Represents a tile on a board
 * @author Daniel McCrystal
 */
public class Tile {

	public Piece piece;
	private Game game;
	public int xCoord, yCoord;
	public int type;
	private Image texture;
	public boolean hovered, selected, hit;
	
	/**
	 * 
	 * @param type the type of the tile (e.g. black or gray)
	 * @param game the game that the tile is a part of
	 */
	public Tile(int type, Game game) {
		this.type = type;
		this.game = game;;
		
		if(type == 1){
			texture = this.game.theme.tileTexture1;
		}
		else
			texture = this.game.theme.tileTexture2;
	}
	
	public Image getTexture() {
		return texture;
	}

	public void load(Graphics page) {
		page.drawImage(texture, xCoord * 100, yCoord * 100, Controls.p);
		
		if(hit) {
			Graphics2D page2d = (Graphics2D) page;
			page2d.setColor(Color.red);
			page2d.setComposite(AlphaComposite.getInstance(
					AlphaComposite.SRC_OVER, (float) 0.3));
			page2d.fillRect(xCoord * 100, yCoord * 100, 100, 100);
			page2d.setComposite(AlphaComposite.getInstance(
					AlphaComposite.SRC_OVER, (float) 1));
			page.fillRect(xCoord * 100, yCoord * 100, 2, 100);
			page.fillRect(xCoord * 100, yCoord * 100, 100, 2);
			page.fillRect(xCoord * 100 + 98, yCoord * 100, 2, 100);
			page.fillRect(xCoord * 100, yCoord * 100 + 98, 100, 2);
		}
		
		if(hovered){
			page.drawImage(game.theme.hoveredTexture, xCoord * 100, yCoord * 100, Controls.p);
		}
		
		if(selected){
			page.drawImage(game.theme.selectedTexture, xCoord * 100, yCoord * 100, Controls.p);
		}
	}

}
