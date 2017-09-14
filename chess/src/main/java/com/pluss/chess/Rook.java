package com.pluss.chess;

import java.util.ArrayList;

public class Rook extends Piece {

  public Rook(Color color, int row, int col) {
    super(row,col);
    this.color = color;
    this.type = PieceType.ROOK;

    if (this.color == Color.WHITE) {
      this.pieceCharacter = 'R';
    } else {
      this.pieceCharacter = 'r';
    }
  }

  @Override
  public ArrayList<Position> getPossibleMoves() {
    ArrayList<Position> possibleMoves = new ArrayList<>();

    for (int row = 0; row < ROWS; row++) {
      if (row == this.row) {
        continue;
      }
      possibleMoves.add(new Position(row, this.col));
    }

    for (int col = 0; col < COLUMNS; col++) {
      if (col == this.col) {
        continue;
      }
      possibleMoves.add(new Position(this.row, col));
    }

    return possibleMoves;
  }

  @Override
  Rook makeCopy() {
    Rook returnPiece = new Rook(color,row, col);
    returnPiece.hasMoved = hasMoved;
    return returnPiece;
  }

}
