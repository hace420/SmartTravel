package travel;

public class Bus extends Transportation{
    private String busCompany;
    private int numberOfStops;
    private double busCost; // (base 20$ with surcharge of 1$ extra for every stop)

    public Bus(){
        super();
        busCompany = "";
        numberOfStops  = 0;
        busCost = 0;
    }
    public Bus(String companyName, String departureCity, String arrivalCity,String busCompany,int numberOfStops, double busCost) {
        super(companyName, departureCity, arrivalCity);
        this.numberOfStops = numberOfStops;
        this.busCompany = busCompany;
        this.busCost = busCost;

    }

    public Bus(Bus other){
        super(other);
        this.numberOfStops = other.numberOfStops;
        this.busCompany = other.busCompany;
        this.busCost = other.busCost;
    }

    // setters
    public void setBusCompany(String busCompany){
        this.busCompany = busCompany;
    }
    public void setNumberOfStops(int numberOfStops){
        this.numberOfStops = numberOfStops;
    }
    public void setBusCost(double busCost){
        this.busCost = busCost;
    }


    // getters
    public String getBusCompany(){
        return busCompany;
    }
    public int getNumberOfStops(){
        return numberOfStops;
    }
    public double getBusCost(){
        return busCost;
    }

    // mehthod to calculate total cost of bus 
    //base price 20 $ + 1$ for every stop
    //example 25 stops = (20$ + 25$ = 45$ for total bus trip)
    public double calculateTotalCost(int numberOfDays){
        return 20 + numberOfStops; // because 1$ per stop so no multiplication
    }

    @Override
    public String toString(){
        return (super.toString() + 
                "\nBus company: " + busCompany + 
                "\nNumber of Stops: "  + numberOfStops );
    }

    @Override
    public boolean equals(Object obj) {
    if (obj == null) {
        return false;
    }

    if (getClass() != obj.getClass()) {
        return false;
    }
    Bus compare = (Bus) obj;
    if (super.equals(compare) &&
        this.busCompany.equalsIgnoreCase(compare.busCompany) &&
        this.numberOfStops == compare.numberOfStops) return true;
        else return false;

  }
    
}
