package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Scanner;


public class Main{
    // IO streams
    static DataOutputStream toServer = null;
    static DataInputStream fromServer = null;

    public static void main(String[] args) {
        Main client = new Main();
        Scanner scanner = new Scanner(System.in);

        double interestRate = client.askQuestion("What is the interest rate", scanner);
        double numberOfYears = client.askQuestion("Number of years", scanner);
        double loanAmount = client.askQuestion("Total amount loaned", scanner);

        try {
            // Create a socket to connect to the server
            Socket socket = new Socket("localhost", 9000);

            // Create an input stream to receive data from the server
            fromServer = new DataInputStream(socket.getInputStream());

            // Create an output stream to send data to the server
            toServer = new DataOutputStream(socket.getOutputStream());

            // Send the loan information to the server
            toServer.writeDouble(interestRate);
            toServer.writeDouble(numberOfYears);
            toServer.writeDouble(loanAmount);
            toServer.flush();
            System.out.println("flushed " + LocalDateTime.now());

            // Get monthly payment and total payment from the server
            double monthlyPayment = fromServer.readDouble();
            double totalPayment = fromServer.readDouble();

            // Display to teat area
            System.out.println("Annual Interest Rate: " + interestRate + '\n');
            System.out.println("Number Of Years: " + numberOfYears + '\n');
            System.out.println("Loan Amount: " + loanAmount + '\n');
            System.out.println("monthlyPayment: " + monthlyPayment + '\n');
            System.out.println("totalPayment: " + totalPayment + '\n');
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
    public double askQuestion(String question, Scanner scanner) {
        System.out.println(question);
        return scanner.nextDouble();

    }
}
