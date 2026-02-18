package travel;
import client.Client;

public class Trip {

  private static int nextId = 2001;
  final private String tripId;
  private String destination;
  private int duration;
  private double basePrice;       // base price for one night
  private Client client;               
  private Transportation transportation;
  private Accommadation accommadation;

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

  public Trip(String destination, int duration, double basePrice, Client client,Transportation transportation, Accommadation accommadation) {
    this.destination = destination;
    this.duration = duration;
    this.basePrice = basePrice;
    this.client = client;
    this.accommadation =accommadation;
    this.transportation =transportation;
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
    this.transportation = other.transportation;
    this.accommadation = other.accommadation;
  }

  public double calculateTotalCost(int numberOfDays) {
    double total = 0;
    total = basePrice*duration;
    if (transportation != null) {
        total += transportation.calculateTotalCost(numberOfDays);
    }

    if (accommadation != null) {
        total += accommadation.calculateTotalCost(numberOfDays);
    }

    return total;
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
  public Client getClient(){
    return client;
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
  public void setClient(Client client){
    this.client = client;
  }

 @Override
public String toString() {

    String result = "\nTrip id: " + tripId +
                    "\nDestination: " + destination +
                    "\nDuration: " + duration +
                    "\nBase Price: " + basePrice +
                    "\nTotal Cost: " + calculateTotalCost(duration);

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

    if (this.basePrice != compare.basePrice) {
        return false;
    }

    return true;
}


}
