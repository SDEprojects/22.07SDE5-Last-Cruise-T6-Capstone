package com.lastcruise;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lastcruise.controller.Controller;
import com.lastcruise.model.AllSounds;
import com.lastcruise.model.Game;
import com.lastcruise.model.GamePanel;
import com.lastcruise.model.Music;
import com.lastcruise.model.SoundEffect;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import javax.swing.JFrame;

public class Main {

  public static void main(String[] args) throws IOException, InterruptedException {

    JFrame window = new JFrame();
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setResizable(false);
    window.setTitle("Last Cruise");

    GamePanel gamePanel =  new GamePanel();
    window.add(gamePanel);

    window.pack();

    window.setLocationRelativeTo(null);
    window.setVisible(true);

    // Set up the game board and place items before the game starts
    gamePanel.setupGame();

    gamePanel.startGameThread();



      //Controller controller = new Controller();
      //boolean runGame = controller.gameSetUp();

//      URL backgroundMusic = Main.class.getResource(AllSounds.ALL_SOUNDS.get("main"));
  //    Music.runAudio(backgroundMusic);
    //  URL sound = Main.class.getResource(AllSounds.ALL_SOUNDS.get("run"));
      //SoundEffect.runAudio(sound);

//      while(runGame){
  //      runGame = controller.getCommand();
    //    controller.updateView();
      //}

//      Music.muteMusic();

      // prevents cmd/terminal window from closing right away after game ends when playing JAR file
  //    System.out.println("Thanks for playing! This window will soon close.");
    //  Thread.sleep(5000);
  }
}
