import java.awt.Image;
import oop.ex2.*;

/**
 * This class represents the Aggressive type of spaceship.
 */
public class AggressiveSpaceShip extends SpaceShip{

    /** This value is the threshold of minimal distance from another ship for this ship to shoot. */
    private final static double MIN_ANGLE_FOR_SHOOTING = 0.21;

    //------------------------------------private methods-------------------------------------------

    /** Calculate the direction needed to turn to in order to face the closest ship. */
    private int getTurnDirection(SpaceShip closestShip) {
        if (this.getPhysics().angleTo(closestShip.getPhysics()) < 0) {
            return RIGHT;
        }
        return LEFT;
    }

    /** Calculate if the ship is close enough to the closest ship in order to perform shot. */
    private boolean isShootingRequired(SpaceShip closestShip) {
        return this.getPhysics().distanceFrom(closestShip.getPhysics()) < MIN_ANGLE_FOR_SHOOTING;
    }

    //------------------------------------public methods-------------------------------------------

    /** Default constructor. */
    public AggressiveSpaceShip() {
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
        this.getPhysics().move(true, this.getTurnDirection(closestShip));
        if (isShootingRequired(closestShip)) {
            this.fire(game);
        }
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
