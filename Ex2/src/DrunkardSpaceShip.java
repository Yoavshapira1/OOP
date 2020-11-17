import java.awt.Image;
import oop.ex2.*;
import java.util.Random;

/**
 * This class represents the Drunkard type of spaceship.
 * The drunkard spaceship always accelerates and turns to a random direction (but always turn).
 * Every few rounds it changes it's direction.
 * Additionally, if it gets closer then a specific threshold to another ship, it gets nervous and attempts to shoot.
 */
public class DrunkardSpaceShip extends SpaceShip {

    /** This value represents the number of rounds to pass before changing direction. */
    private int roundsForTurn;

    /** This value represents the current direction of the spaceship. */
    private int currentDirection;

    /** This value is the threshold of minimal distance from another ship for this ship to shoot. */
    private final static double MIN_DISTANCE_FOR_SHOOTING = 0.3;

    /** This value represents the number of rounds for every turn before it randomize new direction. */
    private final static int ROUNDS_AMOUNT_FOR_TURN = 100;

    /** This variable is the random numbers generator of this spaceship type. */
    private static Random randomGenerator = new Random(System.currentTimeMillis());

    /** This array contain the possible directions this spaceship randomly can move. */
    private final static int[] directions = {LEFT, RIGHT};

    //------------------------------------private methods-------------------------------------------

    /** Check if changing the direction is needed, and get a random direction between {-1,1} if so */
    private int getTurnDirection() {
        if (this.roundsForTurn == 0) {
            this.roundsForTurn = ROUNDS_AMOUNT_FOR_TURN;
            this.currentDirection = directions[randomGenerator.nextInt(directions.length)];
        }
        return this.currentDirection;
    }

    /** Calculate if the ship is close enough to the closest ship in order to perform shot. */
    private boolean isShootingRequired(SpaceShip closestShip) {
        return this.getPhysics().distanceFrom(closestShip.getPhysics()) < MIN_DISTANCE_FOR_SHOOTING;
    }

    //------------------------------------public methods-------------------------------------------

    /** Default constructor. */
    public DrunkardSpaceShip() {
        super();
        this.roundsForTurn = ROUNDS_AMOUNT_FOR_TURN;
        this.currentDirection = directions[randomGenerator.nextInt(directions.length)];
    }

    /**
     * implement the specific movement of this spaceship
     *
     * @param game the game object to which this ship belongs.
     */
    public void moveUniqueActions(SpaceWars game) {
        //do the actions by the required order
        SpaceShip closestShip = game.getClosestShipTo(this);
        this.getPhysics().move(true, this.getTurnDirection());
        if (isShootingRequired(closestShip)) {
            this.fire(game);
        }
        this.roundsForTurn = Math.max(0, this.roundsForTurn - 1);
    }


    /**
     * Gets the image of this ship. This method should return the image of the
     * ship with or without the shield. This will be displayed on the GUI at
     * the end of the round.
     *
     * @return the image of this ship.
     */
    public Image getImage() {
        return GameGUI.ENEMY_SPACESHIP_IMAGE;
    }
}
