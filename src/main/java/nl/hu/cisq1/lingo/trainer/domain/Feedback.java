package nl.hu.cisq1.lingo.trainer.domain;
import java.util.List;
import java.util.Objects;

public class Feedback {
    private String attempt;
    private List<Mark> marks;

    @Override
    public String toString() {
        return "Feedback{" +
                "attempt='" + attempt + '\'' +
                ", marks=" + marks +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return Objects.equals(attempt, feedback.attempt) && Objects.equals(marks, feedback.marks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attempt, marks);
    }

    public Feedback(String attempt, List<Mark> marks){
        this.attempt = Objects.requireNonNull(attempt);
        this.marks = Objects.requireNonNull(marks);
        if(attempt.length() != marks.size())
        {
            throw new RuntimeException(
                    System.out.format(
                            "Amount of marks does not match word length %d%n, %d%n",
                            attempt.length(),
                            marks.size()
                    ).toString());
        }
    }

    public String getAttempt() {
        return attempt;
    }

    public void setAttempt(String attempt) {
        this.attempt = attempt;
    }

    public List<Mark> getMarks() {
        return marks;
    }

    public void setMarks(List<Mark> marks) {
        this.marks = marks;
    }

    public boolean isWordGuessed() {
        return marks.stream().allMatch(Mark.CORRECT::equals);
    }

    public boolean isWordInvalid() {
        return marks.stream().anyMatch(Mark.INVALID::equals);
    }


}
