package com.pluss.chess;

import com.pluss.chess.Helper.BoardHandler;
import com.pluss.chess.Helper.Castling;

import java.util.ArrayList;

public class Game {

  public static final int ROWS = 8;
  public static final int COLUMNS = 8;

  private Color currentPlayer = Color.WHITE;

  private Board board = new Board(); //main board
  private Board backupBoard = new Board(); //board used for testing moves
  private Board lastBoard = new Board(); //Board before the last move

  /*
   * CONSTRUCTORS
   */

  public Game() {
    //initialises board as a standard game
    //White at the bottom and black at the top

    //adding black pieces
    board.set(0, 0, new Rook(Color.BLACK));
    board.set(0, 1, new Knight(Color.BLACK));
    board.set(0, 2, new Bishop(Color.BLACK));
    board.set(0, 3, new Queen(Color.BLACK));
    board.set(0, 4, new King(Color.BLACK));
    board.set(0, 5, new Bishop(Color.BLACK));
    board.set(0, 6, new Knight(Color.BLACK));
    board.set(0, 7, new Rook(Color.BLACK));
    for (int col = 0; col < COLUMNS; col++) {
      board.set(1, col, new Pawn(Color.BLACK));
    }

    //adding white pieces
    board.set(ROWS - 1, 0, new Rook(Color.WHITE));
    board.set(ROWS - 1, 1, new Knight(Color.WHITE));
    board.set(ROWS - 1, 2, new Bishop(Color.WHITE));
    board.set(ROWS - 1, 3, new Queen(Color.WHITE));
    board.set(ROWS - 1, 4, new King(Color.WHITE));
    board.set(ROWS - 1, 5, new Bishop(Color.WHITE));
    board.set(ROWS - 1, 6, new Knight(Color.WHITE));
    board.set(ROWS - 1, 7, new Rook(Color.WHITE));
    for (int col = 0; col < COLUMNS; col++) {
      board.set(ROWS - 2, col, new Pawn(Color.WHITE));
    }

    //adding empty pieces
    for (int row = 2; row < ROWS - 2; ++row) {
      for (int col = 0; col < COLUMNS; ++col) {
        board.set(row, col, new Piece());
      }
    }

    lastBoard = board.makeCopy();
  }

  public Game(String[] initBoard) {
    //constructor for general board given as an array of strings
    for (int row = 0; row < ROWS; ++row) {
      for (int col = 0; col < COLUMNS; ++col) {
        switch (initBoard[row].charAt(col)) {
          case '.':
            board.set(row, col, new Piece());
            break;
          case 'p':
            board.set(row, col, new Pawn(Color.BLACK));
            break;
          case 'P':
            board.set(row, col, new Pawn(Color.WHITE));
            break;
          case 'r':
            board.set(row, col, new Rook(Color.BLACK));
            break;
          case 'R':
            board.set(row, col, new Rook(Color.WHITE));
            break;
          case 'n':
            board.set(row, col, new Knight(Color.BLACK));
            break;
          case 'N':
            board.set(row, col, new Knight(Color.WHITE));
            break;
          case 'b':
            board.set(row, col, new Bishop(Color.BLACK));
            break;
          case 'B':
            board.set(row, col, new Bishop(Color.WHITE));
            break;
          case 'q':
            board.set(row, col, new Queen(Color.BLACK));
            break;
          case 'Q':
            board.set(row, col, new Queen(Color.WHITE));
            break;
          case 'k':
            board.set(row, col, new King(Color.BLACK));
            break;
          case 'K':
            board.set(row, col, new King(Color.WHITE));
            break;
          default:
            throw new Error();
        }
      }
    }

    lastBoard = board.makeCopy();
  }

  /*
   * Make Moves stuff
   */

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

  public boolean makeMove(String from, String to) {
    //takes input in standard chess move form and converts it to row/col form
    //then calls the real makeMove
    if (from.length() != 2 || to.length() != 2) {
      return false;
    }

    Position fromPos = convertSquareNotationToCoordinates(from);
    Position toPos = convertSquareNotationToCoordinates(to);

    return makeMove(fromPos.row, fromPos.col, toPos.row, toPos.col);
  }

  boolean makeMove(int fromRow, int fromCol, int toRow, int toCol) {
    //Board toPush = board.makeCopy();
    if (!isValidMove(fromRow, fromCol, toRow, toCol)) {
      return false;
    }
    Board tempBoard = board.makeCopy();
    //history.add(toPush);
    if (isValidPassant(fromRow, fromCol, toRow, toCol)) {
      doPassant(fromRow, fromCol, toRow, toCol);
    } else if (isValidCastling(fromRow, fromCol, toRow, toCol)) {
      doCastling(fromRow, fromCol, toRow, toCol);
    } else {
      doMove(fromRow, fromCol, toRow, toCol);
    }
    lastBoard = tempBoard;
    changePlayer();

    return true;
  }

  void doPassant(int fromRow, int fromCol, int toRow, int toCol) {
    int deltaRow = (currentPlayer == Color.WHITE ? -1 : 1);
    board.movePiece(fromRow, fromCol, toRow, toCol);
    board.set(toRow - deltaRow, toCol, new Piece());
  }

  private boolean doPromotion(PieceType promoteTo) {
    Position piecePos = findPromotion();
    if (piecePos.equals(new Position(-1,-1))) {
      return false;
    }
    Color pieceColor = board.getColor(piecePos.row, piecePos.col);
    if (promoteTo == PieceType.QUEEN) {
      board.set(piecePos.row, piecePos.col, new Queen(pieceColor));
      return true;
    }
    if (promoteTo == PieceType.BISHOP) {
      board.set(piecePos.row, piecePos.col, new Bishop(pieceColor));
      return true;
    }
    if (promoteTo == PieceType.ROOK) {
      board.set(piecePos.row, piecePos.col, new Rook(pieceColor));
      return true;
    }
    if (promoteTo == PieceType.KNIGHT) {
      board.set(piecePos.row,piecePos.col, new Knight(pieceColor));
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

  /*
   * Functions that tries and checks moves
   */

  public boolean isValidMove(String from, String to) {
    if (from.length() != 2 || to.length() != 2) {
      return false;
    }
    Position fromPos = convertSquareNotationToCoordinates(from);
    Position toPos = convertSquareNotationToCoordinates(to);

    return isValidMove(fromPos.row, fromPos.col, toPos.row, toPos.col);
  }

  boolean isValidPassant(int fromRow, int fromCol, int toRow, int toCol) {
    if (!coordinatesAreValid(fromRow, fromCol, toRow, toCol)) {
      return false;
    }
    //piece has to be pawn belonging to the current player
    if (board.getColor(fromRow, fromCol) != currentPlayer) {
      return false;
    }
    if (board.getType(fromRow, fromCol) != PieceType.PAWN) {
      return false;
    }
    //square has to be empty for a passant to be possible
    if (board.getType(toRow, toCol) != PieceType.NONE) {
      return false;
    }
    int deltaRow = (currentPlayer == Color.WHITE ? -1 : 1);
    //move have to be 1 step diagonal
    if (toRow - fromRow != deltaRow || Math.abs(toCol - fromCol) != 1) {
      return false;
    }
    //piece below has to be pawn
    if (board.getType(toRow - deltaRow, toCol) != PieceType.PAWN) {
      return false;
    }
    //and the right color
    if (board.getColor(toRow - deltaRow, toCol) == currentPlayer) {
      return false;
    }
    //the piece moved there the last move
    if (lastBoard.getType(toRow - deltaRow, toCol) != PieceType.NONE) {
      return false;
    }
    if (lastBoard.getType(toRow + deltaRow, toCol) != PieceType.PAWN) {
      return false;
    }
    if (lastBoard.getColor(toRow + deltaRow, toCol) == currentPlayer) {
      return false;
    }
    return true;
  }

  boolean isValidMoveWithoutRecursion(int fromRow, int fromCol, int toRow, int toCol) {
    //isValidMove that only checks if a piece can move from one place to another
    //without checking more complicated stuff like check and castling

    if (!coordinatesAreValid(fromRow, fromCol, toRow, toCol)) {
      return false;
    }

    boolean passantPass = false;
    if (isValidPassant(fromRow, fromCol, toRow, toCol)) {
      passantPass = true;
    }

    //checks the piece is of the right color and that target square
    //is not occupied by that players piece
    if (board.getColor(fromRow, fromCol) != currentPlayer) {
      return false;
    }
    if (board.getColor(toRow, toCol) == currentPlayer) {
      return false;
    }

    if (!passantPass && board.getType(fromRow, fromCol) == PieceType.PAWN) {
      //special case if piece is pawn
      if (toCol - fromCol == 0) {
        if (board.getType(toRow, toCol) != PieceType.NONE) {
          //if the piece moves forward and there is a piece there, it's an invalid move
          return false;
        }
      } else {
        if (Math.abs(toCol - fromCol) != 1 || Math.abs(toRow - fromRow) != 1) {
          //fail if not moving 1 square diagonally
          return false;
        }
        if (board.getColor(toRow, toCol) == Color.NONE
                || board.getColor(toRow, toCol) == currentPlayer) {
          return false;
        }
      }
    }

    //checks that the move is possible for the piece
    ArrayList<Position> possibleMoves = board.getPossibleMoves(fromRow, fromCol);
    if (!possibleMoves.contains(new Position(toRow, toCol))) {
      return false;
    }

    //checks that no other pieces are in the way (unless piece is a knight)
    if (board.getType(fromRow, fromCol) != PieceType.KNIGHT) {
      if (!checkForInBetweenPieces(fromRow, fromCol, toRow, toCol)) {
        return false;
      }
    }

    return true;
  }


  boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol) {
    //tries to do a move, if possible, does move and returns true, otherwise returns false

    if (!coordinatesAreValid(fromRow, fromCol, toRow, toCol)) {
      return false;
    }

    //special case if it's an castling
    if (isCastlingConditons(fromRow, fromCol, toRow, toCol)) {
      return isValidCastling(fromRow, fromCol, toRow, toCol);
    }

    if (!isValidMoveWithoutRecursion(fromRow, fromCol, toRow, toCol)) {
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

  boolean isValidCastling(int kingRow, int kingCol, int rookRow, int rookCol) {
    boolean potentialInvalidation = Castling.foundInvalidCastling(
        currentPlayer, board, kingRow, kingCol, rookRow, rookCol
    );

    if (potentialInvalidation) {
      return !potentialInvalidation;
    }

    //checks if the king is in check in any of the places he moves through
    if (isInCheck(currentPlayer)) {
      return false;
    }
    int deltaCol = (kingCol < rookCol ? 1 : -1);
    int currentCol = kingCol;

    makeBackup();
    currentCol += deltaCol;
    doMove(kingRow, currentCol, kingRow, currentCol);

    if (isInCheck(currentPlayer)) {
      undoMove();
      return false;
    }
    undoMove();
    currentCol += deltaCol;
    doMove(kingRow, currentCol, kingRow, currentCol);
    if (isInCheck(currentPlayer)) {
      undoMove();
      return false;
    }
    undoMove();
    return true;
  }

  boolean doCastling(int kingRow, int kingCol, int rookRow, int rookCol) {
    //how would a castling work if the pieces are not on the same row?
    if (kingRow != rookRow) {
      return false;
    }
    Castling.castlingSwaps(board, kingRow, kingCol, rookRow, rookCol);

    if (!isValidCastling(kingRow, kingCol, rookRow, rookCol)) {
      return false;
    }

    int deltaCol = (kingCol < rookCol ? 1 : -1);
    int currentCol = kingCol;

    doMove(kingRow, currentCol, kingRow, currentCol + 2*deltaCol);
    doMove(rookRow, rookCol, rookRow, currentCol + deltaCol);
    return true;
  }

  /*
   * Checkers
   */

  boolean checkForInBetweenPieces(int fromRow, int fromCol, int toRow, int toCol) {
    //checks if there is any pieces on the line from (fromRow, fromCol) to (toRow, toCol)
    //check does not include starting and ending squares and assumes that the lines angle
    //is an multiple of 45 deg
    //returns false if there are pieces in between
    return BoardHandler.checkForInBetweenPieces(board, fromRow, fromCol, toRow, toCol);
  }

  boolean isCastlingConditons(int fromRow, int fromCol, int toRow, int toCol) {
    return Castling.isCastlingConditons(currentPlayer,board,
            fromRow,fromCol,toRow,toCol);
  }

  private boolean coordinatesAreValid(int fromRow, int fromCol, int toRow, int toCol) {
    return (0 <= fromRow && fromRow < ROWS && 0 <= fromCol && fromCol < COLUMNS
            && 0 <= toRow && toRow < ROWS && 0 <= toCol && toCol < COLUMNS);
  }

  private boolean isInCheck(Color player) {
    Position king = findKing(player);
    Color playerBackup = currentPlayer;
    currentPlayer = player;
    changePlayer();

    for (int row = 0; row < ROWS; row++) {
      for (int col = 0; col < COLUMNS; col++) {
        if (board.getColor(row, col) == currentPlayer) {
          if (isValidMoveWithoutRecursion(row, col, king.row, king.col)) {
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
          if (isValidMove(row, col, pos.row, pos.col)) {
            returnVal = false;
          }
        }
      }
    }

    currentPlayer = backupPlayer;
    return returnVal;
  }

  /*
   * "Getters"
   */

  //will probably be useful for GUI
  public ArrayList<String> getAllAvailableMovesFromSquare(String square) {
    Position from = convertSquareNotationToCoordinates(square);
    ArrayList<String> allMoves = new ArrayList<>();

    for (int toRow = 0; toRow < ROWS; toRow++) {
      for (int toCol = 0; toCol < COLUMNS; toCol++) {
        if (isValidMove(from.row, from.col, toRow, toCol)) {
          allMoves.add(convertCoordinatesToSquareNotation(toRow, toCol));
        }
      }
    }

    return allMoves;
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

  public boolean promotionAvailable() {
    return !findPromotion().equals(new Position(-1,-1));
  }

  public boolean whiteHasPromotion() {
    return findPromotion().row == 0;
  }

  public boolean blackHasPromotion() { return findPromotion().row == ROWS-1; }

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

  private PieceType getPieceType(Position pos) {
    if (pos.row < 0 || ROWS <= pos.row || pos.col < 0 || COLUMNS <= pos.col) {
      return PieceType.NONE;
    }
    return board.getType(pos.row, pos.col);
  }

  public PieceType getPieceType(String pos) {
    return getPieceType(convertSquareNotationToCoordinates(pos));
  }

  private Color getPieceColor(Position pos) {
    if (pos.row < 0 || ROWS <= pos.row || pos.col < 0 || COLUMNS <= pos.col) {
      return Color.NONE;
    }
    return board.getColor(pos.row, pos.col);
  }

  public Color getPieceColor(String pos) {
    return getPieceColor(convertSquareNotationToCoordinates(pos));
  }

  /*
   * Convenience and debugging methods
   */
  private Position convertSquareNotationToCoordinates(String square) {
    int col = (int)square.charAt(0) - (int)'a';
    int row = ROWS - Character.getNumericValue(square.charAt(1));
    return new Position(row, col);
  }

  private String convertCoordinatesToSquareNotation(int row, int col) {
    StringBuilder square = new StringBuilder();
    square.append((char)((int)'a' + col));
    square.append(ROWS - row);
    return square.toString();
  }

  void changePlayer() {
    currentPlayer = (currentPlayer == Color.WHITE ? Color.BLACK : Color.WHITE);
  }

  // (-1,-1) indicates that no promotion is available
  // will throw error if more than one promotion is available
  Position findPromotion() {
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
          promotePos = new Position(ROWS - 1, col);
        } else {
          throw new Error();
        }
      }
    }
    return promotePos;
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

  //for debugging
  private String[] getLastBoardAsString() {
    String[] printableBoard = new String[ROWS];

    for (int row = 0; row < ROWS; row++) {
      printableBoard[row] = "";
      for (int col = 0; col < COLUMNS; col++) {
        printableBoard[row] += lastBoard.getPieceCharacter(row, col);
      }
    }

    return printableBoard;
  }

  //for debugging
  void printBoard() {
    String[] outBoard = getBoardAsString();
    for (String row : outBoard) {
      System.out.println(row);
    }
  }

  //for debugging
  private void printLastBoard() {
    String[] outBoard = getLastBoardAsString();
    for (String row : outBoard) {
      System.out.println(row);
    }
  }
}