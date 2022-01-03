package nl.hu.cisq1.lingo.trainer.domain.exception;

public class InvalidFeedbackExtension extends RuntimeException {

    public InvalidFeedbackExtension()
    {

    }

    public InvalidFeedbackExtension(String s)
    {
        super(s);
    }
}
