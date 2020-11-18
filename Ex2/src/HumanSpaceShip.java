import java.awt.Image;
import oop.ex2.*;

/**
 * This class represents the Human controlled type of spaceship.
 */
public class HumanSpaceShip extends SpaceShip {

    /** This is the name of the class of the special spaceship.
     * The user-controlled needs to know it in order to check for collision. */
    private static final String NAME_OF_SPECIAL = "SpecialSpaceShip";

    //------------------------------------private methods-------------------------------------------

    /** Check for collision with the closest ship. If this spaceship is the First Aid one ("Special"),
     * it adds the health points that should be added. */
    private void isCollidedWithFirstAid(SpaceWars game) {
        SpaceShip closestShip = game.getClosestShipTo(this);
        if (this.getPhysics().testCollisionWith(closestShip.getPhysics()) &&
           (closestShip.getClass().getName().equals(NAME_OF_SPECIAL)) &&
           (this.isShieldUp) && !closestShip.isShieldUp) {
            this.healthLevel += ((SpecialSpaceShip)closestShip).getHealthAmount();
        }
    }

    //------------------------------------public methods-------------------------------------------

    /** Default constructor. */
    public HumanSpaceShip() {
        super();
    }

    /** Check the user input for moving direction
     * @param game the current game which the spaceship belongs to
     * @return -1 for right, 1 for left, 0 otherwise
     */
    private int getTurnDirection(SpaceWars game) {
        if (game.getGUI().isLeftPressed() ^ game.getGUI().isRightPressed()) {
            if (game.getGUI().isLeftPressed()) {
                return LEFT;
            }
            else {
                return RIGHT;
            }
        }
        return NO_TURN;
    }

    /**
     * implement the specific movement of this spaceship
     * @param game the game object to which this ship belongs.
     */
    public void moveUniqueActions(SpaceWars game) {
        //do the actions by the required order
        if (game.getGUI().isTeleportPressed()) {
            this.teleport();
        }

        this.getPhysics().move(game.getGUI().isUpPressed(), this.getTurnDirection(game));

        if (game.getGUI().isShieldsPressed()) {
            this.shieldOn();
        }

        if (game.getGUI().isShotPressed()) {
            this.fire(game);
        }

        //check for specific collision - with the special spaceship. design wise, there was no option
        //to access this information from the collision tester function - since its not getting the SpaceWar object
        //and the object hasn't getter method for this - so it's have to be manually
        isCollidedWithFirstAid(game);
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
            return GameGUI.SPACESHIP_IMAGE_SHIELD;
        }
        return GameGUI.SPACESHIP_IMAGE;
    }
}
