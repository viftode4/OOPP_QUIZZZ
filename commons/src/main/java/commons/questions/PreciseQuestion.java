package commons.questions;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.concurrent.ThreadLocalRandom;


/**
 * The type Precise question.
 */
public class PreciseQuestion extends Question {

    @JsonProperty("wrong_answer_1")
    private int wrongAnswer1;
    @JsonProperty("wrong_answer_2")
    private int wrongAnswer2;


    /**
     * Instantiates a new Precise question.
     *
     * @param baseActivity            the base activity
     * @param baseActivityConsumption the base activity consumption
     * @param baseAcImage             the base ac image
     * @param wrongAnswer1            the wrong answer 1
     * @param wrongAnswer2            the wrong answer 2
     */
    public PreciseQuestion(String baseActivity, int baseActivityConsumption,
                           byte[] baseAcImage, int wrongAnswer1, int wrongAnswer2) {
        super(baseActivity, baseActivityConsumption, baseAcImage);
        this.wrongAnswer1 = wrongAnswer1;
        this.wrongAnswer2 = wrongAnswer2;
    }

    /**
     * Instantiates a new Precise question.
     *
     * @param baseActivity            the base activity
     * @param baseActivityConsumption the base activity consumption
     * @param baseAcImage             the base ac image
     */
    public PreciseQuestion(String baseActivity, int baseActivityConsumption,
                           byte[] baseAcImage) {
        super(baseActivity, baseActivityConsumption, baseAcImage);
        this.wrongAnswer1 = generateWrongAnswer(baseActivityConsumption);
        this.wrongAnswer2 = generateWrongAnswer(baseActivityConsumption);

        while(this.wrongAnswer1 == baseActivityConsumption)
            this.wrongAnswer1 = generateWrongAnswer(baseActivityConsumption);

        while(this.wrongAnswer1 == this.wrongAnswer2 || this.wrongAnswer2 == baseActivityConsumption)
            this.wrongAnswer2 = generateWrongAnswer(baseActivityConsumption);
    }

    /**
     * Instantiates a new Precise question.
     */
    public PreciseQuestion() {
    }

    /**
     * Gets wrong answer 1.
     *
     * @return the wrong answer 1
     */
    public int getWrongAnswer1() {
        return wrongAnswer1;
    }

    /**
     * Sets wrong answer 1.
     *
     * @param wrongAnswer1 the wrong answer 1
     */
    public void setWrongAnswer1(int wrongAnswer1) {
        this.wrongAnswer1 = wrongAnswer1;
    }

    /**
     * Gets wrong answer 2.
     *
     * @return the wrong answer 2
     */
    public int getWrongAnswer2() {
        return wrongAnswer2;
    }

    /**
     * Sets wrong answer 2.
     *
     * @param wrongAnswer2 the wrong answer 2
     */
    public void setWrongAnswer2(int wrongAnswer2) {
        this.wrongAnswer2 = wrongAnswer2;
    }

    /**
     * The function generates wrong answers for the questions using the correct answer as a base
     * It goes in the interval (ans/2, ans+ans/2)
     * Also if the number is not a multiple of 10 it adds a digit randomly for the correct answer to not be easily recognizable
     *
     * @param base the base the wrong answer should be around of
     * @return the wrong answer
     */
    public int generateWrongAnswer(int base){
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        return (int) ( Math.round((threadLocalRandom.nextInt(base) + base / 2) / 10.0) * 10 + ( base % 10 != 0 ? threadLocalRandom.nextInt(11) : 0 ) );
    }

    /**
     * Checks if this is equal to the Object o
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PreciseQuestion that = (PreciseQuestion) o;

        if (wrongAnswer1 != that.wrongAnswer1) return false;
        return wrongAnswer2 == that.wrongAnswer2;
    }
}
