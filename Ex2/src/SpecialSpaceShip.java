import java.awt.*;
import oop.ex2.*;

/**
 * This class represents the Special type of "spaceship": The First Aid spaceship.
 * This spaceship carries an health point on it, and it never shoots.
 * If the user-controlled spaceship bashes this, the user earns 1 health point.
 * This First Aid spaceship always accelerate, and run away from another spaceships. When it gets close enough to
 * another spaceship, it attempt to put the shield on.
 * However, if it's energy level is not enough for wearing a shield - it gets rest for charging, and attempt to stop
 * Moving. The user can earnHence, the user may want to be careful with his shots, in order to not to hit this
 *  spaceship. The health point by bashing this during it's rest, hence, be careful with the shots.
 * All values are to be modified by the programmer.
 */
public class SpecialSpaceShip extends SpaceShip {

    /** This value tells the spaceship what is the direction to turn in order to have a rest and stop. */
    private int directionToTurnInRest;

    /** This value tells the spaceship what is the angle to turn in order to have a rest and stop. */
    private double angleToTurnInRest;

    /** This indicates wheter this spaceship is in rest mode and stopped moving, or not. */
    private boolean isInRest;

    /** These two values represent the previous location of the spaceship in the game. They are necessary in order to
     * calculate the direction and the angle that should be applied in 'move', in order to stop the spaceship.
     */
    private double previousX;

    private double previousY;

    /** This value represents the amount of rounds has left to accelerate in order to stop moving. */
    private int slowingDownCounter;
    /** This value represents the threshold of minimal distance for the spaceship to put to shield on. */
    private static final double DISTANCE_THRESHOLD_FOR_SHIELD = 0.4;

    /** This value represents the amount of health level that the user can earn by bashing this. */
    public static final int AMOUNT_OF_HEALTH = 1;

    /** This value represents the amount of round needed to accelerate to opposite position, in order to stop moving. */
    private static final int ROUNDS_NEEDED_TO_SLOW = 25;

    //------------------------------------private methods-------------------------------------------

    /** Calculate the direction needed to turn to in order to aun away from the closest ship. */
    private int getTurnDirection(SpaceShip closestShip) {
        if (this.getPhysics().angleTo(closestShip.getPhysics()) < 0) {
            return LEFT;
        }
        return RIGHT;
    }

    /** Check if the closest spaceship is close enough in order to put the shield on. */
    private boolean isShieldRequired(SpaceShip closestShip) {
        return this.getPhysics().distanceFrom(closestShip.getPhysics()) < DISTANCE_THRESHOLD_FOR_SHIELD;
    }

    //---------------------------------Next methods are inspired by-----------------------------------
    //--------------------------------the Physics implementation class--------------------------------
    //------------------------which, unfortunately, uses this methods as not public-------------------
    //------------------------Hence I implemented them, with variations, by myself--------------------

    /**
     * gets the difference in x coordinates between two positions. The computation
     * takes into account the toroidal structure of the space.
     * @param x the other X-position
     * @return the difference in x coordinates
     */
    private double getDx(double x){
        return(correctDif(this.getPhysics().getX() - x));
    }

    /**
     * gets the difference in y coordinates between two positions. The computation
     * takes into account the toroidal structure of the space.
     * @param y the other Y-position
     * @return the difference in y coordinates
     */
    private double getDy(double y){
        return(correctDif(this.getPhysics().getY() - y));
    }

    /**
     * Corrects the difference in coordinates between coordinates in a torus.
     * @param num the given coordinate.
     * @return the corrected coordinate.
     */
    private static double correctDif(double num){
        if(num>0.5)
            return num-1;
        else if (num<-0.5)
            return 1+num;
        else
            return num;
    }

    /**
     * Checks the angle this spaceship should turn in order to face the it's previous position.
     * A negative angle (between 0 and -PI) means that it must turn right.
     * a positive angle (between 0 and +PI) implies turning left.
     * @return the relative angle to turn.
     */
    private double calculateAngle() {
        double dx = -getDx(this.previousX);
        double dy = -getDy(this.previousY);
        double angleFromX = Math.asin(dy/(Math.sqrt(dy*dy+dx*dx)));
        if (dx<0){
            angleFromX = Math.PI-angleFromX;
        }
        double difAngle = angleFromX-this.getPhysics().getAngle();
        difAngle = difAngle % (2*Math.PI);
        if(difAngle>Math.PI){
            difAngle-=Math.PI*2;
        }else if(difAngle<-Math.PI){
            difAngle+=Math.PI*2;
        }
        return difAngle;
    }

    /** Implement the action in a resting status.
     * METHOD: When a spaceship getting into a rest, it calculates the angle it needs to turn in order to face the
     * opposite of his moving direction. Then it starts to turn to this direction, with no acceleration, and again
     * calculate the NEW angle it needs to turn. This operation returns till the NEW angle sign is not differ from
     * the first angle that was calculates - Meanly, the spaceship is roughly on the correct position.
     * Then it start accelerating in order to stop the movement, as much as the slowingDownCounter allow. */
    private void restingMove() {
        if (Math.signum(this.angleToTurnInRest) == -this.directionToTurnInRest) {
            if (this.slowingDownCounter != 0) {
                this.getPhysics().move(true, NO_TURN);
                this.slowingDownCounter--;
            }
            else {
                this.getPhysics().move(false, NO_TURN);
            }
        }
        else {
            this.getPhysics().move(false, this.directionToTurnInRest);
            this.angleToTurnInRest = calculateAngle();
        }
        if (this.currentEnergyLevel >= this.maximumEnergyLevel - CHARGING_ENERGY_VAL) {
            this.isInRest = false;
        }
    }

    /** Implement the action in a non-resting status.
     * When a rest is need to be applied, it switches the isInRest member to true, and prepare the information needed
     * for resting:
     * angleToTurnInRest - relative angle from the moving direction axes.
     * directionToTurnInRest - keeps the sign of the angle, to compare with further calculated angles.
     * slowingDownCounter - reset the counter. */
    private void actionMove(SpaceWars game) {
        SpaceShip closestShip = game.getClosestShipTo(this);
        this.getPhysics().move(true, this.getTurnDirection(closestShip));
        if (isShieldRequired(closestShip)) {
            this.shieldOn();
        }
        if (this.currentEnergyLevel < ENERGY_COST_FOR_SHIELD) {
            this.isInRest = true;
            this.angleToTurnInRest = calculateAngle();
            this.directionToTurnInRest = (int)Math.signum(this.angleToTurnInRest);
            this.slowingDownCounter = ROUNDS_NEEDED_TO_SLOW;
        }
    }

    //------------------------------------public methods-------------------------------------------

    /** Default constructor. */
    public SpecialSpaceShip() {
        super();
        this.isInRest = false;
        this.directionToTurnInRest = 0;
        this.angleToTurnInRest = 0;
        this.slowingDownCounter = 0;
        this.previousX = this.getPhysics().getX();
        this.previousY = this.getPhysics().getY();
    }

    /**
     * implement the specific movement of this spaceship
     *
     * @param game the game object to which this ship belongs.
     */
    public void moveUniqueActions(SpaceWars game) {

        //do the actions by the required order
        if (!this.isInRest) {
            actionMove(game);
        }
        else {
            restingMove();
        }
        this.previousX = this.getPhysics().getX();
        this.previousY = this.getPhysics().getY();
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

    /** Gets the amount of health points that user earn if it bashes this spaceship.
     * @return amount of rounds this spaceship need to accelerate in order to stop. */
    public int getHealthAmount() {
        return AMOUNT_OF_HEALTH;
    }
}