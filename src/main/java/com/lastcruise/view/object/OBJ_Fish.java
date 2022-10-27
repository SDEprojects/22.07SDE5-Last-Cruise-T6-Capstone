package com.lastcruise.view.object;

import java.io.IOException;
import javax.imageio.ImageIO;

public class OBJ_Fish extends SuperObject {

  public  OBJ_Fish() {

    name = "Fish";
    try {
      image = ImageIO.read(getClass().getResourceAsStream("/objects/key.png"));
    } catch (IOException | NullPointerException e) {
      e.printStackTrace();
    }
  }

}
