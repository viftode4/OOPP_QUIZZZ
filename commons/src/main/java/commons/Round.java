package commons;

import commons.questions.Question;

/**
 * The type Round.
 */
public class Round {

    private int roundNum;
    private Question question;

    //it will be in seconds, so we can store it as an int
    //also we can create a time class for this
    private int time;
    private Game game;

    /**
     * Instantiates a new Round.
     *
     * @param roundNum the round num
     * @param question the question
     */
    public Round(int roundNum, Question question) {
        this.roundNum = roundNum;
        this.question = question;
        // time for the round
        this.time = 8;
    }

    /**
     * Instantiates a new Round.
     */
    public Round(){}

    /**
     * Add time.
     *
     * @param player the player
     */
    public void addTime(Player player){
        //to be implemented
    }

    /**
     * Gets game.
     *
     * @return the game
     */
    public Game getGame() {
        return game;
    }

    /**
     * Sets game.
     *
     * @param game the game
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Gets round num.
     *
     * @return the round num
     */
    public int getRoundNum() {
        return roundNum;
    }

    /**
     * Gets question.
     *
     * @return the question
     */
    public Question getQuestion() {
        return question;
    }

    /**
     * Sets question.
     *
     * @param question the question
     */
    public void setQuestion(Question question) {
        this.question = question;
    }

    /**
     * Gets time.
     *
     * @return the time
     */
    public int getTime() {
        return time;
    }

    /**
     * Sets time.
     *
     * @param time the time
     */
    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Round{" +
                "roundNum=" + roundNum +
                ", question=" + question.toString() +
                ", time=" + time +
                ", game=" + game +
                '}';
    }
}
