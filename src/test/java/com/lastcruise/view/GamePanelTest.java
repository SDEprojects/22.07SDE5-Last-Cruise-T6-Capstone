package com.lastcruise.view;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;

class GamePanelTest {

  GamePanel gamePanel = new GamePanel();

  @org.junit.jupiter.api.Test
  void getTileSize() {
    assertEquals(48, gamePanel.getTileSize());
  }




}