package pieces;

import java.awt.Graphics;

import board.Directions;
import board.Team;
import board.Tile;
import engine.Controls;
import engine.Play;

/**
 * Represents a laser piece
 * @author Daniel McCrystal
 */
public class Laser extends Piece {

	public Beam beam;
	private boolean laserOn = true;

	public Laser(int x, int y, int dir, Team t) {
		super(x, y, dir, t);

		maxHealth = 5;
		health = maxHealth;

		team.setTeamLaser(this);

		type = "Laser";

		texture = team.teamTheme.laserTexture;
		setRotation();

		beam = new Beam(team);
	}

	public void toggleLaser() {
		laserOn = !laserOn;
	}

	public boolean laserOn() {
		return laserOn;
	}

	public void primeBeam() {
		beam.posX = posX;
		beam.posY = posY;

		beam.direction = direction;
		
		beam.startDirection = direction;
		if(direction == Directions.UP) {
			beam.startPosX = posX;
			beam.startPosY = posY - 1;
		}
		else if(direction == Directions.DOWN) {
			beam.startPosX = posX;
			beam.startPosY = posY + 1;
		}
		else if(direction == Directions.LEFT) {
			beam.startPosX = posX - 1;
			beam.startPosY = posY;
		}
		else if(direction == Directions.RIGHT) {
			beam.startPosX = posX + 1;
			beam.startPosY = posY;
		}
	}

	public void interact(Beam b) {
		if (b.direction == Directions.UP) {
			if (!laserOn || direction != Directions.DOWN) {
				hit(1);
			}
		} else if (b.direction == Directions.RIGHT) {
			if (!laserOn || direction != Directions.LEFT) {
				hit(1);
			}
		} else if (b.direction == Directions.DOWN) {
			if (!laserOn || direction != Directions.UP) {
				hit(1);
			}
		} else if (b.direction == Directions.LEFT) {
			if (!laserOn || direction != Directions.RIGHT) {
				hit(1);
			}
		}

		b.laserDone = true;
	}

	public void interact(Graphics page, Beam b) {
		if (b.direction == Directions.UP) {
			page.drawImage(b.team.teamTheme.bottomBeamTexture, b.posX * 100,
					b.posY * 100 + 50, Controls.p);
		} else if (b.direction == Directions.RIGHT) {
			page.drawImage(b.team.teamTheme.leftBeamTexture, b.posX * 100, b.posY * 100,
					Controls.p);
		} else if (b.direction == Directions.DOWN) {
			page.drawImage(b.team.teamTheme.topBeamTexture, b.posX * 100, b.posY * 100,
					Controls.p);
		} else if (b.direction == Directions.LEFT) {
			page.drawImage(b.team.teamTheme.rightBeamTexture, b.posX * 100 + 50,
					b.posY * 100, Controls.p);
		}

		b.laserDone = true;
	}

	public Tile[] adjacentTiles() {
		Tile[] affectedTiles = new Tile[4];

		try {
			affectedTiles[0] = Controls.p.activeGame.tileset[posX][posY - 1];
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			affectedTiles[1] = Controls.p.activeGame.tileset[posX + 1][posY];
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			affectedTiles[2] = Controls.p.activeGame.tileset[posX - 1][posY];
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			affectedTiles[3] = Controls.p.activeGame.tileset[posX][posY + 1];
		} catch (ArrayIndexOutOfBoundsException e) {
		}

		return affectedTiles;
	}

	public boolean onEdge() {
		return posX == 0 || posX == Play.DIMENSION_X - 1 || posY == 0
				|| posY == Play.DIMENSION_Y - 1;
	}
}
