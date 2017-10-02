package engine;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import menu.Menu;
import menu.MenuControls;
import menu.Option;
import menu.TextBox;
import board.Team;
import board.Theme;
import board.Tile;

/**
 * This is the class for the Applet that runs the game
 * @author Daniel McCrystal
 */
public class Play extends Applet {
	
	public Menu mainMenu, newGameMenu, selectGameMenu, saveGameMenu, inGameMenu, endMenu;
	
	public MenuControls mc;
	public GameControls gc;
	
	public static final int MENU = 0, IN_GAME = 1, GAME_OVER = 2;
	public int gameState;

	private static final long serialVersionUID = 1L;
	public Game activeGame;

	public static final int DIMENSION_X = 9, DIMENSION_Y = 9;
	public Team winner;
	
	public AudioClip menuMusic;

	public void init() {
		Controls.setP(this);
		
		setBackground(Color.black);
		setSize(DIMENSION_X * 100 + 200, DIMENSION_Y * 100);
		
		MiscMethods.setUpMissingTextures();
		Theme.createThemes();
		
		menuMusic = getAudioClip(getDocumentBase(), "menuMusic.wav");
		setUpMenu();
		setUpGame();
		setUpEndGame();
		
		addMouseMotionListener(mc);
		addMouseListener(mc);
		addKeyListener(mc);
		
		switchToMenu();
	}

	public void paint(Graphics page) {

		switch (gameState) {
		case MENU:
			drawMenu(page);
			break;
		case IN_GAME:
			drawGame(page);
			break;
		case GAME_OVER:
			drawEndGame(page);
			break;
		default: 
			System.out.println("Ya done goofed");	
		}
	}
	
	boolean gameWillBeHardcore = false;
	public void setUpMenu() {
		try {
			MenuControls.menuBG = ImageIO.read(new File("textures/MenuBG.png"));
		} catch(IOException e) {
		}
		
		mc = new MenuControls();
		mainMenu = new Menu() {
			public void drawMenuBG(Graphics page) {
				page.drawImage(MenuControls.menuBG, 0, 0, null);
				page.setFont(new Font("GillSans", Font.PLAIN, 24));
				page.setColor(Color.cyan);
				page.drawString("THE LASER GAME", 200,  100);
				page.fillRect(180, 120, 600, 5);
			}
		};
		newGameMenu = new Menu() {
			public void drawMenuBG(Graphics page) {
				page.drawImage(MenuControls.menuBG, 0, 0, null);
				page.setFont(new Font("GillSans", Font.PLAIN, 24));
				page.setColor(Color.cyan);
				page.drawString("NEW GAME", 200,  100);
				page.fillRect(180, 120, 600, 5);
				page.drawString("NAME:", 200, 200);
				page.drawString("HARDCORE:", 200, 250);
			}
		};
		saveGameMenu = new Menu() {
			public void drawMenuBG(Graphics page) {
				page.drawImage(MenuControls.menuBG, 0, 0, null);
				page.setFont(new Font("GillSans", Font.PLAIN, 24));
				page.setColor(Color.cyan);
			}
		};
		selectGameMenu = new Menu() {
			public void drawMenuBG(Graphics page) {
				page.drawImage(MenuControls.menuBG, 0, 0, null);
				page.setFont(new Font("GillSans", Font.PLAIN, 24));
				page.setColor(Color.cyan);
			}
		};
		inGameMenu = new Menu() {
			public void drawMenuBG(Graphics page) {}
		};
		
		
		// MAIN MENU OPTIONS
		mainMenu.addOption(new Option("New Game", 200, 200, 150, 30) {
			public void doFunction() {
				MenuControls.activeMenu = newGameMenu;
				TextBox tb = (TextBox) newGameMenu.getOption(0);
				tb.clear();
			}
		});
		mainMenu.addOption(new Option("Continue Game", 200, 250, 175, 30) {
			public void doFunction() {
				MenuControls.activeMenu = selectGameMenu;
			}
		});
		
		// NEW GAME MENU OPTIONS
		final TextBox nameTB = new TextBox("", 280, 180, 200, 30);
		newGameMenu.addOption(nameTB);
		newGameMenu.addOption(new Option("BACK", 350, 800, 75, 30) {
			public void doFunction() {
				MenuControls.activeMenu = mainMenu;
			}
		});
		
		newGameMenu.addOption(new Option("START", 500, 800, 100, 30) {
			public void doFunction() {
				activeGame = new Game(Theme.classic, nameTB.getText(), gameWillBeHardcore);
				switchToGame();
			}
		});
		
		newGameMenu.addOption(new Option("FALSE", 350, 230, 75, 30) {
			public void doFunction() {
				gameWillBeHardcore = !gameWillBeHardcore;
				if(gameWillBeHardcore) {
					changeTitle("TRUE");
				} else {
					changeTitle("FALSE");
				}
			}
		});
		
		// SELECT GAME MENU OPTIONS
		selectGameMenu.addOption(new Option("BACK", 350, 800, 75, 30) {
			public void doFunction() {
				MenuControls.activeMenu = mainMenu;
			}
		});
		selectGameMenu.addOption(new Option("SAVE SLOT 1", 200, 200, 145, 30) {
			public void doFunction() {
				if(Game.games[0] != null) {
					activeGame = Game.games[0];
					switchToGame();
				}
			}
		});
		selectGameMenu.addOption(new Option("SAVE SLOT 2", 200, 250, 145, 30) {
			public void doFunction() {
				if(Game.games[1] != null) {
					activeGame = Game.games[1];
					switchToGame();
				}
			}
		});
		selectGameMenu.addOption(new Option("SAVE SLOT 3", 200, 300, 145, 30) {
			public void doFunction() {
				if(Game.games[2] != null) {
					activeGame = Game.games[2];
					switchToGame();
				}
			}
		});
		selectGameMenu.addOption(new Option("SAVE SLOT 4", 200, 350, 145, 30) {
			public void doFunction() {
				if(Game.games[3] != null) {
					activeGame = Game.games[3];
					switchToGame();
				}
			}
		});
		selectGameMenu.addOption(new Option("SAVE SLOT 5", 200, 400, 145, 30) {
			public void doFunction() {
				if(Game.games[4] != null) {
					activeGame = Game.games[4];
					switchToGame();
				}
			}
		});
		
		// SAVE GAME MENU OPTIONS
		saveGameMenu.addOption(new Option("BACK", 350, 800, 75, 30) {
			public void doFunction() {
				gameState = IN_GAME;
				MenuControls.activeMenu = inGameMenu;
				addMouseListener(gc);
				addMouseMotionListener(gc);
				addKeyListener(gc);
			}
		});
		saveGameMenu.addOption(new Option("SAVE SLOT 1", 200, 200, 145, 30) {
			public void doFunction() {
				activeGame.saveGameInSlot(1);
				selectGameMenu.getOption(1).changeTitle(activeGame.name);
				saveGameMenu.getOption(1).changeTitle(activeGame.name);
				switchToMenu();
			}
		});
		saveGameMenu.addOption(new Option("SAVE SLOT 2", 200, 250, 145, 30) {
			public void doFunction() {
				activeGame.saveGameInSlot(2);
				selectGameMenu.getOption(2).changeTitle(activeGame.name);
				saveGameMenu.getOption(2).changeTitle(activeGame.name);
				switchToMenu();
			}
		});
		saveGameMenu.addOption(new Option("SAVE SLOT 3", 200, 300, 145, 30) {
			public void doFunction() {
				activeGame.saveGameInSlot(3);
				selectGameMenu.getOption(3).changeTitle(activeGame.name);
				saveGameMenu.getOption(3).changeTitle(activeGame.name);
				switchToMenu();
			}
		});
		saveGameMenu.addOption(new Option("SAVE SLOT 4", 200, 350, 145, 30) {
			public void doFunction() {
				activeGame.saveGameInSlot(4);
				selectGameMenu.getOption(4).changeTitle(activeGame.name);
				saveGameMenu.getOption(4).changeTitle(activeGame.name);
				switchToMenu();
			}
		});
		saveGameMenu.addOption(new Option("SAVE SLOT 5", 200, 400, 145, 30) {
			public void doFunction() {
				activeGame.saveGameInSlot(5);
				selectGameMenu.getOption(5).changeTitle(activeGame.name);
				saveGameMenu.getOption(5).changeTitle(activeGame.name);
				switchToMenu();
			}
		});
		
		// IN GAME MENU OPTIONS
		inGameMenu.addOption(new Option("SAVE AND QUIT", 925, 850, 145, 30) {
			public void doFunction() {
				gameState = MENU;
				MenuControls.activeMenu = saveGameMenu;
				removeMouseListener(gc);
				removeMouseMotionListener(gc);
				removeKeyListener(gc);
			}
		});
		
	}
	
	public void switchToMenu() {
		
		MenuControls.activeMenu = mainMenu;
		gameState = MENU;
		
		// menuMusic.loop();
	}

	public void drawMenu(Graphics page) {
		MenuControls.activeMenu.loadMenu(page);
	}

	public void setUpGame() {
		gc = new GameControls();
	}
	
	public void switchToGame() {
		menuMusic.stop();
		gameState = Play.IN_GAME;
		MenuControls.activeMenu = inGameMenu;
		
		addMouseListener(gc);
		addMouseMotionListener(gc);
		addKeyListener(gc);
		
		System.out
				.println("==== TURN " + activeGame.getTurnNum() + " (" + activeGame.turn.name + ") ====");
	}

	public void drawGame(Graphics page) {
		activeGame.load(page);
		page.setFont(activeGame.theme.font);
		page.setColor(Color.yellow);
		if (activeGame.selectedPiece != null) {
			page.drawImage(activeGame.selectedPiece.getTexture(), DIMENSION_X * 100 + 50,
					25, this);
			page.drawString("Team: " + activeGame.selectedPiece.getTeam().name,
					DIMENSION_X * 100 + 50, 150);
			page.drawString("Type: " + activeGame.selectedPiece.getType(),
					DIMENSION_X * 100 + 50, 170);
			page.drawString("Direction: " + activeGame.selectedPiece.directionToString(),
					DIMENSION_X * 100 + 50, 190);
			page.drawString("X: " + activeGame.selectedPiece.getX() + ", Y: "
					+ activeGame.selectedPiece.getY(), DIMENSION_X * 100 + 50, 210);
		}
		page.drawRect(DIMENSION_X * 100 + 50, 25, 100, 100);
		page.drawString("Turn: " + activeGame.turn.name, DIMENSION_X * 100 + 50, 250);
		
		inGameMenu.loadMenu(page);
	}
	
	public void setUpEndGame() {
		endMenu = new Menu() {
			public void drawMenuBG(Graphics page) {
				page.drawImage(winner.teamTheme.victoryScreen, 0, 0, null);
			}
		};
		endMenu.addOption(new Option("Return to menu", 425, 830, 150, 30) {
			public void doFunction() {
				switchToMenu();
				repaint();
			}
		});
	}
	
	public void switchToEndGame() {
		gameState = GAME_OVER;
		
		removeMouseListener(gc);
		removeMouseMotionListener(gc);
		removeKeyListener(gc);
		
		activeGame.clear();
		
		MenuControls.activeMenu = endMenu;
	}

	public void drawEndGame(Graphics page) {
		endMenu.loadMenu(page);
	}


	public void nextTurn() {
		
		for(Tile[] ta: activeGame.tileset) {
			for(Tile t: ta) {
				t.hit = false;
			}
		}

		if (activeGame.turn == activeGame.team1)
			activeGame.turn = activeGame.team2;
		else
			activeGame.turn = activeGame.team1;

		if (activeGame.team1.teamLaser.laserOn()) {
			activeGame.team1.teamLaser.primeBeam();
			activeGame.team1.teamLaser.beam.fireBeam();
			if (activeGame.isHardcore())
				harmAdjacent(activeGame.team1);
		}

		if (activeGame.team2.teamLaser.laserOn()) {
			activeGame.team2.teamLaser.primeBeam();
			activeGame.team2.teamLaser.beam.fireBeam();
			if (activeGame.isHardcore())
				harmAdjacent(activeGame.team2);
		}
		
		if(activeGame.team1.teamLaser.getHealth() <= 0){
			winner = activeGame.team2;
			switchToEndGame();
			return;
		}
		else if(activeGame.team2.teamLaser.getHealth() <= 0){
			winner = activeGame.team1;
			switchToEndGame();
			return;
		}

		activeGame.incrementTurnNum();;
		System.out.println("----------------------------------------");
		System.out
				.println("==== TURN " + activeGame.getTurnNum() + " (" + activeGame.turn.name + ") ====");
	}
	
	/**
	 * Damages friendly pieces around a team's laser; for use in hardcore mode
	 * @param t the team to affect
	 */
	public void harmAdjacent(Team t) {
		for (int i = 0; i < 4; i++) {
			try {
				if (t.teamLaser.adjacentTiles()[i].piece.getTeam() == t) {
					t.teamLaser.adjacentTiles()[i].piece.hit(1);
				}
			} catch (NullPointerException e) {
			}
		}
		if(t.teamLaser.onEdge()){
			t.teamLaser.hit(1);
		}
	}
}
