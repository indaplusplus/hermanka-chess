package com.pluss.chess;

import java.util.ArrayList;

public class Bishop extends Piece {

  public Bishop(Color color) {
    super();
    this.color = color;
    this.type = PieceType.BISHOP;

    if (this.color == Color.WHITE) {
      this.pieceCharacter = 'B';
    } else {
      this.pieceCharacter = 'b';
    }
  }

  @Override
  public ArrayList<Position> getPossibleMoves(int row, int col) {
    ArrayList<Position> possibleMoves = new ArrayList<>();
    int[] deltaRow = {1,1,-1,-1};
    int[] deltaCol = {1,-1,1,-1};

    for (int i = 0; i < 4; i++) {
      int times = 1;
      while (true) {
        int newRow = row + times * deltaRow[i];
        int newCol = col + times * deltaCol[i];
        if (newRow < 0 || ROWS <= newRow || newCol < 0 || COLUMNS <= newCol) {
          break;
        }
        possibleMoves.add(new Position(newRow, newCol));
        times++;
      }
    }

    return possibleMoves;
  }

  @Override
  Bishop makeCopy() {
    Bishop returnPiece = new Bishop(color);
    returnPiece.hasMoved = hasMoved;
    return returnPiece;
  }

}
