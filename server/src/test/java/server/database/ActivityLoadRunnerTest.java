package server.database;


import commons.ActivityDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


class ActivityLoadRunnerTest {

    private ActivityLoadRunner activityLoadRunner;
    private ActivityLoadRunnerRepoMock repoMock;

    @BeforeEach
    public void init() {
        this.repoMock = new ActivityLoadRunnerRepoMock();
        this.activityLoadRunner = new ActivityLoadRunner(repoMock);
        System.out.println(System.getProperty("user.dir"));
    }

//    @Test
//    void checkDBIsEmpty() {
//        try {
//            activityLoadRunner.checkDBIsEmpty(new File("C:\\OOPP_Project\\repository-template\\server\\src\\test\\java\\server\\database\\testActivities"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Assertions.assertNotNull(repoMock.findAll());
//        Assertions.assertTrue(repoMock.findAll().size() > 1);
//
//
//    }

    @Test
    void loadActivitiesToDB() {

        Map<ActivityDTO,byte[]> testMap = new HashMap<>();
        testMap.put(new ActivityDTO("hj",23,"url"),new byte[]{0x02, 0x01, 0x55, 0x1f});
        testMap.put(new ActivityDTO("h3",23,"url"),new byte[]{0x02, 0x01, 0x55, 0x1f});
        testMap.put(new ActivityDTO("hy",23,"url"),new byte[]{0x02, 0x01, 0x55, 0x1f});

        try {
            activityLoadRunner.loadActivitiesToDB(testMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assertions.assertNotNull(repoMock.findAll());
        Assertions.assertTrue(repoMock.findAll().size() > 1);

    }

//    @Test
//    void getActivities() throws IOException {
//        Map<ActivityDTO, byte[]> activities = null;
//
//        activities = activityLoadRunner.getActivities(new File("C:\\OOPP_Project\\repository-template\\server\\src\\test\\java\\server\\database\\testActivities"));
//
//        Assertions.assertNotNull(activities);
//        Assertions.assertTrue(activities.size() > 1);
//
//    }
}