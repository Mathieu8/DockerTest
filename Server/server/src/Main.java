import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
       new Main().listenToSocket();
    }

    public void listenToSocket() {
        try {
            ServerSocket serverSocket = new ServerSocket(9080);

            while(true) {
                Socket socket = serverSocket.accept();
                DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
                DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());
                new Date();
                double annualInterestRate = inputFromClient.readDouble();
                double numberOfYears = inputFromClient.readDouble();
                double loanAmount = inputFromClient.readDouble();
                double[] temp = this.doCalculation(annualInterestRate, numberOfYears, loanAmount);
                outputToClient.writeDouble(temp[0]);
                outputToClient.writeDouble(temp[1]);
            }
        } catch (IOException var13) {
            var13.printStackTrace();
        }
    }

    public double[] doCalculation(double interestRate, double numberOfYears, double loanAmount) {
        double monthlyInterestRate = interestRate / 1200.0D;
        double monthlyPayment = loanAmount * monthlyInterestRate / (1.0D - 1.0D / Math.pow(1.0D + monthlyInterestRate, numberOfYears * 12.0D));
        double totalPayment = monthlyPayment * numberOfYears * 12.0D;
        double[] temp = new double[]{monthlyPayment, totalPayment};
        return temp;
    }
}
