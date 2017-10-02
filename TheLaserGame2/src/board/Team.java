package board;

import java.awt.Graphics;
import java.util.ArrayList;

import pieces.Laser;
import pieces.Piece;
import engine.Game;

/**
 * Represents a team
 * @author Daniel McCrystal
 */
public class Team {

	public String name;
	public Game game;
	public TeamTheme teamTheme;
	public ArrayList<Piece> pieces = new ArrayList<Piece>();
	public Laser teamLaser;

	public Team(String str, Game game) {
		name = str;
		this.game = game;
	}

	public void addToTeam(Piece p) {
		pieces.add(p);
	}
	
	public void setTeamLaser(Laser l) {
		teamLaser = l;
	}

	public int indexOf(Piece p) {
		return pieces.indexOf(p);
	}

	public void loadAllPieces(Graphics page) {
		for (Piece p: pieces) {
			p.load(page);
		}
	}

}
