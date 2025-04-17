import java.io.*;
import java.net.*;

import org.json.JSONArray;
import org.json.JSONObject;

public class ClientHandler extends Thread {
    private final Socket clientSocket;
    private final SchoolStatistics stat;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
        this.stat = SchoolStatistics.getInstance();
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
            String line;
            while ((line = in.readLine()) != null) {
                JSONObject json = new JSONObject(line);
                if (json.has("op")) {
                    String request = json.getString("op");
                    System.out.println("richiesta statistiche: " + request);
                    if (request.equals("EDA")) {
                        out.println(stat.GetAvarageNumExam());
                    }
                    else if (json.has("ExamName") && request.equals("EMA")) {
                        out.println(stat.GetAvarageMarkExam(json.getString("ExamName")));
                    }

                } else if (json.has("name") && json.has("surname") && json.has("passedExams") && json.has("placeOfResidence") && json.has("yearOfBirth")) {
                    System.out.println("Nuovo studente: " + json);
                    stat.addStudent(json);

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
