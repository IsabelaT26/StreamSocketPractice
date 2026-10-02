package org.example;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Client {
    public static void main(String[] args) {

        String host = "127.0.0.1";
        int port = 2000;
        Socket socket = null;
        AtomicBoolean running = new AtomicBoolean(true);


        if (args.length >= 1) {
            host = args[0];
        }

        if (args.length >= 2) {
            port = Integer.parseInt(args[1]);
        }

        try {
            socket = new Socket(host, port);

            System.out.println("Connected to " + host + ":" + port);

            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());

            BufferedReader reader = new BufferedReader(inputStreamReader);
            PrintWriter writer = new PrintWriter(outputStreamWriter, true);

            Scanner scanner = new Scanner(System.in);

            Runnable receiveMessages =  () -> {
                try {
                    while (running.get()) {
                        String message = reader.readLine();
                        if(message == null){
                            running.set(false);
                            break;
                        }
                        System.out.println("Server: " + message);
                    }
                    System.out.println("Server disconnected.");
                }catch (IOException e){
                    running.set(false);
                    System.out.println("Connection lost!");
                }
            };

            Thread receiverThread = new Thread(receiveMessages);
            receiverThread.start();


            while (running.get()) {

                String messageToSend = scanner.nextLine();
                writer.println(messageToSend);

                if (writer.checkError()) {
                    running.set(false);
                    System.out.println("Could not send message.");
                }

            }

        }catch (IOException e){
            System.out.println("Error");
        }finally {
            try {
                if(socket != null){
                    socket.close();
                }
            } catch (IOException e) {
                System.out.println("Error");
            }
        }
    }
}
