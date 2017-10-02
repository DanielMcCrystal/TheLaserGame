package pieces;

import java.awt.Graphics;

import board.Directions;
import board.Team;
import engine.Controls;

public class Mirror extends Piece {

	public Mirror(int x, int y, int dir, Team t) {
		super(x, y, dir, t);

		maxHealth = 5;
		health = maxHealth;

		type = "Mirror";

		texture = team.teamTheme.mirrorTexture;
		setRotation();
	}

	public void interact(Beam b) {
		if (b.direction == Directions.UP) {
			if (direction == Directions.SOUTHEAST) {
				b.direction = Directions.RIGHT;
			} else if (direction == Directions.SOUTHWEST) {
				b.direction = Directions.LEFT;
			} else {
				hit(1);
				if (health > 0)
					b.laserDone = true;
				return;
			}
		} else if (b.direction == Directions.RIGHT) {
			if (direction == Directions.NORTHWEST) {
				b.direction = Directions.UP;
			} else if (direction == Directions.SOUTHWEST) {
				b.direction = Directions.DOWN;
			} else {
				hit(1);
				if (health > 0)
					b.laserDone = true;
				return;
			}
		} else if (b.direction == Directions.DOWN) {
			if (direction == Directions.NORTHEAST) {
				b.direction = Directions.RIGHT;
			} else if (direction == Directions.NORTHWEST) {
				b.direction = Directions.LEFT;
			} else {
				hit(1);
				if (health > 0)
					b.laserDone = true;
				return;
			}
		} else if (b.direction == Directions.LEFT) {
			if (direction == Directions.NORTHEAST) {
				b.direction = Directions.UP;
			} else if (direction == Directions.SOUTHEAST) {
				b.direction = Directions.DOWN;
			} else {
				hit(1);
				if (health > 0)
					b.laserDone = true;
				return;
			}
		}
	}

	public void interact(Graphics page, Beam b) {
		if (b.direction == Directions.UP) {
			page.drawImage(b.team.teamTheme.bottomBeamTexture, b.posX * 100,
					b.posY * 100 + 50, Controls.p);
			if (direction == Directions.SOUTHEAST) {
				b.direction = Directions.RIGHT;
				page.drawImage(b.team.teamTheme.rightBeamTexture, posX * 100 + 50,
						posY * 100, Controls.p);
			} else if (direction == Directions.SOUTHWEST) {
				b.direction = Directions.LEFT;
				page.drawImage(b.team.teamTheme.leftBeamTexture, posX * 100, posY * 100,
						Controls.p);
			} else {
				b.laserDone = true;
				return;
			}
		} else if (b.direction == Directions.RIGHT) {
			page.drawImage(b.team.teamTheme.leftBeamTexture, b.posX * 100, b.posY * 100,
					Controls.p);
			if (direction == Directions.NORTHWEST) {
				b.direction = Directions.UP;
				page.drawImage(b.team.teamTheme.topBeamTexture, posX * 100, posY * 100,
						Controls.p);
			} else if (direction == Directions.SOUTHWEST) {
				b.direction = Directions.DOWN;
				page.drawImage(b.team.teamTheme.bottomBeamTexture, posX * 100,
						posY * 100 + 50, Controls.p);
			} else {
				b.laserDone = true;
				return;
			}
		} else if (b.direction == Directions.DOWN) {
			page.drawImage(b.team.teamTheme.topBeamTexture, b.posX * 100, b.posY * 100,
					Controls.p);
			if (direction == Directions.NORTHEAST) {
				b.direction = Directions.RIGHT;
				page.drawImage(b.team.teamTheme.rightBeamTexture, posX * 100 + 50,
						posY * 100, Controls.p);
			} else if (direction == Directions.NORTHWEST) {
				b.direction = Directions.LEFT;
				page.drawImage(b.team.teamTheme.leftBeamTexture, posX * 100, posY * 100,
						Controls.p);
			} else {
				b.laserDone = true;
				return;
			}
		} else if (b.direction == Directions.LEFT) {
			page.drawImage(b.team.teamTheme.rightBeamTexture, b.posX * 100 + 50,
					b.posY * 100, Controls.p);
			if (direction == Directions.NORTHEAST) {
				b.direction = Directions.UP;
				page.drawImage(b.team.teamTheme.topBeamTexture, posX * 100, posY * 100,
						Controls.p);
			} else if (direction == Directions.SOUTHEAST) {
				b.direction = Directions.DOWN;
				page.drawImage(b.team.teamTheme.bottomBeamTexture, posX * 100,
						posY * 100 + 50, Controls.p);
			} else {
				b.laserDone = true;
				return;
			}
		}
	}

	@Override
	public String directionToString() {
		if (direction == Directions.NORTHEAST) {
			return "NE";
		}
		else if (direction == Directions.SOUTHEAST) {
			return "SE";
		}
		else if (direction == Directions.SOUTHWEST) {
			return "SW";
		}
		else if (direction == Directions.NORTHWEST) {
			return "NW";
		}

		return "Something went wrong";
	}

}
