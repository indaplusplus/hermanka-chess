package com.pluss.chessgui;

import com.pluss.chessNetwork.Client;
import com.pluss.chessNetwork.MoveState;
import com.pluss.chessNetwork.Server;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class GuiNetwork extends Gui {

  Client otherPlayer;

  boolean blockMove;

  ArrayList<Integer> lastMove;

  GuiNetwork(String[] args) throws IOException {
    if (args[0].equals("Server")) {
      otherPlayer = new Server();
      blockMove = false;
    } else if (args[0].equals("Client")) {
      otherPlayer = new Client();
      blockMove = true;
    } else {
      return;
    }
    launch(args);
    System.err.println("?");
  }

  String getFen() {
    StringBuilder ret = new StringBuilder();
    for (String row: currentGame.getBoardAsString()) {
      int cnt = 0;
      for (int col = 0; col < GAME_COLS; ++col) {
        if (row.charAt(col) == '.') {
          cnt++;
          continue;
        }
        if (cnt > 0) ret.append(cnt);
        cnt = 0;
        ret.append(row.charAt(col));
      }
      if (cnt > 0) ret.append(cnt);
      ret.append('/');
    }
    ret.deleteCharAt(ret.length()-1);
    return ret.toString();
  }

  String getAlge() {
    StringBuilder ret = new StringBuilder();
    ret.append(lastMove.get(0));
    ret.append(lastMove.get(1));
    ret.append(lastMove.get(2));
    ret.append(lastMove.get(3));
    return ret.toString();
  }

  ArrayList<Integer> fromAlge(String alg) {
    ArrayList<Integer> ret = new ArrayList<>();
    ret.add(alg.charAt(0)-'0');
    ret.add(alg.charAt(1)-'0');
    ret.add(alg.charAt(2)-'0');
    ret.add(alg.charAt(3)-'0');
    return ret;
  }

  void sendMove() {
    MoveState state = new MoveState();
    state.move = getAlge();
    state.board = getFen();
    state.error = promotionRequired;
    try {
      getMove();
    } catch (Exception e) {
      System.err.println("Other Player threw error");
      System.exit(1);
    }
  }

  void getMove() throws Exception {
    MoveState state = otherPlayer.getMove();
    if (state.error) throw new Exception();
    ArrayList<Integer> move = fromAlge(state.move);
    selectedRow = move.get(0);
    selectedCol = move.get(1);
    doActionOnSquare(move.get(2), move.get(3));
  }

  void doActionOnSquareOther(int row, int col) {
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
      ;
      updateBoardGraphics();
      selectedRow = -1;
      selectedCol = -1;
    }

    if (currentGame.promotionAvailable()) {
      activatePromotionMode();
    }

    updateCurrentPlayer();
    blockMove = false;
  }

  @Override
  void doActionOnSquare(int row, int col) {
    if (blockMove) {
      try {
        getMove();
      } catch (Exception e) {
        System.err.println("Other Player threw error");
        System.exit(1);
      }
      return;
    }

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
      lastMove = new ArrayList<Integer>();
      lastMove.add(selectedRow);
      lastMove.add(selectedCol);
      lastMove.add(row);
      lastMove.add(col);
      updateBoardGraphics();
      selectedRow = -1;
      selectedCol = -1;
    }

    if (currentGame.promotionAvailable()) {
      activatePromotionMode();
    }

    updateCurrentPlayer();
    //TODO: Add promotion support

    blockMove = true;
    sendMove();
  }

}
