package commons.questions;


import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The type Guess question.
 */
public class GuessQuestion extends Question {

    @JsonProperty("correct_ans_lower_lim")
    private int correctAnswerLowerLim;
    @JsonProperty("correct_ans_upper_lim")
    private int correctAnswerUpperLim;

    /**
     * Instantiates a new Guess question.
     *
     * @param baseActivity            the base activity
     * @param baseActivityConsumption the base activity consumption
     * @param baseAcImage             the base ac image
     */
    public GuessQuestion(String baseActivity, int baseActivityConsumption, byte[] baseAcImage) {
        super(baseActivity, baseActivityConsumption, baseAcImage);
        this.correctAnswerLowerLim = (int) Math.abs(getBaseActivityConsumption() - (getBaseActivityConsumption()*0.2));
        this.correctAnswerUpperLim = (int) Math.abs(getBaseActivityConsumption() + (getBaseActivityConsumption()*0.2));
    }

    /**
     * Instantiates a new Guess question.
     */
    public GuessQuestion() {
    }

    /**
     * Gets correct answer lower lim.
     *
     * @return the correct answer lower lim
     */
    public int getCorrectAnswerLowerLim() {
        return correctAnswerLowerLim;
    }

    /**
     * Sets correct answer lower lim.
     *
     * @param correctAnswerLowerLim the correct answer lower lim
     */
    public void setCorrectAnswerLowerLim(int correctAnswerLowerLim) {
        this.correctAnswerLowerLim = correctAnswerLowerLim;
    }

    /**
     * Gets correct answer upper lim.
     *
     * @return the correct answer upper lim
     */
    public int getCorrectAnswerUpperLim() {
        return correctAnswerUpperLim;
    }

    /**
     * Sets correct answer upper lim.
     *
     * @param correctAnswerUpperLim the correct answer upper lim
     */
    public void setCorrectAnswerUpperLim(int correctAnswerUpperLim) {
        this.correctAnswerUpperLim = correctAnswerUpperLim;
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

        GuessQuestion that = (GuessQuestion) o;

        if (correctAnswerLowerLim != that.correctAnswerLowerLim) return false;
        return correctAnswerUpperLim == that.correctAnswerUpperLim;
    }
}
