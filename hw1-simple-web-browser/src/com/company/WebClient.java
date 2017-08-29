package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class WebClient {

    public static void main(String[] args) {
        final String hostName = "localhost";
        final String cisHost = "www.cis.gvsu.edu";
        final String demo = "/~dulimarh/demo.html";
        final String arithemticGUI = "/~dulimarh/CS163/Examples/ArithmeticGUI.java\n";
        final String missingFile = "/~dulimarh/MissingFile.html";
        final String books = "/~dulimarh/books.jpg";
        int portNumber = 8000;

        Socket socket;
        PrintWriter writer;
        BufferedReader reader;

        try {
            // Step 1: Create a socket that connects to the above host and port number
            socket = new Socket(cisHost, portNumber);

            // Step 2: Create a PrintWriter from the socket's output stream
            //         Use the autoFlush option
            writer = new PrintWriter(socket.getOutputStream(), true);

            // Step 3: Create a BufferedReader from the socket's input stream
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Step 4: Send an HTTP GET request via the PrintWriter.
            //         Remember to print the necessary blank line
            writer.print(
                    "GET " + demo + " HTTP/1.1\n" +
                    "Host: " + cisHost + "\n" +
                    "Accept-Language: en-us\n"
            );

            System.out.println(socket);
            System.out.println(writer);
        } catch (IOException e) {
            System.out.println("Couldn't connect to host.");
            System.exit(1);
        }



        // Step 5a: Read the status line of the response
        // Step 5b: Read the HTTP response via the BufferedReader until
        //         you get a blank line

        // Step 6a: Create a FileOutputStream for storing the payload
        // Step 6b: Wrap the FileOutputStream in another PrintWriter

        // Step 7: Read the rest of the input from BufferedReader and write
        //         it to the second PrintWriter.
        //         Hint: readLine() returns null when there is no more data
        //         to read

        // Step 8: Remember to close the writer
    }
}