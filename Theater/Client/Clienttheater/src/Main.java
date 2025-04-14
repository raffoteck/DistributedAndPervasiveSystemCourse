import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println(args[1]);
        String serverAddress = args[0]; // Indirizzo del server
        int port = Integer.parseInt(args[1]); // Porta del server

        try (Socket socket = new Socket(serverAddress, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {
            while(true) {
                // Legge due numeri dall'utente0
                //System.out.print("ins: ");
                //String val = scanner.nextLine();

                out.println("acquista");
                System.out.println("Inviato: " + "acquista");

                // Riceve la risposta dal server
                String response = in.readLine();
                System.out.println("Risposta dal server: " + response);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}