package commons;

public enum GameState {
    NEW,
    STARTED,
    FINISHED,
    FULL,
    INVALID,    //invalid username
    NOID,       // invalid gameID
    NOENTER,     //game started or is full or finished
    TIMEUP,
    SHOWANSWER

}
