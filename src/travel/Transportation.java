package travel;

public class Transportation {

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
  public String getCompanyName(){
    return companyName;
  }
  public String getDepartureCity(){
    return departureCity;
  }
  public String getArrivalCity(){
    return arrivalCity;
  }

  @Override
  public String toString(){
    return ("Trip id: " + tripId +
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



}
