package server.database;

import com.fasterxml.jackson.databind.ObjectMapper;
import commons.Activity;
import commons.ActivityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The type Activity load runner.
 */
@Component
public class ActivityLoadRunner implements CommandLineRunner {

    private ActivityRepository activityRepository;


    /**
     * Instantiates a new Activity load runner.
     *
     * @param activityRepository the activity repository
     */
    @Autowired
    public ActivityLoadRunner(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }


    /**
     * Instantiates a new Activity load runner.
     */
    public ActivityLoadRunner() {
    }


    /**
     * Check db is empty.
     *
     * @param toRead the to read
     * @throws IOException the io exception
     */
    public void checkDBIsEmpty(File toRead) throws IOException {

        if(activityRepository == null){
            throw new IOException("Repo is null. Can't create a DB");
        }

        if(activityRepository.findAll().isEmpty()){
            loadActivitiesToDB(getActivities(toRead));
        }

    }


    /**
     * Load activities to db.
     *
     * @param activitiesLoaded the activities loaded
     * @throws IOException the io exception
     */
    public void loadActivitiesToDB(Map<ActivityDTO, byte[]> activitiesLoaded) throws IOException {

        List<Activity> activitiesList = new ArrayList<>();

        Map<ActivityDTO, byte[]> activities = activitiesLoaded;

        for (ActivityDTO activityDTO : activities.keySet()) {

            Activity activity = new Activity(activities.get(activityDTO),
                    activityDTO.getTitle(), activityDTO.getConsumptionInWh());

            activitiesList.add(activity);

        }
        activityRepository.saveAll(activitiesList);


    }


    /**
     * Gets activities.
     *
     * @param toRead the to read
     * @return the activities
     * @throws IOException the io exception
     */
    public Map<ActivityDTO,byte[]> getActivities(File toRead) throws IOException {
        Map<ActivityDTO,byte[]> activities = new HashMap<>();


        FilenameFilter jsons = (dir, name) -> {
            String lowercaseName = name.toLowerCase();
            return lowercaseName.endsWith(".json");
        };

        FilenameFilter images = (dir, name) -> {
            String lowercaseName = name.toLowerCase();
            return !lowercaseName.endsWith(".json");
        };


        System.out.println(System.getProperty("user.dir"));
        File[] jsonFiles = toRead.listFiles(jsons);
        File[] imageFiles = toRead.listFiles(images);


        ObjectMapper mapper = new ObjectMapper();

        if(jsonFiles.length != imageFiles.length){
            try {
                throw new Exception("Not every json file has an image!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        for (int i = 0; i < jsonFiles.length; i++) {

            File currFile = jsonFiles[i];
            File imageFile = null;
            if (imageFiles != null) {
                imageFile = imageFiles[i];
            }
            ActivityDTO activity = mapper.readValue(currFile, ActivityDTO.class);
            byte[] image = Files.readAllBytes(imageFile.toPath());

            if(!activities.containsKey(activity)){
                activities.put(activity,image);
            }


        }
        return activities;

    }


    @Override
    public void run(String... args) throws Exception {
        File fileToRead = new File("server/src/main/resources/activities");
        checkDBIsEmpty(fileToRead);
    }

}
