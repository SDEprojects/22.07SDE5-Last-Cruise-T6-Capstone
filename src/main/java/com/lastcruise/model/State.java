package com.lastcruise.model;

public enum State {
  TITLE,
  WIN,
  LOSE,
  HELP,
  INVENTORY,
  SLEEP,
  PLAY {
    @Override
    public boolean isTerminal() {
      return false;
    }
  };

  public boolean isTerminal() {
    return true;
  }
}