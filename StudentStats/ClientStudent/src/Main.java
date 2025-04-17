import java.io.*;
import java.net.*;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println(args[1]);
        String serverAddress = args[0]; // Indirizzo del server
        int port = Integer.parseInt(args[1]); // Porta del server

        try (Socket socket = new Socket(serverAddress, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            Random rand = new Random();
            int numberOfStudents = rand.nextInt(10) + 1;

            for (int i = 0; i < numberOfStudents; i++) {
                JSONObject student = generateRandomStudent();
                out.println(student.toString());
                System.out.println(student.toString());
            }

            new Thread(() -> {
                String msg;
                try {
                    while ((msg = in.readLine()) != null) {
                        //JSONObject rec = new JSONObject(msg);

                        //String text = rec.getString("text");
                        System.out.println("Messaggio: " + msg);

                    }
                } catch (IOException e) {
                    System.out.println("Connessione al server persa." + e);
                }
            }).start();

            while(true) {

                System.out.print("richiedi statistica: ");
                String stat = scanner.nextLine();
                JSONObject msg = new JSONObject();
                if (stat.equals("EDA")) {
                    msg.put("op", stat);
                }
                else if (stat.equals("EMA")) {
                    System.out.print("nome esame: ");
                    String examName = scanner.nextLine();

                    msg.put("op", stat);
                    msg.put("ExamName", examName);
                }

                out.println(msg.toString());

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JSONObject generateRandomStudent() {
        Random rand = new Random();

        List<String> names = Arrays.asList("Luca", "Marco", "Giulia", "Anna", "Sara");
        List<String> surnames = Arrays.asList("Rossi", "Verdi", "Bianchi", "Neri", "Russo");
        List<String> cities = Arrays.asList("Roma", "Milano", "Napoli", "Torino", "Firenze");
        List<String> examNames = Arrays.asList("Matematica", "Fisica", "Informatica", "Chimica", "Statistica");

        String name = names.get(rand.nextInt(names.size()));
        String surname = surnames.get(rand.nextInt(surnames.size()));
        int yearOfBirth = rand.nextInt(10) + 1995; // tra 1995 e 2004
        String city = cities.get(rand.nextInt(cities.size()));

        JSONObject student = new JSONObject();
        student.put("name", name);
        student.put("surname", surname);
        student.put("yearOfBirth", yearOfBirth);
        student.put("placeOfResidence", city);

        int examsCount = rand.nextInt(5) + 1; // 1-5 esami
        JSONArray exams = new JSONArray();

        for (int i = 0; i < examsCount; i++) {
            JSONObject exam = new JSONObject();
            exam.put("examName", examNames.get(rand.nextInt(examNames.size())));
            exam.put("mark", rand.nextInt(14) + 18); // Voto da 18 a 31
            String date = String.format("%02d/%02d/%d",
                    rand.nextInt(28) + 1,
                    rand.nextInt(12) + 1,
                    rand.nextInt(5) + 2019); // 2019-2023
            exam.put("dateOfVerbalization", date);
            exams.put(exam);
        }

        student.put("passedExams", exams);

        return student;
    }
}