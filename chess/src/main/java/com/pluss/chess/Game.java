package com.pluss.chess;

import java.util.ArrayList;

public class Game {

  public static final int ROWS = 8;
  public static final int COLUMNS = 8;

  private Color currentPlayer = Color.WHITE;

  private Board board = new Board();
  private Board backupBoard = new Board();

  private ArrayList<Board> history = new ArrayList<>();

  public Game() {
    //initialises board as a standard game
    //White at the bottom and black at the top

    //adding black pieces
    board.set(0, 0, new Rook(Color.BLACK, 0, 0));
    board.set(0, 1, new Knight(Color.BLACK, 0, 1));
    board.set(0, 2, new Bishop(Color.BLACK, 0, 2));
    board.set(0, 3, new Queen(Color.BLACK, 0, 3));
    board.set(0, 4, new King(Color.BLACK, 0, 4));
    board.set(0, 5, new Bishop(Color.BLACK, 0, 5));
    board.set(0, 6, new Knight(Color.BLACK, 0, 6));
    board.set(0, 7, new Rook(Color.BLACK, 0, 7));
    for (int col = 0; col < COLUMNS; col++) {
      board.set(1, col, new Pawn(Color.BLACK, 1, col));
    }

    //adding white pieces
    board.set(ROWS - 1, 0, new Rook(Color.WHITE, ROWS - 1, 0));
    board.set(ROWS - 1, 1, new Knight(Color.WHITE, ROWS - 1, 1));
    board.set(ROWS - 1, 2, new Bishop(Color.WHITE, ROWS - 1, 2));
    board.set(ROWS - 1, 3, new Queen(Color.WHITE, ROWS - 1, 3));
    board.set(ROWS - 1, 4, new King(Color.WHITE, ROWS - 1, 4));
    board.set(ROWS - 1, 5, new Bishop(Color.WHITE, ROWS - 1, 5));
    board.set(ROWS - 1, 6, new Knight(Color.WHITE, ROWS - 1, 6));
    board.set(ROWS - 1, 7, new Rook(Color.WHITE, ROWS - 1, 7));
    for (int col = 0; col < COLUMNS; col++) {
      board.set(ROWS - 2, col, new Pawn(Color.WHITE, ROWS - 2, col));
    }

    //adding empty pieces
    for (int row = 2; row < ROWS - 2; ++row) {
      for (int col = 0; col < COLUMNS; ++col) {
        board.set(row, col, new Piece(row, col));
      }
    }
  }

  public Game(String[] initBoard) {
    //constructor for general board given as an array of strings
    for (int row = 0; row < ROWS; ++row) {
      for (int col = 0; col < COLUMNS; ++col) {
        switch (initBoard[row].charAt(col)) {
          case '.':
            board.set(row, col, new Piece(row, col));
            break;
          case 'p':
            board.set(row, col, new Pawn(Color.BLACK, row, col));
            break;
          case 'P':
            board.set(row, col, new Pawn(Color.WHITE, row, col));
            break;
          case 'r':
            board.set(row, col, new Rook(Color.BLACK, row, col));
            break;
          case 'R':
            board.set(row, col, new Rook(Color.WHITE, row, col));
            break;
          case 'n':
            board.set(row, col, new Knight(Color.BLACK, row, col));
            break;
          case 'N':
            board.set(row, col, new Knight(Color.WHITE, row, col));
            break;
          case 'b':
            board.set(row, col, new Bishop(Color.BLACK, row, col));
            break;
          case 'B':
            board.set(row, col, new Bishop(Color.WHITE, row, col));
            break;
          case 'q':
            board.set(row, col, new Queen(Color.BLACK, row, col));
            break;
          case 'Q':
            board.set(row, col, new Queen(Color.WHITE, row, col));
            break;
          case 'k':
            board.set(row, col, new King(Color.BLACK, row, col));
            break;
          case 'K':
            board.set(row, col, new King(Color.WHITE, row, col));
            break;
          default:
            throw new Error();
        }
      }
    }

    backupBoard = board;
  }

  private void makeBackup() {
    backupBoard = board.makeCopy();
  }

  private void restoreBoard() {
    board = backupBoard.makeCopy();
  }

  private void doMove(int fromRow, int fromCol, int toRow, int toCol) {
    board.movePiece(fromRow, fromCol, toRow, toCol);
  }

  private void doMoveBackup(int fromRow, int fromCol, int toRow, int toCol) {
    makeBackup();
    doMove(fromRow, fromCol, toRow, toCol);
  }

  //only has memory one move back
  private void undoMove() {
    restoreBoard();
  }


  boolean checkForInBetweenPieces(int fromRow, int fromCol, int toRow, int toCol) {
    //checks if there is any pieces on the line from (fromRow, fromCol) to (toRow, toCol)
    //check does not include starting and ending squares and assumes that the lines angle
    //is an multiple of 45 deg
    //returns false if there are pieces in between
    int deltaRow = 0;
    if (fromRow != toRow) {
      deltaRow = fromRow < toRow ? 1 : -1;
    }
    int deltaCol = 0;
    if (fromCol != toCol) {
      deltaCol = fromCol < toCol ? 1 : -1;
    }

    int currentRow = fromRow + deltaRow;
    int currentCol = fromCol + deltaCol;
    while (currentRow != toRow || currentCol != toCol) {
      if (board.get(currentRow, currentCol).getType() != PieceType.NONE) {
        return false;
      }
      currentRow += deltaRow;
      currentCol += deltaCol;
    }

    return true;
  }


  void changePlayer() {
    currentPlayer = (currentPlayer == Color.WHITE ? Color.BLACK : Color.WHITE);
  }




  public ReturnValue makeMove(String from, String to) {
    //takes input in standard chess move form and converts it to row/col form
    //then calls the real makeMove
    if (from.length() != 2 || to.length() != 2) {
      return ReturnValue.FAIL;
    }

    int fromCol = (int)from.charAt(0) - (int)'a';
    int fromRow = ROWS - Character.getNumericValue(from.charAt(1));
    int toCol = (int)to.charAt(0) - (int)'a';
    int toRow = ROWS - Character.getNumericValue(to.charAt(1));

    return makeMove(fromRow, fromCol, toRow, toCol);
  }

  ReturnValue makeMove(int fromRow, int fromCol, int toRow, int toCol) {
    if (!tryMove(fromRow, fromCol, toRow, toCol)) {
      return ReturnValue.FAIL;
    }
    doMoveBackup(fromRow, fromCol, toRow, toCol);
    changePlayer();
    if (detectPromotion().equals(new Position(-1,-1))) {
      return ReturnValue.SUCCESS;
    }
    return ReturnValue.PROMOTION;
  }

  private boolean checkCoordinatesAreValid(int fromRow, int fromCol, int toRow, int toCol) {
    return (0 <= fromRow && fromRow < ROWS && 0 <= fromCol && fromCol < COLUMNS
            && 0 <= toRow && toRow < ROWS && 0 <= toCol && toCol < COLUMNS);
  }

  boolean simpleTryMove(int fromRow, int fromCol, int toRow, int toCol) {
    if (!checkCoordinatesAreValid(fromRow, fromCol, toRow, toCol)) {
      return false;
    }

    //checks the piece is of the right color and that target square
    //is not occupied by that players piece
    if (board.get(fromRow, fromCol).getColor() != currentPlayer) {
      return false;
    }
    if (board.get(toRow, toCol).getColor() == currentPlayer) {
      return false;
    }

    //Special case if
    boolean pawnPass = false;
    if (board.get(fromRow, fromCol).getType() == PieceType.PAWN) {
      //special check if piece is pawn
      if (toCol - fromCol == 0) {
        if (board.get(toRow, toCol).getType() != PieceType.NONE) {
          //if the piece moves forward and there is a piece there, it's an invalid move
          return false;
        }
      } else {
        if (Math.abs(toCol - fromCol) != 1 || Math.abs(toRow - fromRow) != 1) {
          //fail if move if not moving 1 square diagonally
          return false;
        }
        if (board.get(toRow, toCol).getColor() != Color.NONE
                && board.get(toRow, toCol).getColor() != currentPlayer) {
          pawnPass = true;
        }
      }
    }

    //checks that the move is possible for the piece
    ArrayList<Position> possibleMoves = board.get(fromRow, fromCol).getPossibleMoves();
    if (!pawnPass && !possibleMoves.contains(new Position(toRow, toCol))) {
      return false;
    }

    //checks that no other pieces are in the way (unless piece is a knight)
    if (board.get(fromRow, fromCol).getType() != PieceType.KNIGHT) {
      if (!checkForInBetweenPieces(fromRow, fromCol, toRow, toCol)) {
        return false;
      }
    }

    return true;
  }


  //tries to do a move, if possible, does move and returns true, otherwise returns false
  boolean tryMove(int fromRow, int fromCol, int toRow, int toCol) {

    if (!checkCoordinatesAreValid(fromRow, fromCol, toRow, toCol)) {
      return false;
    }

    //special case if it's an castling
    if ((    (board.getType(fromRow, fromCol) == PieceType.KING
           && board.getType(toRow, toCol) == PieceType.ROOK)
         ||  (board.getType(fromRow, fromCol) == PieceType.ROOK
           && board.getType(toRow, toCol) == PieceType.KING))
         && board.getColor(fromRow, fromCol) == currentPlayer
         && board.getColor(toRow,toCol) == currentPlayer) {
      return tryCastling(fromRow, fromCol, toRow, toCol);
    }

    if (!simpleTryMove(fromRow, fromCol, toRow, toCol)) {
      return false;
    }
    //makes sure that move does not result in a check
    doMoveBackup(fromRow, fromCol, toRow, toCol);
    if (isInCheck(currentPlayer)) {
      undoMove();
      return false;
    }
    undoMove();

    return true;
  }

  boolean tryCastling(int kingRow, int kingCol, int rookRow, int rookCol) {
    //how would a castling work if the pieces are not on the same row?
    if (kingRow != rookRow) {
      return false;
    }
    //if pieces are swapped, swap pieces
    if (board.getType(kingRow, kingCol) == PieceType.ROOK) {
      int tmp = kingRow;
      kingRow = rookRow;
      rookRow = tmp;
      tmp = kingCol;
      kingCol = rookCol;
      rookCol = tmp;
    }
    //none of the pieces can have moved for an Castling to take place
    if (board.getHasMoved(kingRow, kingCol) || board.getHasMoved(rookRow,rookCol)) {
      return false;
    }
    //there can be mo pieces between them
    if (!checkForInBetweenPieces(kingRow, kingCol, rookRow, rookCol)) {
      return false;
    }

    //checks if the king is in check in any of the places he moves through
    if (isInCheck(currentPlayer)) {
      return false;
    }
    int deltaCol = (kingCol < rookCol ? 1 : -1);
    int currentCol = kingCol;
    doMove(kingRow, currentCol, kingRow, currentCol + deltaCol);
    currentCol += deltaCol;

    if (isInCheck(currentPlayer)) {
      doMove(kingRow, currentCol, kingRow, kingCol);
      return false;
    }
    doMove(kingRow, currentCol, kingRow, currentCol + deltaCol);
    currentCol += deltaCol;
    if (isInCheck(currentPlayer)) {
      doMove(kingRow, currentCol, kingRow, kingCol);
      return false;
    }
    doMove(rookRow, rookCol, rookRow, currentCol - deltaCol);
    return true;
  }



  // (-1,-1) indicates that no promotion is available
  // will throw error if more than one promotion is available
  Position detectPromotion() {
    Position promotePos = new Position(-1,-1);
    for (int col = 0; col < COLUMNS; col++) {
      if (board.getType(0, col) == PieceType.PAWN) {
        //check upper row for pawns
        if (promotePos.col == -1) {
          promotePos = new Position(0,col);
        } else {
          throw new Error();
        }
      }
      if (board.getType(ROWS - 1, col) == PieceType.PAWN) {
        //check lower row for pawns
        if (promotePos.col == -1) {
          promotePos = new Position(ROWS - 1,col);
        } else {
          throw new Error();
        }
      }
    }
    return promotePos;
  }



  private boolean doPromotion(PieceType promoteTo) {
    Position piecePos = detectPromotion();
    if (piecePos.equals(new Position(-1,-1))) {
      return false;
    }
    Color pieceColor = board.getColor(piecePos.row, piecePos.col);
    if (promoteTo == PieceType.QUEEN) {
      board.set(piecePos.row, piecePos.col, new Queen(pieceColor, piecePos.row, piecePos.col));
      return true;
    }
    if (promoteTo == PieceType.BISHOP) {
      board.set(piecePos.row, piecePos.col, new Bishop(pieceColor, piecePos.row, piecePos.col));
      return true;
    }
    if (promoteTo == PieceType.ROOK) {
      board.set(piecePos.row, piecePos.col, new Rook(pieceColor, piecePos.row, piecePos.col));
      return true;
    }
    if (promoteTo == PieceType.KNIGHT) {
      board.set(piecePos.row,piecePos.col, new Knight(pieceColor, piecePos.row, piecePos.col));
      return true;
    }
    return false;
  }

  public boolean doPromotion(char promoteTo) {
    switch (promoteTo) {
      case 'n':
      case 'N':
        return doPromotion(PieceType.KNIGHT);
      case 'q':
      case 'Q':
        return doPromotion(PieceType.QUEEN);
      case 'b':
      case 'B':
        return doPromotion(PieceType.BISHOP);
      case 'r':
      case 'R':
        return doPromotion(PieceType.ROOK);
      default:
        return false;
    }
  }



  private Position findKing(Color player) {
    for (int row = 0; row < ROWS; row++) {
      for (int col = 0; col < COLUMNS; col++) {
        if (board.getType(row, col) == PieceType.KING && board.getColor(row, col) == player) {
          return new Position(row, col);
        }
      }
    }
    return new Position(-1,-1);
  }



  private boolean isInCheck(Color player) {
    Position king = findKing(player);
    Color playerBackup = currentPlayer;
    currentPlayer = player;
    changePlayer();

    for (int row = 0; row < ROWS; row++) {
      for (int col = 0; col < COLUMNS; col++) {
        if (board.getColor(row, col) == currentPlayer) {
          if (simpleTryMove(row, col, king.row, king.col)) {
            currentPlayer = playerBackup;
            return true;
          }
        }
      }
    }
    currentPlayer = playerBackup;
    return false;
  }



  private boolean isInCheckmate(Color player) {
    if (!isInCheck(player)) {
      return false;
    }
    boolean returnVal = true;
    Color backupPlayer = currentPlayer;
    currentPlayer = player;
    for (int row = 0; row < ROWS; row++) {
      for (int col = 0; col < COLUMNS; col++) {
        if (board.getColor(row, col) != player) {
          continue;
        }
        ArrayList<Position> moves = board.getPossibleMoves(row, col);
        for (Position pos : moves) {
          if (tryMove(row, col, pos.row, pos.col)) {
            returnVal = false;
          }
        }
      }
    }
    currentPlayer = backupPlayer;
    return returnVal;
  }



  public boolean hasWhiteWon() {
    return isInCheckmate(Color.BLACK);
  }

  public boolean hasBlackWon() {
    return isInCheckmate(Color.WHITE);
  }

  public boolean isWhiteInCheck() {
    return isInCheck(Color.WHITE);
  }

  public boolean isBlackInCheck() {
    return isInCheck(Color.BLACK);
  }

  public Color getCurrentPlayer() {
    return currentPlayer;
  }

  PieceType getPieceAt(int row, int col) {
    return board.getType(row, col);
  }

  public String[] getBoardAsString() {
    String[] printableBoard = new String[ROWS];

    for (int row = 0; row < ROWS; row++) {
      printableBoard[row] = "";
      for (int col = 0; col < COLUMNS; col++) {
        printableBoard[row] += board.getPieceCharacter(row, col);
      }
    }

    return printableBoard;
  }


  //for debugging
  private String[] getBackupBoardAsString() {
    String[] printableBoard = new String[ROWS];

    for (int row = 0; row < ROWS; row++) {
      printableBoard[row] = "";
      for (int col = 0; col < COLUMNS; col++) {
        printableBoard[row] += backupBoard.getPieceCharacter(row, col);
      }
    }

    return printableBoard;
  }

  public void printBoard() {
    String[] outBoard = getBoardAsString();
    for (String row : outBoard) {
      System.out.println(row);
    }
  }


  //fro debugging
  private void printLastBoard() {
    String[] outBoard = getBackupBoardAsString();
    for (String row : outBoard) {
      System.out.println(row);
    }
  }
}
