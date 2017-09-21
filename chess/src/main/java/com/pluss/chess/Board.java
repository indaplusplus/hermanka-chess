package com.pluss.chess;

import java.util.ArrayList;

class Board {

  static final int ROWS = 8;
  static final int COLUMNS = 8;

  private Piece[][] pieces = new Piece[ROWS][COLUMNS];

  Board() {
    //constructs empty board
    for (int row = 0; row < ROWS; row++) {
      for (int col = 0; col < COLUMNS; col++) {
        pieces[row][col] = new Piece();
      }
    }
  }

  //get piece at position
  Piece get(int row, int col) {
    return pieces[row][col].makeCopy();
  }

  //sets piece at position
  void set(int row, int col, Piece pieceToSet) {
    pieces[row][col] = pieceToSet.makeCopy();
  }

  //moves piece from one square to another
  void movePiece(int fromRow, int fromCol, int toRow, int toCol) {
    pieces[toRow][toCol] = this.get(fromRow,fromCol);
    pieces[toRow][toCol].move();
    pieces[fromRow][fromCol] = new Piece();
  }

  PieceType getType(int row, int col) {
    return pieces[row][col].getType();
  }

  Color getColor(int row, int col) {
    return pieces[row][col].getColor();
  }

  boolean getHasMoved(int row, int col) {
    return pieces[row][col].getHasMoved();
  }

  ArrayList<Position> getPossibleMoves(int row, int col) {
    return pieces[row][col].getPossibleMoves(row, col);
  }

  char getPieceCharacter(int row, int col) {
    return pieces[row][col].getPieceCharacter();
  }

  //makes a copy of the board
  Board makeCopy() {
    Board returnBoard = new Board();
    for (int row = 0; row < ROWS; row++) {
      for (int col = 0; col < COLUMNS; col ++) {
        returnBoard.set(row, col, this.get(row, col));
      }
    }
    return returnBoard;
  }

}
