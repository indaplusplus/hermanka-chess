package com.pluss.chess.Helper;

import com.pluss.chess.Board;
import com.pluss.chess.PieceType;

public class BoardHandler {

  public static boolean checkForInBetweenPieces(Board board, int fromRow, int fromCol, int toRow, int toCol) {
    //checks if there is any pieces on the line from (fromRow, fromCol) to (toRow, toCol)
    //check does not include starting and ending squares and assumes that the lines angle
    //is an multiple of 45 deg
    //returns false if there are pieces in between
    int deltaRow = 0;
    if (fromRow != toRow) {
      deltaRow = fromRow < toRow ? 1 : -1;
    }
    int deltaCol = 0;
    if (fromCol != toCol) {
      deltaCol = fromCol < toCol ? 1 : -1;
    }

    int currentRow = fromRow + deltaRow;
    int currentCol = fromCol + deltaCol;
    while (currentRow != toRow || currentCol != toCol) {
      if (board.getType(currentRow, currentCol) != PieceType.NONE) {
        return false;
      }
      currentRow += deltaRow;
      currentCol += deltaCol;
    }

    return true;
  }

}
