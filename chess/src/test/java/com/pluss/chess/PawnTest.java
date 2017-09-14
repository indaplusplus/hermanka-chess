package com.pluss.chess;

import junit.framework.TestCase;

import java.util.ArrayList;

public class PawnTest extends TestCase {
  public void testGetPossibleMovesNormal() throws Exception {
    Pawn testPawn = new Pawn(Color.WHITE);

    ArrayList<Position> moves = testPawn.getPossibleMoves(6, 1);

    assertEquals(4, moves.size());
    assertTrue(moves.contains(new Position(5, 1)));
    assertTrue(moves.contains(new Position(4, 1)));
  }

  /*public void testGetPossibleMovesMoved() throws Exception {
    Pawn testPawn = new Pawn(Color.WHITE, 6, 1);
    testPawn.move(5,1);

    ArrayList<Position> moves = testPawn.getPossibleMoves();

    assertEquals(1, moves.size());
    assertTrue(moves.contains(new Position(4, 1)));
  }*/

  public void testGetColor() throws Exception {
    Pawn testPawn1 = new Pawn(Color.WHITE);
    Pawn testPawn2 = new Pawn(Color.BLACK);

    assertEquals(Color.WHITE, testPawn1.getColor());
    assertEquals(Color.BLACK, testPawn2.getColor());
  }

  public void testGetType() throws Exception {
    Pawn testPawn = new Pawn(Color.WHITE);

    assertEquals(PieceType.PAWN, testPawn.getType());
  }

  public void testGetPieceCharacter() throws Exception {
    Pawn testPawn1 = new Pawn(Color.WHITE);
    Pawn testPawn2 = new Pawn(Color.BLACK);

    assertEquals('P', testPawn1.getPieceCharacter());
    assertEquals('p', testPawn2.getPieceCharacter());
  }

  /*public void testMakeCopy() throws Exception {
    Pawn testPawn1 = new Pawn(Color.WHITE);
    Pawn testPawn2 = testPawn1.makeCopy();
    testPawn1.move(2,5);
    testPawn2.move(3,3);

    assertEquals(new Position(3,3), testPawn2.getPos());
    assertEquals(new Position(2,5), testPawn1.getPos());
  }*/

}