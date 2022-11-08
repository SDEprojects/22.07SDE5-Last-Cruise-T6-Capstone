package com.lastcruise.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ItemTest {

  @Test
  void checkEatOrDropReturnsEat() {
    Item item = new Item();
    item.setName("banana");
    assertEquals("eat", item.checkEatOrDrop());
  }

  @Test
  void checkEatOrDropReturnsDrop() {
    Item item = new Item();
    item.setName("log");
    assertEquals("drop", item.checkEatOrDrop());
  }
}