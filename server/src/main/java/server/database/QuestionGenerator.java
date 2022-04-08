package server.database;


import commons.Activity;
import commons.questions.ComparisonQuestion;
import commons.questions.GuessQuestion;
import commons.questions.PreciseQuestion;
import commons.questions.Question;


import java.util.*;
import java.util.stream.Collectors;


public class QuestionGenerator {

    private List<Activity> activities;

    public QuestionGenerator(List<Activity> activities) {
        this.activities = activities;
    }

    /**
     * The method generates one question of type "Precise question"
     *
     * @return Question
     */
    public Question generatePreciseQuestion(){

        if(activities.size() == 0){
            throw new RuntimeException("No activities in repo. Cannot create questions.");
        }

        int index = (int)(Math.random() * activities.size());

        Activity activity = activities.get(index);

        Question preciseQuestion = new PreciseQuestion(activity.getActivity(),
                activity.getEnergyConsumption(), activity.getImage());

        return preciseQuestion;

    }

    /**
     * The method generates one question of type "Comparison question"
     *
     * @return Question
     */
    public Question generateComparisonQuestion(){

        List<Activity> compActivities = new ArrayList<>();

        if(activities.size() == 0){
            throw new RuntimeException("No activities in repo. Cannot create questions.");
        }


        for (int i = 0; i < 25; i++) {
            int index = (int)(Math.random() * activities.size());
            Activity activity = activities.get(index);

            if(compActivities.stream().noneMatch(z -> z.getEnergyConsumption() == activity.getEnergyConsumption())
                && compActivities.size() < 3)
                compActivities.add(activity);
        }

        if(activities.size() < 3){
            throw new RuntimeException("Generated less than three activities.");
        }

        List<Activity> collect = compActivities.stream()
                .sorted(Comparator.comparingInt(Activity::getEnergyConsumption)
                .reversed())
                .collect(Collectors.toList());

        Activity correctAnswActivity = collect.get(0);
        Activity wrong1 = collect.get(1);
        Activity wrong2 = collect.get(2);

        Question compQuestion = new ComparisonQuestion(correctAnswActivity.getActivity(),
                correctAnswActivity.getEnergyConsumption(),
                correctAnswActivity.getImage(),
                wrong1.getActivity(),
                wrong2.getActivity(),
                wrong1.getEnergyConsumption(),
                wrong2.getEnergyConsumption(),
                wrong1.getImage(),
                wrong2.getImage());

        return compQuestion;
    }


    /**
     * The method generates one question of type "Guessing question"
     *
     * @return Question
     */
    public Question generateGuessQuestion(){

        if(activities.size() == 0){
            throw new RuntimeException("No activities in repo. Cannot create questions.");
        }

        int index = (int)(Math.random() * activities.size());

        Activity activity = activities.get(index);

        Question guessQuestion = new GuessQuestion(activity.getActivity(),
                activity.getEnergyConsumption(),
                activity.getImage());

        return guessQuestion;
    }

    /**
     * The method generates sets of questions enough for one game session of the quiz application
     *
     * @return List of questions
     */
    public List<Question> generate20Questions(){
        List<Question> questions = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            Question x = generateComparisonQuestion();

            while(questions.contains(x))
                x = generateComparisonQuestion();

            questions.add(x);
        }
        for (int i = 0; i < 7; i++) {
            Question x = generatePreciseQuestion();

            while(questions.contains(x))
                x = generatePreciseQuestion();

            questions.add(x);
        }
        for (int i = 0; i < 6; i++) {
            Question x = generateGuessQuestion();

            while(questions.contains(x))
                x = generateGuessQuestion();

            questions.add(x);
        }

        Collections.shuffle(questions);
        return questions;

    }




}
