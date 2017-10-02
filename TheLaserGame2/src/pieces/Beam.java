package pieces;

import java.awt.Graphics;

import board.Directions;
import board.Team;
import engine.Controls;
import engine.Play;

/**
 * Represents the beam that is emitted from a laser
 * @author Daniel McCrystal
 */
public class Beam {

	Team team;
	int posX, posY;
	int direction;
	boolean laserDone;

	int startPosX, startPosY;
	int startDirection;

	public Beam(Team t) {
		team = t;
	}
	
	/**
	 * draws the beam on the board
	 * @param page
	 */
	public void paintBeam(Graphics page) {
		laserDone = false;
		
		// draw the initial beam coming out of the tip of the laser
		if (direction == Directions.UP) {
			page.drawImage(team.teamTheme.topBeamTexture, posX * 100, posY * 100,
					Controls.p);
		}
		else if (direction == Directions.RIGHT) {
			page.drawImage(team.teamTheme.rightBeamTexture, posX * 100 + 50, posY * 100,
					Controls.p);
		}
		else if (direction == Directions.DOWN) {
			page.drawImage(team.teamTheme.bottomBeamTexture, posX * 100, posY * 100 + 50,
					Controls.p);
		}
		else if (direction == Directions.LEFT) {
			page.drawImage(team.teamTheme.leftBeamTexture, posX * 100, posY * 100,
					Controls.p);
		}
		
		
		// follows the path of the beam
		while (withinBounds()) {

			if (direction == Directions.UP) {
				posY--;
				if (Controls.p.activeGame.tileset[posX][posY].piece != null) {
					Controls.p.activeGame.tileset[posX][posY].piece.interact(
							page, this);
				} else {
					page.drawImage(team.teamTheme.verticalBeamTexture, posX * 100,
							posY * 100, Controls.p);
				}
			} else if (direction == Directions.RIGHT) {
				posX++;
				if (Controls.p.activeGame.tileset[posX][posY].piece != null) {
					Controls.p.activeGame.tileset[posX][posY].piece.interact(
							page, this);
				} else {
					page.drawImage(team.teamTheme.horizontalBeamTexture, posX * 100,
							posY * 100, Controls.p);
				}
			} else if (direction == Directions.DOWN) {
				posY++;
				if (Controls.p.activeGame.tileset[posX][posY].piece != null) {
					Controls.p.activeGame.tileset[posX][posY].piece.interact(
							page, this);
				} else {
					page.drawImage(team.teamTheme.verticalBeamTexture, posX * 100,
							posY * 100, Controls.p);
				}
			} else if (direction == Directions.LEFT) {
				posX--;
				if (Controls.p.activeGame.tileset[posX][posY].piece != null) {
					Controls.p.activeGame.tileset[posX][posY].piece.interact(
							page, this);
				} else {
					page.drawImage(team.teamTheme.horizontalBeamTexture, posX * 100,
							posY * 100, Controls.p);
				}
			}

			if (laserDone) {
				break;
			}
		}
	}
	
	/**
	 * follows the path of the beam and does damage to corresponding pieces
	 */
	public void fireBeam() {
		laserDone = false;
		boolean pieceDestroyed;

		while (withinBounds()) {
			pieceDestroyed = false;
			if (direction == Directions.UP) {
				posY--;
				if (Controls.p.activeGame.tileset[posX][posY].piece != null) {
					Controls.p.activeGame.tileset[posX][posY].piece
							.interact(this);
					if (Controls.p.activeGame.tileset[posX][posY].piece == null) {
						pieceDestroyed = true;
					}
				}
			} else if (direction == Directions.RIGHT) {
				posX++;
				if (Controls.p.activeGame.tileset[posX][posY].piece != null) {
					Controls.p.activeGame.tileset[posX][posY].piece
							.interact(this);
					if (Controls.p.activeGame.tileset[posX][posY].piece == null) {
						pieceDestroyed = true;
					}
				}
			} else if (direction == Directions.DOWN) {
				posY++;
				if (Controls.p.activeGame.tileset[posX][posY].piece != null) {
					Controls.p.activeGame.tileset[posX][posY].piece
							.interact(this);
					if (Controls.p.activeGame.tileset[posX][posY].piece == null) {
						pieceDestroyed = true;
					}
				}
			} else if (direction == Directions.LEFT) {
				posX--;
				if (Controls.p.activeGame.tileset[posX][posY].piece != null) {
					Controls.p.activeGame.tileset[posX][posY].piece
							.interact(this);
					if (Controls.p.activeGame.tileset[posX][posY].piece == null) {
						pieceDestroyed = true;
					}
				}
			}

			if (pieceDestroyed) {
				posX = startPosX;
				posY = startPosY;
				direction = startDirection;
			}

			if (laserDone) {
				break;
			}
		}
	}
	
	/**
	 * determines whether or not the beam is still within the bounds of the board
	 */
	private boolean withinBounds() {
		if (direction == Directions.UP) {
			return posY > 0;
		}
		else if (direction == Directions.RIGHT) {
			return posX < Play.DIMENSION_X - 1;
		}
		else if (direction == Directions.DOWN) {
			return posY < Play.DIMENSION_Y - 1;
		}
		else if (direction == Directions.LEFT) {
			return posX > 0;
		}
		return false;
	}

	public void setTeam(Team t) {
		team = t;
	}

}
