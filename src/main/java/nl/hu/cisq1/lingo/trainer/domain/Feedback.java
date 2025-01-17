package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidFeedbackException;

import java.util.ArrayList;
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

    public Feedback(String attempt, List<Mark> marks) {
        this.attempt = Objects.requireNonNull(attempt);
        this.marks = Objects.requireNonNull(marks);
        if (attempt.length() != marks.size()) {
            throw new InvalidFeedbackException();
        }
    }

    public List<String> giveHint(List<String> previousHint) {

        List<String> hints = new ArrayList<String>();
        for (int i = 0; i < marks.size(); i++) {
            if(marks.get(i).equals(Mark.CORRECT))
            {
                var test = attempt.charAt(i);
                hints.add(String.valueOf(test));
            }
            else
            {
                if(previousHint != null)
                {
                    var previousLetterAtI = previousHint.get(i);
                    if(!previousLetterAtI.equals("."))
                    {
                        hints.add(previousLetterAtI);
                    }
                    else
                    {
                        hints.add(".");
                    }
                }
                else
                {
                    hints.add(".");
                }
            }
        }
        return hints;
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
