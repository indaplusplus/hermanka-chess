package com.pluss.chess;

import java.util.ArrayList;

class Piece {

  static final int ROWS = 8;
  static final int COLUMNS = 8;

  Color color = Color.NONE;
  boolean hasMoved = false;
  PieceType type = PieceType.NONE;

  //character that the piece has in string representation of a board
  //White is upper case and black is lower case
  char pieceCharacter = '.';

  Piece() {}

  //returns all possible moves provided there are no other pieces in the way
  ArrayList<Position> getPossibleMoves(int row, int col) {
    return new ArrayList<Position>();
  }

  void move() {
    hasMoved = true;
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
    Piece returnPiece = new Piece();
    returnPiece.color = color;
    returnPiece.hasMoved = hasMoved;
    returnPiece.type = type;
    returnPiece.pieceCharacter = pieceCharacter;
    return returnPiece;
  }

}
