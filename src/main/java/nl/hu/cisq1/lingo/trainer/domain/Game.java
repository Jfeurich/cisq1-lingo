package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidWordLengthException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game {
    private Integer score;
    private Integer attemptCount;
    private String wordToGuess;
    private Integer wordLength;
    private List<String> attempts;

    public Game(Integer wordLength){
        setBeginState();
        if(wordLength != null)
        {
            this.wordLength = wordLength;
        }
    }

    public Integer getScore()
    {
        return score;
    }

    public void nextGame()
    {
        if(gameIsFinished() && wordIsGuessed())
        {
            updateScore();
            updateWordLength();
            attempts = new ArrayList<>();
        }
        else if(gameIsFinished())
        {
            setBeginState();
        }
    }

    public boolean gameIsFinished()
    {
        return attemptCount.equals(5);
    }

    public boolean wordIsGuessed()
    {
        return attempts.get(attempts.size() - 1).equals(wordToGuess);
    }

    public void setWordToGuess(String wordToGuess) {
        if(wordToGuess.length() == wordLength)
        {
            this.wordToGuess = wordToGuess;
        }
        else
        {
            throw new InvalidWordLengthException(String.format("Word does not match round limit %D",wordLength));
        }
    }
    public void registerAttempt(String attempt)
    {
        if(attemptCount < 5)
        {
            attemptCount++;
            attempts.add(attempt);
        }
    }

    public Feedback getFeedBack()
    {
        List<Mark> marks = new ArrayList<>();

        if(attempts.size() == 0)
        {
            for(int i = 0; i < wordToGuess.length();i++)
            {
                marks.add(Mark.ABSENT);
            }

            return new Feedback(createFirstHint(),marks);
        }
        else if(attempts.get(attempts.size() -1).length() != wordToGuess.length())
        {
            for(int i = 0; i < wordToGuess.length();i++)
            {
                marks.add(Mark.ABSENT);
            }
            return new Feedback(createFirstHint(),marks);
        }
        else
        {
            for(int i = 0; i < wordToGuess.length();i++)
            {
                var c = attempts.get(attempts.size() -1).charAt(i);
                if (wordToGuess.charAt(i) == c)
                {
                    marks.add(Mark.CORRECT);
                }
                else if(wordToGuess.contains(String.valueOf(c)))
                {
                    marks.add(Mark.PRESENT);
                }
                else if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))
                {
                    marks.add(Mark.INVALID);
                }
                else
                {
                    marks.add(Mark.ABSENT);
                }
            }
            return new Feedback(attempts.get(attempts.size() -1),marks);
        }
    }

    private void updateWordLength()
    {
        switch (wordLength)
        {
            case 5: wordLength = 6;
            case 6: wordLength = 7;
            case 7: wordLength = 5;
            default: wordLength = 5;
        }
    }

    private String createFirstHint()
    {
        char[] chars = new char[wordToGuess.length()];

        chars[0] = wordToGuess.charAt(0);
        for(int i =1; i< wordToGuess.length() -1; i++)
        {
            chars[i] = '.';
        }
        return Arrays.toString(chars);
    }

    private void updateScore()
    {
        score += (attemptCount * 5) + score + 5 ;
    }

    private void setBeginState() {
        score = 0;
        wordLength = 5;
        attemptCount = 0;
        attempts = new ArrayList<>();
    }
}