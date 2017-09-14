package com.pluss.chess;

import java.util.ArrayList;

class Piece {

  static final int ROWS = 8;
  static final int COLUMNS = 8;

  //indicates position on the board
  int row = 0;
  int col = 0;

  Color color = Color.NONE;
  boolean hasMoved = false;
  PieceType type = PieceType.NONE;

  //character that the piece has in string representation of a board
  //White is upper case and black is lower case
  char pieceCharacter = '.';

  Piece(int row, int col) {
    this.row = row;
    this.col = col;
  }

  //used to get simple moves that does not involve other pieces (such as castling)
  ArrayList<Position> getPossibleMoves() {
    return new ArrayList<Position>();
  }

  //changes internal location state
  void moveTo(int newRow, int newCol) {
    row = newRow;
    col = newCol;
    hasMoved = true;
  }

  int getRow() {
    return row;
  }

  int getCol() {
    return col;
  }

  Position getPos() {
    return new Position(row, col);
  }

  Color getColor() {
    return color;
  }

  PieceType getType() {
    return type;
  }

  char getPieceCharacter() {
    return pieceCharacter;
  }

  boolean getHasMoved() {
    return hasMoved;
  }

  Piece makeCopy() {
    Piece returnPiece = new Piece(row, col);
    returnPiece.color = color;
    returnPiece.hasMoved = hasMoved;
    returnPiece.type = type;
    returnPiece.pieceCharacter = pieceCharacter;
    return returnPiece;
  }

}
