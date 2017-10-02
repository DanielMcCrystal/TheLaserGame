package board;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import engine.MiscMethods;

/**
 * Represents a visual theme that a game displays
 * @author Daniel McCrystal
 */
public class Theme {

	public static Theme devil_angel, classic;
	
	public TeamTheme team1, team2;
	public BufferedImage tileTexture1, tileTexture2, hoveredTexture, selectedTexture;
	private String identifier;
	public Font font;

	/**
	 * 
	 * @param name The name of the theme
	 */
	public Theme(String name) {
		identifier = name;

		
	}
	
	/**
	 * 
	 * @return the name of the theme
	 */
	public String getIdentifier(){
		return identifier;
	}
	
	/**
	 * Sets the textures for the theme
	 * @param one The path to the texture of the first tile
	 * @param two The path to the texture of the second tile
	 * @param hovered The path to the texture of the 'hovered' indicator 
	 * @param selected The path to the texture of the 'selected' indicator
	 * @return returns self for method chaining
	 */
	public Theme setTileTextures(String one, String two, String hovered,
			String selected) {
		System.out.println("Searching path: " + one);
		try {
			tileTexture1 = ImageIO.read(new File(one));
			System.out.println("--Loaded TILE1 texture for " + identifier
					+ " board--");
		} catch (IOException e) {
			tileTexture1 = MiscMethods.missingTexture;
			System.out.println("==Couldn't find TILE1 texture for "
					+ identifier + " board==");
		}

		System.out.println("Searching path: " + two);
		try {
			tileTexture2 = ImageIO.read(new File(two));
			System.out.println("--Loaded TILE2 texture for " + identifier
					+ " board--");
		} catch (IOException e) {
			tileTexture2 = MiscMethods.missingTexture;
			System.out.println("==Couldn't find TILE2 texture for "
					+ identifier + " board==");
		}

		System.out.println("Searching path: " + hovered);
		try {
			hoveredTexture = ImageIO.read(new File(hovered));
			System.out.println("--Loaded HOVERED texture for " + identifier
					+ " board--");
		} catch (IOException e) {
			hoveredTexture = MiscMethods.missingTexture;
			System.out.println("==Couldn't find HOVERED texture for "
					+ identifier + " board==");
		}

		System.out.println("Searching path: " + selected);
		try {
			selectedTexture = ImageIO.read(new File(selected));
			System.out.println("--Loaded SELECTED texture for " + identifier
					+ " board--");
		} catch (IOException e) {
			selectedTexture = MiscMethods.missingTexture;
			System.out.println("==Couldn't find SELECTED texture for "
					+ identifier + " board==");
		}

		return this;
	}
	
	/**
	 * Sets the textures for the victory screens for the theme
	 * @param team1 The path to the texture of the victory screen for Team 1
	 * @param team2 The path to the texture of the victory screen for Team 2
	 */
	public void setVictoryScreens(String team1, String team2){
		
		System.out.println("Searching path: " + team1);
		try {
			this.team1.victoryScreen = ImageIO.read(new File(team1));
			System.out.println("--Loaded VICTORY SCREEN for " + identifier + " - " + this.team1.name + " team--");
		} catch (IOException e) {
			System.out.println("==Couldn't find VICTORY SCREEN for " + identifier + " - " + this.team1.name + " team==");
		}
		
		System.out.println("Searching path: " + team2);
		try {
			this.team2.victoryScreen = ImageIO.read(new File(team2));
			System.out.println("--Loaded VICTORY SCREEN for " + identifier + " - " + this.team2.name + " team--");
		} catch (IOException e) {
			System.out.println("==Couldn't find VICTORY SCREEN for " + identifier + " - " + this.team2.name + " team==");
		}
	}

	public void setFont(Font f) {
		font = f;
	}
	
	/**
	 * Sets up the textures for each board
	 */
	public static void createThemes() {

		String directory;

		directory = "textures/matchups/devil_angel/";
		devil_angel = new Theme("Devils vs. Angels").setTileTextures(directory
				+ "devilTile.png", directory + "heavenTile.png", directory
				+ "Hovered.png", directory + "Selected.png");
		devil_angel.setFont(new Font("Didot", Font.PLAIN, 18));
		devil_angel.team1 = new TeamTheme("Devils");
		devil_angel.team2 = new TeamTheme("Angels");
		devil_angel.team1.setHealthTexture(directory + "healthOutline.png", directory + "devilHealthBar.png");
		devil_angel.team2.setHealthTexture(directory + "healthOutline.png", directory + "angelHealthBar.png");
		devil_angel.team1.setLaserTexture(directory + "DevilLaser.png");
		devil_angel.team2.setLaserTexture(directory + "HeavenLaser.png");
		devil_angel.team1.setBeamTexture(directory + "devilBeam.png");
		devil_angel.team2.setBeamTexture(directory + "angelBeam.png");
		devil_angel.team1.setMirrorTexture(directory + "DevilMirror.png");
		devil_angel.team2.setMirrorTexture(directory + "AngelMirror.png");
		devil_angel.team1.setSplitterTexture(directory + "redSplitter.png");
		devil_angel.team2.setSplitterTexture(directory + "blueSplitter.png");
		devil_angel.team1.setPortalTexture(directory + "redPortal.png");
		devil_angel.team2.setPortalTexture(directory + "bluePortal.png");

		directory = "textures/matchups/classic/";
		classic = new Theme("Classic").setTileTextures(directory + "tile1.png",
				directory + "tile2.png", directory + "Hovered.png", directory
						+ "Selected.png");
		classic.setFont(new Font("Meiryo", Font.PLAIN, 16));
		classic.team1 = new TeamTheme("Red");
		classic.team2 = new TeamTheme("Blue");
		classic.setVictoryScreens(directory + "redVictory.png", directory + "blueVictory.png");
		classic.team1.setHealthTexture(directory + "redHealthOutline.png", directory + "healthBar.png");
		classic.team2.setHealthTexture(directory + "blueHealthOutline.png", directory + "healthBar.png");
		classic.team1.setLaserTexture(directory + "redLaser.png");
		classic.team2.setLaserTexture(directory + "blueLaser.png");
		classic.team1.setBeamTexture(directory + "redBeam.png");
		classic.team2.setBeamTexture(directory + "blueBeam.png");
		classic.team1.setMirrorTexture(directory + "redMirror.png");
		classic.team2.setMirrorTexture(directory + "blueMirror.png");
		classic.team1.setSplitterTexture(directory + "redSplitter.png");
		classic.team2.setSplitterTexture(directory + "blueSplitter.png");
		classic.team1.setPortalTexture(directory + "redPortal.png");
		classic.team2.setPortalTexture(directory + "bluePortal.png");
		

	}
	
}
