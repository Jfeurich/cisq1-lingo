package nl.hu.cisq1.lingo.trainer.domain.exception;

public class InvalidFeedbackException extends RuntimeException {

    public InvalidFeedbackException()
    {

    }

    public InvalidFeedbackException(String s)
    {
        super(s);
    }
}
