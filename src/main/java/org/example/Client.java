package org.example;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {

        String host = "127.0.0.1";
        int port = 2000;
        Socket socket = null;
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedReader reader = null;
        PrintWriter writer = null;

        try {
            socket = new Socket(host,port);
            inputStreamReader = new InputStreamReader(socket.getInputStream());
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());

            reader = new BufferedReader(inputStreamReader);
            writer = new PrintWriter(outputStreamWriter);

            Scanner scanner = new Scanner(System.in);

            while (true){

                String messageToSend = scanner.nextLine();
                writer.write(messageToSend);

            }

        }catch (IOException e){
            throw new RuntimeException();
        }

//        if (args.length >= 1) {
//            host = args[0];
//        }
//
//        if (args.length >= 2) {
//            port = Integer.parseInt(args[1]);
//        }

//        System.out.println("Host: " + host);
//        System.out.println("Port: " + port);
    }
}
