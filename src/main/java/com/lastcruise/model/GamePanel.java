package com.lastcruise.model;

import com.lastcruise.controller.KeyHandler;
import com.lastcruise.model.Inventory.InventoryEmptyException;
import com.lastcruise.model.entity.Entity;
import com.lastcruise.model.entity.Player;
import com.lastcruise.model.entity.Player.NoEnoughStaminaException;
import com.lastcruise.model.tile.TileManager;
import com.lastcruise.view.View;
import com.lastcruise.view.GameUI;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
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

  private TileManager tileManager = new TileManager( maxScreenCol, maxScreenRow);
  private Collision collision = new Collision(tileSize, tileManager.getMapTileIndex(), tileManager.getTile());

  private Game game;
  private Inventory inventory;
  private GameMap gameMap;
  private View view = new View();

  private GameUI gameUI = new GameUI();

  // CONSTRUCTOR
  public GamePanel() {
    this.player = new Player();
    this.setPreferredSize(new Dimension(screenWidth, screenHeight));
    this.setBackground(Color.black);
    this.setDoubleBuffered(true);
    this.addKeyListener(keyHandler);
    this.setFocusable(true);
    this.inventory = new Inventory();
    this.game = new Game(player);
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
    if (game.getState() == State.SLEEP) {
      player.sleep();
    }
    if (keyHandler.isUpPressed() || keyHandler.isDownPressed() || keyHandler.isLeftPressed()
        || keyHandler.isRightPressed()) {
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
      updateMap();
    }
    // check item collision
    String itemName = collision.checkItem(player, true, inventory);
    pickupItem(itemName);

//    if(keyHandler.isInventoryState()) {
//      game.setState(State.INVENTORY);
//    } else {
//      game.setState(State.PLAY);
//    }
  }

  // checks if the map needs to change and places player in correct map and position

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    //if (gameState == title){
     // titleScreen()}
//    if (game.getState().equals(State.WIN)) {
//      view.winScreen(g2, tileSize, screenWidth);

//    if (game.getState().equals(State.TITLE)) {
//      view.titleScreen(g2, tileSize, screenWidth);
    if (game.getState().equals(State.LOSE)) {
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

      // draw the player inventory
      if (game.getState() == State.INVENTORY) {
        gameUI.drawInventory(this, g2, player.getInventory());
      }

      if (game.getState().equals(State.HELP)) {
        gameUI.drawHelpMenu(tileSize * 2, tileSize * 8, tileSize * 12, tileSize * 4, g2);
      }
      if (game.getState().equals(State.SLEEP)) {
        if (player.getStamina() < 100) {
          gameUI.drawStringInSubWindow(tileSize * 2, tileSize * 8, tileSize * 12, tileSize * 4, g2,
              new String[]{"Sleepy time...zzzZZZzzZZzz "});
        } else {
          gameUI.drawStringInSubWindow(tileSize * 2, tileSize * 8, tileSize * 12, tileSize * 4, g2,
              new String[]{"You have full stamina. WAKE UP!"});
        }
      }
      g2.dispose();
    }
  }

    public void setupGame () {
      //game.setState(State.TITLE);
      //game.setState(State.WIN);
      game.setState(State.LOSE);
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

    private void checkForMapCollision () {
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
    private void updateMap () {
      int tileX = player.getX() / tileSize;
      int tileY = player.getY() / tileSize;
      Location current = gameMap.getCurrentLocation();
      // checks if the tile is a transition tile and returns current map
      String newMap = current.checkForMapTransition(tileX, tileY, player.getDirection());
      if (!current.getName().equals(newMap)) {
        // change current location to new location
        gameMap.setCurrentLocation(gameMap.getLocations().get(newMap));
        // places the player at the entrance for the new map
        int[] entrance = gameMap.getCurrentLocation().getEntranceCoordinates(player.getDirection());
        player.setX(entrance[0] * tileSize);
        player.setY(entrance[1] * tileSize);
        // load the new map
        tileManager.loadMap(gameMap.getCurrentLocation().getFilepath());
      }
    }

    public void pickupItem (String itemName){
      if (!itemName.equals("")) {
        try {

          player.setStamina(player.getStamina() - 10);

          // remove the item from the location inventory and add it to the player inventory
          game.transferItemFromTo(inventory, player.getInventory(), itemName);

        } catch (InventoryEmptyException e) {
          System.out.println("Item " + itemName + " is not in inventory!");
        } catch (NoEnoughStaminaException e) {
          //throw new RuntimeException(e);
          System.out.println("Not enough stamina to pickup item!");
        }
      }
    }


  public int getTileSize() {
    return tileSize;
  }

  public Player getPlayer(){
    return this.player;
  }

}


