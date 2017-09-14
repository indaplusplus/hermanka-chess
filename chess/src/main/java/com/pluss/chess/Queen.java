package com.pluss.chess;

import java.util.ArrayList;

public class Queen extends Piece {

  public Queen(Color color) {
    super();
    this.color = color;
    this.type = PieceType.QUEEN;

    if (this.color == Color.WHITE) {
      this.pieceCharacter = 'Q';
    } else {
      this.pieceCharacter = 'q';
    }
  }

  @Override
  public ArrayList<Position> getPossibleMoves(int row, int col) {
    ArrayList<Position> possibleMoves = new ArrayList<>();

    for (int deltaRow = -1; deltaRow < 2; deltaRow++) {
      for (int deltaCol = -1; deltaCol < 2; deltaCol++) {
        if (deltaRow == 0 && deltaCol == 0) {
          continue;
        }

        int times = 1;
        while (true) {
          int newRow = row + times * deltaRow;
          int newCol = col + times * deltaCol;
          if (newRow < 0 || ROWS <= newRow || newCol < 0 || COLUMNS <= newCol) {
            break;
          }
          possibleMoves.add(new Position(newRow, newCol));
          times++;
        }
      }
    }

    return possibleMoves;
  }

  @Override
  Queen makeCopy() {
    Queen returnPiece = new Queen(color);
    returnPiece.hasMoved = hasMoved;
    return returnPiece;
  }
}
