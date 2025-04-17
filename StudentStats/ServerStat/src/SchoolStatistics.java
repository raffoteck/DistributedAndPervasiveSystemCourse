import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SchoolStatistics {
    private List<Student> Students;
    private static SchoolStatistics instance;

    public SchoolStatistics() {
        Students = new ArrayList<>();
    }

    public static void setInstance()
    {
        if (instance == null)
        {
            instance = new SchoolStatistics();
        }
    }

    public static SchoolStatistics getInstance()
    {
        return instance;
    }

    public synchronized void addStudent(JSONObject Jstudent)
    {
        this.Students.add(new Student(Jstudent.getString("name"), Jstudent.getString("surname"), Jstudent.getString("placeOfResidence"), Jstudent.getInt("yearOfBirth"), Jstudent.getJSONArray("passedExams")));
    }

    public synchronized int GetAvarageNumExam() {
        int sum = 0;
        for (Student student : this.Students) {
            sum = sum + student.getNumOfExams();
        }
        if (this.Students.isEmpty())
        {
            return 0;
        }
        else
        {
            return sum / this.Students.size();
        }
    }

    public synchronized int GetAvarageMarkExam( String ExamName )
    {
        int sum = 0;
        int total = 0;
        for (Student student : this.Students) {
            Exam exam = student.getExam(ExamName);
            if (exam != null)
            {
                total = total +1;
                sum = sum + exam.getMark();
            }
        }

        if (total == 0)
        {
            return 0;
        }
        else
        {
            return sum / total;
        }
    }
}
