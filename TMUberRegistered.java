import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class TMUberRegistered
{
    // These variables are used to generate user account and driver ids
    private static int firstUserAccountID = 900;
    private static int firstDriverId = 700;

    // Generate a new user account id
    public static String generateUserAccountId(ArrayList<User> current)
    {
        return "" + firstUserAccountID + current.size();
    }

    // Generate a new driver id
    public static String generateDriverId(ArrayList<Driver> current)
    {
        return "" + firstDriverId + current.size();
    }

    //Load users fron an input file
    public static ArrayList<User> loadPreregisteredUsers(String inputFile) throws IOException
    {
        //Create the array list that the method will return
        ArrayList<User> fileUsers = new ArrayList<User>();
        
        //Create the file and feed it into the scanner
        File userFile = new File(inputFile);
        
        //If file does not exist, throw FileNotFound exception
        if (!userFile.exists()){
            throw new FileNotFoundException(inputFile + " Not Found");
        }

        Scanner in = new Scanner(userFile);

        //Iterate through the file and fill up the array list
        while(in.hasNextLine()){
            String name = in.nextLine();
            String address = in.nextLine();
            double wallet = Double.parseDouble(in.nextLine());

            //Create the userID
            String ID = generateUserAccountId(fileUsers);

            fileUsers.add(new User(ID, name, address, wallet));
        }
        in.close();
        return fileUsers;

    }

    //Load drivers from an input file
    public static ArrayList<Driver> loadPreregisteredDrivers(String inputFile) throws IOException
    {
        //Create the array list that the method will return
        ArrayList<Driver> fileDrivers = new ArrayList<Driver>();
        
        //Create the file and feed it into the scanner
        File driverFile = new File(inputFile);
        
        //If file does not exist, throw FileNotFound exception
        if (!driverFile.exists()){
            throw new FileNotFoundException(inputFile + " Not Found");
        }
        
        Scanner in = new Scanner(driverFile);

        //Iterate through the file and fill up the array list
        while(in.hasNextLine()){
            String name = in.nextLine();
            String carModel = in.nextLine();
            String licensePlate = in.nextLine();
            String address = in.nextLine();
            

            //Create the userID
            String ID = generateDriverId(fileDrivers);

            fileDrivers.add(new Driver(ID, name, carModel, licensePlate, address));
        }
        in.close();
        return fileDrivers;
    }
}

