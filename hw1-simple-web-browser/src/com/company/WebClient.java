package com.company;

import java.net.*;

public class WebClient {

    public static void main(String[] args) {
        String hostName = "localhost";
        int portNumber = 8000;

        // Step 1: Create a socket that connects to the above host and port number

        // Step 2: Create a PrintWriter from the socket's output stream
        //         Use the autoFlush option

        // Step 3: Create a BufferedReader from the socket's input stream

        // Step 4: Send an HTTP GET request via the PrintWriter.
        //         Remember to print the necessary blank line

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