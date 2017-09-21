package com.pluss.chessgui;

import com.pluss.chess.Color;
import com.pluss.chess.Game;
import com.pluss.chess.PieceType;
import com.pluss.chess.Position;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static com.pluss.chess.PieceType.*;
import static com.pluss.chess.Color.*;

public class Gui extends Application {

  private static final int SCENE_HEIGHT = 840;
  private static final int SCENE_WIDTH = 800;
  private static final int GAME_ROWS = 8;
  private static final int GAME_COLS = 8;
  private static final int SQUARE_SIZE = 90;
  private static final int PIECE_SIZE = 30;
  private static final int FONT_SIZE = 20;

  private static final Game currentGame = new Game();

  private Map<Color, Map<PieceType, Character>> pieceToCharacter = new TreeMap<>();
  private Button[][] buttons;
  private Scene gameScene;
  private GridPane grid;
  private Text currentPlayer = new Text();
  private Text message = new Text();

  private Button knightPromote;
  private Button rookPromote;
  private Button queenPromote;
  private Button bishopPromote;

  private int lambdaRow;
  private int lambdaCol;

  private int selectedRow = -1;
  private int selectedCol = -1;

  private boolean promotionRequired = false;

  public static void main(String[] args) {
    launch(args);
  }

  private void addAllPieceCharacters() {
    pieceToCharacter.put(WHITE, new TreeMap<PieceType, Character>());
    pieceToCharacter.get(WHITE).put(KING, '\u2654');
    pieceToCharacter.get(WHITE).put(QUEEN, '\u2655');
    pieceToCharacter.get(WHITE).put(ROOK, '\u2656');
    pieceToCharacter.get(WHITE).put(BISHOP, '\u2657');
    pieceToCharacter.get(WHITE).put(KNIGHT, '\u2658');
    pieceToCharacter.get(WHITE).put(PAWN, '\u2659');

    pieceToCharacter.put(BLACK, new TreeMap<PieceType, Character>());
    pieceToCharacter.get(BLACK).put(KING, '\u265A');
    pieceToCharacter.get(BLACK).put(QUEEN, '\u265B');
    pieceToCharacter.get(BLACK).put(ROOK, '\u265C');
    pieceToCharacter.get(BLACK).put(BISHOP, '\u265D');
    pieceToCharacter.get(BLACK).put(KNIGHT, '\u265E');
    pieceToCharacter.get(BLACK).put(PAWN, '\u265F');

    pieceToCharacter.put(Color.NONE, new TreeMap<PieceType, Character>());
    pieceToCharacter.get(Color.NONE).put(PieceType.NONE, ' ');
  }

  private String posToString(int row, int col) {
    return new StringBuilder().append((char)(col + (int)'a')).append(8 - row).toString();
  }

  private Position stringToPos(String pos) {
    int col = (int)pos.charAt(0) - (int)'a';
    int row = GAME_ROWS - Character.getNumericValue(pos.charAt(1));
    return new Position(row, col);
  }

  private void updateCurrentPlayer() {
    if (currentGame.getCurrentPlayer() == WHITE) {
      currentPlayer.setText("Whites' Turn");
    } else {
      currentPlayer.setText("Blacks' Turn");
    }
  }

  private void updateBoardGraphics() {
    Image imageWhite = new Image(getClass().getClassLoader().getResourceAsStream("white-empty.png"));
    Image imageBlack = new Image(getClass().getClassLoader().getResourceAsStream("black-empty.png"));

    Background backgroundWhite = new Background(new BackgroundImage(imageWhite,
            BackgroundRepeat.REPEAT,
            BackgroundRepeat.REPEAT,
            BackgroundPosition.DEFAULT,
            BackgroundSize.DEFAULT));

    Background backgroundBlack = new Background(new BackgroundImage(imageBlack,
            BackgroundRepeat.REPEAT,
            BackgroundRepeat.REPEAT,
            BackgroundPosition.DEFAULT,
            BackgroundSize.DEFAULT));

    for (int row = 0; row < GAME_ROWS; ++row) {
      for (int col = 0; col < GAME_COLS; ++col) {
        StringBuilder tempStringBuilder = new StringBuilder();
        String pos = posToString(row, col);
        tempStringBuilder.append(pieceToCharacter.get(currentGame.getPieceColor(pos)).get(currentGame.getPieceType(pos)));
        buttons[row][col].setText(tempStringBuilder.toString());

        buttons[row][col].setBackground((row + col) % 2 == 0 ? backgroundWhite : backgroundBlack);
      }
    }
  }

  private boolean hasGameEnded() {
    if (currentGame.hasWhiteWon()) {
      currentPlayer.setText("White has won");
    } else if (currentGame.hasBlackWon()) {
      currentPlayer.setText("Black has won");
    } else {
      return false;
    }
    message.setText("yay");
    return true;
  }

  private void activatePromotionMode() {
    promotionRequired = true;
    message.setText("Select what to promote to");

    Image imageWhite = new Image(getClass().getClassLoader().getResourceAsStream("white-empty.png"));
    Image imageBlack = new Image(getClass().getClassLoader().getResourceAsStream("black-empty.png"));

    Background backgroundWhite = new Background(new BackgroundImage(imageWhite,
            BackgroundRepeat.REPEAT,
            BackgroundRepeat.REPEAT,
            BackgroundPosition.DEFAULT,
            BackgroundSize.DEFAULT));

    Background backgroundBlack = new Background(new BackgroundImage(imageBlack,
            BackgroundRepeat.REPEAT,
            BackgroundRepeat.REPEAT,
            BackgroundPosition.DEFAULT,
            BackgroundSize.DEFAULT));

    knightPromote.setBackground(backgroundWhite);
    rookPromote.setBackground(backgroundBlack);
    bishopPromote.setBackground(backgroundWhite);
    queenPromote.setBackground(backgroundBlack);

    Color player = Color.NONE;
    if (currentGame.whiteHasPromotion()) {
      player = WHITE;
    }
    if (currentGame.blackHasPromotion()) {
      player = BLACK;
    }

    knightPromote.setText("" + pieceToCharacter.get(player).get(KNIGHT));
    rookPromote.setText("" + pieceToCharacter.get(player).get(ROOK));
    bishopPromote.setText("" + pieceToCharacter.get(player).get(BISHOP));
    queenPromote.setText("" + pieceToCharacter.get(player).get(QUEEN));

    updateBoardGraphics();
  }

  private void deactivatePromotion() {
    message.setText("");

    Image backgroundImage = new Image(getClass().getClassLoader().getResourceAsStream("blank.png"));

    Background background = new Background(new BackgroundImage(backgroundImage,
            BackgroundRepeat.REPEAT,
            BackgroundRepeat.REPEAT,
            BackgroundPosition.DEFAULT,
            BackgroundSize.DEFAULT));

    knightPromote.setBackground(background);
    rookPromote.setBackground(background);
    bishopPromote.setBackground(background);
    queenPromote.setBackground(background);

    knightPromote.setText("");
    rookPromote.setText("");
    bishopPromote.setText("");
    queenPromote.setText("");

    updateBoardGraphics();
    updateCurrentPlayer();

    promotionRequired = false;
  }

  private void doPromotion(Character piece) {
    System.err.print(piece + " ");
    if (!promotionRequired) {
      return;
    }
    currentGame.doPromotion(piece);
    deactivatePromotion();
  }

  private void markSelected(int row, int col) {
    if ((row + col) % 2 == 0) { //White
      Image backgroundImage = new Image(getClass().getClassLoader().getResourceAsStream("white-selected.png"));

      Background background = new Background(new BackgroundImage(backgroundImage,
              BackgroundRepeat.REPEAT,
              BackgroundRepeat.REPEAT,
              BackgroundPosition.DEFAULT,
              BackgroundSize.DEFAULT));

      buttons[row][col].setBackground(background);
    } else {
      Image backgroundImage = new Image(getClass().getClassLoader().getResourceAsStream("black-selected.png"));

      Background background = new Background(new BackgroundImage(backgroundImage,
              BackgroundRepeat.REPEAT,
              BackgroundRepeat.REPEAT,
              BackgroundPosition.DEFAULT,
              BackgroundSize.DEFAULT));

      buttons[row][col].setBackground(background);
    }
  }

  private void markReachable(int row, int col) {
    if ((row + col) % 2 == 0) { //White
      Image backgroundImage = new Image(getClass().getClassLoader().getResourceAsStream("white-reachable.png"));

      Background background = new Background(new BackgroundImage(backgroundImage,
              BackgroundRepeat.REPEAT,
              BackgroundRepeat.REPEAT,
              BackgroundPosition.DEFAULT,
              BackgroundSize.DEFAULT));

      buttons[row][col].setBackground(background);
    } else {
      Image backgroundImage = new Image(getClass().getClassLoader().getResourceAsStream("black-reachable.png"));

      Background background = new Background(new BackgroundImage(backgroundImage,
              BackgroundRepeat.REPEAT,
              BackgroundRepeat.REPEAT,
              BackgroundPosition.DEFAULT,
              BackgroundSize.DEFAULT));

      buttons[row][col].setBackground(background);
    }
  }

  private void markSquares(int row, int col) {
    ArrayList<String> moves = currentGame.getAllAvailableMovesFromSquare(posToString(row, col));
    markSelected(row, col);
    for (String pos : moves) {
      Position tmpPos = stringToPos(pos);
      markReachable(tmpPos.row, tmpPos.col);
    }
  }

  private void doActionOnSquare(int row, int col) {
    System.err.print(posToString(row, col) + " ");

    if (hasGameEnded()) {
      return;
    }
    if (promotionRequired) {
      return;
    }
    if (selectedRow == -1 && selectedCol == -1) {
      selectedRow = row;
      selectedCol = col;
      markSquares(row, col);
      message.setText(posToString(row, col) + " selected");
      return;
    } else {
      String fromPos = posToString(selectedRow, selectedCol);
      String toPos = posToString(row, col);
      if (!currentGame.makeMove(fromPos, toPos)) {
        message.setText("Invalid move");
      } else {
        message.setText("");
      }
      updateBoardGraphics();
      selectedRow = -1;
      selectedCol = -1;
    }

    if (currentGame.promotionAvailable()) {
      activatePromotionMode();
    }

    updateCurrentPlayer();
  }

  private void addPromotionSquares() {
    Image backgroundImage = new Image(getClass().getClassLoader().getResourceAsStream("blank.png"));

    Background background = new Background(new BackgroundImage(backgroundImage,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT));

    knightPromote = new Button();
    rookPromote = new Button();
    bishopPromote = new Button();
    queenPromote = new Button();

    knightPromote.setFont(new Font(PIECE_SIZE));
    rookPromote.setFont(new Font(PIECE_SIZE));
    bishopPromote.setFont(new Font(PIECE_SIZE));
    queenPromote.setFont(new Font(PIECE_SIZE));

    knightPromote.setMinWidth(SQUARE_SIZE);
    knightPromote.setMinHeight(SQUARE_SIZE);

    rookPromote.setMinWidth(SQUARE_SIZE);
    rookPromote.setMinHeight(SQUARE_SIZE);

    bishopPromote.setMinWidth(SQUARE_SIZE);
    bishopPromote.setMinHeight(SQUARE_SIZE);

    queenPromote.setMinWidth(SQUARE_SIZE);
    queenPromote.setMinHeight(SQUARE_SIZE);

    knightPromote.setBackground(background);
    rookPromote.setBackground(background);
    bishopPromote.setBackground(background);
    queenPromote.setBackground(background);

    knightPromote.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        doPromotion('n');
      }
    });

    rookPromote.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        doPromotion('r');
      }
    });

    bishopPromote.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        doPromotion('b');
      }
    });

    queenPromote.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        doPromotion('q');
      }
    });

    grid.add(knightPromote, GAME_COLS - 4, GAME_ROWS, 1, 2);
    grid.add(rookPromote, GAME_COLS - 3, GAME_ROWS, 1, 2);
    grid.add(bishopPromote, GAME_COLS - 2, GAME_ROWS, 1, 2);
    grid.add(queenPromote, GAME_COLS - 1, GAME_ROWS, 1, 2);
  }

  private void createSquares() {
    buttons = new Button[GAME_ROWS][GAME_COLS];

    for (int row = 0; row < GAME_ROWS; ++row) {
      for (int col = 0; col < GAME_COLS; ++col) {
        buttons[row][col] = new Button();
        lambdaRow = row;
        lambdaCol = col;
        buttons[row][col].setOnAction(new EventHandler<ActionEvent>() {
          int localRow = lambdaRow;
          int localCol = lambdaCol;

          @Override
          public void handle(ActionEvent event) {
            doActionOnSquare(localRow, localCol);
          }
        });
        Image buttonImage;
        if ((row + col) % 2 == 0) {
          buttonImage = new Image(getClass().getClassLoader().getResourceAsStream("white-empty.png"));
        } else {
          buttonImage = new Image(getClass().getClassLoader().getResourceAsStream("black-empty.png"));
        }
        buttons[row][col].setMinHeight(SQUARE_SIZE);
        buttons[row][col].setMinWidth(SQUARE_SIZE);
        buttons[row][col].setMaxHeight(SQUARE_SIZE);
        buttons[row][col].setMaxWidth(SQUARE_SIZE);

        buttons[row][col].setFont(new Font(PIECE_SIZE));

        buttons[row][col].setBackground(new Background(new BackgroundImage(buttonImage,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT)));

        grid.add(buttons[row][col], col, row);
      }
    }
  }

  @Override
  public void start(Stage primaryStage) {
    addAllPieceCharacters();

    primaryStage.setTitle("Chess");

    grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    gameScene = new Scene(grid, SCENE_WIDTH, SCENE_HEIGHT);

    createSquares();
    addPromotionSquares();

    grid.add(currentPlayer, 0, GAME_ROWS, GAME_COLS, 1);
    grid.add(message, 0, GAME_ROWS + 1, GAME_COLS, 1);
    currentPlayer.setFont(new Font(FONT_SIZE));
    message.setFont(new Font(FONT_SIZE));

    primaryStage.setScene(gameScene);
    primaryStage.show();

    updateBoardGraphics();
    updateCurrentPlayer();
  }
}