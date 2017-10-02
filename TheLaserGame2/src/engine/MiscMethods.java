package engine;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import board.Directions;

/**
 * Holds miscellaneous methods
 * @author Daniel McCrystal
 */
public class MiscMethods {
	public static BufferedImage missingTexture, missingBeam;

	public static void setUpMissingTextures() {
		try {
			missingTexture = ImageIO.read(new File("textures/Missing.png"));
		} catch (IOException e) {
			System.out.println("Should never get here");
		}

		try {
			missingBeam = ImageIO.read(new File("textures/missingBeam.png"));
		} catch (IOException e) {
			System.out.println("Should never get here");
		}
	}
	
	/**
	 * 
	 * @param rot the direction to rotate
	 * @param dir the current direction of the piece
	 * @return the direction that the piece would be if it were rotated in the given direction
	 */
	public static int getRotated(int rot, int dir) {
		if (rot == Directions.CLOCKWISE) {
			if (dir == 3)
				return 0;
			return dir + 1;
		}
		if (rot == Directions.COUNTERCLOCKWISE) {
			if (dir == 0)
				return 3;
			return dir - 1;
		}
		return 0;
	}

	public static int getRotatedTwice(int dir) {
		return getRotated(Directions.CLOCKWISE,
				getRotated(Directions.CLOCKWISE, dir));
	}
}