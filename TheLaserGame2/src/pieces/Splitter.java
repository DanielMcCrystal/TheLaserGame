package pieces;

import java.awt.Graphics;

import engine.MiscMethods;
import board.Directions;
import board.Team;

/**
 * Represents a splitter piece
 * @author Daniel McCrystal
 */
public class Splitter extends Piece {

	Beam leftBeam, rightBeam;

	public Splitter(int x, int y, int dir, Team t) {
		super(x, y, dir, t);
		
		maxHealth = 8;
		health = maxHealth;

		type = "Splitter";
		
		texture = team.teamTheme.splitterTexture;
		setRotation();

		leftBeam = new Beam(team);
		rightBeam = new Beam(team);
	}

	public void primeBeam() {
		leftBeam.posX = posX;
		leftBeam.posY = posY;
		leftBeam.direction = MiscMethods.getRotated(
				Directions.COUNTERCLOCKWISE, direction);

		rightBeam.posX = posX;
		rightBeam.posY = posY;
		rightBeam.direction = MiscMethods.getRotated(Directions.CLOCKWISE,
				direction);
		
		// set leftBeam start
		if(leftBeam.direction == Directions.UP) {
			leftBeam.startPosX = posX;
			leftBeam.startPosY = posY - 1;
		}
		else if(leftBeam.direction == Directions.DOWN) {
			leftBeam.startPosX = posX;
			leftBeam.startPosY = posY + 1;
		}
		else if(leftBeam.direction == Directions.LEFT) {
			leftBeam.startPosX = posX - 1;
			leftBeam.startPosY = posY;
		}
		else if(leftBeam.direction == Directions.RIGHT) {
			leftBeam.startPosX = posX + 1;
			leftBeam.startPosY = posY;
		}
		
		// set rightBeam start
		if(rightBeam.direction == Directions.UP) {
			rightBeam.startPosX = posX;
			rightBeam.startPosY = posY - 1;
		}
		else if(rightBeam.direction == Directions.DOWN) {
			rightBeam.startPosX = posX;
			rightBeam.startPosY = posY + 1;
		}
		else if(rightBeam.direction == Directions.LEFT) {
			rightBeam.startPosX = posX - 1;
			rightBeam.startPosY = posY;
		}
		else if(rightBeam.direction == Directions.RIGHT) {
			rightBeam.startPosX = posX + 1;
			rightBeam.startPosY = posY;
		}
		
		leftBeam.startDirection = leftBeam.direction;
		rightBeam.startDirection = rightBeam.direction;
	}

	public void interact(Graphics page, Beam b) {
		if (b.direction == Directions.UP) {
			page.drawImage(b.team.teamTheme.bottomBeamTexture, posX * 100, posY * 100 + 50, null);
		}
		else if (b.direction == Directions.RIGHT) {
			page.drawImage(b.team.teamTheme.leftBeamTexture, posX * 100, posY * 100, null);
		}
		else if (b.direction == Directions.DOWN) {
			page.drawImage(b.team.teamTheme.topBeamTexture, posX * 100, posY * 100, null);
		}
		else if (b.direction == Directions.LEFT) {
			page.drawImage(b.team.teamTheme.rightBeamTexture, posX * 100 + 50, posY * 100, null);
		}

		b.laserDone = true;

		if (b.direction == direction) {
			primeBeam();
			leftBeam.setTeam(b.team);
			rightBeam.setTeam(b.team);

			rightBeam.paintBeam(page);
			leftBeam.paintBeam(page);
		}
	}

	public void interact(Beam b) {
		b.laserDone = true;
		if (b.direction == direction) {
			primeBeam();

			rightBeam.fireBeam();
			leftBeam.fireBeam();
		}
		else if(b.direction == MiscMethods.getRotatedTwice(direction)){
			hit(1);
		}
	}
}
