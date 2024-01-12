# Usage
The following program can be used by saving and running the Java project. Logging will appear on the console alongside usage information. 
To view the authentication process visit http://localhost:8080/gameusers

Currently, there are only 3 possible roles, which all share the same password. Each role has different view privileges
Usernames: admin, user, or guest Password: password
There are only 3 game users with ID numbers 1,2,3 hardcoded into the application

# Possible improvements
Possible improvements to this project include adding a changeable list of admins/users with unique passwords. However, this authentication process is still vulnerable to brute-force attacks. Adding an email scheduler, to provide 2-factor authentication could be a possible addition that would make this application more secure.
Additionally, the list of users is hardcoded as a hashmap, moving this information to a database would support consistent records when the site is accessed, as currently the list of users is reset every time the application is restarted.
One other area of interest that I was undecided on was whether or not to allow users to delete their profiles, the current iteration of this project only allows delete privileges to “admin”

# Software design document summary
The client wanted to make a game called Draw It or use it based on a mobile game they already have, but it needed to work on more than one operating system. The part of the code I worked with was adding support for a singleton design pattern, user authentication, and debugging the provided swing code.

# Design doc Improvements
I wish I could find more in-depth information about operating system requirements, these change very often, and from my research typically third-party apps, such as Xcode or NetBeans, are used for development to handle a lot of this side. But it would have been interesting to take more time and research it more thoroughly
