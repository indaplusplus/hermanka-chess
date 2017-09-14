package com.pluss.chess;

import java.util.ArrayList;

public class Rook extends Piece {

  public Rook(Color color) {
    super();
    this.color = color;
    this.type = PieceType.ROOK;

    if (this.color == Color.WHITE) {
      this.pieceCharacter = 'R';
    } else {
      this.pieceCharacter = 'r';
    }
  }

  @Override
  public ArrayList<Position> getPossibleMoves(int row, int col) {
    ArrayList<Position> possibleMoves = new ArrayList<>();

    for (int currentRow = 0; currentRow < ROWS; currentRow++) {
      if (currentRow == row) {
        continue;
      }
      possibleMoves.add(new Position(currentRow, col));
    }

    for (int currentCol = 0; currentCol < COLUMNS; currentCol++) {
      if (currentCol == col) {
        continue;
      }
      possibleMoves.add(new Position(row, currentCol));
    }

    return possibleMoves;
  }

  @Override
  Rook makeCopy() {
    Rook returnPiece = new Rook(color);
    returnPiece.hasMoved = hasMoved;
    return returnPiece;
  }
}
