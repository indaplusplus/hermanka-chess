package com.pluss.chess;

import junit.framework.TestCase;

import java.util.ArrayList;

public class RookTest extends TestCase {
  public void testGetPossibleMoves() throws Exception {
    Rook testRook = new Rook(Color.WHITE, 4, 6);
    ArrayList<Position> moves = testRook.getPossibleMoves();

    assertEquals(14, moves.size());
    assertTrue(moves.contains(new Position(3,6)));
    assertTrue(moves.contains(new Position(7,6)));
    assertTrue(moves.contains(new Position(4,1)));
    assertTrue(moves.contains(new Position(4,7)));
    assertTrue(moves.contains(new Position(0,6)));
    assertTrue(moves.contains(new Position(5,6)));
    assertTrue(moves.contains(new Position(4,2)));
    assertTrue(moves.contains(new Position(4,3)));
  }

}