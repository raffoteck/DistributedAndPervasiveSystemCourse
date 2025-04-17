public class Exam {
    private final String name;
    private final int mark;
    private final String dateOfVerbalization;


    public Exam(String name, int mark, String dateOfVerbalization) {
        this.name = name;
        this.mark = mark;
        this.dateOfVerbalization = dateOfVerbalization;
    }

    public int getMark()
    {
        return this.mark;
    }

    public String getName()
    {
        return this.name;
    }
}
