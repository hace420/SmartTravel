import java.util.Scanner;
import client.Client;

public class Trip {

  private static int nextId = 2001;
  private String tripId;
  private String destination;
  private int duration;
  private double basePrice;
  private Client client;

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

  public double calculateTotalCost() {
    return basePrice;
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

}
