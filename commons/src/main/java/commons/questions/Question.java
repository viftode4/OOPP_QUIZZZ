package commons.questions;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Arrays;

/**
 * The type Question.
 */
//CHECKSTYLE:OFF
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PreciseQuestion.class, name = "preciseQuestion"),
        @JsonSubTypes.Type(value = GuessQuestion.class, name = "guessQuestion"),
        @JsonSubTypes.Type(value = ComparisonQuestion.class, name = "comparisonQuestion")
})
//CHECKSTYLE:ON
public abstract class Question {

    private String baseActivity;
    private int baseActivityConsumption;
    private byte[] baseAcImage;

    /**
     * Instantiates a new Question.
     *
     * @param baseActivity            the base activity
     * @param baseActivityConsumption the base activity consumption
     * @param baseAcImage             the base ac image
     */
    public Question(String baseActivity, int baseActivityConsumption, byte[] baseAcImage) {
        this.baseActivity = baseActivity;
        this.baseActivityConsumption = baseActivityConsumption;
        this.baseAcImage = baseAcImage;
    }

    /**
     * Instantiates a new Question.
     */
    public Question() {
    }

    /**
     * Gets base activity.
     *
     * @return the base activity
     */
    public String getBaseActivity() {
        return baseActivity;
    }

    /**
     * Sets base activity.
     *
     * @param baseActivity the base activity
     */
    public void setBaseActivity(String baseActivity) {
        this.baseActivity = baseActivity;
    }

    /**
     * Gets base activity consumption.
     *
     * @return the base activity consumption
     */
    public int getBaseActivityConsumption() {
        return baseActivityConsumption;
    }

    /**
     * Sets base activity consumption.
     *
     * @param baseActivityConsumption the base activity consumption
     */
    public void setBaseActivityConsumption(int baseActivityConsumption) {
        this.baseActivityConsumption = baseActivityConsumption;
    }

    /**
     * Get base ac image byte [ ].
     *
     * @return the byte [ ]
     */
    public byte[] getBaseAcImage() {
        return baseAcImage;
    }

    /**
     * Sets base ac image.
     *
     * @param baseAcImage the base ac image
     */
    public void setBaseAcImage(byte[] baseAcImage) {
        this.baseAcImage = baseAcImage;
    }

    /**
     * Checks if this is equal to o
     * @param o the object to compare to
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        if (baseActivityConsumption != question.baseActivityConsumption) return false;
        if (baseActivity != null ? !baseActivity.equals(question.baseActivity) : question.baseActivity != null)
            return false;
        return Arrays.equals(baseAcImage, question.baseAcImage);
    }

    /**
     *
     * @return returns the question as an activity
     */
    public String toString(){
        return baseActivity;
    }

}
