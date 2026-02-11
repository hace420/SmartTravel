package travel;
import client.Client;

public class Trip {

  private static int nextId = 2001;
  final private String tripId;
  private String destination;
  private int duration;
  private double basePrice;
  private Client client;

  // constructors
  public Trip() {
    tripId = "T" + nextId;
    destination = "";
    duration = 0;
    basePrice = 0;
    client = new Client();
    nextId++;
  }

  public Trip(String destination, int duration, double basePrice, Client client) {
    this.destination = destination;
    this.duration = duration;
    this.basePrice = basePrice;
    this.client = client;
    this.tripId = "T" + nextId;
    nextId++;
  }

  public Trip(Trip other){
    this.destination = other.destination;
    this.basePrice = other.basePrice;
    this.duration = other.duration;
    this.tripId = "T"+ nextId;
    nextId++;
    this.client = other.client;
  }

  public double calculateTotalCost() {
    return basePrice*duration;
  }

  // getters
  public String getTripId() {
    return tripId;
  }

  public String getDestination() {
    return destination;
  }

  public int getDuration() {
    return duration;
  }

  // setters
  public void setDestination(String destination) {
    this.destination = destination;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public void setBasePrice(double basePrice) {
    this.basePrice = basePrice;
  }

 @Override
 public String toString(){
  return( "\nTrip id: " + tripId + 
         "\nDestination: " + destination +
         "\nDuration: " + duration +
         "\nBase Price: " + basePrice +
         "\nTotal cost: "+ this.calculateTotalCost() +
         "\nClient: " + client);

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
    if (this.client.equals(compare.client) && this.destination.equalsIgnoreCase(compare.destination) &&
        this.duration == compare.duration && this.basePrice == compare.basePrice) return true;
        else return false;
  }

}
