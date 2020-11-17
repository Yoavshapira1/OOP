import java.awt.Image;
import oop.ex2.*;

/**
 * This class represents the Runner type of spaceship.
 */
public class RunnerSpaceShip extends SpaceShip {

    /** This value is the threshold of minimal distance from another ship for this ship to attempt teleporting. */
    private final static double MIN_DISTANCE_FOR_TELEPORTING = 0.25;

    /** This value is the threshold of minimal angle from another ship for this ship to attempt teleporting. */
    private final static double MIN_ANGLE_FOR_TELEPORTING = 0.23;

    //------------------------------------private methods-------------------------------------------

    /** Calculate the direction needed to turn to in order to aun away from the closest ship. */
    private int getTurnDirection(SpaceShip closestShip) {
        if (this.getPhysics().angleTo(closestShip.getPhysics()) < 0) {
            return LEFT;
        }
        return RIGHT;
    }

    /** Check if the closest ship is too close, hence teleporting is needed to be done. */
    private boolean isTeleportRequired(SpaceShip closestShip) {
        return Math.abs(closestShip.getPhysics().angleTo(this.getPhysics())) < MIN_ANGLE_FOR_TELEPORTING &&
                Math.abs(closestShip.getPhysics().distanceFrom(this.getPhysics())) < MIN_DISTANCE_FOR_TELEPORTING;
    }

    //------------------------------------public methods-------------------------------------------

    /** Default constructor. */
    public RunnerSpaceShip() {
        super();
    }

    /**
     * implement the specific movement of this spaceship
     *
     * @param game the game object to which this ship belongs.
     */
    public void moveUniqueActions(SpaceWars game) {
        //do the actions by the required order
        SpaceShip closestShip = game.getClosestShipTo(this);
        if (isTeleportRequired(closestShip)) {
            this.teleport();
        }

        this.getPhysics().move(true, this.getTurnDirection(closestShip));
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
