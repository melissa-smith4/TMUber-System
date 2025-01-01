# TMUber-System
A ride-sharing application that simulates and manages users, drivers, and service requests, featuring queue-based request handling, error management, and data organization with maps and inheritance.

Upon running the program, users will be prompted to enter one of 19 available commands to begin. It is recommended to **load users and drivers before proceeding with other actions.**

## Commands

**Q or quit:**
- Exit the program

**Loadusers:**
- Loads users from a file
- A users.txt file with pre-registered users has been provided to load from.

**Loaddrivers:**
- Loads drivers from a file
- A drivers.txt file with pre-registered drivers has been provided to load from. 

**Users:**
- View all current users

**Drivers:**
- View all current drivers

**Requests:**
- View all current service requests, organized by zones

**Addr:**
- Check if an address is valid
- The city consists of a grid of 9x9 city blocks
- In TMUber, addresses are in the following formats:
  - (two digit number) + (single digit number) + (st, nd, rd, th) + (avenue or street)
  - Ex. 34 4th avenue, 99 1st street
- More information about addresses and city block calculations can be found at the top of the CityMap.java file

**Dist:**
- Check the distance between two addresses
- If one or both of the addresses are in an invalid format, a distance of 0 city blocks will be returned.

**Reguser:**
- Register a new user

**Regdriver:**
- Register a new driver

**Reqride:**
- Request a new ride

**Reqdlvy:**
- Request a new delivery

**Sortbyname:**
- List users in alphabetical order

**Sortbywallet:**
- List users by how much money they have in their wallet

**Cancelreq:**
- Cancel a ride or delivery request

**Driveto:**
- Move a driver to a new address
- Drivers must be in the same zone as a service request before a pickup in that zone can be performed. Ensure that the driver is in the same zone as the service request or has driven to the “from” address of the service request.

**Pickup:**
- Have a driver pickup a rider or a delivery.  A driver in the same zone as a service request can be selected by the user to perform the pickup.
- The driveto command can be used to move a driver to the same zone as a service request

**Dropoff:**
- Have a driver drop off a delivery or a rider.
- After dropping off, the driver’s location will be set to the “to” location of the ride or delivery that they were handling
- The driver will then be paid and money will be deducted from the user’s wallet. Delivery fees, ride fees, and pay rates are listed at the beginning of the TMUberSystemManager.java file

**Revenues:**
- View the total revenues from all completed service requests.
