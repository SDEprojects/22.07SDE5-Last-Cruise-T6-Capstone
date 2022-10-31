package com.lastcruise.model.tile;

import com.lastcruise.model.GamePanel;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;

public class TileManager {

  private final Tile[] tile;
  private final int[][] mapTileIndex;
  private final int maxScreenCol;
  private final int maxScreenRow;

  // CONSTRUCTOR
  public TileManager(int maxScreenCol, int maxScreenRow) {
    tile = new Tile[10];
    this.maxScreenCol = maxScreenCol;
    this.maxScreenRow = maxScreenRow;
    mapTileIndex = new int[maxScreenCol][maxScreenRow];
    getTileImage();
  }

  // load the tile images and store them in an array of Tile objects
  // these indexes are used to make maps through text files
  public void getTileImage() {
    try {
      tile[0] = new Tile();
      tile[0].setImage(ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png")));

      tile[1] = new Tile();
      tile[1].setImage(ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png")));
      tile[1].setCollision(true);

      tile[2] = new Tile();
      tile[2].setImage(ImageIO.read(getClass().getResourceAsStream("/tiles/water.png")));
      tile[2].setCollision(true);

      tile[3] = new Tile();
      tile[3].setImage(ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png")));
      tile[3].setCollision(true);

      tile[4] = new Tile();
      tile[4].setImage(ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png")));

      tile[5] = new Tile();
      tile[5].setImage(ImageIO.read(getClass().getResourceAsStream("/tiles/dark.png")));

      tile[6] = new Tile();
      tile[6].setImage(ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png")));

      tile[7] = new Tile();
      tile[7].setImage(ImageIO.read(getClass().getResourceAsStream("/tiles/stalagmite.png")));
      tile[7].setCollision(true);

      tile[8] = new Tile();
      tile[8].setImage(ImageIO.read(getClass().getResourceAsStream("/tiles/rock.png")));
      tile[8].setCollision(true);

      tile[9] = new Tile();
      tile[9].setImage(ImageIO.read(getClass().getResourceAsStream("/tiles/coconut-tree.png")));
      tile[9].setCollision(true);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // uses the filepath of the map in resources to read the text file and store the Tile indexes in a 2D array
  // the 2D array is used to draw the map
  public void loadMap(String filePath) {
    try {
      InputStream inputStream = getClass().getResourceAsStream(filePath);
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

      int col = 0;
      int row = 0;
      while (col < maxScreenCol && row < maxScreenRow) {
        String line = reader.readLine();
        while (col < maxScreenCol) {
          String[] numbers = line.split(" ");
          int num = Integer.parseInt(numbers[col]);
          mapTileIndex[col][row] = num;
          col++;
        }
        if (col == maxScreenCol) {
          col = 0;
          row++;
        }
      }
      reader.close();
    } catch (Exception e){
      e.printStackTrace();
    }
  }

  // draws the map tile by tile based on the 2D array
  public void draw(Graphics2D g2, int tileSize) {
    int col = 0;
    int row = 0;
    int x = 0;
    int y = 0;

    while (col < maxScreenCol && row < maxScreenRow) {
      int tileIndex = mapTileIndex[col][row];
      g2.drawImage(tile[tileIndex].getImage(), x, y, tileSize, tileSize, null);
      col++;
      x += tileSize;

      if (col ==  maxScreenCol) {
        col = 0;
        x = 0;
        row ++;
        y += tileSize;
      }
    }
  }

  public Tile[] getTile() {
    return tile;
  }

  public int[][] getMapTileIndex() {
    return mapTileIndex;
  }
}
