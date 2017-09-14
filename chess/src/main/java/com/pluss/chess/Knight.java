package com.pluss.chess;

import java.util.ArrayList;

public class Knight extends Piece {

  public Knight(Color color) {
    super();
    this.color = color;
    this.type = PieceType.KNIGHT;

    if (this.color == Color.WHITE) {
      this.pieceCharacter = 'N';
    } else {
      this.pieceCharacter = 'n';
    }
  }

  @Override
  public ArrayList<Position> getPossibleMoves(int row, int col) {
    ArrayList<Position> possibleMoves = new ArrayList<>();
    int[] deltaRow = {1, -1, 1, -1, 2, 2, -2, -2};
    int[] deltaCol = {2, 2, -2, -2, 1, -1, 1, -1};

    for (int i = 0; i < 8; ++i) {
      int newRow = row + deltaRow[i];
      int newCol = col + deltaCol[i];
      if (newRow < 0 || ROWS <= newRow || newCol < 0 || COLUMNS <= newCol) {
        continue;
      }
      possibleMoves.add(new Position(newRow, newCol));
    }

    return possibleMoves;
  }

  @Override
  Knight makeCopy() {
    Knight returnPiece = new Knight(color);
    returnPiece.hasMoved = hasMoved;
    return returnPiece;
  }
}
