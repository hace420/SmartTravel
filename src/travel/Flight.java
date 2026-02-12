package travel;

public class Flight extends Transportation {
    private String airlineName;
    private double luggageAllowance; //in KG
    private double ticketCost;
    private double luggageCost;

    // constructors
    public Flight(){
        super();
        airlineName = "";
        luggageAllowance = 0.0;
        ticketCost =0;
        luggageCost =0;
    }

    public Flight(String companyName, String departureCity, String arrivalCity, String airlineName, double luggageAllowance, double luggageCost, double ticketCost) {
        super(companyName, departureCity, arrivalCity); 
        this.airlineName = airlineName;
        this.luggageAllowance = luggageAllowance;
        this.ticketCost = ticketCost;
        this.luggageCost = luggageCost;
    }

    public Flight(Flight other){
        super(other);
        this.airlineName = other.airlineName;
        this.luggageAllowance = other.luggageAllowance;
        this.luggageCost = other.luggageCost;
        this.ticketCost = other.ticketCost;
    }

// setters 
public void setAirlineName(String airlineName){
    this.airlineName =airlineName;
}
public void setLuggageAllowance(double luggageAllowance){
    this.luggageAllowance = luggageAllowance;
}
public void setTicketCost(double ticketCost){
    this.ticketCost = ticketCost;
}
public void setLuggageCost(double luggageCost){
    this.luggageCost = luggageCost;
}

// getters
public String getAirlineName(){
    return airlineName;
}
public double getLuggageAllowance(){
    return luggageAllowance;
}
public double getTicketCost(){
    return ticketCost;
}
public double getLuggageCost(){
    return luggageCost;
}

// Calculating total cost of flight (ticket + luggage costs)
// number of days not needed as tickets cost is for round trip ticket 
@Override
public double calculateTotalCost(int numberOfDays){ 
    return ticketCost + luggageCost;
}

@Override
public String toString(){
    return super.toString() + 
           "\nAirline Name: " + airlineName + 
           "\nLuggage Allowance: " + luggageAllowance +
           "\nTicket cost: " + ticketCost +
           "\nLuggage cost: " + luggageCost;
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
        this.luggageAllowance == compare.luggageAllowance &&
        this.ticketCost == compare.ticketCost &&
        this.luggageCost == compare.luggageCost) {
        return true;
    } else {
        return false;
    }
}









}
