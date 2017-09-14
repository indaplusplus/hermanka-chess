package com.pluss.chess;

import junit.framework.TestCase;

import java.util.ArrayList;

public class KingTest extends TestCase {
  public void testGetPossibleMoves1() throws Exception {
    King testKing = new King(Color.WHITE);
    ArrayList<Position> moves = testKing.getPossibleMoves(3, 3);

    assertEquals(8, moves.size());
    assertTrue(moves.contains(new Position(2, 2)));
    assertTrue(moves.contains(new Position(2, 3)));
    assertTrue(moves.contains(new Position(2, 4)));
    assertTrue(moves.contains(new Position(3, 4)));
    assertTrue(moves.contains(new Position(4, 4)));
    assertTrue(moves.contains(new Position(4, 3)));
    assertTrue(moves.contains(new Position(4, 2)));
    assertTrue(moves.contains(new Position(3, 2)));
  }

}