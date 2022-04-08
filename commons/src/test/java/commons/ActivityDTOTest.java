package commons;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class ActivityDTOTest {

    ActivityDTO activityDTO;

    @BeforeEach
    public void setup() {
        this.activityDTO = new ActivityDTO("test",50,"dir.bg");
    }

    @Test
    void getTitle() {
        Assertions.assertEquals(activityDTO.getTitle(),"test");
    }

    @Test
    void setTitle() {
        activityDTO.setTitle("samoLevskiITesno");
        Assertions.assertEquals(activityDTO.getTitle(),"samoLevskiITesno");
    }

    @Test
    void getConsumptionInWh() {
        Assertions.assertEquals(activityDTO.getConsumptionInWh(),50);
    }

    @Test
    void setConsumptionInWh() {
        activityDTO.setConsumptionInWh(12);
        Assertions.assertEquals(activityDTO.getConsumptionInWh(),12);
    }

    @Test
    void getSource() {
        Assertions.assertEquals(activityDTO.getSource(),"dir.bg");
    }

    @Test
    void setSource() {
        activityDTO.setSource("dnes.bg");
        Assertions.assertEquals(activityDTO.getSource(),"dnes.bg");
    }
}