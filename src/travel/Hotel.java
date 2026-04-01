//------------------------------------------
// Assignment (2)
// Question: ()
// Written by: (Christian Buckley 40329967)
//------------------------------------------
package travel;

import exceptions.InvalidAccommodationDataException;

public class Hotel extends Accommadation{
    private int numberOfStars; // rating goes from 1 to 5 (5 being the best)
    private double serviceFees; // cost that will be added to pricePerNight (fee will only be charged once not per night)
    
    // constructors
    public Hotel(){
        super();
        numberOfStars = 0;
    }
    public Hotel(String name,String location,double pricePerNight,int numberOfStars,double serviceFees)throws InvalidAccommodationDataException{
        if (numberOfStars < 1 || numberOfStars >5) throw new InvalidAccommodationDataException("Stars must be between 1-5");
        super(name, location, pricePerNight);
        this.numberOfStars = numberOfStars;
        this.serviceFees = serviceFees;
    }
    public Hotel(String AccommId,String name,String location,double pricePerNight,int numberOfStars,double serviceFees)throws InvalidAccommodationDataException{
        if (numberOfStars < 1 || numberOfStars >5) throw new InvalidAccommodationDataException("Stars must be between 1-5");
        super(AccommId,name, location, pricePerNight);
        this.numberOfStars = numberOfStars;
        this.serviceFees = serviceFees;
    }
    public Hotel(Hotel other){
        super(other);
        this.numberOfStars = other.numberOfStars;
        this.serviceFees = other.serviceFees;
    }

    // setters
    public void setNumberOfStars(int numberOfStars){
        this.numberOfStars = numberOfStars;
    }
    public void setServiceFees(double serviceFees){
        this.serviceFees = serviceFees;
    }

    // getters
    public int getNumberOfStars()throws InvalidAccommodationDataException{
        if (numberOfStars < 1 || numberOfStars >5) throw new InvalidAccommodationDataException("Stars must be between 1-5");
        return numberOfStars;
    }
    public double getServiceFees(){
        return serviceFees;
    }

    // calculates total cost service fees are only added at the end not charged daily
    @Override
    public double calculateTotalCost(int numberOfDays)throws InvalidAccommodationDataException{
        if (numberOfDays <= 0 ) throw new InvalidAccommodationDataException("NUmber of days needs to be greater to or equal to 1");
        return (this.getPricePerNight() * numberOfDays) + serviceFees;
    }

    @Override
    public String toString(){
        return  "HOTEL"+
                super.toString() +
                "\nNumber of stars(1-5): " + numberOfStars +
                "\nService fees: " + serviceFees;
    }
  
     @Override
    public boolean equals(Object obj) {
    if (obj == null) {
        return false;
    }

    if (getClass() != obj.getClass()) {
        return false;
    }
    Hotel compare = (Hotel) obj;
    if (super.equals(compare) &&
        this.numberOfStars == compare.numberOfStars &&
        this.serviceFees == compare.serviceFees) return true;
        else return false;

  }
    public String getType(){
        return "HOTEL";
    }
    public String getExtraCsvFields(){
        return (";"+serviceFees+";"+numberOfStars);
    }






}
