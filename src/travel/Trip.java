//------------------------------------------
// Assignment (3)
// Question: ()
// Written by: (Christian Buckley 40329967)
//------------------------------------------
package travel;
import client.Client;
import exceptions.*;
import interfaces.Billable;
import interfaces.CsvPersistable;
import interfaces.Identifiable;
import persistence.ErrorLogger;


public class Trip implements Identifiable, CsvPersistable, Billable, Comparable<Trip>{

  private static int nextId = 2001;
  final private String tripId;
  private String destination;
  private int duration;
  private double basePrice;       // base price for one night
  private Client client;               
  private Transportation transportation;
  private Accommadation accommadation;
  // used for loading csv lines storing id temporarelly until all list are fully loaded.
  private transient String clientIdTemp;
  private transient String transportIdTemp;
  private transient String accommIdTemp;

  // constructors
  public Trip() {
    tripId = "T" + nextId;
    destination = "";
    duration = 0;
    basePrice = 0;
    this.client =null;
    this.accommadation = null;
    this.transportation = null;
    nextId++;
  }

  public Trip(String destination, int duration, double basePrice, Client client,Transportation transportation, Accommadation accommadation) throws InvalidTripDataException{
    if (basePrice < 100.00) throw new InvalidTripDataException("Base price must be greater than or equal to 100.00$");
    if (duration < 1 || duration > 20) throw new InvalidTripDataException("Duration must be between 1-20 days");
    if (client == null) throw new InvalidTripDataException("Client does not exist in system");

    this.destination = destination;
    this.duration = duration;
    this.basePrice = basePrice;
    this.client = client;
    this.accommadation =accommadation;
    this.transportation =transportation;
    this.tripId = "T" + nextId;
    nextId++;
  }
  public Trip(String tripId,String destination, int duration, double basePrice, Client client,Transportation transportation, Accommadation accommadation) throws InvalidTripDataException{
    if (basePrice < 100.00) throw new InvalidTripDataException("Base price must be greater than or equal to 100.00$");
    if (duration < 1 || duration > 20) throw new InvalidTripDataException("Duration must be between 1-20 days");

    this.destination = destination;
    this.duration = duration;
    this.basePrice = basePrice;
    this.client = client;
    this.accommadation =accommadation;
    this.transportation =transportation;
    this.tripId = tripId;
    
  }

  public Trip(Trip other){
    this.destination = other.destination;
    this.basePrice = other.basePrice;
    this.duration = other.duration;
    this.tripId = "T"+ nextId;
    nextId++;
    this.client = other.client;
    this.transportation = other.transportation;
    this.accommadation = other.accommadation;
  }

  public double calculateTotalCost(int numberOfDays) throws InvalidAccommodationDataException{
    double total = 0;
    if (numberOfDays <= 0 ) throw new InvalidAccommodationDataException("Number of days needs to be greater to or equal to 1");
    total = basePrice*numberOfDays;
    if (transportation != null) {
        total += transportation.calculateTotalCost(numberOfDays);
    }

    if (accommadation != null) {
        total += accommadation.calculateTotalCost(numberOfDays);
    }

    return total;
  }

  // getters
  public double getTotalCost(){
    double cost =0;
    try {
       cost = this.calculateTotalCost(duration);
    } catch (InvalidAccommodationDataException ex) {
      ErrorLogger.log(ex.getMessage());
    }
    return cost;
    
  }
  public String getId() {
    return tripId;
  }

  public String getDestination() {
    return destination;
  }

  public int getDuration() {
    return duration;
  }
  public Client getClient(){
    return client;
  }
  public Transportation getTransportation(){
    return transportation;
  }
  public Accommadation getAccommadation(){
    return accommadation;
  }
  public double getBasePrice(){
    return basePrice;
  }
  public String getClientIdTemp(){
    return clientIdTemp;
  }
  public String getTransportIdTemp(){
    return transportIdTemp;
  }
  public String getAccommodationIdTemp(){
    return accommIdTemp;
  }


  // setters
  public void setDestination(String destination) {
    this.destination = destination;
  }

  public void setDuration(int duration) throws InvalidTripDataException{
    if (duration < 1 || duration > 20) throw new InvalidTripDataException("Duration must be between 1-20 days");
    this.duration = duration;
  }

  public void setBasePrice(double basePrice) throws InvalidTripDataException{
    if (basePrice < 100.00) throw new InvalidTripDataException("Base price must be greater than or equal to 100.00$");
    this.basePrice = basePrice;
  }
  public void setClient(Client client) throws InvalidTripDataException{
    if (client == null) throw new InvalidTripDataException("Client does not exist in system");
    this.client = client;
  }
  public void setTransportation(Transportation transportation){
    this.transportation = transportation;
  }
  public void setAccommadation(Accommadation accommadation){
    this.accommadation = accommadation;
  }
  

 @Override
public String toString() {
    double totalCost =0;
    try {
        totalCost = calculateTotalCost(duration);
    } catch (InvalidAccommodationDataException ex) {
      System.out.println("Error: "+ex.getMessage());
    }

    String result = "\nTrip id: " + tripId +
                    "\nDestination: " + destination +
                    "\nDuration: " + duration +
                    "\nBase Price: " + basePrice +
                    "\nTotal Cost: " + totalCost;

    if (transportation != null) {
        result += "\n" + transportation.toString();
    } else {
        result += "\nTransportation: none";
    }

    if (accommadation != null) {
        result += "\n" + accommadation.toString();
    } else {
        result += "\nAccommodation: none";
    }

    if (client != null) {
        result += "\n" + client.toString();
    } else {
        result += "\nClient: none";
    }

    return result;
}

  @Override
public boolean equals(Object obj) {

    if (obj == null) {
        return false;
    }

    if (getClass() != obj.getClass()) {
        return false;
    }

    Trip compare = (Trip) obj;

    // Check client
    if (this.client == null) {
        if (compare.client != null) {
            return false;
        }
    } else {
        if (!this.client.equals(compare.client)) {
            return false;
        }
    }

    // Check transportation
    if (this.transportation == null) {
        if (compare.transportation != null) {
            return false;
        }
    } else {
        if (!this.transportation.equals(compare.transportation)) {
            return false;
        }
    }

    // Check accommodation
    if (this.accommadation == null) {
        if (compare.accommadation != null) {
            return false;
        }
    } else {
        if (!this.accommadation.equals(compare.accommadation)) {
            return false;
        }
    }

    // Check simple fields
    if (!this.destination.equalsIgnoreCase(compare.destination)) {
        return false;
    }

    if (this.duration != compare.duration) {
        return false;
    }

    if (Double.compare(this.basePrice, compare.basePrice) != 0) {
        return false;
    }

    return true;
}
    public static void updateNextId(int id){
            nextId = id;
        }
  // used for dashboard
        public String getClientId(){
          if (client != null){
            return client.getId();
          } else {
            return "No Client can be found linked to this trip";
          }
        }

  public String toCsvRow() {
    return tripId + ";" +
           (client != null ? client.getId() : "") + ";" + // shortend if else checks is client != null if yes returns client.getId if no then returns ""
           (accommadation != null ? accommadation.getId() : "") + ";" +
           (transportation != null ? transportation.getId() : "") + ";" +
           destination + ";" +
           duration + ";" +
           basePrice;
}
  // Sets temporary IDs during loading (to be resolved later)
    public void setReferenceIds(String clientId, String transportId, String accommodationId) {
        this.clientIdTemp = clientId;
        this.transportIdTemp = transportId;
        this.accommIdTemp = accommodationId;
    }

  public static Trip fromCsvRow(String csvline) throws InvalidTripDataException {
    String[] tokens = csvline.split(";");
    if (tokens.length != 7) {
        throw new InvalidTripDataException("Invalid CSV format for trip: " + csvline);
    }
    String tripId = tokens[0];
    String clientId = "null".equals(tokens[1]) ? "" : tokens[1]; // checks if tokens[1] is null is so assignes "" if not then keeps original value passed in from csv file
    String accommodationId = "null".equals(tokens[2]) ? "" : tokens[2];
    String transportId = "null".equals(tokens[3]) ? "" : tokens[3];
    String destination = tokens[4];
    int duration;
    double basePrice;
    try {
        duration = Integer.parseInt(tokens[5]);
        basePrice = Double.parseDouble(tokens[6]);
    } catch (NumberFormatException e) {
        throw new InvalidTripDataException("Invalid numeric data in trip line: " + csvline);
    }
    Trip t = new Trip(tripId, destination, duration, basePrice, null, null, null);
    t.setReferenceIds(clientId, transportId, accommodationId);
    return t;
}

   public int compareTo(Trip other){
   
       return  Double.compare(other.getTotalCost(), this.getTotalCost());
    
   
  }




}