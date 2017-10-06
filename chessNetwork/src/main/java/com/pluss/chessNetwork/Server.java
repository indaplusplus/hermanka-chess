package com.pluss.chessNetwork;

import se.kth.inda17plusplus.MoveOuterClass.Move;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Client {

  ServerSocket server;
  public Server() throws IOException {
    super(false);
    server = new ServerSocket(0xDAD);
    socket = server.accept();
  }

}
