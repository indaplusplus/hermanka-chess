package com.pluss.chess;

import junit.framework.TestCase;

import java.util.ArrayList;

public class BishopTest extends TestCase {
  public void testGetPossibleMoves() throws Exception {
    Bishop testBishop = new Bishop(Color.WHITE, 4, 4);

    ArrayList<Position> moves = testBishop.getPossibleMoves();

    assertEquals(13, moves.size());
    assertTrue(moves.contains(new Position(7, 1)));
    assertTrue(moves.contains(new Position(0, 0)));
    assertTrue(moves.contains(new Position(2, 2)));
    assertTrue(moves.contains(new Position(6, 2)));
    assertTrue(moves.contains(new Position(2, 6)));
    assertTrue(moves.contains(new Position(5, 3)));
    assertTrue(moves.contains(new Position(7, 7)));
    assertFalse(moves.contains(new Position(4, 0)));
  }

}