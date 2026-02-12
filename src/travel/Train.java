package travel;

public class Train extends Transportation {

    private TrainType trainType; // options available are 1. High-speed 2. Long-Distance 3.Economy
    private SeatClass seatClass;  // options are 1. First class 2. Bussiness 3. Economy
    private double trainCost;

    public Train() {
        super();
        trainType = TrainType.ECONOMY;
        seatClass = SeatClass.ECONOMY;
        trainCost = 0;
    }

    public Train(String companyName, String departureCity, String arrivalCity,
                 TrainType trainType, SeatClass seatClass, double trainCost) {
        super(companyName, departureCity, arrivalCity);
        this.trainType = trainType;
        this.seatClass = seatClass;
        this.trainCost = trainCost;
    }

    public Train(Train other) {
        super(other);
        this.trainType = other.trainType;
        this.seatClass = other.seatClass;
        this.trainCost = other.trainCost;
    }

    // setters
    public void setTrainType(TrainType trainType) {
        this.trainType = trainType;
    }

    public void setSeatClass(SeatClass seatClass) {
        this.seatClass = seatClass;
    }

    public void setTrainCost(double trainCost) {
        this.trainCost = trainCost;
    }

    // getters
    public TrainType getTrainType() {
        return trainType;
    }

    public SeatClass getSeatClass() {
        return seatClass;
    }

    public double getTrainCost() {
        return trainCost;
    }

    // method to calculate total cost (base price 100$ for economy train and economy seat)
    // Higher class trains and seats will add multiplier to base price of 100$
    // example High-Speed train(2x multiplier) and First class (2x mult.) = 100 *2 *2=400$
    @Override
    public double calculateTotalCost(int numberOfDays) {

        // calculating multiplyier to add on-to base price before seat class
        // 1. High-speed = 2x Long-Distance = 1.5x  Economy = 1x
        double trainMultiplier = 1.0;

        switch (trainType) {
            case HIGH_SPEED -> trainMultiplier = 2.0;
            case LONG_DISTANCE -> trainMultiplier = 1.5;
            case ECONOMY -> trainMultiplier = 1.0;
        }

        // calculating multiplier for seat class
        // First class = 2x    Bussiness = 1.5x    Economy = 1x
        double seatMultiplier = 1.0;

        switch (seatClass) {
            case FIRST_CLASS -> seatMultiplier = 2.0;
            case BUSINESS -> seatMultiplier = 1.5;
            case ECONOMY -> seatMultiplier = 1.0;
        }

        // Actually calculating total train cost after multiplying by both multipliers (base price 100$)
        double basePrice = 100;
        return basePrice * seatMultiplier * trainMultiplier;
    }

    @Override
    public String toString() {
        return (super.toString() +
                "\nTrain type: " + trainType +
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

        return super.equals(compare) &&
               this.seatClass == compare.seatClass &&
               this.trainType == compare.trainType;
    }
}
