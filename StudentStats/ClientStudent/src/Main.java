import java.io.*;
import java.net.*;
import java.util.Scanner;
import org.json.JSONObject;

public class Main {
    public static void main(String[] args) {
        System.out.println(args[1]);
        String serverAddress = args[0]; // Indirizzo del server
        int port = Integer.parseInt(args[1]); // Porta del server

        try (Socket socket = new Socket(serverAddress, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            System.out.print("insert name: ");
            String name = scanner.nextLine();
            JSONObject json = new JSONObject();
            json.put("id", name);
            out.println(json.toString());

            new Thread(() -> {
                String msg;
                try {
                    while ((msg = in.readLine()) != null) {
                        JSONObject rec = new JSONObject(msg);

                        String to = rec.getString("to");
                        String from = rec.getString("from");
                        String text = rec.getString("text");

                        // Mostra solo se il messaggio è indirizzato a questo utente
                        if (to.equals(name)) {
                            System.out.println("Messaggio da " + from + ": " + text);
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Connessione al server persa." + e);
                }
            }).start();

            while(true) {

                System.out.print("insert destination: ");
                String dest = scanner.nextLine();

                System.out.print("insert message: ");
                String text = scanner.nextLine();

                JSONObject msg = new JSONObject();
                msg.put("from", name);
                msg.put("to", dest);
                msg.put("text", text);

                out.println(msg.toString());

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}