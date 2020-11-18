yoavshapira1


=============================
=      File description     =
=============================
SpaceShipFactory.java - as required.
SpaceShip.java - abstract class represents s spaceship. All spaceship extend this class.
AggressiveSpaceShip.java - as required.
BasherSpaceShip.java - as required.
HumanSpaceShip.java - as required.
RunnerSpaceShip.java - as required.
DrunkardSpaceShip.java - class for the Drunkard spaceship; This space ship always accelerates and turns. Every specific period of rounds,
			 it changes to a new random direction.
SpecialSpaceShip.java - class for the Special spaceship: *** !!The First Aid Spaceship!! *** (Unfortunately, didn't manage to change it's
			 picture to a spaceship with Magen David...)
		        This ship carries a health point. it run away and put it's shield on if too close to another spaceship, till it finishes the energy.
		        Then, it accelerates to the opposite direction of its moving direction, and stop.	
		        If the user, and only the user, bashes this spaceship while its resting - he earns 1 health point.


=============================
=          Design           =
=============================
SpaceShip is an abstract class, and the super-class of all spaceships.
	Most of the actions of this, are common for all spaceships and hence implemented in the super class.
	However, the getImage() and moveUniqueActions() are an abstract methods that differ from each spaceship to another.
	I preffered to reduce the use in protected modifiers, anyhow there are some used in abstract methods - hence they are protected.
Advantages: 
* It's intuitive - every type of spaceship *is a-* spaceship, hence is instance of a spaceship class.
* It's easy to add new kind of spaceship to the game - just need to choose it's behavior and implement teo methods.
* It's very easy to read and to modify every parameter or behavior of all spaceships.
* Code is never doubled (spaceship wise, at least...)
* Minimal API - a lot of private methods and members are in use, making the understanding of the project easy.

Also, it's modulary - but limited: Since a lot of the members are not protected rather than private, as we tought to be a good practice, 
not every crazy idea of spaceship's behavior can be implemented easily without changing the API.

=============================
=  Implementation details   =
=============================
Drunkard:
	* His pilot is drank, hence turn to random directions and gets nervous when close to another spaceship - and attempt to shoot.
	* Java.Random is in use

Special (FirstAid):
This implementation was a bit tricky, since stop moving turned out as a hard programming issue...
	* As described, when it finished it's energy (=>less then put the shield on), it gets rest. In order to stop, it needs to turn the
	 exact opposite directionof this current moving direction (NOT facing direction!)
	* In order to do that, two variables are in use: One for the current angle that need to be applied, and one for the ORIGIN 
	direction that the ORIGIN angle refer to. (negativ & positive <=> left & right)
	* When the new angle refer to another direction then the ORIGIN direction, meanly the spaceship have CROSSED the axes
	 of it's moving, meanly - turning completed!
	* HORAY. Now it needs to stop moving. This is done using a 'timer' that count how many rounds were passed since it atsrted
	 to accelerate (since it's opposite direction, it will decreace speed)
	* PROGRAMMING ISSUE: In order to calculate the required angle, I had to use a previous coordinates of the spaceship, and 
	then calculate it using the Physics's class methods, and the coordinates.
	* THE PROBLEM: The calculating methods get SpaceShipPhysics object. But Physics is an abstract, SpaceShipPhysics is not 
	clonable, and new object of SpaceShipPhysics creates random coordinates.
	* Solution: I implemented the "angleTo" method in the Special class, with several adaptations. For this, I had to use two
	 more members that remembers the last coordinates of the spaceship.
	*** SADLY, I couldn't manage to add another picture of my own. 

Human:
	* Since bashing is a common method for all spaceship, and the sinerio of Human-controlled bashing the Special spacehsip is unique -
	 I added a check for collision in the doAction of the human.
	* The problem was that I couldn't get to the information of the spaceships that were collided with, so I hade to check it manually.

