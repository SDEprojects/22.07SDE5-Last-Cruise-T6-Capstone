package com.lastcruise.controller;

import com.lastcruise.view.GameUI;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
  private boolean upPressed, downPressed, leftPressed, rightPressed;

  private boolean inventoryState = false;

  @Override
  public void keyTyped(KeyEvent e) {
  }

  @Override
  public void keyPressed(KeyEvent e) {
    int code = e.getKeyCode();

    if (code == KeyEvent.VK_W) {
      upPressed = true;
    }
    if (code == KeyEvent.VK_S) {
      downPressed = true;
    }
    if (code == KeyEvent.VK_A) {
      leftPressed = true;
    }
    if (code == KeyEvent.VK_D) {
      rightPressed = true;
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    int code = e.getKeyCode();

    if (code == KeyEvent.VK_W) {
      upPressed = false;
    }
    if (code == KeyEvent.VK_S) {
      downPressed = false;
    }
    if (code == KeyEvent.VK_A) {
      leftPressed = false;
    }
    if (code == KeyEvent.VK_D) {
      rightPressed = false;
    }
  }

  public void characterState(int code, GameUI ui) {

    if(code == KeyEvent.VK_I) {
      // game state = play
    }
    if(code == KeyEvent.VK_W) {
      ui.setSlotRow(ui.getSlotRow()-1) ;
    }
    if(code == KeyEvent.VK_A) {
      ui.setSlotCol(ui.getSlotCol()-1);
    }
    if(code == KeyEvent.VK_S) {
      ui.setSlotRow(ui.getSlotRow()+1);
    }
    if(code == KeyEvent.VK_D) {
      ui.setSlotCol(ui.getSlotCol()+1);
    }
  }

  public boolean isUpPressed() {
    return upPressed;
  }

  public boolean isDownPressed() {
    return downPressed;
  }

  public boolean isLeftPressed() {
    return leftPressed;
  }

  public boolean isRightPressed() {
    return rightPressed;
  }

  public boolean isInventoryState() {
    return inventoryState;
  }
}
