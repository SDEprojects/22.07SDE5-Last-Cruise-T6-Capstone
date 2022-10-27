package com.lastcruise.view.object;

import java.io.IOException;
import javax.imageio.ImageIO;

public class OBJ_Rocks extends SuperObject {

  public  OBJ_Rocks() {

    name = "Rocks";
    try {
      image = ImageIO.read(getClass().getResourceAsStream("/objects/key.png"));
    } catch (IOException | NullPointerException e) {
      e.printStackTrace();
    }
  }

}
