package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ActivityTest {

    byte[] image;
    Activity testActivity;

    @BeforeEach
    public void setup() {
        this.image = new byte[]{0x02, 0x01, 0x55, 0x1f};
        this.testActivity = new Activity(image, "test", 75);
    }

    @Test
    public void activityBasicConstructorTest() {
        Activity basicActivity = new Activity();
        assertNotNull(basicActivity);
    }

    @Test
    public void activityConstructorTest() {
        assertNotNull(testActivity);
    }

    @Test
    public void activityGettersTest() {
        assertEquals("test", testActivity.getActivity());
        assertArrayEquals(new byte[]{0x02, 0x01, 0x55, 0x1f}, testActivity.getImage());
        assertEquals(75, testActivity.getEnergyConsumption());
    }

    @Test public void activitySettersTest() {
        testActivity.setActivity("not test");
        testActivity.setEnergyConsumption(95);
        testActivity.setImage(new byte[]{0x01, 0x00, 0x12, 0x33});
        assertEquals("not test", testActivity.getActivity());
        assertEquals(95, testActivity.getEnergyConsumption());
        assertArrayEquals(new byte[]{0x01, 0x00, 0x12, 0x33}, testActivity.getImage());
    }

    @Test
    public void activityEqualsTest() {
        byte[] image2 = new byte[]{0x02, 0x01, 0x55, 0x1f};
        Activity testActivity2 = new Activity(image2, "test", 75);
        assertEquals(testActivity2, testActivity);

        byte[] image3 = new byte[]{0x01, 0x01, 0x55, 0x1f};
        Activity testActivity3 = new Activity(image3, "test", 75);
        assertNotEquals(testActivity3, testActivity);

        Activity testActivity4 = new Activity(image2, "not test", 75);
        assertNotEquals(testActivity4, testActivity);

        Activity testActivity5 = new Activity(image2, "test", 22);
        assertNotEquals(testActivity5, testActivity);
    }

    @Test
    public void toStringTest() {
        String testString = "Activity: \n" +
                "image (represented as an array of bytes) = [2, 1, 85, 31]\n"+
                "activity name = test, energy consumption in wH = 75";
        assertEquals(testString, testActivity.toString());
    }
}
