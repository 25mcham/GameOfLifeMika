import java.util.Random;
public enum CellState { // these are the only states,
    ALIVE, //so it will only ever accept these to prevent run-time error, type safe way to declare
    DEAD,
    WILL_DIE,
    WILL_REVIVE;
}
