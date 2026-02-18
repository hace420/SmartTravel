package travel;

public class Hostel extends Accommadation {
    private int numberOfBeds;
    private double fees;

    // constructors
    public Hostel(){
        super();
        numberOfBeds =0;
        fees=0;
    }
    public Hostel(String name,String location,double pricePerNight,int numberOfBeds,double fees){
        super(name, location, pricePerNight);
        this.numberOfBeds = numberOfBeds;
        this.fees =fees;

    }
    public Hostel(Hostel other){
        super(other);
        this.numberOfBeds = other.numberOfBeds;
        this.fees = other.fees;

    }

    //  setters
    public void setNumberOfBeds(int numberOfBeds){
        this.numberOfBeds = numberOfBeds;
    }
    public void setFees(double fees){
        this.fees = fees;
    }

    // getters
    public int getNumberOfBeds(){
        return numberOfBeds;
    }
    public double getFees(){
        return fees;
    }

    // calculates total cost for hostel 
    @Override
    public double calculateTotalCost(int numberOfDays){
        return (this.getPricePerNight() * numberOfDays) + fees;
    }

    @Override
    public String toString(){
        return super.toString() + 
        "\nNumber of beds: " + numberOfBeds +
        "\nFees: " + fees;
    }

    @Override
    public boolean equals(Object obj) {
    if (obj == null) {
        return false;
    }

    if (getClass() != obj.getClass()) {
        return false;
    }
    Hostel compare = (Hostel) obj;
    if (super.equals(compare) &&
        this.numberOfBeds == compare.numberOfBeds &&
        this.fees == compare.fees) return true;
        else return false;

  }


    
    
}
