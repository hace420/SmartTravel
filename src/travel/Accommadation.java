//------------------------------------------
// Assignment (3)
// Question: ()
// Written by: (Christian Buckley 40329967)
//------------------------------------------
package travel;

import exceptions.InvalidAccommodationDataException;
import interfaces.*;

public abstract  class Accommadation implements Identifiable, CsvPersistable, Comparable<Accommadation>{
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
    public Accommadation(String AccommId,String name,String location,double pricePerNight)throws InvalidAccommodationDataException{    
        if (pricePerNight < 0) throw new InvalidAccommodationDataException("Price must be at a minimum of 0$");
        this.name = name;
        this.location = location;
        this.pricePerNight = pricePerNight;
        this.AccommId = AccommId;
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
    public String getId(){
        return AccommId;
    }
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
    public static void updateAccommId(int id){
        nextId = id;
    }
    // csv persistable 

    protected abstract String getType();

    // base details of accommodation
    public String baseCsvRow(){
        return (this.getType()+";"+AccommId+";"+name+";"+location+";"+pricePerNight);

    }
    // subcvlasses add there extra fields
    protected abstract String getExtraCsvFields();

    // added both previous methods to create final csv row
    public String toCsvRow(){
        return baseCsvRow() + getExtraCsvFields();
    }


    public static Accommadation fromCsvRow(String csvline) throws InvalidAccommodationDataException {
        String[] tokens = csvline.split(";");
        if (tokens.length < 5) {
            throw new InvalidAccommodationDataException("Invalid accommodation CSV line: " + csvline);
        }
        String type = tokens[0];
        String accommId = tokens[1];
        String name = tokens[2];
        String location = tokens[3];

        double price;
        try {
            price = Double.parseDouble(tokens[4]);
        } catch (NumberFormatException ex) {
            throw new InvalidAccommodationDataException("Invalid price in accommodation line: " + csvline);
        }

        if ("HOTEL".equals(type)) {
            if (tokens.length != 7) {
                throw new InvalidAccommodationDataException("Invalid Hotel CSV line (wrong field count): " + csvline);
            }
            double fees;
            int stars;
            try {
                fees = Double.parseDouble(tokens[5]);
                stars = Integer.parseInt(tokens[6]);
            } catch (NumberFormatException ex) {
                throw new InvalidAccommodationDataException("Invalid numeric data in Hotel line: " + csvline);
            }
            return new Hotel(accommId, name, location, price, stars, fees);

        } else if ("HOSTEL".equals(type)) {
            if (tokens.length != 7) {
                throw new InvalidAccommodationDataException("Invalid Hostel CSV line (wrong field count): " + csvline);
            }
            double extraFees;
            int beds;
            try {
                extraFees = Double.parseDouble(tokens[5]);
                beds = Integer.parseInt(tokens[6]);
            } catch (NumberFormatException ex) {
                throw new InvalidAccommodationDataException("Invalid numeric data in Hostel line: " + csvline);
            }
            return new Hostel(accommId, name, location, price, beds, extraFees);

        } else {
            throw new InvalidAccommodationDataException("Unknown accommodation type: " + type);
        }
    }

    // comparable
    public int compareTo(Accommadation other){
        return Double.compare(other.pricePerNight, this.pricePerNight);
        
    
   
  }






    

}
