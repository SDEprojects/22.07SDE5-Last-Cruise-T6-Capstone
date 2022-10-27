package com.lastcruise.model;

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
    private boolean collision = false;
    private int worldX, worldY;

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
}
