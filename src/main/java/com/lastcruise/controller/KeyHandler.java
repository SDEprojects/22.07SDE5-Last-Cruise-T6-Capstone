package com.lastcruise.controller;

import com.lastcruise.model.Game;
import com.lastcruise.model.GamePanel;
import com.lastcruise.model.Inventory.InventoryEmptyException;
import com.lastcruise.model.Item;
import com.lastcruise.model.State;
import com.lastcruise.model.entity.Entity;
import com.lastcruise.model.entity.Player;
import com.lastcruise.model.entity.Player.ItemNotEdibleException;
import com.lastcruise.model.entity.Player.NoEnoughStaminaException;
import com.lastcruise.view.GameUI;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;
import java.util.ConcurrentModificationException;
import java.util.HashMap;

public class KeyHandler implements KeyListener {

  private boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, buildPressed;

  private boolean inventoryState = false;

  private Game game;

  private GameUI gameUI;

  @Override
  public void keyTyped(KeyEvent e) {
  }

  @Override
  public void keyPressed(KeyEvent e) {
    int code = e.getKeyCode();

    if (code == KeyEvent.VK_I) {
      if (isInventoryState()) {
        inventoryState = false;
        game.setState(State.PLAY);
        System.out.println("Inventory state: " + inventoryState);
      } else {
        inventoryState = true;
        game.setState(State.INVENTORY);
        System.out.println("Inventory state: " + inventoryState);
      }
    }
    if (code == KeyEvent.VK_H) {
      if (game.getState() == State.HELP) {
        game.setState(State.PLAY);
      } else {
        game.setState(State.HELP);
      }
    }
    if (code == KeyEvent.VK_Z) {
      if (game.getState() == State.SLEEP) {
        game.setState(State.PLAY);
      } else {
        game.setState(State.SLEEP);
      }
    }
    if (game.getState() == State.PLAY) {
      playState(code);
    }
    if (game.getState() == State.INVENTORY) {
      inventoryState(code);
    }

    if (code == KeyEvent.VK_B){
      buildPressed = true;
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

  public void playState(int code) {
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

  public void inventoryState(int code) {

    if (code == KeyEvent.VK_I) {
      // game state = play
    }
    if (code == KeyEvent.VK_W) {
      if (gameUI.getSlotCol() != 0) {
        gameUI.setSlotCol(gameUI.getSlotCol() - 1);
      }
    }
    if (code == KeyEvent.VK_A) {
      if (gameUI.getSlotRow() != 0) {
        gameUI.setSlotRow(gameUI.getSlotRow() - 1);
      }
    }
    if (code == KeyEvent.VK_S) {
      if (gameUI.getSlotCol() != 1) {
        gameUI.setSlotCol(gameUI.getSlotCol() + 1);
      }
    }
    if (code == KeyEvent.VK_D) {
      if (gameUI.getSlotRow() != 3) {
        gameUI.setSlotRow(gameUI.getSlotRow() + 1);
      }
    }
    if (code == KeyEvent.VK_ENTER) {
      enterPressed = true;
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

  public boolean isEnterPressed() {
    return enterPressed;
  }

  public void setEnterPressed(boolean enterPressed) {
    this.enterPressed = enterPressed;
  }

  public boolean isBuildPressed() {
    return buildPressed;
  }

  public void setBuildPressed(boolean buildPressed) {
    this.buildPressed = buildPressed;
  }

  public void setGame(Game game) {
    this.game = game;
  }

  public void setGameUI(GameUI gameUI) {
    this.gameUI = gameUI;
  }

}
