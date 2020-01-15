package com.ServerPayment;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BankServer {
    public static void main(String[] arg) throws IOException {
        ServerSocket serverSocket=new ServerSocket(5555);
        Socket socket;
        while(true){
            System.out.println("waiting for Clinet");
            socket=serverSocket.accept();
            new ThrideServer(socket);
        }
    }
}
