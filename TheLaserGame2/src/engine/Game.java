package engine;

import java.awt.Graphics;
import java.util.ArrayList;

import pieces.Laser;
import pieces.Mirror;
import pieces.Piece;
import pieces.Portal;
import pieces.Splitter;
import board.Directions;
import board.Team;
import board.Theme;
import board.Tile;

/**
 * Represents a game, that can be saved
 * 
 * @author Daniel McCrystal
 */
public class Game {

	public static Game[] games = new Game[5];

	public void saveGameInSlot(int slot) {
		if (slot < 1 || slot > 5) {
			System.out.println("Slot is out of bounds");
			return;
		}
		games[slot - 1] = this;
		
		/*
		try (FileOutputStream fs = new FileOutputStream("saves.lgs")) {

			ObjectOutputStream os = new ObjectOutputStream(fs);

			for (Game g : games) {
				os.writeObject(g);
			}
			os.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
	}

	public Theme theme;
	public Tile[][] tileset;
	public Team team1, team2, turn;
	public Piece selectedPiece;
	public String name;
	private int turnNum;
	private boolean hardcore;

	public Game(Theme theme, String name, boolean hardcore) {
		this.hardcore = hardcore;
		this.theme = theme;
		this.name = name;
		team1 = new Team(theme.team1.name, this);
		team2 = new Team(theme.team2.name, this);

		team1.teamTheme = theme.team1;
		team2.teamTheme = theme.team2;

		tileset = new Tile[Play.DIMENSION_X][Play.DIMENSION_Y];

		turnNum = 1;
		turn = team1;

		createTiles();
		setUpPieces();
	}

	public void incrementTurnNum() {
		turnNum++;
	}

	public int getTurnNum() {
		return turnNum;
	}

	public boolean isHardcore() {
		return hardcore;
	}

	/**
	 * Displays all the tiles on the board
	 * 
	 * @param page
	 */
	public void load(Graphics page) {
		for (int i = 0; i < Play.DIMENSION_Y; i++) {
			for (int k = 0; k < Play.DIMENSION_X; k++) {
				tileset[k][i].load(page);
			}
		}
		if (team1.teamLaser.laserOn()) {
			team1.teamLaser.primeBeam();
			team1.teamLaser.beam.paintBeam(page);
		}
		if (team2.teamLaser.laserOn()) {
			team2.teamLaser.primeBeam();
			team2.teamLaser.beam.paintBeam(page);
		}

		team1.loadAllPieces(page);
		team2.loadAllPieces(page);
	}

	/**
	 * Creates a new set of tiles for the board
	 */
	public void createTiles() {
		int xCoord, yCoord = 0;
		boolean tileIsType1 = true;

		for (int col = 0; col < tileset.length; col++) {
			xCoord = 0;
			tileIsType1 = col % 2 == 0;
			for (int row = 0; row < tileset[col].length; row++) {
				tileset[row][col] = new Tile((tileIsType1) ? 1 : 2, this);
				tileIsType1 = !tileIsType1;
				tileset[row][col].xCoord = xCoord++;
				tileset[row][col].yCoord = yCoord;
			}
			yCoord++;
		}
	}

	/**
	 * 
	 * @param x
	 *            The x-position to test
	 * @param y
	 *            The y-position to test
	 * @return The tile that contains the x,y position
	 */
	public Tile tileWithin(int x, int y) {
		for (int i = 0; i < tileset.length; i++) {
			for (int k = 0; k < tileset[i].length; k++) {
				if (x >= tileset[k][i].xCoord * 100
						&& x < tileset[k][i].xCoord * 100 + 100
						&& y >= tileset[k][i].yCoord * 100
						&& y < tileset[k][i].yCoord * 100 + 100) {
					return tileset[k][i];
				}
			}
		}
		return null;
	}

	/**
	 * Resets the tiles for new games
	 */
	public void clear() {
		createTiles();

		team1.pieces = new ArrayList<Piece>();
		team2.pieces = new ArrayList<Piece>();

	}

	/**
	 * Instantiates the pieces for the game
	 */
	public void setUpPieces() {
		new Laser(2, (hardcore) ? 7 : 8, Directions.UP, team1);
		new Mirror(0, 2, Directions.SOUTHEAST, team1);
		new Mirror(0, 3, Directions.SOUTHEAST, team1);
		new Mirror(0, 4, Directions.SOUTHEAST, team1);
		new Mirror(0, 5, Directions.SOUTHEAST, team1);
		new Mirror(0, 6, Directions.SOUTHEAST, team1);
		new Mirror(3, 8, Directions.NORTHWEST, (hardcore) ? team2: team1);
		new Mirror(3, 0, Directions.SOUTHWEST, (hardcore) ? team2: team1);

		new Laser(6, (hardcore) ? 1 : 0, Directions.DOWN, team2);
		new Mirror(8, 2, Directions.NORTHWEST, team2);
		new Mirror(8, 3, Directions.NORTHWEST, team2);
		new Mirror(8, 4, Directions.NORTHWEST, team2);
		new Mirror(8, 5, Directions.NORTHWEST, team2);
		new Mirror(8, 6, Directions.NORTHWEST, team2);
		new Mirror(5, 0, Directions.SOUTHEAST, (hardcore) ? team1 : team2);
		new Mirror(5, 8, Directions.NORTHEAST, (hardcore) ? team1 : team2);

		new Splitter(3, 4, Directions.RIGHT, team1);
		Portal portal1 = new Portal(1, 3, Directions.UP, team1);
		new Splitter(5, 4, Directions.LEFT, team2);
		Portal portal2 = new Portal(7, 5, Directions.DOWN, team2);
		portal1.link(portal2);
		portal2.link(portal1);
	}

}
