package com.lastcruise.view;

import com.lastcruise.model.GamePanel;
import com.lastcruise.model.Inventory;
import com.lastcruise.model.Item;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;


public class GameUI {

  private int slotCol = 0;
  private int slotRow = 0;
  private int rowSelection = 0;

  private HashMap<Integer, String> itemNames;
  public void titleScreen(Graphics2D g2, int tileSize, int screenWidth)  {
    BufferedImage titleImage = null;
    try {
      titleImage = ImageIO.read(getClass().getResourceAsStream("/title/ship.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 90F));
    String gameTitle = "LAST CRUISE";
    int x = getXforCenteredText(gameTitle, g2, screenWidth);
    int y = tileSize*3;
    g2.setColor(Color.white);
    g2.drawString(gameTitle, x, y);
    g2.setColor(new Color(0, 0, 0));
    g2.fillRect(0, 0, screenWidth, tileSize);
    //shadow
    g2.setColor(Color.white);
    g2.drawString(gameTitle, x+5, y+5);

    //Image
    x = screenWidth/2 -(tileSize*2)/2;
    y += tileSize*2;
    g2.drawImage(titleImage, tileSize*5, tileSize*3, tileSize*6,tileSize*6, null);

    //Menu
    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 45F));
    gameTitle = "NEW GAME";
    x = getXforCenteredText(gameTitle, g2, screenWidth);
    y += tileSize*4;
    g2.drawString(gameTitle, x, y);

    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 45F));
    gameTitle = "LOAD GAME";
    x = getXforCenteredText(gameTitle, g2, screenWidth);
    y += tileSize;
    g2.drawString(gameTitle, x, y);

    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 45F));
    gameTitle = "EXIT GAME";
    x = getXforCenteredText(gameTitle, g2, screenWidth);
    y += tileSize;
    g2.drawString(gameTitle, x, y);

    // cursor set up
    int cursorX = x - 24;
    int cursorY = y - tileSize * (3 - rowSelection) + 6;
    g2.setColor(Color.BLUE);
    g2.setStroke(new BasicStroke(3));
    g2.drawRoundRect(cursorX, cursorY, tileSize * 6 + 12, tileSize, 25, 25);
  }

  public void winScreen(Graphics2D g2, int tileSize, int screenWidth) {
    BufferedImage winImage = null;
    try {
      winImage = ImageIO.read(getClass().getResourceAsStream("/win/win_image.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80F));
    String gameTitle = "YOU WIN!!";
    int x = getXforCenteredText(gameTitle, g2, screenWidth);
    int y = tileSize * 2;
    g2.setColor(Color.white);
    g2.drawString(gameTitle, x, y);
    g2.setColor(new Color(0, 0, 0));
    g2.fillRect(0, 0, screenWidth, tileSize);
    //shadow
    g2.setColor(Color.white);
    g2.drawString(gameTitle, x + 5, y + 5);

    //Image
    x = screenWidth / 2 - (tileSize * 2) / 2;
    y += tileSize * 2;
    g2.drawImage(winImage, tileSize * 5, tileSize * 3, tileSize * 6, tileSize * 6, null);

    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 25F));
    gameTitle = "NEW GAME";
    x = getXforCenteredText(gameTitle, g2, screenWidth);
    y += tileSize*5;
    g2.drawString(gameTitle, tileSize*7, tileSize*9);

    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 25F));
    gameTitle = "EXIT GAME";
    x = getXforCenteredText(gameTitle, g2, screenWidth);
    y += tileSize*4;
    g2.drawString(gameTitle, tileSize*7, tileSize*10);
  }

  private int getXforCenteredText(String gameTitle, Graphics2D g2, int screenWidth) {
    int length = (int)g2.getFontMetrics().getStringBounds(gameTitle, g2).getWidth();
    int x = screenWidth/2-length/2;
    return x;
  }
  public void drawPlayerStamina(Graphics2D g2, int playerStamina) {
    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24));
    g2.drawString("Stamina", 24, 24);

    int rectWidth = 48 * 3;
    g2.setColor(Color.black);
    g2.fillRect(24, 28, rectWidth, 24);
    g2.setColor(Color.red);
    int staminaWidth = playerStamina * rectWidth / 100;
    g2.fillRect(24, 28, staminaWidth ,24);
  }
  public void drawInventory(GamePanel gp, Graphics2D g2, Inventory playerInventory) {

    // frame
    int frameX = gp.getTileSize() * 11;
    int frameY = gp.getTileSize();
    int frameWidth = gp.getTileSize() * 5;
    int frameHeight = gp.getTileSize() * 3;
    drawSubWindow(frameX, frameY, frameWidth, frameHeight, g2);

    // slot
    final int slotXstart = frameX + 20; // offset the start position by 20 px
    final int slotYstart = frameY + 20;
    int slotX = slotXstart;
    int slotY = slotYstart;

    int index = 0;
    itemNames = new HashMap<>();
    // draw player's items
    for (Item item : playerInventory.getInventory().values()) {
      g2.drawImage(item.getImage(), slotX, slotY, gp.getTileSize(), gp.getTileSize(), null);
      slotX += gp.getTileSize();
      item.setIndex(index);
      itemNames.put(index, item.getName());
      //System.out.println("Item: " + item.getName() + " Index: " + item.getIndex());

      if(index == 3) {
        slotX = slotXstart;
        slotY += gp.getTileSize();
      }

      index++;
    }

    // cursor position
    int cursorX = slotXstart + (gp.getTileSize() * slotRow);
    int cursorY = slotYstart + (gp.getTileSize() * slotCol);
    int cursorWidth = gp.getTileSize();
    int cursorHeight = gp.getTileSize();

    // draw cursor
    g2.setColor(Color.white);
    // set the box width
    g2.setStroke(new BasicStroke(3));
    // draw cursor
    g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 25, 25);


    // ITEM DESCRIPTION FRAME
    // description frame
    int dFrameX = frameX;
    int dFrameY = frameY + frameHeight;
    int dFrameWidth = frameWidth;
    int dFrameHeight = gp.getTileSize();
    drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight, g2);
    // description text
    int textX = dFrameX + 20;
    //int textY = dFrameY + gp.getTileSize();
    int textY = dFrameY + 30;
    g2.setFont(g2.getFont().deriveFont(20F));

    // get the index of the current item
    int itemIndex = getItemIndex();

    // draw the item description text
    if(itemIndex < itemNames.size()){
      String itemDescription = playerInventory.getInventory().get(itemNames.get(itemIndex)).getName();
      String cap = itemDescription.substring(0, 1).toUpperCase() + itemDescription.substring(1);
      g2.drawString(cap, textX, textY);
    }


  }
  public void drawHelpMenu(int x, int y, int width, int height, Graphics2D g2) {
    drawSubWindow(x, y, width, height, g2);
    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24));
    g2.drawString("HELP MENU", x + 12 + 48 * 4, y + 40);
    g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 18));
    int indentX = x + 48 * 2;
    int indentY = y + 48;
    String[] helpMenu = new String[]{
        "Press Z key to SLEEP",
        "Press I key to see your INVENTORY",
        "Press B key to BUILD something",
        "Press E key to ESCAPE the island",
        "Press H key to close and open the HELP menu",
        "Press M key to change sound and MUSIC",
        "Press ENTER to make a selection",
    };
    for (int i = 0; i < helpMenu.length; i++) {
      g2.drawString(helpMenu[i], indentX, indentY + 20 * (i + 1));
    }
  }
  public void drawStringInSubWindow(int x, int y, int width, int height, Graphics2D g2, String[] lines) {
    drawSubWindow(x, y, width, height, g2);
    int indentX = x + 48 * 2;
    int indentY = y + 48;
    for (int i = 0; i < lines.length; i++) {
      g2.drawString(lines[i], indentX, indentY + 20 * (i + 1));
    }
  }
  public void drawSubWindow(int x, int y, int width, int height, Graphics2D g2) {

    Color color = new Color(0, 0, 0, 210);
    g2.setColor(color);
    g2.fillRoundRect(x, y, width, height, 35, 35);

    color = new Color(255, 255, 255);
    g2.setColor(color);
    g2.setStroke(new BasicStroke(5));
    g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);

  }

  public int getItemIndex() {
    int itemIndex = slotRow + (slotCol * 4);
    return itemIndex;
  }

  public int getSlotCol() {
    return slotCol;
  }

  public void setSlotCol(int slotCol) {
    this.slotCol = slotCol;
  }

  public int getSlotRow() {
    return slotRow;
  }

  public void setSlotRow(int slotRow) {
    this.slotRow = slotRow;
  }

  public int getRowSelection() {
    return rowSelection;
  }

  public void setRowSelection(int rowSelection) {
    this.rowSelection = rowSelection;
  }
}
