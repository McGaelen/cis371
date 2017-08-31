package com.company;

import java.io.*;
import java.net.*;

public class WebClient {

    public static void main(String[] args) {
        final String local = "localhost";
        final String cisHost = "www.cis.gvsu.edu";
        final String demo = "/~dulimarh/demo.html";
        final String arithmeticGUI = "/~dulimarh/CS163/Examples/ArithmeticGUI.java";
        final String missingFile = "/~dulimarh/MissingFile.html";
        final String books = "/~dulimarh/books.jpg";
        final int localPort = 8000;
        final int cisPort = 80;

        Socket socket;
        PrintWriter writer;
        BufferedReader reader;
        PrintWriter out;

        try {
            // Step 1: Create a socket that connects to the above host and port number
            socket = new Socket(cisHost, cisPort);

            // Step 2: Create a PrintWriter from the socket's output stream
            //         Use the autoFlush option
            writer = new PrintWriter(socket.getOutputStream(), true);

            // Step 3: Create a BufferedReader from the socket's input stream
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Step 4: Send an HTTP GET request via the PrintWriter.
            //         Remember to print the necessary blank line
            writer.println(
                "GET " + demo + " HTTP/1.0\n"
            );

            // Step 5a: Read the status line of the response
            System.out.println(reader.readLine());

            // Step 5b: Read the HTTP response via the BufferedReader until
            //         you get a blank line
            String response = reader.readLine();
            while(!response.equals("")) {
                System.out.println(response);
                response = reader.readLine();
            }
            System.out.println();

            // Step 6a: Create a FileOutputStream for storing the payload
            // Step 6b: Wrap the FileOutputStream in another PrintWriter
            out = new PrintWriter(new FileOutputStream("./out.txt"), false);

            // Step 7: Read the rest of the input from BufferedReader and write
            //         it to the second PrintWriter.
            //         Hint: readLine() returns null when there is no more data
            //         to read
            response = reader.readLine();
            while(response != null) {
                System.out.println(response);
                out.append(response + "\n");
                response = reader.readLine();
            }

            // Step 8: Remember to close the writer
            writer.close();
            out.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}