import oop.ex2.*;

/**
 * creates the array contains the spaceship that user typed
 */
public class SpaceShipFactory {

    /**
     * creat the array
     * @param args arguments from user
     * @return an array of the spaceships
     */
    public static SpaceShip[] createSpaceShips(String[] args) {
        SpaceShip arr[] = new SpaceShip[args.length];
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "h":
                    arr[i] = new HumanSpaceShip();
                    break;
                case "r":
                    arr[i] = new RunnerSpaceShip();
                    break;
                case "b":
                    arr[i] = new BasherSpaceShip();
                    break;
                case "a":
                    arr[i] = new AggressiveSpaceShip();
                    break;
                case "d":
                    arr[i] = new DrunkardSpaceShip();
                    break;
                case "s":
                    arr[i] = new SpecialSpaceShip();
                    break;
                default:
                    break;
            }
        }
        return arr;
    }
}
