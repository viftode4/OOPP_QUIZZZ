package commons.questions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ComparisonQuestionTest {

    private ComparisonQuestion comparisonQuestion;


    @BeforeEach
    void init(){
        byte[] data = new byte[20];
        new Random().nextBytes(data);

        byte[] data1 = new byte[20];
        new Random().nextBytes(data);

        byte[] data2 = new byte[20];
        new Random().nextBytes(data);


        this.comparisonQuestion = new ComparisonQuestion("test",100,data,"rest",
                "nest",15,16,data1,data2);
    }

    @Test
    void getWrongActivityComp1() {
        Assertions.assertEquals("rest",comparisonQuestion.getWrongActivityComp1());
    }

    @Test
    void setWrongActivityComp1() {
        comparisonQuestion.setWrongActivityComp1("mest");
        Assertions.assertEquals("mest",comparisonQuestion.getWrongActivityComp1());
    }

    @Test
    void getWrongActivityComp2() {
        Assertions.assertEquals("nest",comparisonQuestion.getWrongActivityComp2());
    }

    @Test
    void setWrongActivityComp2() {
        comparisonQuestion.setWrongActivityComp2("mest");
        Assertions.assertEquals("mest",comparisonQuestion.getWrongActivityComp2());
    }

    @Test
    void getWrongAcCons1() {
        Assertions.assertEquals(15,comparisonQuestion.getWrongAcCons1());
    }

    @Test
    void setWrongAcCons1() {
        comparisonQuestion.setWrongAcCons1(45);
        Assertions.assertEquals(45,comparisonQuestion.getWrongAcCons1());
    }

    @Test
    void getWrongAcCons2() {
        Assertions.assertEquals(16,comparisonQuestion.getWrongAcCons2());
    }

    @Test
    void setWrongAcCons2() {
        comparisonQuestion.setWrongAcCons2(45);
        Assertions.assertEquals(45,comparisonQuestion.getWrongAcCons2());
    }

    @Test
    void getWrongAcImage1() {
        assertEquals("byte[]", comparisonQuestion.getWrongAcImage1().getClass().getSimpleName());
    }

    @Test
    void setWrongAcImage1() {

        byte[] temp = new byte[20];
        new Random().nextBytes(temp);

        comparisonQuestion.setWrongAcImage1(temp);

        assertEquals("byte[]", comparisonQuestion.getWrongAcImage1().getClass().getSimpleName());
    }

    @Test
    void getWrongAcImage2() {
        assertEquals("byte[]", comparisonQuestion.getWrongAcImage2().getClass().getSimpleName());
    }

    @Test
    void setWrongAcImage2() {
        byte[] temp = new byte[20];
        new Random().nextBytes(temp);

        comparisonQuestion.setWrongAcImage2(temp);

        assertEquals("byte[]", comparisonQuestion.getWrongAcImage2().getClass().getSimpleName());
    }

    @Test
    void testEquals() {

        byte[] data = new byte[20];
        new Random().nextBytes(data);

        byte[] data1 = new byte[20];
        new Random().nextBytes(data);

        byte[] data2 = new byte[20];
        new Random().nextBytes(data);

        ComparisonQuestion cquestion = new ComparisonQuestion("test",100,data,"rest",
                "nest",15,16,data1,data2);

        Assertions.assertEquals(cquestion.getWrongAcCons1(),comparisonQuestion.getWrongAcCons1());
        Assertions.assertEquals(cquestion.getWrongAcCons2(),comparisonQuestion.getWrongAcCons2());
        Assertions.assertEquals(cquestion.getWrongActivityComp1(),comparisonQuestion.getWrongActivityComp1());
        Assertions.assertEquals(cquestion.getWrongActivityComp2(),comparisonQuestion.getWrongActivityComp2());

    }
}