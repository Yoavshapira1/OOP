yoavshapira1


=============================
=      File description     =
=============================


=============================
=          Design           =
=============================
SimpleSet - a superior Interface 
		I added a method to the API: getSetClass - which return the name of the structure, in string.
SimpleHashSet - an abstract class, implements SimpleSet.  All non-getters methods are abstract, since they differ from set
		set. That's the reason the members are protected.
		I added a method to the API: setCapacity. This is used in maintainencing the set.
CollectionFacadeSet - implement SimpleSet. Used as a WRAPPER for java's collections.
OpenHashSet - An instance of SimpleHashSet, hence heritages SimpleSet. 
ClosedHashSet - An instance of SimpleHashSet, hence heritages SimpleSet.

=============================
=  Implementation details   =
=============================
* OpenHashSet:
	- I implemented a nested class for the buckets. Every bucket has a members of LinkedList<>;
	- When capacity change is needed, a new OpenHashSet is generated, the values are copied, and then the 
	values of the original set are chaned to the new set.

* ClosedHashSet:
	- I implemented a nested class for a value in the set. A value class holds single member of a String.
	When initialize the set, meanly ar array of "Value" class, the array contains null pointers.
	after adding a value, the index is not null anymore.
	The delete operation only changes the inner String value to be null, so the slot is still "in use" but in fact - is empty.
	The contains run in a loop: till it finds an empty slot (==null), OR less than (the current size) times.

* Performance Analyze:
	- Bad result for data1: 
		Initialize both sets (very bad)
		look for the word with the same hashcode in the ClosedHashSet
	- Strenghtes and weaknesses:
		I wouldn't use Linked List ever!
		The best performer is java's HashSet. I would use it for any need if I dont have an idea of the elements to be held.
		Anyhow, If I can map most of the Keys to the same hashcode - Id choose the CloseHashSet rather then the Open,
			Since it's contains - averagely - will take less time
		The big Strength of the OpenHashSet is looking for a word with the same hashcode as most of it's content - So
			If I can map the Keys to only few same hashcodes,
		
	
		
	
