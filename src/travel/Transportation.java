//------------------------------------------
// Assignment (2)
// Question: ()
// Written by: (Christian Buckley 40329967)
//------------------------------------------
package travel;


public abstract class Transportation {

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

  public String getTripId(){
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



}
