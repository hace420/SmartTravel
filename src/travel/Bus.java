package travel;

public class Bus extends Transportation{
    private String busCompany;
    private int numberOfStops;

    public Bus(){
        super();
        busCompany = "";
        numberOfStops  = 0;
    }
    public Bus(String companyName, String departureCity, String arrivalCity,String busCompany,int numberOfStops) {
        super(companyName, departureCity, arrivalCity);
        this.numberOfStops = numberOfStops;
        this.busCompany = busCompany;

    }

    public Bus(Bus other){
        super(other);
        this.numberOfStops = other.numberOfStops;
        this.busCompany = other.busCompany;
    }

    // setters
    public void setBusCompany(String busCompany){
        this.busCompany = busCompany;
    }
    public void setNumberOfStops(int numberOfStops){
        this.numberOfStops = numberOfStops;
    }

    // getters
    public String getBusCompany(){
        return busCompany;
    }
    public int getNumberOfStops(){
        return numberOfStops;
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
