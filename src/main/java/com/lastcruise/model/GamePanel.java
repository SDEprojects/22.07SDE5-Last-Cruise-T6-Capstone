package com.lastcruise.model;

import com.lastcruise.controller.KeyHandler;
import com.lastcruise.model.entity.Player;
import com.lastcruise.model.tile.TileManager;
import com.lastcruise.view.View;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {

  // SCREEN SETTINGS
  private final int originalTileSize = 16;
  private final int scale = 3;

  public final int tileSize = originalTileSize * scale;
  private final int maxScreenCol = 16;
  private final int maxScreenRow = 12;
  public final int screenWidth = tileSize * maxScreenCol;
  private final int screenHeight = tileSize * maxScreenRow;
  // Frames per second
  private int FPS = 60;
  private KeyHandler keyHandler = new KeyHandler();
  private Thread gameThread;
  private Player player = new Player();
  private TileManager tileManager = new TileManager( maxScreenCol, maxScreenRow);
  private Collision collision = new Collision(tileSize, tileManager.getMapTileIndex(), tileManager.getTile());
  private Game game;
  private Inventory inventory;

  private View view = new View();

  public GamePanel() {
    this.setPreferredSize(new Dimension(screenWidth, screenHeight));
    this.setBackground(Color.black);
    this.setDoubleBuffered(true);
    this.addKeyListener(keyHandler);
    this.setFocusable(true);
    this.inventory = new Inventory();
    this.game = new Game("Player");
  }

  public void startGameThread() {
    gameThread = new Thread(this);
    gameThread.start();
  }

  @Override
  public void run() {
    double drawInterval = 1000000000/FPS;
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
    if (keyHandler.isUpPressed() || keyHandler.isDownPressed() || keyHandler.isLeftPressed() || keyHandler.isRightPressed()){
      player.updateDirection(keyHandler.isUpPressed(), keyHandler.isDownPressed(), keyHandler.isLeftPressed(), keyHandler.isRightPressed());

      int topRow = (player.getY() + player.getSolidArea().y - player.getSpeed()) / tileSize;
      int bottomRow = (player.getY() + player.getSolidArea().y + player.getSolidArea().height + player.getSpeed()) / tileSize;
      int leftCol = (player.getX() + player.getSolidArea().x - player.getSpeed()) / tileSize;
      int rightCol = (player.getX() + player.getSolidArea().x + player.getSolidArea().width + player.getSpeed()) / tileSize;

      try {
        player.setCollisionOn(collision.checkTile(topRow, bottomRow, leftCol, rightCol, player.getDirection()));
      } catch (ArrayIndexOutOfBoundsException e) {
        player.setCollisionOn(true);
      }
      // IF COLLISION IS FALSE THE PLAYER CAN MOVE
      player.updatePosition();
    }
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    //if (gameState == title){
    //  titleScreen()}
    if (game.getState().equals(State.TITLE)){
      view.titleScreen(g2, tileSize, screenWidth);

    }


    // draw tiles
    tileManager.draw(g2, tileSize);
    // draw items
    try {
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
    g2.dispose();
  }

  public void setupGame() {

    //System.out.println(game.getCurrentLocationName());

    // set inventory to the inventory of the current location
    if (game.getCurrentLocationInventory() != null) {
      this.inventory = game.getCurrentLocationInventory();
    }
  }

  public int getTileSize() {
    return tileSize;
  }

}

