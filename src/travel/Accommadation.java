package travel;

import exceptions.InvalidAccommodationDataException;

public abstract  class Accommadation {
    private static int nextId = 4001;
    final private String AccommId;
    private String name;
    private double pricePerNight;
    private String location;


    // constructors
    public Accommadation(){
        name = "";
        location= "";
        AccommId = "A" + nextId;
        nextId++;
        pricePerNight =0;
    }
    public Accommadation(String name,String location,double pricePerNight)throws InvalidAccommodationDataException{    
        if (pricePerNight < 0) throw new InvalidAccommodationDataException("Price must be at a minimum of 0$");
        this.name = name;
        this.location = location;
        this.pricePerNight = pricePerNight;
        this.AccommId = "A" + nextId;
        nextId++;
    }
    public Accommadation(Accommadation other){
        this.name = other.name;
        this.location = other.location;
        this.pricePerNight = other.pricePerNight;
        this.AccommId = "A" + nextId;
        nextId++;
    }

    // setters
    public void setName(String name){
        this.name = name;
    }
    public void setLocation(String location){
        this.location = location;
    }
    public void setPricePerNight(double pricePerNight)throws InvalidAccommodationDataException{
        if (pricePerNight < 0) throw new InvalidAccommodationDataException("Price must be at a minimum of 0$");
        this.pricePerNight = pricePerNight;
    }

    // getters
    public String getName(){
        return name;
    }
    public String getLocation(){
        return location;
    }
    public double getPricePerNight(){
        return pricePerNight;
    }

    // method to calculate total cost of add-on transport fees
    public abstract double calculateTotalCost(int numberOfDays)throws InvalidAccommodationDataException;
    

    @Override
    public String toString(){
        return ("\nAccommadation Id: " + AccommId +
                "\nName: " +name + 
                "\nLocation: " + location +
                "\nPrice per night: " + pricePerNight + "$");
    }

   @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }

    if (getClass() != obj.getClass()) {
      return false;
    }
    Accommadation compare = (Accommadation) obj;
    if (this.name.equalsIgnoreCase(compare.name) &&
        this.location.equalsIgnoreCase(compare.location) &&
        this.pricePerNight == compare.pricePerNight) return true;
        else return false;
    }






    

}
