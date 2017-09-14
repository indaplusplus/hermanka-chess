package com.pluss.chess;

import java.util.ArrayList;

public class Pawn extends Piece {

  Pawn(Color color, int row, int col) {
    super(row,col);
    this.color = color;
    this.type = PieceType.PAWN;

    if (this.color == Color.WHITE) {
      this.pieceCharacter = 'P';
    } else {
      this.pieceCharacter = 'p';
    }
  }

  @Override
  ArrayList<Position> getPossibleMoves() {
    ArrayList<Position> possibleMoves = new ArrayList<>();
    int deltaRow;

    //moves up if white, down if black
    if (color == Color.WHITE) {
      deltaRow = -1;
    } else {
      deltaRow = 1;
    }

    if (0 <= row + deltaRow && row + deltaRow < ROWS) {
      possibleMoves.add(new Position(row + deltaRow, col));
    }
    //can move two tiles if it hasn't moved before
    if (!hasMoved && 0 <= row + 2 * deltaRow && row + 2 * deltaRow < ROWS) {
      possibleMoves.add(new Position(row + 2 * deltaRow, col));
    }

    return possibleMoves;
  }

  @Override
  Pawn makeCopy() {
    Pawn returnPiece = new Pawn(color,row, col);
    returnPiece.hasMoved = hasMoved;
    return returnPiece;
  }

}
