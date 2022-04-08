package commons;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The type Activity dto.
 */
public class ActivityDTO {


    private String title;
    @JsonProperty("consumption_in_wh")
    private int consumptionInWh;
    private String source;

    /**
     * Instantiates a new Activity dto.
     *
     * @param title           the title
     * @param consumptionInWh the consumption in wh
     * @param source          the source
     */
    public ActivityDTO(String title, int consumptionInWh, String source) {
        this.title = title;
        this.consumptionInWh = consumptionInWh;
        this.source = source;
    }

    /**
     * Instantiates a new Activity dto.
     */
    public ActivityDTO() {
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets consumption in wh.
     *
     * @return the consumption in wh
     */
    public int getConsumptionInWh() {
        return consumptionInWh;
    }


    /**
     * Sets consumption in wh.
     *
     * @param consumptionInWh the consumption in wh
     */
    public void setConsumptionInWh(int consumptionInWh) {
        this.consumptionInWh = consumptionInWh;
    }

    /**
     * Gets source.
     *
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * Sets source.
     *
     * @param source the source
     */
    public void setSource(String source) {
        this.source = source;
    }

}
