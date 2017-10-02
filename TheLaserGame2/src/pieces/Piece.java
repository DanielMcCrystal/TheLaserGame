package pieces;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import board.Directions;
import board.Team;
import engine.Controls;

/**
 * Holds the functions that all pieces share
 * @author Daniel McCrystal
 */
public abstract class Piece {

	protected int posX, posY;
	protected int direction;
	protected BufferedImage texture, healthBarTexture;
	protected int maxHealth, health;
	protected double healthPercent;
	protected Team team;
	protected String type;
	protected boolean hasHealth;

	private float opacity = (float) 0.5;

	public Piece(int x, int y, int dir, Team t) {
		posX = x;
		posY = y;
		direction = dir;
		team = t;

		team.addToTeam(this);

		healthBarTexture = team.teamTheme.healthBarTexture;
		hasHealth = true;

		team.game.tileset[posX][posY].piece = this;
	}
	
	/**
	 * sets the initial texture to the correct rotation
	 */
	protected void setRotation() {
		if (direction != 0) {
			AffineTransform transformer = new AffineTransform();
			if (direction == 1) {
				transformer.rotate(Math.PI / 2, texture.getWidth() / 2,
						texture.getHeight() / 2);

			}
			else if (direction == 2) {
				transformer.rotate(Math.PI, texture.getWidth() / 2,
						texture.getHeight() / 2);
			}
			else if (direction == 3) {
				transformer.rotate(-Math.PI / 2, texture.getWidth() / 2,
						texture.getHeight() / 2);
			}
			AffineTransformOp op = new AffineTransformOp(transformer,
					AffineTransformOp.TYPE_BILINEAR);
			texture = op.filter(texture, null);
		}
		
	}

	public void move(int dir) {
		int origPosX = posX;
		int origPosY = posY;

		Controls.p.activeGame.tileset[posX][posY].piece = null;

		if (dir == Directions.UP) {
			posY--;
		}
		else if (dir == Directions.RIGHT) {
			posX++;
		}
		else if (dir == Directions.DOWN) {
			posY++;
		}
		else if (dir == Directions.LEFT) {
			posX--;
		}

		Controls.p.activeGame.tileset[posX][posY].piece = this;

		System.out.println(team.name + " " + type + " moved from (" + origPosX
				+ ", " + origPosY + ") to (" + posX + ", " + posY + ")");
	}

	public void rotate(int dir) {
		AffineTransform transformer = new AffineTransform();
		if (dir == Directions.CLOCKWISE) {

			transformer.rotate(Math.PI / 2, texture.getWidth() / 2,
					texture.getHeight() / 2);

			if (direction == 3)
				direction = 0;
			else
				direction++;

			System.out.println(team.name + " " + type
					+ " rotated clockwise at (" + posX + ", " + posY + ")");
		}
		else if (dir == Directions.COUNTERCLOCKWISE) {

			transformer.rotate(-Math.PI / 2, texture.getWidth() / 2,
					texture.getHeight() / 2);

			if (direction == 0)
				direction = 3;
			else
				direction--;

			System.out.println(team.name + " " + type
					+ " rotated counterclockwise at (" + posX + ", " + posY
					+ ")");
		}
		AffineTransformOp op = new AffineTransformOp(transformer,
				AffineTransformOp.TYPE_BILINEAR);
		texture = op.filter(texture, null);
	}
	
	/**
	 * Method that gets called when a piece gets damaged by a beam
	 * @param dmg
	 */
	public void hit(int dmg) {
		System.out.println(team.name + " " + type + " was hit!");
		
		team.game.tileset[posX][posY].hit = true;

		health -= dmg;
		healthPercent = (double) health / maxHealth;

		healthBarTexture = team.teamTheme.healthBarTexture.getSubimage(0, 0,
				(int) (healthPercent * 50 + 25), 100);

		if (health <= 0) {
			System.out.println(team.name + " " + type + " was destroyed at ("
					+ posX + ", " + posY + ")");
			team.game.tileset[posX][posY].hit = false;
			team.game.tileset[posX][posY].piece = null;
			team.pieces.remove(team.indexOf(this));
		}
	}
	
	/**
	 * Implemented to define how the beam should interact with the piece when being drawn
	 * @param page
	 * @param b
	 */
	public abstract void interact(Graphics page, Beam b);
	
	/**
	 * Implemented to define how the beam should interact with the piece when calculating damage
	 * @param b
	 */
	public abstract void interact(Beam b);

	public BufferedImage getTexture() {
		return texture;
	}

	public int getX() {
		return posX;
	}

	public int getY() {
		return posY;
	}

	public int getDirection() {
		return direction;
	}

	public Team getTeam() {
		return team;
	}

	public String getType() {
		return type;
	}

	public int getHealth() {
		return health;
	}

	public String directionToString() {
		if (direction == Directions.UP) {
			return "Up";
		}
		else if (direction == Directions.RIGHT) {
			return "Right";
		}
		else if (direction == Directions.DOWN) {
			return "Down";
		}
		else if (direction == Directions.LEFT) {
			return "Left";
		}

		return "Something went wrong";
	}

	public void load(Graphics page) {

		Graphics2D page2d = (Graphics2D) page;

		page2d.drawImage(texture, posX * 100, posY * 100, Controls.p);

		if (hasHealth) {
			page2d.drawImage(healthBarTexture, posX * 100, posY * 100,
					Controls.p);
			page2d.drawImage(team.teamTheme.healthOutlineTexture, posX * 100, posY * 100,
					Controls.p);
		}

		if (team.game.selectedPiece == this) {

			page2d.setComposite(AlphaComposite.getInstance(
					AlphaComposite.SRC_OVER, (float) 0.3));
			if (Controls.p.gc.up != null && Controls.p.gc.up.piece == null) {
				if (Controls.p.activeGame.tileset[posX][posY - 1].hovered)
					page2d.setComposite(AlphaComposite.getInstance(
							AlphaComposite.SRC_OVER, opacity));
				page.drawImage(texture, posX * 100, (posY - 1) * 100,
						Controls.p);

			}

			page2d.setComposite(AlphaComposite.getInstance(
					AlphaComposite.SRC_OVER, (float) 0.3));
			if (Controls.p.gc.right != null
					&& Controls.p.gc.right.piece == null) {
				if (Controls.p.activeGame.tileset[posX + 1][posY].hovered)
					page2d.setComposite(AlphaComposite.getInstance(
							AlphaComposite.SRC_OVER, opacity));
				page.drawImage(texture, (posX + 1) * 100, posY * 100,
						Controls.p);

			}

			page2d.setComposite(AlphaComposite.getInstance(
					AlphaComposite.SRC_OVER, (float) 0.3));
			if (Controls.p.gc.down != null && Controls.p.gc.down.piece == null) {
				if (Controls.p.activeGame.tileset[posX][posY + 1].hovered)
					page2d.setComposite(AlphaComposite.getInstance(
							AlphaComposite.SRC_OVER, opacity));
				page.drawImage(texture, posX * 100, (posY + 1) * 100,
						Controls.p);

			}

			page2d.setComposite(AlphaComposite.getInstance(
					AlphaComposite.SRC_OVER, (float) 0.3));
			if (Controls.p.gc.left != null && Controls.p.gc.left.piece == null) {
				if (Controls.p.activeGame.tileset[posX - 1][posY].hovered)
					page2d.setComposite(AlphaComposite.getInstance(
							AlphaComposite.SRC_OVER, opacity));
				page.drawImage(texture, (posX - 1) * 100, posY * 100,
						Controls.p);

			}

			page2d.setComposite(AlphaComposite.getInstance(
					AlphaComposite.SRC_OVER, (float) 1));
		}

	}
	
	public String toString() {
		return team.name + " " + type + " @ x:" + posX + ", y:" + posY; 
	}
}
