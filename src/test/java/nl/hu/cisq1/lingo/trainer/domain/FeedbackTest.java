package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class FeedbackTest {

    @Test
    @DisplayName("Word is guessed if all letters are correct")
    public void wordIsGuessed() {
        // Arrange

        // Act
        /* When we create a new word feedback object (new Feedback) with a guessed "Word" and a collection of marks
            List.of(Mark.Correct,Mark.Correct,Mark.Correct,Mark.Correct,Mark.Correct) */
        Feedback feedback = new Feedback("woord", List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT));
        // Assert
        /* Then the result of feedback.isWordGuessed() equals true (assertTrue(feedback.isWordGuessed())) */
        assertTrue(feedback.isWordGuessed());
    }

    @Test
    @DisplayName("Word is not guessed if not all letters are correct")
    public void wordIsNotGuessed() {
        // Arrange

        // Act
        // When we create a new word feedback object (new Feedback) with a guessed "Word" and a collection of marks not all correct
        Feedback feedback = new Feedback("woord", List.of(Mark.ABSENT, Mark.ABSENT, Mark.PRESENT, Mark.CORRECT, Mark.CORRECT));
        // Assert
        /* Then the result of feedback.isWordGuessed() equals true (assertTrue(feedback.isWordGuessed())) */
        assertFalse(feedback.isWordGuessed());
    }

    @Test
    @DisplayName("Word is not guessed if not any letters are invalid")
    public void guessIsInvalid() {
        // Arrange

        // Act
        // When we create a new word feedback object (new Feedback) with a guessed "Word" and a collection of marks where at least one is invalid
        Feedback feedback = new Feedback("woord", List.of(Mark.INVALID, Mark.ABSENT, Mark.PRESENT, Mark.CORRECT, Mark.CORRECT));
        // Assert
        /* Then the result of feedback.isWordGuessed() equals true (assertTrue(feedback.isWordGuessed())) */
        assertTrue(feedback.isWordInvalid());
    }

    @Test
    @DisplayName("Word is not guessed if none of the letters are invalid, and not all of the letters are correct")
    public void guessIsNotInvalid() {
        // Arrange
        // Act
        // When we create a new word feedback object (new Feedback) with a guessed "Word" and a collection of marks where none is invalid, and not all are correct
        Feedback feedback = new Feedback("woord", List.of(Mark.ABSENT, Mark.ABSENT, Mark.PRESENT, Mark.CORRECT, Mark.CORRECT));
        // Assert
        /* Then the result of feedback.isWordGuessed() equals true (assertTrue(feedback.isWordGuessed())) */
        assertFalse(feedback.isWordInvalid());
    }

    @Test
    @DisplayName("Word length differs from marks length")
    public void guessIsOfWrongLength(){
        // When we create a new word feedback object with a word and a collection of marks from a length that's different from the word,
        // Then the feedback class should throw an extension
        Assertions.assertThrows(
                RuntimeException.class,
                () -> new Feedback("woord", List.of(Mark.CORRECT))
        );
    }

    @ParameterizedTest
    @MethodSource("provideHintExamples")
    public void returnCorrectLetters(String attempt, List<Mark> marks, List<String> previoushint){
        // Arrange
        Feedback fb = new Feedback(attempt,marks);
        // Act
        // Assert
        assertEquals(fb.giveHint(previoushint),previoushint);
    }

    public static Stream<Arguments> provideHintExamples() {
        return Stream.of(
                Arguments.of("gelukt", List.of(Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT), List.of("g","e","l","u","k","t")),
                Arguments.of("foutje", List.of(Mark.ABSENT,Mark.ABSENT,Mark.ABSENT,Mark.ABSENT,Mark.CORRECT,Mark.CORRECT), List.of("b",".",".",".","j","e")),
                Arguments.of("gelukt", List.of(Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.ABSENT,Mark.PRESENT,Mark.ABSENT), List.of("g","e","l",".",".","k"))
        );
    }

}