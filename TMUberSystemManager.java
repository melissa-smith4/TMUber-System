import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.Iterator;


/*
 * 
 * This class contains the main logic of the system.
 * 
 *  It keeps track of all users, drivers and service requests (RIDE or DELIVERY)
 * 
 */
public class TMUberSystemManager
{
  private Map<String, User> users;
  private Map<String, Driver> drivers;
  
  @SuppressWarnings("unchecked")
  private Queue<TMUberService>[] serviceRequests = new Queue[4];

  public double totalRevenue; // Total revenues accumulated via rides and deliveries
  
  // Rates per city block
  private static final double DELIVERYRATE = 1.2;
  private static final double RIDERATE = 1.5;
  // Portion of a ride/delivery cost paid to the driver
  private static final double PAYRATE = 0.1;

  //These variables are used to generate user account and driver ids
  int userAccountId = 900;
  int driverId = 700;

  public TMUberSystemManager()
  {
    
    users = new LinkedHashMap<String, User>();
    drivers = new LinkedHashMap<String, Driver>();


    //Add queues representing 4 zones to serviceRequests
    for (int i = 0; i < 4; i ++){
      serviceRequests[i] = new LinkedList<TMUberService>();
    }
   
    totalRevenue = 0;
  }
  
  // Given user account id, find user in the map of users
  // Return null if not found
  public User getUser(String accountId)
  {
    //If there are no users currently added, return null
    if (users.size() == 0){
      return null;
    }
    //Iterate through users and check if user account ID matches input account ID
    for (User user: users.values()){
      if (user.getAccountId().equals(accountId)){
        return user;
      }
    }
    //Return null if no users found
    return null;
  }
  //Given a driver id, find the corresponding driver in the map of drivers
  public Driver getDriver(String driverID)
  {
    //Set temporary driver
    Driver thisDriver = null;
    
    //Get driver associated with request
    for (String key:drivers.keySet()){
      if (key.equals(driverID)){
        thisDriver = drivers.get(key);
      }
    }
    //If thisDriver still = null, user ID not valid
    if (thisDriver == null){
      throw new InvalidIDException("Driver Account Not Found");
    }
    return thisDriver;
  }
  
  // Check for duplicate user
  private boolean userExists(User user)
  {
    //If no users are currently in the list, return false
    if (users.size() == 0){
      return false;
    }
    //Iterate through users and check if any users matches the input user
    for (User person: users.values()){
      if (person.equals(user)){
        return true;
      }
    }
    return false;
  }
  
 // Check for duplicate driver
 private boolean driverExists(Driver driver)
 {
  //If no drivers exist, return false
  if (drivers.size() == 0){
    return false;
  }
   //Iterate through drivers and check if any drivers match the input driver
   for (Driver d: drivers.values()){
      if (d.equals(driver)){
        return true;
    }
   }
   return false;
 }
  
  // Given a user, check if user ride/delivery request already exists in service requests
  private boolean existingRequest(TMUberService req)
  {
    //Cast to either Delivery or Ride type first
    if (req.getServiceType().equals("DELIVERY")){
      TMUberDelivery inputDelivery = (TMUberDelivery) req;
      
      //Create an iterator for each zone's queue
      for (int i = 0; i < 4; i ++){
        Iterator<TMUberService> iter = serviceRequests[i].iterator();
        
        //Check if the input delivery matches any deliveries in service requests
        while(iter.hasNext()){
          TMUberService currDelivery = iter.next();
          if (currDelivery.getServiceType().equals("DELIVERY")){
            TMUberDelivery castedCurr = (TMUberDelivery) currDelivery;
            if (inputDelivery.getUser().equals(castedCurr.getUser()) && inputDelivery.getFoodOrderId().equals(castedCurr.getFoodOrderId()) && inputDelivery.getRestaurant().equalsIgnoreCase(castedCurr.getRestaurant())){
              return true;
            }
          }
        }
      }
    }
    if (req.getServiceType().equals("RIDE")){
      TMUberRide inputRide = (TMUberRide) req;
      
      //Create an iterator for each zone's queue
      for (int i = 0; i < 4; i ++){
        Iterator<TMUberService> iter = serviceRequests[i].iterator();
        
        //Check if the input ride matches any rides in service requests
        while(iter.hasNext()){
          TMUberService currRide = iter.next();
          if (currRide.getServiceType().equals("RIDE")){
            TMUberRide castedCurr = (TMUberRide) currRide;
            if (inputRide.equals(castedCurr)){
              return true;
            }
          }
        }
      }
    }
    return false;
  }

  // Calculate the cost of a ride or of a delivery based on distance 
  private double getDeliveryCost(int distance)
  {
    return distance * DELIVERYRATE;
  }

  private double getRideCost(int distance)
  {
    return distance * RIDERATE;
  }


  // Print Information (printInfo()) about all registered users in the system
  public void listAllUsers()
  {
    System.out.println();
    
    ArrayList<User> currUsers = userMapToArray(users);

    for (int i = 0; i < currUsers.size(); i++)
    {
      int index = i + 1;
      System.out.printf("%-2s. ", index);
      currUsers.get(i).printInfo();
      System.out.println(); 
    }
  }

  // Print Information (printInfo()) about all registered drivers in the system
  public void listAllDrivers()
  {
    //Iterate through drivers and use printInfo() function to display their information
    System.out.println();
    
    ArrayList<Driver> currDrivers = driverMapToArray(drivers);
    
    //Use for loop to iterate through the drivers
    for (int i = 0; i < currDrivers.size(); i ++)
    {
      int index = i + 1;
      //Print out the indices using a specific format
      System.out.printf("%-2s. ", index);
      //Follow each number with the corresponding driver in the list
      currDrivers.get(i).printInfo();
      System.out.println();
    }
  }

  // Print Information (printInfo()) about all current service requests
  public void listAllServiceRequests()
  {

    //Iterate through service requests and use printInfo() function to display their information
    System.out.println();
    //Use for loop to iterate through the service request queues
    for (int i = 0; i < serviceRequests.length; i ++){
      int index = 1;

      System.out.println("\nZONE " + i + "\n======\n");
      
      //Create an iterator for each zone
      Iterator<TMUberService> iter = serviceRequests[i].iterator();

      while (iter.hasNext()){
        System.out.printf("%-2s. ", index);
        System.out.print("------------------------------------------------------------");
        //Follow each number with the corresponding request in the list
        iter.next().printInfo();
        System.out.println();
        index ++;
      }
      
    }
  }

  // Add a new user to the system
  public void registerNewUser(String name, String address, double wallet)
  {
    //Check if name or address are null or ""
    if (name == null || name == ""){
      throw new InvalidNameException("Invalid User Name");
    }
    if (address == null || address == ""){
      throw new InvalidAddressException("Invalid User Address");
    }
    //Check if the address is valid
    if (!CityMap.validAddress(address)){
      throw new InvalidAddressException("Invalid User Address");
    }
    //Ensure that their wallet is not in the negatives
    if (wallet < 0.00){
      throw new InvalidWalletException("Invalid Money in Wallet");
    }
    //Generate ArrayList of current users users map
    ArrayList<User> arrayUsers = userMapToArray(users);

    //Generate the new user and check if it already exists in the system
    User newUser = new User(TMUberRegistered.generateUserAccountId(arrayUsers), name, address, wallet);
    if (userExists(newUser)){
      throw new ExistingPersonException("User Already Exists in System");
    }
    
    users.put(newUser.getAccountId(), newUser);
  }

  // Add a new driver to the system
  public void registerNewDriver(String name, String carModel, String carLicencePlate, String address)
  {
    //Check if name, carModel, or carLicensePlate are null or ""
    if (name == null || name == ""){
      throw new InvalidNameException("Invalid Driver Name");
    }
    if (carModel == null || carModel == ""){
      throw new InvalidCarModelException("Invalid Car Model");
    }
    if (carLicencePlate == null || carLicencePlate == ""){
      throw new InvalidLicenseException("Invalid Licence Plate");
    }
    //Check if the address is valid
    if (address == null || address == ""){
      throw new InvalidAddressException("Invalid Driver Address");
    }
    if (!CityMap.validAddress(address)){
      throw new InvalidAddressException("Invalid Driver Address");
    }

    //Generate arrayList of current drivers from current drivers map
    ArrayList<Driver> arrayDrivers = driverMapToArray(drivers);

   //Create new Driver using generateUserAccountId method for the id and check if it exists in the system
    Driver newDriver = new Driver(TMUberRegistered.generateDriverId(arrayDrivers), name, carModel, carLicencePlate, address);
    if (driverExists(newDriver)){
      throw new ExistingPersonException("Driver Already Exists in System");
    }

    drivers.put(newDriver.getId(), newDriver);
  }

  // Request a ride. User wallet will be reduced when drop off happens
  public void requestRide(String accountId, String from, String to)
  {
    //Create a temporary user and use them it as a placeholder for the user to find
    //This is to prevent initialization errors.
    User tempUser = new User("0000", "temp", "temp", 0.0);
    
    //Create variables for user matching account id, distance, and available driver to be found
    User thisUser = tempUser;
    int distance = 0;

    //Check if from and to addresses are valid
    if (!CityMap.validAddress(from) || !CityMap.validAddress(to)){
      throw new InvalidAddressException("Invalid User Address");
    }
    //Find the user in list of users using the account ID
    for (User u: users.values()){
      if (u.getAccountId().equals(accountId)){
        thisUser = u;
      }
    }
    //If thisUser has not been updated from tempUser, return error message
    if (thisUser.equals(tempUser)){
      throw new InvalidIDException("User Account Not Found");
    }

    //If user already has a ride request, send error message 
    if (thisUser.getRides() == 1){
      throw new ExistingRequestException("User Already Has Ride Request");
    }

    //Get distance between addresses
    distance = CityMap.getDistance(from, to);
    if (distance < 1){
      throw new InsufficientDistanceException("Insufficient Travel Distance");
    }

    //Get ride cost
    double rideCost = getRideCost(distance);

    //If ride cost greater than user's funds, return insufficient funds message
    if (thisUser.getWallet() < rideCost){
      throw new InvalidWalletException("Insufficient Funds");
    }
    
    //Use the from address to get the zone for the service request
    int zone = CityMap.getCityZone(from);

    //Add request to serviceRequests
    serviceRequests[zone].add(new TMUberRide(from, to, thisUser, distance, rideCost));
    thisUser.addRide();
  }

  // Request a food delivery. User wallet will be reduced when drop off happens
  public void requestDelivery(String accountId, String from, String to, String restaurant, String foodOrderId)
  {

    //Create a temporary user and use it as a placeholder for the user to find
    //This is to prevent initialization errors.
    User tempUser = new User("0000", "temp", "temp", 0.0);
    
    //Create variables for user matching account id, distance, and available driver to be found
    User thisUser = tempUser;
    int distance = 0;

    //Check if from and to addresses are valid
    if (!CityMap.validAddress(from) || !CityMap.validAddress(to)){
      throw new InvalidAddressException("Invalid User Address");
    }

    //Check if restaurant name is valid
    if (restaurant == null || restaurant == ""){
      throw new InvalidNameException("Invalid Restaurant Name");
    }

    //Check if food order ID is valid
    if (foodOrderId == null || foodOrderId == ""){
      throw new InvalidIDException("Invalid Food Order ID");
    }

    //Find the user in list of users using the account ID
    for (User u: users.values()){
      if (u.getAccountId().equals(accountId)){
        thisUser = u;
      }
    }
    //If thisUser has not been updated from tempUser, return error message
    if (thisUser.equals(tempUser)){
      throw new InvalidIDException("User Account Not Found");
    }
  
    //Get distance between addresses
    distance = CityMap.getDistance(from, to);
    if (distance < 1){
      throw new InsufficientDistanceException("Insufficient Travel Distance");
    }

    //Get zone based on from address
    int zone = CityMap.getCityZone(from);

    //Get delivery cost
    double deliveryCost = getDeliveryCost(distance);

    //If  delivery cost greater than user's funds, return insufficient funds message
    if (thisUser.getWallet() < deliveryCost){
      throw new InvalidWalletException("Insufficient Funds");
    }
    
    //Create new delivery
    TMUberDelivery newD = new TMUberDelivery(from, to, thisUser, distance, deliveryCost, restaurant, foodOrderId);

    //Check if delivery already exists
    if (serviceRequests[0].size() != 0 || serviceRequests[1].size() != 0 || serviceRequests[2].size() != 0 || serviceRequests[3].size() != 0){
      if (existingRequest(newD)){
        throw new ExistingRequestException("User Already Has Delivery Request at Restaurant with this Food Order");
      }
      else{
        thisUser.addDelivery();
      }
    }
    //If all queues are empty, then there are no current service requests and thus, there are no duplicates --> increment user deliveries
    else{
      thisUser.addDelivery();
    }

    //Add request to serviceRequests
    serviceRequests[zone].add(new TMUberDelivery(from, to, thisUser, distance, deliveryCost, restaurant, foodOrderId));
  }


  // Cancel an existing service request. 
  // parameter int request is the index in the serviceRequests array list
  public void cancelServiceRequest(int request, int zone)
  {
    // Check if valid request # and zone # 
    if (zone != 0 && zone != 1 && zone != 2 && zone != 3){
      throw new InvalidRequestException("Invalid Zone #");
    }
    if (serviceRequests[zone].size() < request){
      throw new InvalidRequestException("Invalid Request #");
    }

    //Generate array list for current service requests queue
    ArrayList<ArrayList<TMUberService>> services = reqsToArray(serviceRequests);

    ///Get the request type for the corrsponding zone and request number
    String type = services.get(zone).get(request - 1).getServiceType();

    //Get the user related to the request
    User thisUser = services.get(zone).get(request - 1).getUser();

    // Remove request from list
    Iterator<TMUberService> iter = serviceRequests[zone].iterator();
    for (int i = 0; i < request; i ++){
      iter.next();
    }
    iter.remove();

    // Decrement number of rides or number of deliveries for this user
    if (type == "RIDE"){
      thisUser.removeRide();
    }
    else{
      thisUser.removeDelivery();
    }
  }
  
  // Drop off a ride or a delivery. This completes a service.
  public void dropOff(String ID)
  {
    //Set temporary driver
    Driver thisDriver = null;
    
    //Get driver associated with request
    for (String key:drivers.keySet()){
      if (key.equals(ID)){
        thisDriver = drivers.get(key);
      }
    }
    //If thisDriver still = null, driver ID is not valid
    if (thisDriver == null){
      throw new InvalidIDException("Driver Account Not Found");
    }
    //Make sure driver is driving
    if (thisDriver.getStatus() == Driver.Status.AVAILABLE){
      throw new InvalidStatusException("Driver Does Not Have a Service in Progress");
    }

    //Get service and user associated with request
    TMUberService service = thisDriver.getService();
    User thisUser = service.getUser();

    //Get service cost
    double serviceCost = service.getCost();

    //Pay driver, deduct driver fee from revenues
    thisDriver.pay(PAYRATE * serviceCost);
    totalRevenue -= (PAYRATE * serviceCost);
    
    //Deduct cost from user, add cost to total revenues
    thisUser.payForService(serviceCost);
    totalRevenue += serviceCost;
    
    //Change driver's status
    thisDriver.setStatus(Driver.Status.AVAILABLE);

    //Change driver's service
    thisDriver.setService(null);

    //Change driver's address to dropoff address
    thisDriver.setAddress(service.getTo());

    //Change the driver's zone according to the new address
    int newZone = CityMap.getCityZone(service.getTo());
    thisDriver.setZone(newZone);

  }

  // Sort users by name
  // Then list all users
  public void sortByUserName()
  {
    //Turn the map of users into an arrayList
    ArrayList<User> arrayUsers = userMapToArray(users);
    //Sort all users by name
    Collections.sort(arrayUsers, new NameComparator());
    //Print all users
    listAllUsers2(arrayUsers);
  }

  // Helper class for method sortByUserName
  private class NameComparator implements Comparator<User>
  {
    public int compare(User firstPerson, User secondPerson){
      return firstPerson.getName().compareTo(secondPerson.getName());
    }
  }

  // Sort users by number amount in wallet
  // Then list all users
  public void sortByWallet()
  {
    //Turn the map of users into an arrayList
    ArrayList<User> arrayUsers = userMapToArray(users);
    
    //Sort users by amount in wallet
    Collections.sort(arrayUsers, new UserWalletComparator());
    //Print all users
    listAllUsers2(arrayUsers);
  }
  // Helper class for use by sortByWallet
  private class UserWalletComparator implements Comparator<User>
  {
    public int compare(User firstUser, User secondUser){
      return Double.compare(firstUser.getWallet(), secondUser.getWallet());
    }
  }

  
  //New version of list all users method to support sortByWallet and sortByUsername
  //Same as original listAllUsers, just takes in an ArrayList parameter additionally
  public void listAllUsers2(ArrayList<User> inputUsers)
  {
    System.out.println();

    for (int i = 0; i < inputUsers.size(); i++)
    {
      int index = i + 1;
      System.out.printf("%-2s. ", index);
      inputUsers.get(i).printInfo();
      System.out.println(); 
    }
  }
  
  public void pickup(String driverId){
    Driver thisDriver = getDriver(driverId);

    //Use the driver's address to find its current zone
    String driverAddress = thisDriver.getAddress();
    int zone = CityMap.getCityZone(driverAddress);

    //If no service requests for this zone, return error
    if (serviceRequests[zone].size() == 0){
      throw new InvalidRequestException("No Service Requests in Driver's Zone");
    }

    //Give the driver the first serviceRequest for the acquired zone
    TMUberService newService = serviceRequests[zone].remove();
    thisDriver.setService(newService);

    //Set driver's status to DRIVING
    thisDriver.setStatus(Driver.Status.DRIVING);

    //Set the driver's address to the request's from address
    String fromAddress = newService.getFrom();
    thisDriver.setAddress(fromAddress);

  }
  
  
  public void driveTo(String driverId, String address){
    //If the given address is valid, proceed
    if (CityMap.validAddress(address)){
      
    //Iterate through drivers and find the corresponding driver
    //Set temporary driver and iterate through drivers to find correct person
    Driver driver = new Driver("temp", "temp", "temp", "temp", "temp");
      
    for(Driver d: drivers.values()){
      if (d.getId().equals(driverId)){
        driver = d;
      }
    }

    //If temp driver has not been updated, driver ID is invalid
    if (driver.getId().equals("temp")){
      throw new InvalidIDException("Driver Account Not Found");
    }

    //Check if driver is available
    if (driver.getStatus() != Driver.Status.AVAILABLE){
      throw new InvalidStatusException("Driver is Already Driving");
    }

    //Set driver's address to input address
    driver.setAddress(address);

    //Set driver's zone to zone for given address
    driver.setZone(CityMap.getCityZone(address));

    }
    else{
      throw new InvalidAddressException("Invalid Address");
    }
    
  }

  //Set the inputted users from userList into the users Map
  public void setUsers(ArrayList<User> userList){
    for (User user: userList){
      users.put(user.getAccountId(), user);
    }
  }
  //Set the inputted drivers from driverList into the drivers Map
  public void setDrivers(ArrayList<Driver> driverList){
    for (Driver driver: driverList){
      drivers.put(driver.getId(), driver);
    }
  }
  //Create an array list of the current users using the users map
  public ArrayList<User> userMapToArray(Map<String, User> userMap){
    ArrayList<User> toReturn = new ArrayList<User>();
    
    //Add users to the array lisst
    for (User user: users.values()){
      toReturn.add(user);
    }
    return toReturn;
  }
  //Create an aray list of the current drivers using the drivers map
  public ArrayList<Driver> driverMapToArray(Map<String, Driver> driverMap){
    ArrayList<Driver> toReturn = new ArrayList<Driver>();
    
    //Add drivers to the array list
    for (Driver driver: drivers.values()){
      toReturn.add(driver);
    }
    return toReturn;
  }
  //Find the corresponding service request, given a zone and request number
  public ArrayList<ArrayList<TMUberService>> reqsToArray(Queue<TMUberService>[] queue){
    //Create an array list of arraylists of TMUberServices
    ArrayList<ArrayList<TMUberService>> services = new ArrayList<ArrayList<TMUberService>>();
    
    //Create an iterator for each zone
    for (int i = 0; i < queue.length; i ++){
      Iterator<TMUberService> iter = serviceRequests[i].iterator();
      ArrayList<TMUberService> zone = new ArrayList<TMUberService>();
      
      //If queue is not empty, add the service requests into the zone array list
      if (queue[i].size() != 0){
        while (iter.hasNext()){
          zone.add(iter.next());
        }
      }
      //Add the zone array list to the overall arraylist
      services.add(zone);
      
    }
    return services;

  }

}
//EXCEPTION CLASSES FOR TMUberSystemManager
//Exception for an invalid name
class InvalidNameException extends RuntimeException
{
  public InvalidNameException(String message)
  {
    super(message);
  }
}
//Exception for an invalid address
class InvalidAddressException extends RuntimeException
{
  public InvalidAddressException(String message)
  {
    super(message);
  }
}
//Exception for inavlid wallet value
class InvalidWalletException extends RuntimeException
{
  public InvalidWalletException(String message)
  {
    super(message);
  }
}
//Exception to indicate this driver or user already exists
class ExistingPersonException extends RuntimeException
{
  public ExistingPersonException(String message)
  {
    super(message);
  }
}
//Exception for invalid car model
class InvalidCarModelException extends RuntimeException
{
  public InvalidCarModelException(String message)
  {
    super(message);
  }
}
//Exception for invalid license plate
class InvalidLicenseException extends RuntimeException
{
  public InvalidLicenseException(String message)
  {
    super(message);
  }
}
//Exception for invalid ID
class InvalidIDException extends RuntimeException
{
  public InvalidIDException(String message)
  {
    super(message);
  }
}
//Exception for insufficient travel distance
class InsufficientDistanceException extends RuntimeException
{
  public InsufficientDistanceException(String message)
  {
    super(message);
  }
}
//Exception for already existing service request
class ExistingRequestException extends RuntimeException
{
  public ExistingRequestException(String message)
  {
    super(message);
  }
}
//Exception for an invalid service request
class InvalidRequestException extends RuntimeException
{
  public InvalidRequestException(String message)
  {
    super(message);
  }
}
//Exception for an invalid driver status
class InvalidStatusException extends RuntimeException
{
  public InvalidStatusException(String message)
  {
    super(message);
  }
}
