package commons.questions;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

/**
 * The type Comparison question.
 */
public class ComparisonQuestion extends Question {


    @JsonProperty("wrong_activity_comp1")

    private String wrongActivityComp1;
    @JsonProperty("wrong_activity_comp2")

    private String wrongActivityComp2;
    @JsonProperty("wrong_activity_cons1")

    private int wrongAcCons1;
    @JsonProperty("wrong_activity_cons2")

    private int wrongAcCons2;
    @JsonProperty("wrong_activity_image1")

    private byte[] wrongAcImage1;
    @JsonProperty("wrong_activity_image2")

    private byte[] wrongAcImage2;


    /**
     * Instantiates a new Comparison question.
     *
     * @param baseActivity            the base activity
     * @param baseActivityConsumption the base activity consumption
     * @param baseAcImage             the base ac image
     * @param wrongActivityComp1      the wrong activity comp 1
     * @param wrongActivityComp2      the wrong activity comp 2
     * @param wrongAcCons1            the wrong ac cons 1
     * @param wrongAcCons2            the wrong ac cons 2
     * @param wrongAcImage1           the wrong ac image 1
     * @param wrongAcImage2           the wrong ac image 2
     */
    public ComparisonQuestion(String baseActivity,
                              int baseActivityConsumption,
                              byte[] baseAcImage,
                              String wrongActivityComp1,
                              String wrongActivityComp2,
                              int wrongAcCons1,
                              int wrongAcCons2,
                              byte[] wrongAcImage1,
                              byte[] wrongAcImage2) {
        super(baseActivity, baseActivityConsumption, baseAcImage);
        this.wrongActivityComp1 = wrongActivityComp1;
        this.wrongActivityComp2 = wrongActivityComp2;
        this.wrongAcCons1 = wrongAcCons1;
        this.wrongAcCons2 = wrongAcCons2;
        this.wrongAcImage1 = wrongAcImage1;
        this.wrongAcImage2 = wrongAcImage2;
    }

    /**
     * Instantiates a new Comparison question.
     */
    public ComparisonQuestion() {
    }

    /**
     * Gets wrong activity comp 1.
     *
     * @return the wrong activity comp 1
     */
    public String getWrongActivityComp1() {
        return wrongActivityComp1;
    }

    /**
     * Sets wrong activity comp 1.
     *
     * @param wrongActivityComp1 the wrong activity comp 1
     */
    public void setWrongActivityComp1(String wrongActivityComp1) {
        this.wrongActivityComp1 = wrongActivityComp1;
    }

    /**
     * Gets wrong activity comp 2.
     *
     * @return the wrong activity comp 2
     */
    public String getWrongActivityComp2() {
        return wrongActivityComp2;
    }

    /**
     * Sets wrong activity comp 2.
     *
     * @param wrongActivityComp2 the wrong activity comp 2
     */
    public void setWrongActivityComp2(String wrongActivityComp2) {
        this.wrongActivityComp2 = wrongActivityComp2;
    }

    /**
     * Gets wrong ac cons 1.
     *
     * @return the wrong ac cons 1
     */
    public int getWrongAcCons1() {
        return wrongAcCons1;
    }

    /**
     * Sets wrong ac cons 1.
     *
     * @param wrongAcCons1 the wrong ac cons 1
     */
    public void setWrongAcCons1(int wrongAcCons1) {
        this.wrongAcCons1 = wrongAcCons1;
    }

    /**
     * Gets wrong ac cons 2.
     *
     * @return the wrong ac cons 2
     */
    public int getWrongAcCons2() {
        return wrongAcCons2;
    }

    /**
     * Sets wrong ac cons 2.
     *
     * @param wrongAcCons2 the wrong ac cons 2
     */
    public void setWrongAcCons2(int wrongAcCons2) {
        this.wrongAcCons2 = wrongAcCons2;
    }

    /**
     * Get wrong ac image 1 byte [ ].
     *
     * @return the byte [ ]
     */
    public byte[] getWrongAcImage1() {
        return wrongAcImage1;
    }

    /**
     * Sets wrong ac image 1.
     *
     * @param wrongAcImage1 the wrong ac image 1
     */
    public void setWrongAcImage1(byte[] wrongAcImage1) {
        this.wrongAcImage1 = wrongAcImage1;
    }

    /**
     * Get wrong ac image 2 byte [ ].
     *
     * @return the byte [ ]
     */
    public byte[] getWrongAcImage2() {
        return wrongAcImage2;
    }

    /**
     * Sets wrong ac image 2.
     *
     * @param wrongAcImage2 the wrong ac image 2
     */
    public void setWrongAcImage2(byte[] wrongAcImage2) {
        this.wrongAcImage2 = wrongAcImage2;
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

        ComparisonQuestion that = (ComparisonQuestion) o;

        if (wrongAcCons1 != that.wrongAcCons1) return false;
        if (wrongAcCons2 != that.wrongAcCons2) return false;
        if (wrongActivityComp1 != null ? !wrongActivityComp1.equals(that.wrongActivityComp1) : that.wrongActivityComp1 != null)
            return false;
        if (wrongActivityComp2 != null ? !wrongActivityComp2.equals(that.wrongActivityComp2) : that.wrongActivityComp2 != null)
            return false;
        if (!Arrays.equals(wrongAcImage1, that.wrongAcImage1)) return false;
        return Arrays.equals(wrongAcImage2, that.wrongAcImage2);
    }

}
