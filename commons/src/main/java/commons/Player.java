package commons;

import javax.persistence.*;
import java.util.Objects;

/**
 * The type Player.
 */
@Entity
@Table(name = "player")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "score")
    private int score;

    //Total number of emojis used
    @Transient
    private int emojisUsed;
    @Transient
    private int correctQNo;
    @Transient
    private boolean joker1;
    @Transient
    private boolean joker2;
    @Transient
    private boolean joker3;
    @Transient
    private int gameid;
    @Transient
    private boolean flagAns;

    /**
     * Gets game.
     *
     * @return the game
     */
    public int getGame() {
        return gameid;
    }

    /**
     * Sets game.
     *
     * @param gameid the gameid
     */
    public void setGame(int gameid) {
        this.gameid = gameid;
    }

    /**
     * Instantiates a new Player.
     *
     * @param id       the id
     * @param username the username
     */
    public Player(Long id, String username) {
        this.id = id;
        this.username = username;
        this.joker1 = true;
        this.joker2 = true;
        this.joker3 = true;
    }

    /**
     * Instantiates a new Player.
     */
    public Player(){
        this.joker1 = true;
        this.joker2 = true;
        this.joker3 = true;
    }

    /**
     * Sets the ansFlag which tells if the player answered or not
     * @param flagAns the flag
     */
    public void setFlagAns(boolean flagAns) {
        this.flagAns = flagAns;
    }

    /**
     * Checks if the player has answered
     * @return the flag
     */
    public boolean hasAnswered() {
        return this.flagAns;
    }
    /**
     * Update score.
     *
     * @param amount the amount
     */
    public void updateScore(int amount){
        this.score+=amount;
    }
    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets score.
     *
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets score.
     *
     * @param score the score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Increases the score by an amount
     * @param amount the amount
     */
    public void increaseScore(int amount){
        this.score += amount;
    }

    /**
     * Increments the number of correct questions
     */
    public void incCorrectQNo(){
        this.correctQNo++;
    }

    /**
     * Get correct q no int.
     *
     * @return the int
     */
    public int getCorrectQNo(){
        return this.correctQNo;
    }

    /**
     * Gets emojis used.
     *
     * @return the emojis used
     */
    public int getEmojisUsed() {
        return emojisUsed;
    }

    /**
     * Sets emojis used.
     *
     * @param emojisUsed the emojis used
     */
    public void setEmojisUsed(int emojisUsed) {
        this.emojisUsed = emojisUsed;
    }

    /**
     * Is joker 1 boolean.
     *
     * @return the boolean
     */
    public boolean isJoker1() {
        return joker1;
    }

    /**
     * Sets joker 1.
     *
     * @param joker1 the joker 1
     */
    public void setJoker1(boolean joker1) {
        this.joker1 = joker1;
    }

    /**
     * Is joker 2 boolean.
     *
     * @return the boolean
     */
    public boolean isJoker2() {
        return joker2;
    }

    /**
     * Sets joker 2.
     *
     * @param joker2 the joker 2
     */
    public void setJoker2(boolean joker2) {
        this.joker2 = joker2;
    }

    /**
     * Is joker 3 boolean.
     *
     * @return the boolean
     */
    public boolean isJoker3() {
        return joker3;
    }

    /**
     * Sets joker 3.
     *
     * @param joker3 the joker 3
     */
    public void setJoker3(boolean joker3) {
        this.joker3 = joker3;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Sets correct q no.
     *
     * @param correctQNo the correct q no
     */
    public void setCorrectQNo(int correctQNo) {
        this.correctQNo = correctQNo;
    }

    /**
     * Sets gameid.
     *
     * @param gameid the gameid
     */
    public void setGameid(int gameid) {
        this.gameid = gameid;
    }

    /**
     * Compares two Players to check if they are equal (username)
     * @param o the other object/Player to compare to
     * @return whether they are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        return username.equals(player.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, score, emojisUsed, correctQNo, joker1, joker2, joker3, gameid);
    }

    /**
     * @return A String representation of a Player
     */
    @Override
    public String toString() {
        return "User id: " + id +
                ", Username: '" + username + '\'' +
                ", Score: " + score +
                ", Emojis used: " + emojisUsed +
                ", Correct answers: " + correctQNo +
                ", joker1 used: " + joker1 +
                ", joker2 used: " + joker2 +
                ", joker3 used: " + joker3 +
                ", game id: " + gameid;
    }
}