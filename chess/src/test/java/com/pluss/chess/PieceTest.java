package com.pluss.chess;

import junit.framework.TestCase;

import java.util.ArrayList;

public class PieceTest extends TestCase {
  public void testGetPossibleMoves() throws Exception {
    Piece testPiece = new Piece();
    ArrayList<Position> possibleMoves = testPiece.getPossibleMoves(4, 4);

    assertEquals(0, possibleMoves.size());
  }

  /*public void testMoveTo() throws Exception {
    Piece testPiece = new Piece(0,0);

    testPiece.move(4,5);

    assertEquals(4, testPiece.getRow());
    assertEquals(5, testPiece.getCol());
  }

  public void testGetRow() throws Exception {
    Piece testPiece = new Piece(3,6);

    assertEquals(3, testPiece.getRow());
  }

  public void testGetCol() throws Exception {
    Piece testPiece = new Piece(3,6);

    assertEquals(6, testPiece.getCol());
  }

  public void testGetPos() throws Exception {
    Piece testPiece = new Piece(3,6);
    Position pos = testPiece.getPos();

    assertEquals(3, pos.row);
    assertEquals(6, pos.col);
  }*/

  public void testGetColor() throws Exception {
    Piece testPiece = new Piece();

    assertEquals(Color.NONE, testPiece.getColor());
  }

  public void testGetType() throws Exception {
    Piece testPiece = new Piece();

    assertEquals(PieceType.NONE, testPiece.getType());
  }

  public void testGetPieceCharacter() throws Exception {
    Piece testPiece = new Piece();

    assertEquals('.', testPiece.getPieceCharacter());
  }

  /*public void testAssignment() throws Exception {
    Piece testPiece1 = new Piece(1,4);
    Piece testPiece2 = testPiece1.makeCopy();
    //testPiece2.copyFrom(testPiece1);
    testPiece1.move(3,3);

    assertEquals(4, testPiece2.getCol());
  }*/
}