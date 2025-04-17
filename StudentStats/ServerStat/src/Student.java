import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Student {
    public final String name;
    public final String surname;
    public final String placeOfResidence;
    public final int yearOfBirth;
    List<Exam> exams;

    public Student(String name, String surname, String placeOfResidence, int yearOfBirth, JSONArray exams) {
        this.name = name;
        this.surname = surname;
        this.placeOfResidence = placeOfResidence;
        this.yearOfBirth = yearOfBirth;
        this.exams = new ArrayList<>();

        for (int i = 0; i < exams.length(); i++) {
            JSONObject examObj = exams.getJSONObject(i);
            this.exams.add(new Exam( examObj.getString("examName"), examObj.getInt("mark"), examObj.getString("dateOfVerbalization")));
        }
    }

    public int getNumOfExams() {
        return exams.size();
    }

    public Exam getExam(String examName) {
        for (Exam exam : exams) {
            if (exam.getName().equals(examName)) {
                return exam;
            }
        }
        return null;
    }

}
