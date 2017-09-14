package com.pluss.chess;

import junit.framework.TestCase;

import java.util.ArrayList;

public class KnightTest extends TestCase {
  public void testGetPossibleMoves() throws Exception {
    Knight testKnight = new Knight(Color.WHITE);

    ArrayList<Position> moves = testKnight.getPossibleMoves(4, 4);

    assertEquals(8, moves.size());
    assertTrue(moves.contains(new Position(2, 3)));
    assertTrue(moves.contains(new Position(3, 2)));
    assertTrue(moves.contains(new Position(5, 6)));
    assertTrue(moves.contains(new Position(6, 5)));
    assertTrue(moves.contains(new Position(3, 6)));
    assertTrue(moves.contains(new Position(6, 3)));
    assertTrue(moves.contains(new Position(2, 5)));
    assertTrue(moves.contains(new Position(5, 2)));
  }

  public void testGetPossibleMoves2() throws Exception {
    Knight testKnight = new Knight(Color.BLACK);

    ArrayList<Position> moves = testKnight.getPossibleMoves(1, 1);

    assertEquals(4, moves.size());
    assertTrue(moves.contains(new Position(0, 3)));
    assertTrue(moves.contains(new Position(3, 0)));
    assertTrue(moves.contains(new Position(3, 2)));
    assertTrue(moves.contains(new Position(2, 3)));
  }

}