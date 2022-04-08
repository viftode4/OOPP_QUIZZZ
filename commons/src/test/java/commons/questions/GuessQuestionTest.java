package commons.questions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;


class GuessQuestionTest {

    private GuessQuestion guessQuestion;

    @BeforeEach
    void init(){
        byte[] data = new byte[20];
        new Random().nextBytes(data);
        this.guessQuestion = new GuessQuestion("test",100,data);
    }

    @Test
    void getCorrectAnswerLowerLim() {
        Assertions.assertEquals(80,this.guessQuestion.getCorrectAnswerLowerLim());
    }

    @Test
    void setCorrectAnswerLowerLim() {
        this.guessQuestion.setCorrectAnswerLowerLim(90);
        Assertions.assertEquals(90,this.guessQuestion.getCorrectAnswerLowerLim());
    }

    @Test
    void getCorrectAnswerUpperLim() {
        Assertions.assertEquals(120,this.guessQuestion.getCorrectAnswerUpperLim());
    }

    @Test
    void setCorrectAnswerUpperLim() {
        this.guessQuestion.setCorrectAnswerUpperLim(700);
        Assertions.assertEquals(700,this.guessQuestion.getCorrectAnswerUpperLim());
    }

    @Test
    void testEquals() {
        byte[] data = new byte[20];
        new Random().nextBytes(data);

        GuessQuestion prec = new GuessQuestion("test",100,data);

        Assertions.assertEquals(guessQuestion.getCorrectAnswerLowerLim(),prec.getCorrectAnswerLowerLim());
        Assertions.assertEquals(guessQuestion.getCorrectAnswerUpperLim(),prec.getCorrectAnswerUpperLim());
        Assertions.assertEquals(guessQuestion.getBaseActivity(),prec.getBaseActivity());
        Assertions.assertEquals(guessQuestion.getBaseActivityConsumption(),prec.getBaseActivityConsumption());
    }
}