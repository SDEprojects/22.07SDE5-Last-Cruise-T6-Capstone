package com.lastcruise.view;

import com.lastcruise.model.GamePanel;
import com.lastcruise.model.Inventory;
import com.lastcruise.model.Item;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GameUI {

  private int slotCol = 0;
  private int slotRow = 0;

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
    // draw player's items
    for (Item item : playerInventory.getInventory().values()) {
      g2.drawImage(item.getImage(), slotX, slotY, gp.getTileSize(), gp.getTileSize(), null);
      slotX += gp.getTileSize();

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
    };
    for (int i = 0; i < helpMenu.length; i++) {
      g2.drawString(helpMenu[i], indentX, indentY + 20 * (i + 1));
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


}
