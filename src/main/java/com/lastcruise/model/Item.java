package com.lastcruise.model;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Set;
import javax.imageio.ImageIO;

public class Item {
    private String name;
    private String description;
    private boolean edible;
    private Set<String> required;
    private BufferedImage image;
    private boolean collision = true;
    private int x;
    private int y;
    private Rectangle solidArea = new Rectangle(8, 16, 32, 32);
    private final int solidAreaDefaultX = 8;
    private final int solidAreaDefaultY = 16;

    private int index;


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    public int getSolidAreaDefaultX() {
        return solidAreaDefaultX;
    }

    public int getSolidAreaDefaultY() {
        return solidAreaDefaultY;
    }
    public Rectangle getSolidArea() {
        return solidArea;
    }

    public void setSolidArea(Rectangle solidArea) {
        this.solidArea = solidArea;
    }
    public void setSolidAreaX(int x){
        this.solidArea.x = x;
    }
    public void setSolidAreaY(int y){
        this.solidArea.y = y;
    }
    public boolean isCollision() {
        return collision;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(String imagePath) {
        try {
            // Image path example "/objects/key.png"
            image = ImageIO.read(getClass().getResourceAsStream(imagePath));
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<String> getRequired() {
        return required;
    }

    public void setRequired(Set<String> required) {
        this.required = required;
    }

    public void setEdible(boolean edible) {
        this.edible = edible;
    }

    public boolean getEdible(){
        return edible;
    }

    @Override
    public String toString() {
        return "Item{" +
            "name='" + name + '\'' +
            ", description='" + description + '\'' +
            "}";
    }

    public void draw(Graphics2D g2, int tileSize) {
        g2.drawImage(getImage(), getX()*tileSize, getY()*tileSize, tileSize, tileSize, null);
    }




}
