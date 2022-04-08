package commons.questions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {


    private Question question;

    @BeforeEach
    void init(){
        byte[] data = new byte[20];
        new Random().nextBytes(data);
        this.question = new GuessQuestion("test",100,data);
    }

    @Test
    void getBaseActivity() {

    }

    @Test
    void setBaseActivity() {
        question.setBaseActivity("kur");
        Assertions.assertEquals("kur",question.getBaseActivity());
    }

    @Test
    void getBaseActivityConsumption() {
        Assertions.assertEquals(100,question.getBaseActivityConsumption());
    }

    @Test
    void setBaseActivityConsumption() {
        question.setBaseActivityConsumption(110);
        Assertions.assertEquals(110,question.getBaseActivityConsumption());
    }

    @Test
    void getBaseAcImage() {
        Assertions.assertNotNull(question.getBaseAcImage());
    }

    @Test
    void setBaseAcImage() {
        byte[] data = new byte[20];
        new Random().nextBytes(data);
        question.setBaseAcImage(data);
        Assertions.assertNotNull(question.getBaseAcImage());
    }

    @Test
    void testEquals() {
        byte[] data = new byte[20];
        new Random().nextBytes(data);

        Question q = new GuessQuestion("test",100,data);

        Assertions.assertEquals(q.getBaseActivity(),question.getBaseActivity());
        Assertions.assertEquals(q.getBaseActivityConsumption(),question.getBaseActivityConsumption());

    }
}