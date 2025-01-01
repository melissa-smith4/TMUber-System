import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

// Simulation of a Simple Command-line based Uber App 

// This system supports "ride sharing" service and a delivery service

public class TMUberUI
{
  public static void main(String[] args)
  {
    // Create the System Manager - the main system code is in here 

    TMUberSystemManager tmuber = new TMUberSystemManager();
    
    Scanner scanner = new Scanner(System.in);
    System.out.print("\nWelcome to TMUber! Enter any command to begin.\n");
    System.out.print(">");

    // Process keyboard actions
    while (scanner.hasNextLine())
    {
      try{
        String action = scanner.nextLine();

        if (action == null || action.equals("")) 
        {
          System.out.print("\n>");
          continue;
        }
        // Quit the App
        else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT")){
          System.out.print("\nThank you for using TMUber! See you next time.\n\n");
          return;
        }
        // Print all the registered drivers
        else if (action.equalsIgnoreCase("DRIVERS"))  // List all drivers
        {
          tmuber.listAllDrivers(); 
        }
        // Print all the registered users
        else if (action.equalsIgnoreCase("USERS"))  // List all users
        {
          tmuber.listAllUsers(); 
        }
        // Print all current ride requests or delivery requests
        else if (action.equalsIgnoreCase("REQUESTS"))  // List all requestsques
        {
          tmuber.listAllServiceRequests(); 
        }
        // Load all users from the users.txt file
        else if (action.equalsIgnoreCase("LOADUSERS")) 
        {
          //Get the file name
          String fileName = "";
          System.out.print("Users File: ");
          if (scanner.hasNextLine())
          {
            fileName = scanner.nextLine();
          }
          //Create an array list and send it to setUsers
          ArrayList<User> usersArray = TMUberRegistered.loadPreregisteredUsers(fileName);
          tmuber.setUsers(usersArray);
          System.out.println("Users loaded");
        }

        //Load all drivers from the drivers.txt file
        else if (action.equalsIgnoreCase("LOADDRIVERS")) 
        {
          //Get the file name
          String fileName = "";
          System.out.print("Drivers File: ");
          if (scanner.hasNextLine())
          {
            fileName = scanner.nextLine();
          }
          //Create an array list and send it to setDrivers
          ArrayList<Driver> driversArray = TMUberRegistered.loadPreregisteredDrivers(fileName);
          tmuber.setDrivers(driversArray);
          System.out.println("Drivers loaded");
        }

        // Register a new driver
        else if (action.equalsIgnoreCase("REGDRIVER")) 
        {
          String name = "";
          System.out.print("Name: ");
          if (scanner.hasNextLine())
          {
            name = scanner.nextLine();
          }
          String carModel = "";
          System.out.print("Car Model: ");
          if (scanner.hasNextLine())
          {
            carModel = scanner.nextLine();
          }
          String license = "";
          System.out.print("Car License: ");
          if (scanner.hasNextLine())
          {
            license = scanner.nextLine();
          }
          String address = "";
          System.out.print("Address: ");
          if (scanner.hasNextLine())
          {
            address = scanner.nextLine();
          }
          tmuber.registerNewDriver(name, carModel, license, address);
          System.out.printf("Driver: %-15s Car Model: %-15s License Plate: %-10s", name, carModel, license);
        }

        // Register a new user
        else if (action.equalsIgnoreCase("REGUSER")) 
        {
          String name = "";
          System.out.print("Name: ");
          if (scanner.hasNextLine())
          {
            name = scanner.nextLine();
          }
          String address = "";
          System.out.print("Address: ");
          if (scanner.hasNextLine())
          {
            address = scanner.nextLine();
          }
          double wallet = 0.0;
          System.out.print("Wallet: ");
          if (scanner.hasNextDouble())
          {
            wallet = scanner.nextDouble();
            scanner.nextLine(); // consume nl!! Only needed when mixing strings and int/double
          }
          tmuber.registerNewUser(name, address, wallet);
          System.out.printf("User: %-15s Address: %-15s Wallet: %2.2f", name, address, wallet);
        }
        // Request a ride
        else if (action.equalsIgnoreCase("REQRIDE")) 
        {
          // Get the following information from the user (on separate lines)
          // Then use the TMUberSystemManager requestRide() method properly to make a ride request
          // "User Account Id: "      (string)
          // "From Address: "         (string)
          // "To Address: "           (string)

          //Get account ID from user and set as accID
          String accID = "";
          System.out.print("User Account Id: ");
          if (scanner.hasNextLine())
          {
            accID = scanner.nextLine();
          }
          //Get from address from user and set as fromAddress
          String fromAddress = "";
          System.out.print("From Address: ");
          if (scanner.hasNextLine())
          {
            fromAddress = scanner.nextLine();
          }
          //Get to address from user and set as toAddress
          String toAddress = "";
          System.out.print("To Address: ");
          if (scanner.hasNextLine())
          {
            toAddress = scanner.nextLine();
          }
          tmuber.requestRide(accID, fromAddress, toAddress); 
          //Print information associated with newly created ride 
          System.out.printf("RIDE for: %-15s From: %-15s To: %-15s", tmuber.getUser(accID).getName(), fromAddress, toAddress);

        }

        // Request a food delivery
        else if (action.equalsIgnoreCase("REQDLVY")) 
        {
          // Get the following information from the user (on separate lines)
          // Then use the TMUberSystemManager requestDelivery() method properly to make a ride request
          // "User Account Id: "      (string)
          // "From Address: "         (string)
          // "To Address: "           (string)
          // "Restaurant: "           (string)
          // "Food Order #: "         (string)
        
          //Get account ID from user and set as accID
          String accID = "";
          System.out.print("User Account Id: ");
          if (scanner.hasNextLine())
          {
            accID = scanner.nextLine();
          }
          //Get from address from user and set as fromAddress
          String fromAddress = "";
          System.out.print("From Address: ");
          if (scanner.hasNextLine())
          {
            fromAddress = scanner.nextLine();
          }
          //Get to address from user and set as toAddress
          String toAddress = "";
          System.out.print("To Address: ");
          if (scanner.hasNextLine())
          {
            toAddress = scanner.nextLine();
          }
          //Get restaurant name from user and set as res
          String res = "";
          System.out.print("Restaurant: ");
          if (scanner.hasNextLine())
          {
            res = scanner.nextLine();
          }
          //Get food order number from user and set as fOrderNum
          String fOrderNum = "";
          System.out.print("Food Order #: ");
          if (scanner.hasNextLine())
          {
            fOrderNum = scanner.nextLine();
          }
          //Request the delivery
          tmuber.requestDelivery(accID, fromAddress, toAddress, res, fOrderNum);
          //Print information associated with newly created delivery 
          System.out.printf("DELIVERY for: %-15s From: %-15s To: %-15s", tmuber.getUser(accID).getName(), fromAddress, toAddress);
        }

        // Sort users by name
        else if (action.equalsIgnoreCase("SORTBYNAME")) 
        {
          tmuber.sortByUserName();
        }
        // Sort users by number of ride they have had
        else if (action.equalsIgnoreCase("SORTBYWALLET")) 
        {
          tmuber.sortByWallet();
        }

        // Cancel a current service (ride or delivery) request
        else if (action.equalsIgnoreCase("CANCELREQ")) 
        {
          //Get the request number
          int request = -1;
          System.out.print("Request #: ");
          if (scanner.hasNextInt())
          {
            request = scanner.nextInt();
            scanner.nextLine(); // consume nl character
          }
          //Get the zone number
          int zone = -1;
          System.out.print("Zone: ");
          if (scanner.hasNextInt())
          {
            zone = scanner.nextInt();
            scanner.nextLine(); // consume nl character
          }
          tmuber.cancelServiceRequest(request, zone);
          System.out.println("Service Request #" + request +  " in Zone " + zone + " Cancelled");
        }

        // Drop-off the user or the food delivery to the destination address
        else if (action.equalsIgnoreCase("DROPOFF")) 
        {
          // Get the driver's ID
          String driverID = "";
          System.out.print("Driver Id: ");
          if (scanner.hasNext())
          {
            driverID = scanner.next();
            scanner.nextLine(); // consume nl
          }

          tmuber.dropOff(driverID); 
          System.out.println("Driver " + driverID + " Dropping Off");
        }

        // Move driver to a new address
        else if (action.equalsIgnoreCase("DRIVETO")) 
        {
          //Get the driver's ID
          String driverID  = "";
          System.out.print("Driver Id: ");
          if (scanner.hasNextLine())
          {
            driverID = scanner.nextLine();
          }
          //Get the address
          String address  = "";
          System.out.print("Address: ");
          if (scanner.hasNextLine())
          {
            address = scanner.nextLine();
          }
          
          tmuber.driveTo(driverID, address);
          System.out.println("Driver " + driverID + " Now in Zone " + CityMap.getCityZone(address));
        }
        //Pickup a user ride or delivery
        else if (action.equalsIgnoreCase("PICKUP")) 
        {
          //Get the driver's ID
          String driverID  = "";
          System.out.print("Driver Id: ");
          if (scanner.hasNextLine())
          {
            driverID = scanner.nextLine();
          }
          //Call the pickup method using the driver's ID
          tmuber.pickup(driverID);
          System.out.println("Driver " + driverID + " Picking Up in Zone " + CityMap.getCityZone(tmuber.getDriver(driverID).getService().getFrom()));
        }

        // Get the Current Total Revenues
        else if (action.equalsIgnoreCase("REVENUES")) 
        {
          System.out.println("Total Revenue: " + tmuber.totalRevenue);
        }
        // Unit Test of Valid City Address 
        else if (action.equalsIgnoreCase("ADDR")) 
        {
          String address = "";
          System.out.print("Address: ");
          if (scanner.hasNextLine())
          {
            address = scanner.nextLine();
          }
          System.out.print(address);
          if (CityMap.validAddress(address))
            System.out.println("\nValid Address"); 
          else
            System.out.println("\nBad Address"); 
        }
        // Unit Test of CityMap Distance Method
        else if (action.equalsIgnoreCase("DIST")) 
        {
          String from = "";
          System.out.print("From: ");
          if (scanner.hasNextLine())
          {
            from = scanner.nextLine();
          }
          String to = "";
          System.out.print("To: ");
          if (scanner.hasNextLine())
          {
            to = scanner.nextLine();
          }
          System.out.print("\nFrom: " + from + " To: " + to);
          System.out.println("\nDistance: " + CityMap.getDistance(from, to) + " City Blocks");
        }
        
        System.out.print("\n>");
      }
      //CATCH BLOCKS FOR ALL THROWN EXCEPTIONS
      catch(FileNotFoundException e){
        System.out.println(e.getMessage() + "\n");
      }
      catch(IOException e){
        return;
      }
      catch(InvalidNameException e){
        System.out.println(e.getMessage() + "\n");
      }
      catch(InvalidAddressException e){
        System.out.println(e.getMessage() + "\n");
      }
      catch(InvalidWalletException e){
        System.out.println(e.getMessage() + "\n");
      }
      catch(ExistingPersonException e){
        System.out.println(e.getMessage() + "\n");
      }
      catch(InvalidCarModelException e){
        System.out.println(e.getMessage() + "\n");
      }
      catch(InvalidLicenseException e){
        System.out.println(e.getMessage() + "\n");
      }
      catch(InvalidIDException e){
        System.out.println(e.getMessage() + "\n");
      }
      catch(InsufficientDistanceException e){
        System.out.println(e.getMessage() + "\n");
      }
      catch(ExistingRequestException e){
        System.out.println(e.getMessage() + "\n");
      }  
      catch(InvalidRequestException e){
        System.out.println(e.getMessage() + "\n");
      }
      catch(InvalidStatusException e){
        System.out.println(e.getMessage() + "\n");
      }  
    }
  }
}

