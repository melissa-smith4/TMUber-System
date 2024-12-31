import java.util.Arrays;
import java.util.Scanner;

// The city consists of a grid of 9 X 9 City Blocks

// Streets are east-west (1st street to 9th street)
// Avenues are north-south (1st avenue to 9th avenue)

// Example 1 of Interpreting an address:  "34 4th Street"
// A valid address *always* has 3 parts.
// Part 1: Street/Avenue residence numbers are always 2 digits (e.g. 34).
// Part 2: Must be 'n'th or 1st or 2nd or 3rd (e.g. where n => 1...9)
// Part 3: Must be "Street" or "Avenue" (case insensitive)

// Use the first digit of the residence number (e.g. 3 of the number 34) to determine the avenue.
// For distance calculation you need to identify the the specific city block - in this example 
// it is city block (3, 4) (3rd avenue and 4th street)

// Example 2 of Interpreting an address:  "51 7th Avenue"
// Use the first digit of the residence number (i.e. 5 of the number 51) to determine street.
// For distance calculation you need to identify the the specific city block - 
// in this example it is city block (7, 5) (7th avenue and 5th street)
//
// Distance in city blocks between (3, 4) and (7, 5) is then == 5 city blocks
// i.e. (7 - 3) + (5 - 4) 

public class CityMap
{
  // Checks for string consisting of all digits
  // An easier solution would use String method matches()
  private static boolean allDigits(String s)
  {
    for (int i = 0; i < s.length(); i++)
      if (!Character.isDigit(s.charAt(i)))
        return false;
    return  true;
  }

  // Get all parts of address string
  // An easier solution would use String method split()

  private static String[] getParts(String address)
  {
    String parts[] = new String[3];
    
    if (address == null || address.length() == 0)
    {
      parts = new String[0];
      return parts;
    }
    int numParts = 0;
    Scanner sc = new Scanner(address);
    while (sc.hasNext())
    {
      if (numParts >= 3)
        parts = Arrays.copyOf(parts, parts.length+1);

      parts[numParts] = sc.next();
      numParts++;
    }
    if (numParts == 1)
      parts = Arrays.copyOf(parts, 1);
    else if (numParts == 2)
      parts = Arrays.copyOf(parts, 2);
    return parts;
  }

  // Checks for a valid address
  public static boolean validAddress(String address)
  {
    // Make use of the helper methods above if you wish
    // There are quite a few error conditions to check for 
    // e.g. number of parts != 3
    String [] theseParts = getParts(address);
    //If number of parts does not equal 3
    if (theseParts.length != 3){
      return false;
    }
    //Check if lengths are accurate
    if(theseParts[0].length() != 2){
      return false;
    }
    if(theseParts[1].length() != 3){
      return false;
    }
    if (theseParts[2].length() != 6){
      return false;
    }
    //If third part does not equal address or avenue
    if (!(theseParts[2].equalsIgnoreCase("AVENUE")) && !(theseParts[2].equalsIgnoreCase("STREET"))){
      return false;
    }
    //If first part is not a two digit number
    if (!Character.isDigit(theseParts[0].charAt(0)) || !Character.isDigit(theseParts[0].charAt(1))){
      return false;
    }
    //If second part does not begin with a number
    char zeroIndex = theseParts[1].charAt(0);
    if (!Character.isDigit(zeroIndex)){
      return false;
    }
    //If second part begins with a number 4 through 9
    if (zeroIndex == '4' || zeroIndex == '5' || zeroIndex == '6' || zeroIndex == '7' || zeroIndex == '8' || zeroIndex == '9'){
      //If second part doesnt end in "th", return false
      if (!(theseParts[1].substring(1).equalsIgnoreCase("th"))){
        return false;
      }
    }//If second part begins with a 1 and doesnt end in "st"
    if (zeroIndex == '1'){
      if (!(theseParts[1].substring(1).equalsIgnoreCase("st"))){
        return false;
      }
    }//If second part begns with a 2 and doesnt end in "nd"
    if (zeroIndex == '2'){
      if (!(theseParts[1].substring(1).equalsIgnoreCase("nd"))){
        return false;
      }
    }//If second part begins with a 3 and doesnt end in "rd"
    if (zeroIndex == '3'){
      if (!(theseParts[1].substring(1).equalsIgnoreCase("rd"))){
        return false;
      }
    }
    return true;
  }

  // Computes the city block coordinates from an address string
  // returns an int array of size 2. e.g. [3, 4] 
  // where 3 is the avenue and 4 the street
  // See comments at the top for a more detailed explanation
  public static int[] getCityBlock(String address)
  {
    int[] block = {-1, -1};
    
    //Get the parts for the given new address
    String [] theseParts = getParts(address);
    
    //Variables to retrieve and add into block array
    int avenue = 0;
    int street = 0;
    
    //If the ending word of the address is any case of "Avenue", get the street and avenue numbers accordingly
    if (theseParts[2].equalsIgnoreCase("AVENUE")){
      avenue = theseParts[1].charAt(0);
      street = theseParts[0].charAt(0);
      block[0] = avenue;
      block[1] = street;
    }
    //If the ending word of the address is any case of "Street", get the street and avenue numbers accordingly
    if (theseParts[2].equalsIgnoreCase("STREET")){
      street = theseParts[1].charAt(0);
      avenue = theseParts[0].charAt(0);
      block[0] = avenue;
      block[1] = street;
    }
    return block;
  }
  
  // Calculates the distance in city blocks between the 'from' address and 'to' address
  
  public static int getDistance(String from, String to)
  {
    
    //If from address or to address are invalid, return a distance of 0 blocks
    if (!validAddress(from) || !validAddress(to)){
      return 0;
    }

    //Get the cityBlocks for each address
    int [] fromBlock = getCityBlock(from);
    int [] toBlock = getCityBlock(to);

    //Subtracting avenues
    int avDist = Math.abs(toBlock[0] - fromBlock[0]);

    //Subtracting streets
    int stDist = Math.abs(toBlock[1] - fromBlock[1]);

    //Adding to get overall distance
    int distance = avDist + stDist;
    return distance;

  }

  //Gets the zone for a given address
  public static int getCityZone(String address){
    //If address is valid, proceed
    if (validAddress(address)){
      //Get parts so we can compare address ending
      String[] parts = getParts(address);
      
      //If address ends in street, get zones accordingly
      if (parts[2].equalsIgnoreCase("STREET")){
        int street = parts[1].charAt(0);
        int avenue = parts[0].charAt(0);

        //Zones 0 and 1
        if (street == '6' || street == '7' || street == '8' || street == '9'){
          if (avenue == '1' || avenue == '2' || avenue == '3' || avenue == '4' || avenue == '5'){
            return 0;
          }
          if (avenue == '6' || avenue == '7' || avenue == '8' || avenue == '9'){
            return 1;
          }
        }
        //Zones 2 and 3
        if (street == '1' || street == '2' || street == '3' || street == '4' || street == '5'){
          if (avenue == '1' || avenue == '2' || avenue == '3' || avenue == '4' || avenue == '5'){
            return 3;
          }
          if (avenue == '6' || avenue == '7' || avenue == '8' || avenue == '9'){
            return 2;
          }
          return 1;
        }
      }
      
      //If address ends in avenue, get zones accordingly
      if (parts[2].equalsIgnoreCase("AVENUE")){
        int street = parts[0].charAt(0);
        int avenue = parts[1].charAt(0);

        //Zones 1 and 2
        if (avenue == '6' || avenue == '7' || avenue == '8' || avenue == '9'){
          if (street == '1' || street == '2' || street == '3' || street == '4' || street == '5'){
            return 2;
          }
          if (street == '6' || street == '7' || street == '8' || street == '9'){
            return 1;
          }
        }
        //Zones 0 and 3
        if (avenue == '1' || avenue == '2' || avenue == '3' || avenue == '4' || avenue == '5'){
          if (street == '1' || street == '2' || street == '3' || street == '4' || street == '5'){
            return 3;
          }
          if (street == '6' || street == '7' || street == '8' || street == '9'){
            return 0;
          }
          return 0;
        }
      }

    }
    //If address is not valid, return -1
    return -1;
  }
}
