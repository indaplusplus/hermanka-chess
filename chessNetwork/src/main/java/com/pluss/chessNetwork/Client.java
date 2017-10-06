package com.pluss.chessNetwork;

import se.kth.inda17plusplus.MoveOuterClass.*;

import java.io.IOException;
import java.net.Socket;

public class Client extends Thread {
  Socket socket;

  Client(Socket socket) {
    this.socket = socket;
  }

  public Client() throws IOException {
    this(new Socket("127.0.0.1", 0xDAD));
  }

  Client(boolean tmp) {} //aka stupid solution

  private Move getMovePrivate() throws IOException {
    return Move.parseDelimitedFrom(this.socket.getInputStream());
  }

  public void makeMove(MoveState state) throws IOException {
    Move.Builder builder = Move.newBuilder();
    builder.setMove(state.move);
    builder.setResultingState(state.board);
    builder.setLastMoveErrored(state.error);

    Move action = builder.build();
    action.writeDelimitedTo(this.socket.getOutputStream());
  }

  public MoveState getMove() throws IOException {
    MoveState ret = new MoveState();
    Move move = getMovePrivate();
    ret.move = move.getMove();
    ret.board = move.getResultingState();
    ret.error = move.getLastMoveErrored();

    return ret;
  }
}
