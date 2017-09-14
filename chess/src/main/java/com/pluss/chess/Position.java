package com.pluss.chess;

class Position {

  int row;
  int col;

  Position(int row, int col) {
    this.row = row;
    this.col = col;
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof Position) {
      Position ptr = (Position) other;
      return this.row == ptr.row && this.col == ptr.col;
    }
    return false;
  }

}
