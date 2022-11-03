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

  private boolean upPressed, downPressed, leftPressed, rightPressed;

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
      game.craftRaft();
    }

    if (code == KeyEvent.VK_E){
      boolean win = game.escapeIsland();
      if(win){
        System.out.println("You escaped the island!");
        game.setState(State.WIN);
      }else {
        System.out.println("You can not escape the island without a raft!");
      }
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
      //System.out.println("Return");
      int index = gameUI.getItemIndex();
      //System.out.println("Item index " + index);

      //System.out.println("Num items: " + game.getPlayerInventory().getInventory().size());

      Item foundItem = null;
      for (Item item : game.getPlayerInventory().getInventory().values()) {
        if (item.getIndex() == index) {
          foundItem = item;
          System.out.println("Index: " + index + " Name: " + item.getName());
        }
      }
      if (foundItem != null) {
        if (foundItem.getName().equals("banana")
            || foundItem.getName().equals("berries")
            || foundItem.getName().equals("fish")
            || foundItem.getName().equals("mushroom")) {
          try {
            game.eatItem(foundItem.getName());
          } catch (InventoryEmptyException | ItemNotEdibleException |
                   ConcurrentModificationException e) {
            //System.out.println(e);
          }
        } else if (foundItem.getName().equals("machete")
            || foundItem.getName().equals("cloth")
            || foundItem.getName().equals("log")
            || foundItem.getName().equals("paddle")
            || foundItem.getName().equals("rocks")
            || foundItem.getName().equals("rope")
            || foundItem.getName().equals("steelpipe")) {
          try {
            int x = (game.getPlayer().getX() + 64) / 48;
            int y = (game.getPlayer().getY() + 64) / 48;
            foundItem.setX(x);
            foundItem.setY(y);
            game.transferItemFromTo(game.getPlayerInventory(), game.getCurrentLocationInventory(),
                foundItem.getName());
          } catch (InventoryEmptyException |
                   ConcurrentModificationException |
                   NoEnoughStaminaException e) {
            //System.out.println(e);
          }
        }
      }
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

  public void setGame(Game game) {
    this.game = game;
  }

  public void setGameUI(GameUI gameUI) {
    this.gameUI = gameUI;
  }

}
