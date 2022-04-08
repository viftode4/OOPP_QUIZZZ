package thread;


import server.storage.GameStorage;

import static commons.GameState.*;

public class TimerThread extends Thread {

    /**
     * the boolean for the usage of the haste joker, it's false if the joker haven't used, true if it's used
     */
    public volatile boolean joker3 = false;

    /**
     * id of the game that will use the thread
     */
    public long gameId;
    //public boolean onLeaderboard = false;



    /**
     * Constructor for the class TimerThread
     * @param gameId id of the game that will use the thread
     */
    public TimerThread(long gameId) {
        this.gameId = gameId;
    }


    /**
     * Starts the thread, 13 seconds in total, 8 for in game, 5 for the leaderboard.
     * If somebody uses the haste joker, the time will be halved from the point they used the joker
     */
    public void run() {


        while (!GameStorage.getInstance().getGames().get(Math.toIntExact(gameId)).getStatus().equals(FINISHED)) {


            while (GameStorage.getInstance().getGames().get(Math.toIntExact(gameId)).getTimervalue() > 0) {
                if (joker3 && GameStorage.getInstance().getGames().get(Math.toIntExact(gameId)).getStatus().equals(STARTED)) {
                    GameStorage.getInstance().getGames().get(Math.toIntExact(gameId))
                            .setTimervalue(GameStorage.getInstance().getGames()
                                    .get(Math.toIntExact(gameId)).getTimervalue() / 2);
                    joker3 = false;

                }
                try {
                    this.sleep(100);

                } catch (Exception e) {
                    this.interrupt();

                }
                GameStorage.getInstance().getGames().get(Math.toIntExact(gameId))
                        .setTimervalue(GameStorage.getInstance().getGames().get(Math.toIntExact(gameId)).getTimervalue() - 0.1);

            }
            //at this point timer has reached 5 seconds into the leaderboard so start next question for everyone
            if(GameStorage.getInstance().getGames().get(Math.toIntExact(gameId)).getStatus().equals(STARTED)){
                //was playing now answers should show for 3 seconds
                GameStorage.getInstance().getGames().get(Math.toIntExact(gameId)).setStatus(SHOWANSWER);
                GameStorage.getInstance().getGames().get(Math.toIntExact(gameId)).setTimervalue(3);
                System.out.println("showanswer");
            }
            else if(GameStorage.getInstance().getGames().get(Math.toIntExact(gameId)).getStatus().equals(SHOWANSWER)){
                //it was showing answers now go to leaderboard
                GameStorage.getInstance().getGames().get(Math.toIntExact(gameId)).setStatus(TIMEUP);
                GameStorage.getInstance().getGames().get(Math.toIntExact(gameId)).setTimervalue(5);
                System.out.println("leader");
            }else{
                GameStorage.getInstance().getGames().get(Math.toIntExact(gameId)).setTimervalue(8.8);
                if (GameStorage.getInstance().getGames().get(Math.toIntExact(gameId)).getRoundNumber() != 19) {
                    GameStorage.getInstance().getGames().get(Math.toIntExact(gameId)).increaseRoundNumber();
                    GameStorage.getInstance().getGames().get(Math.toIntExact(gameId)).setStatus(STARTED);
                } else {
                    GameStorage.getInstance().getGames().get(Math.toIntExact(gameId)).setStatus(SHOWANSWER);
                    try {
                        this.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    GameStorage.getInstance().getGames().get(Math.toIntExact(gameId)).setStatus(FINISHED);
                }
                System.out.println("nextq/end");
            }






        }
    }
}
