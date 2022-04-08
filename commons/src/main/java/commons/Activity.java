package commons;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;


/**
 * The type Activity.
 */
@Entity
@Table(name = "activity")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "image")
    @Lob
    private byte[] image;

    @Column(name = "activity")
    private String activity;

    @Column(name = "energy_consumption")
    private int energyConsumption;

    /**
     * Creates a new Activity
     *
     * @param image             the image associated with the activity
     * @param activity          the name of the activity
     * @param energyConsumption the amount of energy the activity uses in wH
     */
    public Activity(byte[] image, String activity, int energyConsumption) {
        this.image = image;
        this.activity = activity;
        this.energyConsumption = energyConsumption;

    }

    /**
     * Default constructor for Activity
     */
    public Activity() {
    }

    /**
     * Getters and setters for Activity
     *
     * @return whatever is asked if getter
     */
    public Long getId() {
        return id;
    }

    /**
     * Gets activity.
     *
     * @return the activity
     */
    public String getActivity() {
        return activity;
    }

    /**
     * Sets activity.
     *
     * @param activity the activity
     */
    public void setActivity(String activity) {
        this.activity = activity;
    }

    /**
     * Gets energy consumption.
     *
     * @return the energy consumption
     */
    public int getEnergyConsumption() {
        return energyConsumption;
    }

    /**
     * Sets energy consumption.
     *
     * @param energyConsumption the energy consumption
     */
    public void setEnergyConsumption(int energyConsumption) {
        this.energyConsumption = energyConsumption;
    }

    /**
     * Get image byte [ ].
     *
     * @return the byte [ ]
     */
    public byte[] getImage() {
        return image;
    }

    /**
     * Sets image.
     *
     * @param image the image
     */
    public void setImage(byte[] image) {
        this.image = image;
    }

    /**
     * Compares two Activities to check whether they are equal
     * @param o the Activity to compare with
     * @return whether they are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity1 = (Activity) o;
        return energyConsumption == activity1.energyConsumption && Objects.equals(id, activity1.id)
                && Arrays.equals(image, activity1.image) && Objects.equals(activity, activity1.activity);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, activity, energyConsumption);
        result = 31 * result + Arrays.hashCode(image);
        return result;
    }

    /**
     * @return a string representation of an Activity
     */
    @Override
    public String toString() {
        return "Activity: \n" +
                "image (represented as an array of bytes) = " + Arrays.toString(image) +
                "\nactivity name = " + activity +
                ", energy consumption in wH = " + energyConsumption;
    }
}

