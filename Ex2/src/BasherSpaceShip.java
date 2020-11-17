import java.awt.Image;
import oop.ex2.*;

/**
 * This class represents the Basher type of spaceship.
 */
public class BasherSpaceShip extends SpaceShip{

    /** This value is the threshold of minimal distance from another ship for this ship to wear a shield. */
    private final static double MIN_DISTANCE_FOR_SHIELD = 0.19;

    //------------------------------------private methods-------------------------------------------

    /** Calculate the direction needed to turn to in order to face the closest ship. */
    private int getTurnDirection(SpaceShip closestShip) {
        if (this.getPhysics().angleTo(closestShip.getPhysics()) < 0) {
            return RIGHT;
        }
        if (this.getPhysics().angleTo(closestShip.getPhysics()) > 0) {
            return LEFT;
        }
        return 0;
    }

    //------------------------------------public methods-------------------------------------------

    /** Default constructor. */
    public BasherSpaceShip() {
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
        if (this.getPhysics().distanceFrom(closestShip.getPhysics()) <= MIN_DISTANCE_FOR_SHIELD) {
            this.shieldOn();
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
        if (this.isShieldUp) {
            return GameGUI.ENEMY_SPACESHIP_IMAGE_SHIELD;
        }
        return GameGUI.ENEMY_SPACESHIP_IMAGE;
    }

}
