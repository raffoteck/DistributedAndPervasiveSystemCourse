import java.io.*;
import java.net.*;

public class ClientHandler extends Thread {
    private final Socket clientSocket;
    private final Reservations reservations;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
        this.reservations = Reservations.getInstance();
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Ricevuto: " + inputLine);

                out.println("Booked tickets: " + this.reservations.reserveTicket());
            }
        } catch (IOException e) {
            System.out.println("Connessione chiusa: " + clientSocket.getInetAddress());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
