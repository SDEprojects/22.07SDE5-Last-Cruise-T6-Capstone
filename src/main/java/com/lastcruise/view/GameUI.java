package com.lastcruise.view;

import com.lastcruise.model.GamePanel;
import com.lastcruise.model.Inventory;
import com.lastcruise.model.Item;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GameUI {

  private int slotCol = 0;
  private int slotRow = 0;

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
