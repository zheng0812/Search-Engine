* **db_stuff** : contains the basic *mongod.conf* file along with a csv containing the names and other information associated with them
* **server_stuff** : contains a basic npm initialized folder with the **package.json** and some packages you might find useful to utilize for the project. Remember ```npm install``` to download the packages before running
* **android_stuff** : simple android project with base code for you to build off of. Contains a MainActivity and the gradle build packages for retrofit, firebase, and google services


### Features: 

* A mechanism for the user to search for a name of interest
* Results showing the popularity of that name for some period of time (start year to end year)
* An ability to compare two names for popularity for some period of time (start year to end year)

### Additional Feature List  

* Authorization :
  * Oauth implementation
  * Self handling of username/passwords
* Tagging names : 
  * Give the user a way of, once a name is searched, to "save" it so that it will appear in comparisons
  * To fully implement this there should be some way to untag names
  * Rating names  - allow users to tag names with a 1 to 5 star rating, but must be done graphically
    * To receive points for this the rating must be done using a graphical mechanism and the results, when shown, must show them sorted by star rating
* Show the results in a more meaningful/graphical manner 
  * Show the users a graphic for the chosen names that indicates popularity graphically
* Results sorted 
  * Show the results about names for the period selected but sort the years by overall popularity for the first name (e.g., if the name Rich is being shown for years 1995-1997 then show the data from the year the name Rich was most popular)
* Prefix searching 
  * Allow the users to search for names by a prefix, return all matching names (if multiple, sort by some appropriate mechanism)
  * This mechanism must then include some way for the user to indicate the name(s) of interest
* Wildcard searching 
  * Allow the users to search as in the previous, but using wildcards
