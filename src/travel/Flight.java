package travel;

public class Flight extends Transportation {
    private String airlineName;
    private double luggageAllowance; //in KG

    // constructors
    public Flight(){
        super();
        airlineName = "";
        luggageAllowance = 0.0;
    }

    public Flight(String companyName, String departureCity, String arrivalCity, String airlineName, double luggageAllowance) {
        super(companyName, departureCity, arrivalCity); 
        this.airlineName = airlineName;
        this.luggageAllowance = luggageAllowance;
    }

    public Flight(Flight other){
        super(other);
        this.airlineName = other.airlineName;
        this.luggageAllowance = other.luggageAllowance;
    }

// setters 
public void setAirlineName(String airlineName){
    this.airlineName =airlineName;
}
public void setLuggageAllowance(double luggageAllowance){
    this.luggageAllowance = luggageAllowance;
}

// getters
public String getAirlineName(){
    return airlineName;
}
public double getLuggageAllowance(){
    return luggageAllowance;
}

@Override
public String toString(){
    return super.toString() + 
           "\nAirline Name: " + airlineName + 
           "\nLuggage Allowance: " + luggageAllowance;
}
@Override
public boolean equals(Object obj) {
    if (obj == null) {
        return false;
    }

    if (getClass() != obj.getClass()) {
        return false;
    }

    Flight compare = (Flight) obj;

    if (super.equals(compare) &&
        this.airlineName.equalsIgnoreCase(compare.airlineName) &&
        this.luggageAllowance == compare.luggageAllowance) {
        return true;
    } else {
        return false;
    }
}









}
