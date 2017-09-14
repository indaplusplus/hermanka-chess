package com.pluss.chess;

import junit.framework.TestCase;

public class GameTest extends TestCase {
  public void testHasWhiteWon() throws Exception {
    String[] initStrings =
                   {"R...kbnr",
                    "pppppppp",
                    "........",
                    "........",
                    "........",
                    "........",
                    "PPPPPPPP",
                    "RNBQKBNR"};
    Game testGame = new Game(initStrings);

    assertTrue(testGame.hasWhiteWon());
  }

  public void testHasBlackWon() throws Exception {
    String[] initStrings =
                   {"rnbqkbnr",
                    "pppppppp",
                    "........",
                    "........",
                    "........",
                    "........",
                    "PPPPPPPP",
                    "RNBQK..r"};
    Game testGame = new Game(initStrings);

    assertTrue(testGame.hasBlackWon());
  }

  public void testGetBoardAsStringAndNormalConstructor() throws Exception {
    Game testGame = new Game();
    String[] boardStrings = testGame.getBoardAsString();
    String[] correctStrings = {"rnbqkbnr",
                               "pppppppp",
                               "........",
                               "........",
                               "........",
                               "........",
                               "PPPPPPPP",
                               "RNBQKBNR"};

    //assertEquals(correctStrings, boardStrings);
    for (int i = 0; i < Game.ROWS; i++) {
      assertEquals(correctStrings[i], boardStrings[i]);
    }
  }

  public void testSpecialConstructor() throws Exception {
    String[] initStrings =
           {"rnbq.bnr",
            "pppp.ppp",
            "k.......",
            "..r...p.",
            "....Q...",
            "........",
            "PPPPPPPP",
            "RNBQKBNR"};
    Game testGame = new Game(initStrings);

    String[] boardString = testGame.getBoardAsString();
    for (int i = 0; i < Game.ROWS; i++) {
      assertEquals(initStrings[i], boardString[i]);
    }
  }

  public void testTryMoveIsCorrectAndDoesUndo() throws Exception {
    String[] initStrings =
                   {"rnbqkbnr",
                    "pppppppp",
                    "........",
                    "........",
                    "........",
                    "........",
                    "PPPPPPPP",
                    "RNBQKBNR"};
    Game testGame = new Game(initStrings);

    assertTrue(testGame.tryMove(7, 6, 5, 5));

    String[] currentBoard = testGame.getBoardAsString();
    for (int row = 0; row < Game.ROWS; row++) {
      assertEquals(initStrings[row], currentBoard[row]);
    }
  }

  public void testTryMoveIsCorrectAndDoesUndo2() throws Exception {
    String[] initStrings =
                   {"rnbqk.nr",
                    "ppppp..p",
                    "......p.",
                    "P....pb.",
                    ".....P..",
                    "........",
                    "RPPPP.PP",
                    ".NBQKBNR"};
    Game testGame = new Game(initStrings);


    testGame.changePlayer();
    assertTrue(testGame.makeMove(3, 6, 4, 7) == ReturnValue.SUCCESS);

    String[] correctStrings =
                   {"rnbqk.nr",
                    "ppppp..p",
                    "......p.",
                    "P....p..",
                    ".....P.b",
                    "........",
                    "RPPPP.PP",
                    ".NBQKBNR"};

    String[] currentBoard = testGame.getBoardAsString();
    for (int row = 0; row < Game.ROWS; row++) {
      assertEquals(correctStrings[row], currentBoard[row]);
    }
  }

  public void testTestForCheckIsCorrectAndDoesUndoWhenPersonIsInCheck() throws Exception {
    String[] initStrings =
                   {"rnbqkbnr",
                    "pppppppp",
                    "........",
                    "........",
                    ".......b",
                    "........",
                    "PPPPP.PP",
                    "RNBQKBNR"};
    Game testGame = new Game(initStrings);
    assertTrue(testGame.isWhiteInCheck());

    String[] currentBoard = testGame.getBoardAsString();
    for (int row = 0; row < Game.ROWS; row++) {
      assertEquals(initStrings[row], currentBoard[row]);
    }
  }

  public void testTestForCheckIsCorrect() throws Exception {
    String[] initStrings =
                   {"rQbqkbnr",
                    "p.ppppp.",
                    ".......p",
                    "........",
                    "...n....",
                    "........",
                    ".PPPPPPP",
                    "RNBQKBNR"};
    Game testGame = new Game(initStrings);

    assertFalse(testGame.isBlackInCheck());
  }

  public void testDetectsPromotionCorrectly() throws Exception {
    String[] initStrings =
                   {"r.bqkbnr",
                    "pPppppp.",
                    ".......p",
                    "........",
                    "...n....",
                    "........",
                    ".PPPPPPP",
                    "RNBQKBNR"};
    Game testGame = new Game(initStrings);

    assertEquals(ReturnValue.PROMOTION, testGame.makeMove("b7", "b8"));
  }

  public void testTestForCheckmateIsCorrectAndDoesUndoWhenPersonIsInCheckmate() throws Exception {
    String[] initStrings =
                   {"rnbqkbnr",
                    "pppppppp",
                    ".....r..",
                    "..K..r..",
                    ".....r..",
                    "........",
                    "PPPPPPPP",
                    "RNBQ.BNR"};
    Game testGame = new Game(initStrings);
    assertTrue(testGame.isWhiteInCheck());

    String[] currentBoard = testGame.getBoardAsString();
    for (int row = 0; row < Game.ROWS; row++) {
      assertEquals(initStrings[row], currentBoard[row]);
    }
  }

  public void testCastlingRight() throws Exception {
    String[] initStrings =
                   {"rnbqkbnr",
                    "pppppppp",
                    "........",
                    "........",
                    "........",
                    "........",
                    "PPPPPPPP",
                    "RNBQK..R"};
    Game testGame = new Game(initStrings);

    assertTrue(testGame.makeMove("e1", "h1") != ReturnValue.FAIL);
    assertEquals("RNBQ.RK.", testGame.getBoardAsString()[7]);
  }

  public void testCastlingLeft() throws Exception {
    String[] initStrings =
                   {"rnbqkbnr",
                    "pppppppp",
                    "........",
                    "........",
                    "........",
                    "........",
                    "PPPPPPPP",
                    "R...KBNR"};
    Game testGame = new Game(initStrings);

    assertTrue(testGame.makeMove("e1", "a1") != ReturnValue.FAIL);
    assertEquals("..KR.BNR", testGame.getBoardAsString()[7]);
  }

  public void testCastlingFailsPieceInTheWay() throws Exception {
    String[] initStrings =
                   {"rnbqkbnr",
                    "pppppppp",
                    "........",
                    "........",
                    "........",
                    "........",
                    "PPPPPPPP",
                    "RNBQK.NR"};
    Game testGame = new Game(initStrings);

    String[] currentBoard = testGame.getBoardAsString();
    testGame.makeMove("e1", "h1");
    assertEquals(ReturnValue.FAIL, testGame.makeMove("e1", "h1"));
    for (int row = 0; row < Game.ROWS; row++) {
      assertEquals(initStrings[row], currentBoard[row]);
    }
  }

  public void testCastlingFailsWhenCheckSquareIsPassed() throws Exception {
    String[] initStrings =
                   {"rnbqkbnr",
                    "pppppppp",
                    "........",
                    "........",
                    "......Q.",
                    "........",
                    "PPPPPP.P",
                    "RNBQK..R"};
    Game testGame = new Game(initStrings);

    String[] currentBoard = testGame.getBoardAsString();
    testGame.makeMove("e1", "h1");
    assertEquals(ReturnValue.FAIL, testGame.makeMove("e1", "h1"));
    for (int row = 0; row < Game.ROWS; row++) {
      assertEquals(initStrings[row], currentBoard[row]);
    }
  }

  public void testPromotionSuccess() throws Exception {
    String[] initStrings =
                   {"rnbqkbPr",
                    "pppppppp",
                    "........",
                    "........",
                    "........",
                    "........",
                    "PPPPPPPP",
                    "RNBQKBNR"};
    Game testGame = new Game(initStrings);

    assertFalse(testGame.detectPromotion().equals(new Position(-1,-1)));
    assertTrue(testGame.doPromotion('Q'));
  }

  public void testPromotionFail1() throws Exception {
    String[] initStrings =
                   {"rnbqkbPr",
                    "pppppppp",
                    "........",
                    "........",
                    "........",
                    "........",
                    "PPPPPPPP",
                    "RNBQKBNR"};
    Game testGame = new Game(initStrings);

    assertFalse(testGame.detectPromotion().equals(new Position(-1,-1)));
    assertFalse(testGame.doPromotion('K'));
  }

  public void testPromotionFail2() throws Exception {
    String[] initStrings =
                   {"rnbqkbnr",
                    "pppppppp",
                    "........",
                    "........",
                    "........",
                    "........",
                    "PPPPPPPP",
                    "RNBQKBNR"};
    Game testGame = new Game(initStrings);

    assertTrue(testGame.detectPromotion().equals(new Position(-1,-1)));
  }

  public void testPassantSuccess() throws Exception {
    String[] initStrings =
                   {"rnbqkbnr",
                    "pppppppp",
                    "........",
                    "......P.",
                    "........",
                    "........",
                    "PPPPPPPP",
                    "RNBQKBNR"};
    Game testGame = new Game(initStrings);

    testGame.changePlayer();
    testGame.makeMove("h7", "h5");

    assertTrue(testGame.isValidPassant(3, 6, 2, 7));
  }

  public void testPassantFail() throws Exception {
    String[] initStrings =
                   {"rnbqkbnr",
                    "pppppppp",
                    "........",
                    "......P.",
                    "........",
                    "........",
                    "PPPPPPPP",
                    "RNBQKBNR"};
    Game testGame = new Game(initStrings);

    testGame.changePlayer();
    testGame.makeMove("h7", "h5");
    testGame.makeMove("a2", "a4");
    testGame.makeMove("a7", "a5");

    assertFalse(testGame.isValidPassant(5, 6, 6, 7));
    assertFalse(testGame.makeMove(5, 6, 6, 7) != ReturnValue.FAIL);
  }
}