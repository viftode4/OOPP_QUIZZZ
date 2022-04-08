package commons.questions;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;


class PreciseQuestionTest {

    private PreciseQuestion preciseQuestion;

    @BeforeEach
    void init() {
        byte[] data = new byte[20];
        new Random().nextBytes(data);

        this.preciseQuestion = new PreciseQuestion("test",100,data,80,110);
    }

    @Test
    void getWrongAnswer1() {
        Assertions.assertEquals(80,preciseQuestion.getWrongAnswer1());
    }

    @Test
    void setWrongAnswer1() {
        preciseQuestion.setWrongAnswer1(75);
        Assertions.assertEquals(75,preciseQuestion.getWrongAnswer1());
    }

    @Test
    void getWrongAnswer2() {
        Assertions.assertEquals(110,preciseQuestion.getWrongAnswer2());
    }

    @Test
    void setWrongAnswer2() {
        preciseQuestion.setWrongAnswer2(200);
        Assertions.assertEquals(200,preciseQuestion.getWrongAnswer2());
    }

    @Test
    void generateWrongAnswer() {
        int i = preciseQuestion.generateWrongAnswer(100);
        Assertions.assertTrue(Math.abs(100-i) < 60);
    }

    @Test
    void testEquals() {

        byte[] data = new byte[20];
        new Random().nextBytes(data);

        PreciseQuestion prec = new PreciseQuestion("test",100,data,80,110);

        Assertions.assertEquals(preciseQuestion.getWrongAnswer1(),prec.getWrongAnswer1());
        Assertions.assertEquals(preciseQuestion.getWrongAnswer2(),prec.getWrongAnswer2());
        Assertions.assertEquals(preciseQuestion.getBaseActivity(),prec.getBaseActivity());
        Assertions.assertEquals(preciseQuestion.getBaseActivityConsumption(),prec.getBaseActivityConsumption());
    }
}