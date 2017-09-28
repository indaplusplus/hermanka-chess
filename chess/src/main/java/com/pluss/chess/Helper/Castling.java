package com.pluss.chess.Helper;

import com.pluss.chess.Board;
import com.pluss.chess.Color;
import com.pluss.chess.PieceType;


public class Castling {

  public static boolean isCastlingConditons(Color currentPlayer, Board board, int fromRow, int fromCol, int toRow, int toCol) {
    // the pieces has to be a King and a Rook
    if (!(board.getType(fromRow, fromCol) == PieceType.KING
        && board.getType(toRow, toCol) == PieceType.ROOK)
        || (board.getType(fromRow, fromCol) == PieceType.ROOK
        && board.getType(toRow, toCol) == PieceType.KING)) {
      return false;
    }

    //the pieces have to be the right color
    return board.getColor(fromRow, fromCol) == currentPlayer
        && board.getColor(toRow, toCol) == currentPlayer;
  }

  public static boolean foundInvalidCastling(Color currentPlayer, Board board, int kingRow, int kingCol, int rookRow, int rookCol) {
    //how would a castling work if the pieces are not on the same row?
    if (!isCastlingConditons(currentPlayer, board, kingRow, kingCol, rookRow, rookCol)) {
      return true;
    }
    if (kingRow != rookRow) {
      return true;
    }
    //if pieces are swapped, swap pieces
    if (board.getType(kingRow, kingCol) == PieceType.ROOK) {
      int tmp = kingRow;
      kingRow = rookRow;
      rookRow = tmp;
      tmp = kingCol;
      kingCol = rookCol;
      rookCol = tmp;
    }

    //none of the pieces can have moved for an Castling to take place
    if (board.getHasMoved(kingRow, kingCol) || board.getHasMoved(rookRow,rookCol)) {
      return true;
    }

    //there can be no pieces between them
    if (!BoardHandler.checkForInBetweenPieces(board, kingRow, kingCol, rookRow, rookCol)) {
      return true;
    }

    return false;
  }

  public static void castlingSwaps(Board board, int kingRow, int kingCol, int rookRow, int rookCol){
    //if pieces are swapped, swap pieces
    if (board.getType(kingRow, kingCol) == PieceType.ROOK) {
      int tmp = kingRow;
      kingRow = rookRow;
      rookRow = tmp;
      tmp = kingCol;
      kingCol = rookCol;
      rookCol = tmp;
    }
  }

}
