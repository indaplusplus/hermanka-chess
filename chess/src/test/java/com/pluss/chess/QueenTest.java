package com.pluss.chess;

import junit.framework.TestCase;

import java.util.ArrayList;

public class QueenTest extends TestCase {
  public void testGetPossibleMoves() throws Exception {
    Queen testQueen = new Queen(Color.WHITE);

    ArrayList<Position> moves = testQueen.getPossibleMoves(4, 4);

    assertEquals(27, moves.size());
    assertTrue(moves.contains(new Position(0, 0)));
    assertTrue(moves.contains(new Position(1, 7)));
    assertTrue(moves.contains(new Position(5, 3)));
    assertTrue(moves.contains(new Position(6, 6)));
    assertTrue(moves.contains(new Position(0, 4)));
    assertTrue(moves.contains(new Position(7, 4)));
    assertTrue(moves.contains(new Position(4, 1)));
    assertTrue(moves.contains(new Position(4, 6)));
    assertFalse(moves.contains(new Position(8, 8)));
  }

}