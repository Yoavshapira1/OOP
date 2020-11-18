import oop.ex2.*;

/**
 * creates the array contains the spaceship that user typed
 */
public class SpaceShipFactory {

    /** private members indicates the possible types of spaceship. */
    private static final String Human = "h";
    private static final String Aggressive = "a";
    private static final String Runner = "r";
    private static final String Basher = "b";
    private static final String Drankard = "d";
    private static final String Special = "s";

    /**
     * creat the array
     * @param args arguments from user
     * @return an array of the spaceships
     */
    public static SpaceShip[] createSpaceShips(String[] args) {
        SpaceShip arr[] = new SpaceShip[args.length];
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case Human:
                    arr[i] = new HumanSpaceShip();
                    break;
                case Runner:
                    arr[i] = new RunnerSpaceShip();
                    break;
                case Basher:
                    arr[i] = new BasherSpaceShip();
                    break;
                case Aggressive:
                    arr[i] = new AggressiveSpaceShip();
                    break;
                case Drankard:
                    arr[i] = new DrunkardSpaceShip();
                    break;
                case Special:
                    arr[i] = new SpecialSpaceShip();
                    break;
                default:
                    break;
            }
        }
        return arr;
    }
}
