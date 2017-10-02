package engine;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import board.Directions;
import board.Tile;

/**
 * Holds the controls for game navigation
 * @author Daniel McCrystal
 */
public class GameControls extends Controls {
	
	public Tile selected, hovered, lastHovered, up, right, down, left;
	
	public void enter() {
		boolean done = false;
		
		// if a tile is already selected
		if (selected != null) {
			// if there is a piece on that tile AND it is that piece's team's turn AND the mouse is hovering another valid tile
			if (selected.piece != null && selected.piece.getTeam() == p.activeGame.turn
					&& hovered != null) {
				// if the tile hovered by the mouse does not have a piece on it
				if(hovered.piece == null) {
					if (hovered == up) {
						selected.piece.move(Directions.UP);
						done = true;
					}
					if (hovered == right) {
						selected.piece.move(Directions.RIGHT);
						done = true;
					}
					if (hovered == down) {
						selected.piece.move(Directions.DOWN);
						done = true;
					}
					if (hovered == left) {
						selected.piece.move(Directions.LEFT);
						done = true;
					}
				}
			}
			// de-select the currently selected tile
			selected.selected = false;
		}
		p.activeGame.selectedPiece = null;

		if (done) {
			p.nextTurn();
			p.repaint();
			return;
		}

		up = null;
		right = null;
		down = null;
		left = null;
		
		// if the mouse is hovering a valid tile
		if (hovered != null) {
			// select the hovered tile
			selected = hovered;
			// if there is a piece on the selected tile
			if (selected.piece != null) {
				selected.selected = true;
				p.activeGame.selectedPiece = selected.piece;
				if (selected.piece != null && selected.piece.getTeam() == p.activeGame.turn) {
					// determine pieces on each side of the piece
					if (selected.yCoord > 0)
						up = p.activeGame.tileset[selected.xCoord][selected.yCoord - 1];
					if (selected.xCoord < Play.DIMENSION_X - 1)
						right = p.activeGame.tileset[selected.xCoord + 1][selected.yCoord];
					if (selected.yCoord < Play.DIMENSION_Y - 1)
						down = p.activeGame.tileset[selected.xCoord][selected.yCoord + 1];
					if (selected.xCoord > 0)
						left = p.activeGame.tileset[selected.xCoord - 1][selected.yCoord];
				}
			}
		}

		p.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		enter();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (hovered != null)
			hovered.hovered = false;
		lastHovered = hovered;
		hovered = p.activeGame.tileWithin(e.getX(), e.getY());
		if (hovered != null)
			hovered.hovered = true;

		if (lastHovered != hovered) {
			p.repaint();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		boolean done = false;
		if (p.activeGame.selectedPiece != null && p.activeGame.selectedPiece.getTeam() == p.activeGame.turn) {
			if (e.getKeyCode() == KeyEvent.VK_E) {
				p.activeGame.selectedPiece.rotate(Directions.CLOCKWISE);
				done = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_Q) {
				p.activeGame.selectedPiece.rotate(Directions.COUNTERCLOCKWISE);
				done = true;
			}
			
			if(e.getKeyCode() == KeyEvent.VK_O && p.activeGame.selectedPiece == p.activeGame.turn.teamLaser){
				p.activeGame.turn.teamLaser.toggleLaser();
				done = true;
			}

			if (done) {
				selected.selected = false;
				p.activeGame.selectedPiece = null;
				p.nextTurn();
				p.repaint();
				return;
			}
		}
		if (hovered != null) {
			if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
				if (hovered.yCoord > 0) {
					hovered.hovered = false;
					hovered = p.activeGame.tileset[hovered.xCoord][hovered.yCoord - 1];
					hovered.hovered = true;
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
				if (hovered.xCoord < Play.DIMENSION_X - 1) {
					hovered.hovered = false;
					hovered = p.activeGame.tileset[hovered.xCoord + 1][hovered.yCoord];
					hovered.hovered = true;
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
				if (hovered.yCoord < Play.DIMENSION_Y - 1) {
					hovered.hovered = false;
					hovered = p.activeGame.tileset[hovered.xCoord][hovered.yCoord + 1];
					hovered.hovered = true;
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
				if (hovered.xCoord > 0) {
					hovered.hovered = false;
					hovered = p.activeGame.tileset[hovered.xCoord - 1][hovered.yCoord];
					hovered.hovered = true;
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_SPACE) {
			enter();
		}
		
		p.repaint();
	}
}
