package com.lastcruise.model.entity;

import com.lastcruise.controller.KeyHandler;
import com.lastcruise.model.GameItems;
import com.lastcruise.model.GamePanel;
import com.lastcruise.model.Inventory.InventoryEmptyException;
import com.lastcruise.model.Item;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player extends Entity {
    public Player() {
        setSolidArea(new Rectangle(8,16,32,32));
        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues() {
        setX(0);
        setY(0);
        setSpeed(4);
        setSolidAreaX(8);
        setSolidAreaY(16);
        setSolidAreaDefaultX(8);
        setSolidAreaDefaultY(16);
        setDirection("down");
    }
    public void getPlayerImage() {
        try {
            setUp1(ImageIO.read(getClass().getResourceAsStream("/player/player-up-1.png")));
            setUp2(ImageIO.read(getClass().getResourceAsStream("/player/player-up-2.png")));
            setDown1(ImageIO.read(getClass().getResourceAsStream("/player/player-down-1.png")));
            setDown2(ImageIO.read(getClass().getResourceAsStream("/player/player-down-2.png")));
            setLeft1(ImageIO.read(getClass().getResourceAsStream("/player/player-left-1.png")));
            setLeft2(ImageIO.read(getClass().getResourceAsStream("/player/player-left-2.png")));
            setRight1(ImageIO.read(getClass().getResourceAsStream("/player/player-right-1.png")));
            setRight2(ImageIO.read(getClass().getResourceAsStream("/player/player-right-2.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void updatePosition() {
        if (!isCollisionOn()) {
            switch (getDirection()) {
                case "up":
                    setY(getY() - getSpeed());
                    break;
                case "down":
                    setY(getY() + getSpeed());
                    break;
                case "left":
                    setX(getX() - getSpeed());
                    break;
                case "right":
                    setX(getX() + getSpeed());
                    break;
            }
        }
        setSpriteCounter(getSpriteCounter() + 1);
        if (getSpriteCounter() > 10) {
            setSpriteCounter(getSpriteCounter() == 1 ? 2 : 1);
            setSpriteCounter(0);
        }
    }

    public void updateDirection(boolean up, boolean down, boolean left, boolean right) {
        if (up) {
            setDirection("up");
        } else if (down) {
            setDirection("down");
        } else if (left) {
            setDirection("left");
        } else if (right) {
            setDirection("right");
        }
        // CHECK TILE COLLISION
        setCollisionOn(false);
    }

    public void draw(Graphics2D g2, int tileSize) {
        BufferedImage image = null;
        switch (getDirection()) {
            case "up":
                if (getSpriteNum() == 1) {
                    image = getUp1();
                } else {
                    image = getUp2();
                }
                break;
            case "down":
                if (getSpriteNum() == 1) {
                    image = getDown1();
                } else {
                    image = getDown2();
                }
                break;
            case "left":
                if (getSpriteNum() == 1) {
                    image = getLeft1();
                } else {
                    image = getLeft2();
                }
                break;
            case "right":
                if (getSpriteNum() == 1) {
                    image = getRight1();
                } else {
                    image = getRight2();
                }
                break;
        }
        System.out.println("Player x: " + getX());
        System.out.println("Player y: " + getY());
        g2.drawImage(image, getX(), getY(), tileSize*2, tileSize*2, null);
    }

    // ================================ ORIGINAL GAME PLAY========================================
    private int stamina = 100;


    public Player(String name) {
        super(name);
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public void reduceStaminaMove() throws NoEnoughStaminaException {
        int energy = 15;
        if (hasEnoughStamina(energy)) {
            stamina -= energy;
        } else {
            throw new NoEnoughStaminaException();
        }
    }

    public void reduceStaminaPickUp() throws NoEnoughStaminaException {
        int energy = 25;
        if (hasEnoughStamina(energy)) {
            stamina -= energy;
        } else {
            throw new NoEnoughStaminaException();
        }
    }

    private boolean hasEnoughStamina(int energy) {
        return stamina - energy >= 0;
    }

    public void consumeItem(String item) throws ItemNotEdibleException, InventoryEmptyException {
        if(!GameItems.GAME_ITEMS_HASHMAP.containsKey(item)){
            throw new ItemNotEdibleException();
        }
        Item food = GameItems.GAME_ITEMS_HASHMAP.get(item);
        if (food.getEdible()) {
            this.getInventory().remove(food.getName());
            stamina += 50;
        } else {
            throw new ItemNotEdibleException();
        }
    }

    public void sleep(){
        stamina = 100;
    }


    public static class NoEnoughStaminaException extends Throwable {

    }

    public static class ItemNotEdibleException extends Throwable {

    }
}
