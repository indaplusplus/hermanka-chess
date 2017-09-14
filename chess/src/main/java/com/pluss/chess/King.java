package com.pluss.chess;

import java.util.ArrayList;

public class King extends Piece {

  public King(Color color, int row, int col) {
    super(row,col);
    this.color = color;
    this.type = PieceType.KING;

    if (color == Color.WHITE) {
      this.pieceCharacter = 'K';
    } else {
      this.pieceCharacter = 'k';
    }
  }

  @Override
  public ArrayList<Position> getPossibleMoves() {
    ArrayList<Position> possibleMoves = new ArrayList<>();

    for (int deltaRow = -1; deltaRow < 2; deltaRow++) {
      for (int deltaCol = -1; deltaCol < 2; deltaCol++) {
        if (deltaCol == 0 && deltaRow == 0) {
          continue;
        }
        int newRow = row + deltaRow;
        int newCol = col + deltaCol;
        if (0 <= newRow && newRow < ROWS && 0 <= newCol && newCol < COLUMNS) {
          possibleMoves.add(new Position(newRow, newCol));
        }
      }
    }

    return possibleMoves;
  }

  @Override
  King makeCopy() {
    King returnPiece = new King(color,row, col);
    returnPiece.hasMoved = hasMoved;
    return returnPiece;
  }

}
