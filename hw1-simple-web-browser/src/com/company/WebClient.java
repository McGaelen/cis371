package com.company;

import java.io.*;
import java.net.*;

public class WebClient {

    public static void main(String[] args) {
        final String local = "localhost";
        final String cisHost = "www.cis.gvsu.edu";
        final String demo = "/~dulimarh/demo.html";
        final String arithemticGUI = "/~dulimarh/CS163/Examples/ArithmeticGUI.java";
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
            socket = new Socket(local, localPort);

            // Step 2: Create a PrintWriter from the socket's output stream
            //         Use the autoFlush option
            writer = new PrintWriter(socket.getOutputStream(), true);

            // Step 3: Create a BufferedReader from the socket's input stream
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Step 4: Send an HTTP GET request via the PrintWriter.
            //         Remember to print the necessary blank line
            writer.print(
                    "GET " + "/in.txt" + " HTTP/1.0\n" +
                    "Host: " + local+ "\n" +
                    "Accept-Language: en-us\n"
            );

            // Step 5a: Read the status line of the response
            System.out.println(reader.readLine());

            // Step 5b: Read the HTTP response via the BufferedReader until
            //         you get a blank line
            String response;
            do {
                response = reader.readLine();
                System.out.println(response);
            } while(!response.equals(""));

            // Step 6a: Create a FileOutputStream for storing the payload
            // Step 6b: Wrap the FileOutputStream in another PrintWriter
//            out = new PrintWriter(new FileOutputStream("./out.txt"));

            // Step 7: Read the rest of the input from BufferedReader and write
            //         it to the second PrintWriter.
            //         Hint: readLine() returns null when there is no more data
            //         to read

            // Step 8: Remember to close the writer
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }



    }
}