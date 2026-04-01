//------------------------------------------
// Assignment (2)
// Question: ()
// Written by: (Christian Buckley 40329967)
//------------------------------------------
package travel;


import exceptions.*;
import interfaces.CsvPersistable;
import interfaces.Identifiable;

public abstract class Transportation implements Identifiable, CsvPersistable{

  private static int nextId = 3001;
  final private String tripId;
  private String companyName;
  private String departureCity;
  private String arrivalCity;

 // constructors
  public Transportation(){
    tripId = "TR" + nextId;
    nextId++;
    companyName = "";
    departureCity= "";
    arrivalCity = "";
    
  } 

  public Transportation(String companyName, String departureCity, String arrivalCity){
    this.companyName = companyName;
    this.departureCity = departureCity;
    this.arrivalCity = arrivalCity;
    tripId = "TR" + nextId;
    nextId++;


  }
  // used for file loading/saving
  public Transportation(String tripId, String companyName, String departureCity, String arrivalCity){
    this.companyName = companyName;
    this.departureCity = departureCity;
    this.arrivalCity = arrivalCity;
    this.tripId = tripId;
  }

  public Transportation(Transportation other){
    this.companyName = other.companyName;
    this.departureCity = other.departureCity;
    this.arrivalCity = other.arrivalCity;
    this.tripId = "TR" + nextId;
    nextId++;
    
  }

  // setters
  
  public void setCompanyName(String companyName){
    this.companyName = companyName;
  }
  public void setDepartureCity(String departureCity){
    this.departureCity =departureCity;
  }
  public void setArrivalCity(String arrivalCity){
    this.arrivalCity = arrivalCity;
  }

  //getters 

  public String getId(){
    return tripId;
  }
  public String getCompanyName(){
    return companyName;
  }
  public String getDepartureCity(){
    return departureCity;
  }
  public String getArrivalCity(){
    return arrivalCity;
  }

  // method to calculate total cost of add-on transport fees
  public abstract double calculateTotalCost(int numberOfDays);

  

  @Override
  public String toString(){
    return ("\nTransport id: " + tripId +
            "\nCompany name: " + companyName + 
            "\nDeparture City: " + departureCity +
            "\nArrival City: " + arrivalCity);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }

    if (getClass() != obj.getClass()) {
      return false;
    }
    Transportation compare  = (Transportation) obj;
    if (this.companyName.equalsIgnoreCase(compare.companyName) &&
        this.departureCity.equalsIgnoreCase(compare.departureCity) &&
        this.arrivalCity.equalsIgnoreCase(compare.arrivalCity)) return true;
        else return false;
  }

  // used for file loading/saving to keep tripid count
  public static void updateTripId(int id){
    nextId = id;
  }

  // csv persistency
  protected abstract String getType();


  // base details of accommodation
  public String baseCsvRow(){
        return (this.getType()+";"+tripId+";"+companyName+";"+departureCity+";"+arrivalCity);

    }
    // subcvlasses add there extra fields
    protected abstract String getExtraCsvFields();

    // added both previous methods to create final csv row
    public String toCsvRow(){
        return baseCsvRow() + this.getExtraCsvFields();
    }

    public static Transportation fromCsvRow(String csvline) throws InvalidTransportDataException{
      String[] tokens = csvline.split(";");
      if (tokens.length < 5 ){
        throw new InvalidTransportDataException("Invalid transportation CSV line: " + csvline);
      }
      String type = tokens[0];
      String id = tokens[1];
      String name = tokens[2];
      String depCity = tokens[3];
      String arivCity = tokens[4];

      if ("TRAIN".equalsIgnoreCase(type)){
        if (tokens.length != 8){
          throw new InvalidTransportDataException("Invalid transportation CSV line: " + csvline);
        }
        TrainType trainType= null;
        SeatClass seatClass=null;
        if ("HIGH_SPEED".equalsIgnoreCase(tokens[5])){
           trainType = TrainType.HIGH_SPEED;
        } else if ("LONG_DISTANCE".equalsIgnoreCase(tokens[5])){
           trainType = TrainType.LONG_DISTANCE;
        } else if ("ECONOMY".equalsIgnoreCase(tokens[5])){
           trainType = TrainType.ECONOMY;
        } else {
          throw new InvalidTransportDataException("Invalid train type CSV line: " + csvline);
        }
        if ("FIRST_CLASS".equalsIgnoreCase(tokens[6])){
          seatClass = SeatClass.FIRST_CLASS;
        } else if ("BUSINESS".equalsIgnoreCase(tokens[6])){
           seatClass = SeatClass.BUSINESS;
        } else if ("ECONOMY".equalsIgnoreCase(tokens[6])){
           seatClass = SeatClass.ECONOMY;
        } else {
          throw new InvalidTransportDataException("Invalid seat class CSV line: " + csvline);
        }
        double cost =0;
        try {
          cost = Double.parseDouble(tokens[7]);
        } catch (NumberFormatException ex) {
                throw new InvalidTransportDataException("Invalid numeric data in line: " + csvline);
        }
        return new Train(id, name, depCity, arivCity, trainType, seatClass, cost);      
        
      } else if ("FLIGHT".equalsIgnoreCase(type)){
        if (tokens.length != 9){
          throw new InvalidTransportDataException("Invalid transportation CSV line: " + csvline);
        }
        String airlineName = tokens[5];
        double luggageAllowance=0; //in KG
        double ticketCost=0;
        double luggageCost=0;
        try {
          luggageAllowance = Double.parseDouble(tokens[6]);
          ticketCost = Double.parseDouble(tokens[7]);
          luggageCost = Double.parseDouble(tokens[8]);
        } catch (NumberFormatException ex) {
                throw new InvalidTransportDataException("Invalid numeric data in flight line: " + csvline);
        }
        return new Flight(id, name, depCity, arivCity, airlineName, luggageAllowance, luggageCost, ticketCost);
      } else if ("BUS".equalsIgnoreCase(type)){
        if (tokens.length != 8){
          throw new InvalidTransportDataException("Invalid transportation CSV line: " + csvline);
        }
        String compName = tokens[5];
        int stops=0;
        double cost=0;
        try {
          stops = Integer.parseInt(tokens[6]);
          cost = Double.parseDouble(tokens[7]);
        } catch (NumberFormatException ex) {
          throw new InvalidTransportDataException("Invalid numeric data in Bus line: " + csvline);
        }
        return new Bus(id, name, depCity, arivCity, compName, stops, cost);
      } else {
        throw new InvalidTransportDataException("Invalid type of transport: " + csvline);
      }

    }




}
