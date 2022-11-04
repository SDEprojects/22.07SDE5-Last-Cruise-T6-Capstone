package com.lastcruise.view;

import com.lastcruise.model.Collision;
import com.lastcruise.controller.Game;
import com.lastcruise.model.GameMap;
import com.lastcruise.model.Inventory;
import com.lastcruise.model.Item;
import com.lastcruise.model.Location;
import com.lastcruise.model.State;
import com.lastcruise.model.entity.Player;
import com.lastcruise.model.tile.TileManager;
import com.lastcruise.view.audio.AllSounds;
import com.lastcruise.view.audio.Music;
import com.lastcruise.view.audio.SoundEffect;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.net.URL;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {

  // SCREEN SETTINGS
  private final int originalTileSize = 16;
  private final int scale = 3;

  private final int tileSize = originalTileSize * scale;
  private final int maxScreenCol = 16;
  private final int maxScreenRow = 12;
  private final int screenWidth = tileSize * maxScreenCol;
  private final int screenHeight = tileSize * maxScreenRow;
  // Frames per second
  private int FPS = 60;
  private KeyHandler keyHandler = new KeyHandler();
  private Thread gameThread;

  private Player player;

  private TileManager tileManager;
  private Collision collision;

  private Game game;
  private Inventory inventory;
  private GameMap gameMap;
  private SoundEffect soundEffect;
  private GameUI gameUI;

  // CONSTRUCTOR
  public GamePanel() {
    this.player = new Player();
    this.setPreferredSize(new Dimension(screenWidth, screenHeight));
    this.setBackground(Color.black);
    this.setDoubleBuffered(true);
    this.addKeyListener(keyHandler);
    this.setFocusable(true);
    this.game = new Game(player);
    this.inventory = new Inventory();
    this.soundEffect = new SoundEffect();
    this.gameUI = new GameUI();
    this.tileManager = new TileManager(maxScreenCol, maxScreenRow);
    this.collision = new Collision(tileSize, tileManager.getMapTileIndex(), tileManager.getTile());
    keyHandler.setGame(game);
    keyHandler.setGameUI(gameUI);
    game.setPlayer(this.player);
  }

  public void startGameThread() {
    gameThread = new Thread(this);
    gameThread.start();
  }

  @Override
  public void run() {
    double drawInterval = 1000000000 / FPS;
    double delta = 0;
    long lastTime = System.nanoTime();
    long currentTime;

    while (gameThread != null) {
      currentTime = System.nanoTime();
      delta += (currentTime - lastTime) / drawInterval;
      lastTime = currentTime;
      if (delta >= 1) {
        update();
        repaint();
        delta--;
      }
    }
  }

  public void update() {
    switch (game.getState()) {
      case TITLE:
        if (keyHandler.isEnterPressed()) {
          switch (gameUI.getRowSelection()) {
            case 0:
              game.setState(State.PLAY);
              break;
            case 1:
              game.setState(State.PLAY);
              System.out.println("load game");
              break;
            case 2:
              System.exit(0);
              break;
          }
          keyHandler.setEnterPressed(false);
        }
        break;
      case SLEEP:
        player.sleep();
        soundEffect.loadAndPlayFx("sleep");
        break;
      case INVENTORY:
        if (keyHandler.isEnterPressed()) {
          game.useItem(gameUI.getItemIndex());
          keyHandler.setEnterPressed(false);
        }
        break;
      case WIN:
        if (keyHandler.isEnterPressed()) {
          keyHandler.setEnterPressed(false);
          if (gameUI.getWinGameBoxPosition() == 0) {
            System.out.println("Start new game selected!");
            newGame();
            setupGame();
            playBackgroundMusic();
          } else {
            System.exit(0);
          }
        }
        break;
      case PLAY:
        if (keyHandler.isBuildPressed()) {
          game.craftRaft();
          soundEffect.loadAndPlayFx("build");
          keyHandler.setBuildPressed(false);
        }
        if (keyHandler.isUpPressed() || keyHandler.isDownPressed() || keyHandler.isLeftPressed()
            || keyHandler.isRightPressed()) {
          soundEffect.loadAndPlayFx("footsteps");
          // update the player's direction when someone presses W, A, S, or D
          player.updateDirection(keyHandler.isUpPressed(), keyHandler.isDownPressed(),
              keyHandler.isLeftPressed(), keyHandler.isRightPressed());
          // check if player collides with certain tiles
          checkForMapCollision();
          // update the position if the player can continue without collision
          player.updatePosition();
          // update player stamina status
          player.updateStamina();
          // check if player needs to change maps
          boolean changeMap = game.updateMap(tileSize);
          if (changeMap){
            tileManager.loadMap(gameMap.getCurrentLocation().getFilepath());
          }

        }
        break;
    }

    // check item collision
    String itemName = collision.checkItem(player, true, inventory);
    game.pickupItem(itemName);
  }

  // checks if the map needs to change and places player in correct map and position

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    if (game.getState().equals(State.TITLE)) {
      gameUI.titleScreen(g2, tileSize, screenWidth);
    } else if (game.getState().equals(State.WIN)) {
      stopBackgroundMusic();
      gameUI.winScreen(g2, tileSize, screenWidth);
      soundEffect.loadAndPlayFx("win");
    } else if (game.getState().equals(State.LOSE)) {
      stopBackgroundMusic();
      gameUI.loseScreen(g2, tileSize, screenWidth);
    } else {
      // draw tiles
      tileManager.draw(g2, tileSize);
      // draw items
      try {
        inventory = gameMap.getCurrentLocation().getItems();
        if (inventory.getInventory() != null) {
          for (Item item : inventory.getInventory().values()) {
            item.draw(g2, tileSize);
          }
        }
      } catch (NullPointerException e) {
        e.printStackTrace();
      }
      // draw player
      player.draw(g2, tileSize);

      // draw stamina bar
      gameUI.drawPlayerStamina(g2, player.getStamina());

      switch (game.getState()) {
        case INVENTORY:
          // draw the player inventory
          gameUI.drawInventory(this, g2, player.getInventory());
          break;
        case HELP:
          gameUI.drawHelpMenu(tileSize * 2, tileSize * 7, tileSize * 12, tileSize * 5, g2);
          break;
        case SLEEP:
          if (player.getStamina() < 100) {
            gameUI.drawStringInSubWindow(tileSize * 2, tileSize * 8, tileSize * 12, tileSize * 4, g2,
                new String[]{"Sleepy time...zzzZZZzzZZzz "});
          } else {
            gameUI.drawStringInSubWindow(tileSize * 2, tileSize * 8, tileSize * 12, tileSize * 4, g2,
                new String[]{"You have full stamina. WAKE UP!"});
          }
      }

      if (player.getStamina() == 0) {
        gameUI.drawStringInSubWindow(tileSize * 2, tileSize * 8, tileSize * 12, tileSize * 4, g2,
            new String[]{"Uh oh...", "You have no more stamina!", "Are you tired?",
                "Do you need some rest?", "Are you hungry?"});
      }
      g2.dispose();
    }
  }
  public void newGame() {
    player = new Player();
    game = new Game(player);
    inventory = new Inventory();
    gameUI = new GameUI();
    tileManager = new TileManager(maxScreenCol, maxScreenRow);
    collision = new Collision(tileSize, tileManager.getMapTileIndex(), tileManager.getTile());
    keyHandler.setGame(game);
    keyHandler.setGameUI(gameUI);
    game.setPlayer(this.player);
  }
  public void setupGame() {
    game.setState(State.TITLE);
    // set inventory to the inventory of the current location
    if (game.getCurrentLocationInventory() != null) {
      this.inventory = game.getCurrentLocationInventory();
    }
    // create instances of all locations
    if (game.getGameMap() != null) {
      this.gameMap = game.getGameMap();
    }
    // set the first location (beach)
    gameMap.setCurrentLocation(gameMap.getLocations().get("BEACH"));
    // load the initial map
    tileManager.loadMap(gameMap.getCurrentLocation().getFilepath());
//    System.out.println(gameMap.getCurrentLocation().getNorth().get("x"));
  }

  private void checkForMapCollision() {
    // the bounds of player's collision area
    int topRow = (player.getY() + player.getSolidArea().y - player.getSpeed()) / tileSize;
    int bottomRow = (player.getY() + player.getSolidArea().y + player.getSolidArea().height
        + player.getSpeed()) / tileSize;
    int leftCol = (player.getX() + player.getSolidArea().x - player.getSpeed()) / tileSize;
    int rightCol = (player.getX() + player.getSolidArea().x + player.getSolidArea().width
        + player.getSpeed()) / tileSize;

    // check if the next move will collide with a certain tile
    // out of bounds exception stops player from going off map
    try {
      player.setCollisionOn(
          collision.checkTile(topRow, bottomRow, leftCol, rightCol, player.getDirection()));
    } catch (ArrayIndexOutOfBoundsException e) {
      player.setCollisionOn(true);

    }
  }

  // checks if the map needs to change and places player in correct map and position

  public void playBackgroundMusic() {
    URL backgroundMusic = getClass().getResource(AllSounds.ALL_SOUNDS.get("main"));
    Music.runAudio(backgroundMusic);
  }

  public void stopBackgroundMusic() {
    Music.muteMusic();
  }

  public int getTileSize() {
    return tileSize;
  }

  public Player getPlayer() {
    return this.player;
  }

}

