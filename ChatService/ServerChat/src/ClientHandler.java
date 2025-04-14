import java.io.*;
import java.net.*;
import org.json.JSONObject;

public class ClientHandler extends Thread {
    private final Socket clientSocket;
    private final MessageRoom room;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
        this.room = MessageRoom.getInstance();
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
            String line;
            while ((line = in.readLine()) != null) {
                JSONObject json = new JSONObject(line);
            if (json.has("id")) {
                String newClientName = json.getString("id");
                System.out.println("Nuovo client connesso: " + newClientName);
                new Thread(new SenderThread(newClientName, out)).start();
                System.out.println("Messaggio sconosciuto ricevuto: " + json);

            } else if (json.has("from") && json.has("to") && json.has("text")) {
                System.out.println("Messaggio sconosciuto ricevuto: " + json);
                String from = json.getString("from");
                String to = json.getString("to");
                String text = json.getString("text");
                room.AddMessage(json);
                System.out.println("Messaggio da " + from + " a " + to + ": " + text);
            } else if (!json.toString().equals("{}")){
                System.out.println("Messaggio sconosciuto ricevuto: " + json);
            }
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