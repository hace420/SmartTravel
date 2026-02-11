package travel;

public class Train extends Transportation{
    private String trainType;
    private String seatClass;

    public Train(){
        super();
        trainType = "";
        seatClass = "";
    }
    public Train(String companyName, String departureCity, String arrivalCity, String trainType, String seatClass) {
        super(companyName, departureCity, arrivalCity);
        this.trainType = trainType;
        this.seatClass = seatClass;

    }

    public Train(Train other){
        super(other);
        this.trainType = other.trainType;
        this.seatClass = other.seatClass;
    }

    // setters
    public void setTrainType(String trainType){
            this.trainType = trainType;
    }
    public void setSeatClass(String seatClass){
        this.seatClass = seatClass;
    }

    // getters
    public String getTrainType(){
        return trainType;
    }
    public String getSeatClass(){
        return seatClass;
    }
    
    @Override
    public String toString(){
        return (super.toString() + 
                "\nTrain type: "+ trainType + 
                "\nSeat class: " + seatClass);
    }

    @Override
    public boolean equals(Object obj) {
    if (obj == null) {
        return false;
    }

    if (getClass() != obj.getClass()) {
        return false;
    }
    Train compare = (Train) obj;
    if (super.equals(compare) &&
        this.seatClass.equalsIgnoreCase(compare.seatClass) &&
        this.trainType.equalsIgnoreCase(compare.trainType)) return true;
        else return false;
  }

}
