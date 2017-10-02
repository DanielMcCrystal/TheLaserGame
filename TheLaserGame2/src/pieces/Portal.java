package pieces;

import java.awt.Graphics;

import engine.MiscMethods;
import board.Directions;
import board.Team;

/**
 * Represents a portal piece
 * @author Daniel McCrystal
 */
public class Portal extends Piece {

	private Portal portal;

	public Portal(int x, int y, int dir, Team t) {
		super(x, y, dir, t);
		hasHealth = false;
		
		type = "Portal";

		texture = team.teamTheme.portalTexture;
		setRotation();
	}

	public void interact(Graphics page, Beam b) {
		if (b.direction == Directions.UP) {
			page.drawImage(b.team.teamTheme.bottomBeamTexture, posX * 100,
					posY * 100 + 50, null);
		} else if (b.direction == Directions.RIGHT) {
			page.drawImage(b.team.teamTheme.leftBeamTexture, posX * 100, posY * 100, null);
		} else if (b.direction == Directions.DOWN) {
			page.drawImage(b.team.teamTheme.topBeamTexture, posX * 100, posY * 100, null);
		} else if (b.direction == Directions.LEFT) {
			page.drawImage(b.team.teamTheme.rightBeamTexture, posX * 100 + 50,
					posY * 100, null);
		}

		// Test if beam goes into portal and go there
		boolean wentThrough = false;

		if (b.direction == direction) {
			b.direction = portal.direction;
			wentThrough = true;
		} else if (b.direction == MiscMethods.getRotatedTwice(direction)) {
			b.direction = MiscMethods.getRotatedTwice(portal.direction);
			wentThrough = true;
		} else {
			b.laserDone = true;
		}

		if (wentThrough) {
			b.posX = portal.posX;
			b.posY = portal.posY;
			if (b.direction == Directions.UP) {
				page.drawImage(b.team.teamTheme.topBeamTexture, b.posX * 100,
						b.posY * 100, null);
			}
			if (b.direction == Directions.RIGHT) {
				page.drawImage(b.team.teamTheme.rightBeamTexture, b.posX * 100 + 50,
						b.posY * 100, null);
			}
			if (b.direction == Directions.DOWN) {
				page.drawImage(b.team.teamTheme.bottomBeamTexture, b.posX * 100,
						b.posY * 100 + 50, null);
			}
			if (b.direction == Directions.LEFT) {
				page.drawImage(b.team.teamTheme.leftBeamTexture, b.posX * 100,
						b.posY * 100, null);
			}
		}
	}

	public void interact(Beam b) {
		boolean wentThrough = false;

		if (b.direction == direction) {
			b.direction = portal.direction;
			wentThrough = true;
		} else if (b.direction == MiscMethods.getRotatedTwice(direction)) {
			b.direction = MiscMethods.getRotatedTwice(portal.direction);
			wentThrough = true;
		} else {
			b.laserDone = true;
		}

		if (wentThrough) {
			b.posX = portal.posX;
			b.posY = portal.posY;
		}
	}

	public void link(Portal p) {
		portal = p;
	}

}
