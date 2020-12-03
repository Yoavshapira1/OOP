yoavshapira1


=============================
=      File description     =
=============================
Spaceship:
	Spaceship.java - class represents the spaceship object
	Storage.java - an Interface for the storage tools in the program
	Locker.java - class implements Storage
	LongTermStorage.java - class implements Long Term Storage
	SpaceshipTestsInterface.java - an interface for the tests
	SpaceshipTest.java - tests for the spaceship class
	LockerTest.java - tests for the locker class
	LongTermTest.java - tests for the long term storage class
	SpaceshipDepositoryTests.java - a Suite class for gather the tests


BoopingSite:
	BoopingSite.java - implement the search engine required
	BoopingSiteTest.java - test the BoopingSite class

=============================
=          Design           =
=============================
Spaceship:
	* Interface: Locker and LoneTermStorage (LTS) are examples for storage tools. Theoretically, a spaceship can invenet new
	storage tool in the future. Hence it's natural that the qualitites of a storage tool is an interface.
	* Composition: Since a spaceship have a LTS and might have lockers, I decided to store the LTS and a list of
	Lockers as a members in the spaceship object. 
	
	* Interface for the TESTS: I chose to write a small interface for the tests, which includes the accessing to the 
	necessary parameters

BoopingSite:
	* The class BoopingSite includes nested classes which implement different Comparators<Hotel>, as the methods
	require. These nested classes are modified as static, because the don't depends on any attribute of the BoopingSite
	class - they have their own right to exist.

=============================
=  Implementation details   =
=============================
Spaceship:
	* Spaceship: 
		- crew's ID's and lockers are mapped to a HashMap, when the keys are the ID's, and the values are
		  sets of lockers. Every locker belongs to a members, but a members might
		  have more than 1 Locker. That way, it's easy to get to a locker in a constant time.
		- LTS: The LongTermStorage is initialize in the constructor, and hence is unique for each spaceship.
		- Constraints: The constraint are a member of the spaceship, Since the spaceship creates it's own 
		  Lockers.		
	* Storage Interface:
		- Magic numbers: The interface holds shared magic numbers like error numbers & messages, and also
		  determine what methods must be shared for any storage tool.
	* Locker:
		- Data Structures: The locker hold it's information in a HashMaps - Both items and constraints.
		The item choose is pretty clear - quick operations runtime, and saving memory.
		I decided to keep the constraints in HashMap, althoght it take more memory and runtime to initialize, 
		because it's a VERY COMMON data structue in use in Locker's methds: In every adding we need quick access.
		(Sadly, the API you gave required a specific method signature - the constructor need to get Item[][] of constraints.
		Otherwise - I would have built this constraint HashMap in the spaceship itself).
	* Long Term Storage:
		- The data structure is abviously HashMap.
		- The ONLY DIFFERENCE between this and the Locker, DESIGN wise, it's the reset/remove method. 

	GENERAL:
		- When an empty array asked to be returned, I returned an array of Object[0].
		- Public members: the Locker's members:
				*TRANSFER_TO_LTS_THRESHOLD
				*MAX_PERCENTAGE_FOR_TYPE
				*Spaceship members indicated different failure
		I chose to modify them as public for the tests to be able to access them, otherwise the tests had to 
		have them as members - but the real failure indicators may chagne independently.

BoopingSite:
	* The hotels are kept in an ArrayList, which is null in case the constructor given with a invalid input.
	in this case, any operation will result an empty array (new Hotel[0]).