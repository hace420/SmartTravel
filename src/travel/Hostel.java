package travel;
import exceptions.*;

public class Hostel extends Accommadation {
    private int numberOfBeds;
    private double fees;

    // constructors
    public Hostel(){
        super();
        numberOfBeds =0;
        fees=0;
    }
    public Hostel(String name,String location,double pricePerNight,int numberOfBeds,double fees)throws InvalidAccommodationDataException{
        if (pricePerNight > 150) throw new InvalidAccommodationDataException("Price per night for Hostel is at the most 150$");
        super(name, location, pricePerNight);
        this.numberOfBeds = numberOfBeds;
        this.fees =fees;

    }
    public Hostel(String AccommId,String name,String location,double pricePerNight,int numberOfBeds,double fees)throws InvalidAccommodationDataException{
        if (pricePerNight > 150) throw new InvalidAccommodationDataException("Price per night for Hostel is at the most 150$");
        super(AccommId,name, location, pricePerNight);
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
    public double calculateTotalCost(int numberOfDays)throws InvalidAccommodationDataException{
        if (numberOfDays <= 0 ) throw new InvalidAccommodationDataException("NUmber of days needs to be greater to or equal to 1");
        return (this.getPricePerNight() * numberOfDays) + fees;
    }

    @Override
    public String toString(){
        return "HOSTEL\n"+
        super.toString() + 
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
