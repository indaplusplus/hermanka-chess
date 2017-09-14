package com.pluss.chess;

import com.sun.org.apache.regexp.internal.RE;

import java.util.Scanner;

public class App {

  private static Game currentGame = new Game();
  private static Scanner in = new Scanner(System.in);


  private static void printBoard() {
    //prints the board with standard Chess indexes
    String[] board = currentGame.getBoardAsString();

    String toPrint = "  ";
    for (int j = 0; j < Game.COLUMNS; ++j) {
      toPrint += (char)((int)'a' + j);
    }
    System.out.println(toPrint);
    System.out.println(" +--------+");

    for (int row = 0; row < Game.ROWS; ++row) {
      toPrint = (Game.ROWS - row) + "|" + board[row] + "|" + (Game.ROWS - row);
      System.out.println(toPrint);
    }

    System.out.println(" +--------+ ");
    toPrint = "  ";
    for (int j = 0; j < Game.COLUMNS; ++j) {
      toPrint += (char)((int)'a' + j);
    }
    System.out.println(toPrint);
  }


  private static void printInstructions() {
    System.out.println("Moves are entered in my variation of algebraic chess notation");
    System.out.println("Example: a2 a4 moves the piece on a2 to a4");
    System.out.println("First the square to move from and then the square to move to");
    System.out.println("Castling, promotion and passant are all available");
  }

  private static void getPromotion() {
    printBoard();
    System.out.println("");
    System.out.println("Promotion available");
    while (true) {
      System.out.print("Promote to: ");
      System.out.flush();
      String promoteTo = in.next();
      if (currentGame.doPromotion(promoteTo.charAt(0))) {
        break;
      }
      System.out.println("Invalid piece");
    }
  }


  private static void getAndMakeMove() {
    if (currentGame.getCurrentPlayer() == Color.WHITE) {
      System.out.println("Current player: White");
    } else {
      System.out.println("Current player: Black");
    }

    while (true) {
      System.out.print("Insert move to make: ");
      System.out.flush();
      String from = in.next();
      String to = in.next();
      ReturnValue ret = currentGame.makeMove(from, to);
      if (ret == ReturnValue.SUCCESS) {
        break;
      }
      if (ret == ReturnValue.PROMOTION) {
        getPromotion();
        break;
      }
      System.out.println("Invalid move");
    }
  }

  private static void testIfInCheck() {
    if (currentGame.isWhiteInCheck()) {
      System.out.println("White is in check");
    }
    if (currentGame.isBlackInCheck()) {
      System.out.println("Black is in check");
    }
  }

  private static void endGame() {
    //declares the winner and then does whatever has to be done after the end of the game
    if (currentGame.hasWhiteWon()) {
      System.out.println("White has won");
    } else {
      System.out.println("Black has won");
    }

    currentGame = null;
  }

  public static void main(String[] args) {

    printInstructions();
    while (!currentGame.hasWhiteWon() && !currentGame.hasBlackWon()) {
      System.out.println("");
      printBoard();
      System.out.println("");
      testIfInCheck();
      getAndMakeMove();
    }
    endGame();
  }
}
