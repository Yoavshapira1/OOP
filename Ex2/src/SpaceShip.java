import java.awt.Image;
import oop.ex2.*;

/**
 * The API spaceships need to implement for the SpaceWars game. 
 * It is your decision whether SpaceShip.java will be an interface, an abstract class,
 *  a base class for the other spaceships or any other option you will choose.
 *  protected members are to be used by the spaceship itself.
 * @author Yoav Shapira
 */
public abstract class SpaceShip{

    /** This value represents the maximum energy a spaceship can reach. */
    protected int maximumEnergyLevel;

    /** This value represents the current energy a spaceship has. */
    protected int currentEnergyLevel;

    /** This value represents the current health level a spaceship has. */
    protected int healthLevel;

    /** This holds the physics information of the spaceship, in relation of "Has a-". */
    private SpaceShipPhysics physics;

    /** This tells if the spaceship is in it's period of firing cooling, meanly can not shoot. */
    private int coolingPeriodTimer;

    /** This boolean value tells if a spaceship has it's shield up. */
    protected boolean isShieldUp;

    //--------------------------------------------------------------------------
    //---------private parameters are can be modified by the programmer---------
    //----------static values are common for all spaceships in the game---------
    //--------------------------------------------------------------------------

    /** This value represents the maximum energy a spaceship can reach. */
    private final static int INIT_MAXIMUM_ENERGY_LEVEL = 210;

    /** This value represents the current energy a spaceship has. */
    private final static int INIT_CURRENT_ENERGY_LEVEL = 190;

    /** This value represents the current health level a spaceship has. */
    private final static int INIT_HEALTH_LEVEL = 22;

    /** This value represents the amount of energy that to be charged in every round of the game. */
    protected final static int CHARGING_ENERGY_VAL = 1;

    /** This value represents the amount of health level that reduce when spaceship got shot. */
    private final static int REDUCE_HEALTH_BY_SHOT = 1;

    /** This value represents the amount of health level that reduce when spaceship has collided. */
    private final static int REDUCE_HEALTH_BY_COLLISION = 1;

    /** This value represents the amount of maximal energy level that reduce when spaceship has collided. */
    private final static int REDUCE_MAX_ENERGY_BY_SHOT = 10;

    /** This value represents the amount of maximal energy level that reduce when spaceship got shot. */
    private final static int REDUCE_MAX_ENERGY_BY_COLLISION = 10;

    /** This value represents the amount of energy level to be rise when spaceship is bashing. */
    private final static int BASHING_ENERGY_BOOST = 18;

    /** This value represents the amount of energy that reduce when spaceship is shooting. */
    private final static int ENERGY_COST_FOR_SHOT = 19;

    /** This value represents the amount of energy that reduce when spaceship is teleporting. */
    private final static int ENERGY_COST_FOR_TELEPORT = 140;

    /** This value represents the cost of an active shield in single round of the game. */
    protected final static int ENERGY_COST_FOR_SHIELD = 3;

    /** This value represents the amount of rounds that a spaceship needs to wait to be able to shoot again. */
    private final static int COOL_FIRING_PERIOD = 7;

    /** These two values represent right and left, according to the given API. */
    protected final static int RIGHT = -1;
    protected final static int LEFT = 1;
    protected final static int NO_TURN = 0;

    //----------------------------------------------------------------------------------------------------
    //---------private methods, handles the computational implementations and avoid doubled code----------
    //----------------------------------------------------------------------------------------------------

    /**
     * implements the reduction of energy that a hit cause
     * the value of reduction is depends on the cause of reduction: Shot or Collision.
     * this called by the functions "gotHit" and "collidedWithAnotherShip"
     * @param cause "Shot" or "Collision", by default is "Shot" (programmed to work this way for convenience
     */
    protected void gotHitReduction(String cause) {
        int healthReductionValue = REDUCE_HEALTH_BY_SHOT;
        int energyReductionValue = REDUCE_MAX_ENERGY_BY_SHOT;
        if (cause.equals("Collision")) {
            healthReductionValue = REDUCE_HEALTH_BY_COLLISION;
            energyReductionValue = REDUCE_MAX_ENERGY_BY_COLLISION;
        }
        this.healthLevel -= healthReductionValue;
        this.maximumEnergyLevel = Math.max(this.maximumEnergyLevel - energyReductionValue, 0);
        this.currentEnergyLevel = Math.min(this.maximumEnergyLevel, this.currentEnergyLevel);
    }

    /**
     * implements the increasing in energy level caused by bashing
     */
    private void bashed() {
        this.maximumEnergyLevel += BASHING_ENERGY_BOOST;
        currentEnergyLevel += BASHING_ENERGY_BOOST;
    }

    //------------------------------------------------------------------------------------
    //---------abstract methods, which are differ from one spaceship to another-----------
    //------------------------------------------------------------------------------------

    /**
     * This function implement the uniqe movement for this spaceship
     * It is an abstract since every spaceship has it's own different movement
     * @param game the current game frame
     */
    protected abstract void moveUniqueActions(SpaceWars game);

    /**
     * Gets the image of this ship. This method should return the image of the
     * ship with or without the shield. This will be displayed on the GUI at
     * the end of the round.
     *
     * @return the image of this ship.
     */
    public abstract Image getImage();

    //-------------------------------------------------------------------------------------
    //---------public methods, which are implemented uniformly for all spaceships----------
    //-------------------------------------------------------------------------------------

    /**
     * default constructor
     */
    public SpaceShip() {
        this.maximumEnergyLevel = INIT_MAXIMUM_ENERGY_LEVEL;
        this.currentEnergyLevel = INIT_CURRENT_ENERGY_LEVEL;
        this.healthLevel = INIT_HEALTH_LEVEL;
        this.physics = new SpaceShipPhysics();
        this.coolingPeriodTimer = 0;
        this.isShieldUp = false;
    }

    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     * This is a non-abstract function, since the basic moves are common for all spaceships.
     * The unique movement is implemented in the abstract method "moveAction", and called by "doAction"
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {
        //reset the shield and count rounds for the cooling fire timer
        this.isShieldUp = false;
        this.coolingPeriodTimer = Math.max(this.coolingPeriodTimer - 1, 0);

        this.moveUniqueActions(game);
        this.currentEnergyLevel = Math.min(this.currentEnergyLevel + CHARGING_ENERGY_VAL, this.maximumEnergyLevel);
    }

    /**
     * This method is called every time a collision with this ship occurs 
     */
    public void collidedWithAnotherShip(){
        if (!this.isShieldUp) {
            this.gotHitReduction("Collision");
        }
        else {
            this.bashed();
        }
    }

    /** 
     * This method is called whenever a ship has died. It resets the ship's 
     * attributes, and starts it at a new random position.
     */
    public void reset() {
        this.physics = new SpaceShipPhysics();
        this.currentEnergyLevel = INIT_CURRENT_ENERGY_LEVEL;
        this.healthLevel = INIT_HEALTH_LEVEL;
        this.maximumEnergyLevel = INIT_MAXIMUM_ENERGY_LEVEL;
        this.coolingPeriodTimer = 0;
        this.isShieldUp = false;
    }

    /**
     * Checks if this ship is dead.
     * 
     * @return true if the ship is dead. false otherwise.
     */
    public boolean isDead() {
        return (this.healthLevel <= 0);
    }

    /**
     * Gets the physics object that controls this ship.
     * 
     * @return the physics object that controls the ship.
     */
    public SpaceShipPhysics getPhysics() {
        return this.physics;
    }

    /**
     * This method is called by the SpaceWars game object when ever this ship
     * gets hit by a shot.
     */
    public void gotHit() {
        this.gotHitReduction("Shot");
    }

    /**
     * Attempts to fire a shot.
     * 
     * @param game the game object.
     */
    public void fire(SpaceWars game) {
       if (ENERGY_COST_FOR_SHOT <= this.currentEnergyLevel && this.coolingPeriodTimer == 0) {
           game.addShot(this.physics);
           this.currentEnergyLevel -= ENERGY_COST_FOR_SHOT;
           this.coolingPeriodTimer = COOL_FIRING_PERIOD;
       }
    }

    /**
     * Attempts to turn on the shield.
     */
    public void shieldOn() {
        if (ENERGY_COST_FOR_SHIELD <= this.currentEnergyLevel) {
            this.isShieldUp = true;
            this.currentEnergyLevel -= ENERGY_COST_FOR_SHIELD;
        }
    }

    /**
     * Attempts to teleport.
     */
    public void teleport() {
        if (ENERGY_COST_FOR_TELEPORT <= this.currentEnergyLevel) {
            this.physics = new SpaceShipPhysics();
            this.currentEnergyLevel -= ENERGY_COST_FOR_TELEPORT;
        }
       
    }
    
}
