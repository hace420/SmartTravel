package driver;
import travel.*;
import client.*;
import java.util.Scanner;

public class driver {
    private static Client[] clients = new Client[50];       // initializing arrays for storing information
    private static Trip[] trips = new Trip[50];
    private static Transportation[] transportations = new Transportation[50];
    private static Accommadation[] accommadations = new Accommadation[50];

    private static int clientCount = 0;                  // used to track if space is available in array 
    private static int tripCount = 0;
    private static int transportCount = 0;
    private static int accommadationCount = 0;
    public static void main(String[] args) {

        int menuChoice = 0;
        Scanner in = new Scanner(System.in);

        System.out.println("Select between 1. Menu driven interface 2. Predefined (hard-coded) testing scenario");
        menuChoice = in.nextInt();
        in.nextLine();

        if (menuChoice == 1){
            menuDriven(in);
        } else if (menuChoice ==2){
            
        } else {
            System.out.println("\nInvalid choice!");
        }
    
    }
//----------------------------------
// MENU DRIVEN 
//----------------------------------

    public static void menuDriven(Scanner in){
        int choice=0;
        do {
            System.out.println("\n===== SMART TRAVEL SYSTEM =====");
            System.out.println("\n1. Client Management");
            System.out.println("\n2. Trip Management");
            System.out.println("\n3. Transportation Management");
            System.out.println("\n4. Accommodation Management");
            System.out.println("\n5. Additional Operations");
            System.out.println("\n0. Exit");
            System.out.print("\nChoice: ");
            choice = in.nextInt();
            in.nextLine();

            switch (choice) {
                case 1: clientMenu(in); break;
                case 2: tripMenu(in); break;
                case 3: transportationMenu(in); break;
                case 4: accommodationMenu(in); break;
                case 5: additionalMenu(in); break;
            }

        } while (choice != 0);

        
    }
    //----------------------------------
    //CLIENT MENU
    //----------------------------------

    public static void clientMenu(Scanner in){
    int choiceClient = 0;
    do {
        System.out.println("\n===== CLIENT MANAGEMENT =====");
        System.out.println("1. Add Client");
        System.out.println("2. Edit Client");
        System.out.println("3. Delete Client");
        System.out.println("4. List Clients");
        System.out.println("5. Quit");
        System.out.print("Choice: ");
        choiceClient = in.nextInt();
        in.nextLine(); 

        switch (choiceClient) {
            case 1: 
                System.out.print("Enter first name: ");
                String first = in.nextLine();
                System.out.print("Enter last name: ");
                String last = in.nextLine();
                System.out.print("Enter email: ");
                String email = in.nextLine();

                Client newClient = new Client(first, last, email);
                if (clientCount < clients.length) {
                    clients[clientCount] = newClient;
                    clientCount++;
                    System.out.println("Client " + first + " " + last + " added successfully!");
                } else {
                    System.out.println("Client list is full!");
                }
                break;

            case 2:
                listClients();
                System.out.println("Select client index you would like to edit: ");
                int choice = 0;
                choice = in.nextInt();
                in.nextLine();
                
                // checking if choice is valid
                if (choice < 0 || choice >= clientCount) {
                System.out.println("Invalid client selection!");
                break;
    }

                System.out.println("Would you like to edit 1. First name 2. Last name 3. Email address");
                int choice2 = 0;
                choice2 = in.nextInt();
                in.nextLine();

                switch (choice2) {
                    case 1 :
                        String f;
                        System.out.println("Enter first name: ");
                        f = in.nextLine();
                        clients[choice].setFirstName(f);

                        break;
                    case 2:
                         String l;
                        System.out.println("Enter last name: ");
                        l = in.nextLine();
                        clients[choice].setLastName(l);
                        break;
                    case 3:
                         String e;
                        System.out.println("Enter email address: ");
                        e = in.nextLine();
                        clients[choice].setEmailAddress(e);;
                        break;
                    default:
                        System.out.println("Invalid option!");;
                }
                break;

            case 3:
                listClients();
                System.out.println("Select client index you would like to delete: ");
                int choice3 = 0;
                choice3 = in.nextInt();
                in.nextLine();

                if (choice3 < 0 || choice3 >= clientCount) {
                System.out.println("Invalid client selection!");
                break;
                }
                //moving all elemeents in array one position to the left starting at index of client that needs to be removed
                // this "deletes" the cleint at that index but will cause last 2 elements to be duplicates
                // avoid null in the middle of array
                for (int i = choice3; i < clientCount-1;i++){
                    clients[i] = clients[i+1];
                }
                // clears last duplicate client at the highest index of array
                clients[clientCount-1] = null;
               
                clientCount--;
                System.out.println("Client deleted successfully!");

                break;

            case 4:
                listClients();
                break;

            case 5:
                System.out.println("Returning to main menu...");
                break;

            default:
                System.out.println("Invalid choice! Try again.");
        }

    } while (choiceClient != 5);
    } 
    public static void listClients(){
        System.out.println("Clients list:");
        for (int i = 0; i < clientCount; i++) {
        System.out.println(i + ". " + clients[i].getFirstName() + " " + clients[i].getLastName() + " " + clients[i].getEmailAddress());
        }
    }  


    //----------------------------------
    // TRIP MENU
    //----------------------------------
    public static void tripMenu(Scanner in){
        int tripChoice =0;
        do { 
            System.out.println("===== TRIP MANAGEMENT =====");
            System.out.println("1. Create trip");
            System.out.println("2.Edit trip");
            System.out.println("3. Cancel a trip");
            System.out.println("4.List all trips");
            System.out.println("5.List all trips for a specific client");
            System.out.println("6.Exit");
            tripChoice = 0;
            tripChoice = in.nextInt();
            in.nextLine();

            switch (tripChoice) {
                case 1 :
                    // --- Trip destination , duration and price ---
                    System.out.print("Enter trip destination: ");
                    String destination = in.nextLine();
                    System.out.print("Enter trip duration (days): ");
                    int duration = in.nextInt();
                    in.nextLine();
                    System.out.print("Enter base price per night: ");
                    double basePrice = in.nextDouble();
                    in.nextLine();

                    // --- Select client ---
                    listClients();
                    System.out.print("Select client index: ");
                    int clientIndex = in.nextInt();
                    in.nextLine();
                    // chekcing for valid client index
                    if (clientIndex < 0 || clientIndex >= clientCount) {
                    System.out.println("Invalid client selection!");
                    break;
                    }

                    Client selectedClient = clients[clientIndex];

                    // -- Select Transport if any -- 

                    Transportation transport = null;
                    System.out.print("Do you want to add transportation? (y/n): ");
                    String addTransport = in.nextLine();
                        if (addTransport.equalsIgnoreCase("y")) {
                            System.out.println("Select transportation type: 1. Flight 2. Train 3. Bus");
                            int transportType = in.nextInt();
                             in.nextLine();
    
                             System.out.print("Enter company name: ");
                             String company = in.nextLine();
                             System.out.print("Enter departure city: ");
                             String depart = in.nextLine();
                             System.out.print("Enter arrival city: ");
                             String arrive = in.nextLine();

                            switch (transportType) {
                            case 1:
                                // Flight-specific attributes
                                System.out.print("Enter airline name: ");
                                String airline = in.nextLine();
                                System.out.print("Enter luggage allowance: ");
                                double luggage = in.nextDouble();
                                System.out.print("Enter ticket cost: ");
                                double ticketCost = in.nextDouble();
                                System.out.print("Enter luggage cost: ");
                                double luggageCost = in.nextDouble();
                                in.nextLine();
                                transport = new Flight(company, depart, arrive, airline, luggage, ticketCost, luggageCost);
                                    break;
                            case 2:
                                // Train-specific attributes
                                System.out.println("Enter train type (1. High-speed 2. Long-Distance 3.Economy)");
                                int type = in.nextInt();
                                TrainType trainType = TrainType.ECONOMY; // default choice
                                switch (type) {
                                    case 1 : trainType = TrainType.HIGH_SPEED;break;
                                    case 2 : trainType = TrainType.LONG_DISTANCE;break;
                                    case 3 : trainType = TrainType.ECONOMY;break;
                                    default:
                                        System.out.println("Invalid option!");break;
                                }
                                System.out.println("Enter seat class (1. First class 2. Bussiness 3. Economy)");
                                int seat = in.nextInt();
                                SeatClass seatclass = SeatClass.ECONOMY;
                                switch (seat) {
                                    case 1 : seatclass = SeatClass.FIRST_CLASS;break;
                                    case 2 : seatclass = SeatClass.BUSINESS;break;
                                    case 3 : seatclass = SeatClass.ECONOMY;break;
                                    default:
                                        System.out.println("Invalid option!");break;
                                }
                                System.out.println("Enter train cost");
                                double cost = in.nextDouble();

                                transport = new Train(company, depart, arrive,trainType,seatclass,cost);
                                 break;
                            case 3:
                                // Bus-specific attributes

                                System.out.println("Enter bus company name");
                                String n = in.nextLine();
                                System.out.println("Enter number of stops bus will make");
                                int stops = in.nextInt();
                                in.nextLine();
                                System.out.println("Enter bus cost (base 20$ with surcharge of 1$ extra for every stop)");
                                double c = in.nextDouble();
                                in.nextLine();
                                transport = new Bus(company, depart, arrive, n, stops, c);
                                break;
                                }
                             transportations[transportCount] = transport;
                             transportCount++;

                        }
                
                // --- select accommodation if any ---
                Accommadation acc = null;
                System.out.println("Do you want to add a accommodation (y or n)");
                String answer = in.nextLine();
                if (answer.equalsIgnoreCase("y")){
                    System.out.println("Enter company name");
                    String name = in.nextLine();
                    System.out.println("Enter price per night");
                    double price = in.nextDouble();
                    in.nextLine();
                    System.out.println("Enter location");
                    String location = in.nextLine();

                    System.out.println("Please choose between 1. Hotel or 2. Hostel for acommodation");
                    int accChoice = in.nextInt();
                    in.nextLine();
                    switch (accChoice) {
                        case 1:
                            // Hotel attributes
                            System.out.println("Enter number of stars (1-5)");
                            int stars = in.nextInt();
                            in.nextLine();
                            System.out.println("Enter service fees (fees will only be charged once not per night)");
                            double fees = in.nextDouble();
                            in.nextLine();
                            acc = new Hotel(name, location, price, stars, fees);                         
                            break;
                        case 2:
                            // Hostel attributes
                            System.out.println("Enter number of beds");
                            int beds = in.nextInt();
                            in.nextLine();
                            System.out.println("Enter additional fees");
                            double fee = in.nextDouble();
                            in.nextLine();
                            acc = new Hostel(name, location, price, beds, fee);
                            break;                            
                        default:
                            System.out.println("Invalid option!");break;
                    }
                    accommadations[accommadationCount] = acc;
                    accommadationCount++;

                }
                if (tripCount < trips.length) {
                trips[tripCount++] = new Trip(destination, duration, basePrice, selectedClient, transport, acc);
                System.out.println("Trip successfully created!");
                } else {
                System.out.println("Trip list is full!");
                }
                break;
            case 2:
                // edit trip 
                
                if (tripCount == 0) {
                        System.out.println("No trips available to edit.");
                        break;
                    }

                    listTrips();

                    System.out.print("Select trip index to edit: ");
                    int editIndex = in.nextInt();
                    in.nextLine();

                    if (editIndex < 0 || editIndex >= tripCount) {
                        System.out.println("Invalid trip selection!");
                        break;
                    }

                    Trip tripToEdit = trips[editIndex];

                    System.out.println("Edit options:");
                    System.out.println("1. Destination");
                    System.out.println("2. Duration");
                    System.out.println("3. Base Price");
                    System.out.println("4. Change Client");
                    System.out.println("5. Cancel");

                    int editChoice = in.nextInt();
                    in.nextLine();

                    switch (editChoice) {

                        case 1:
                            System.out.print("Enter new destination: ");
                            String newDest = in.nextLine();
                            tripToEdit.setDestination(newDest);
                            break;

                        case 2:
                            System.out.print("Enter new duration: ");
                            int newDuration = in.nextInt();
                            in.nextLine();
                            tripToEdit.setDuration(newDuration);
                            break;

                        case 3:
                            System.out.print("Enter new base price: ");
                            double newPrice = in.nextDouble();
                            in.nextLine();
                            tripToEdit.setBasePrice(newPrice);
                            break;

                        case 4:
                            listClients();
                            System.out.print("Select new client index: ");
                            int newClientIndex = in.nextInt();
                            in.nextLine();

                            if (newClientIndex < 0 || newClientIndex >= clientCount) {
                                System.out.println("Invalid client selection!");
                                break;
                            }

                            tripToEdit.setClient(clients[newClientIndex]);
                            break;

                        case 5: // exit sub menu
                            break;

                        default:
                            System.out.println("Invalid option.");
                    }

                    System.out.println("Trip updated successfully.");
                    break;
            case 3:
                listTrips();
                System.out.println("Select trip you would like to delete by entering the trips index number");
                int index = in.nextInt();
                in.nextLine();
                if (index < 0 || index >= tripCount) {
                    System.out.println("Invalid entry!"); break;
                }
                //moving all elemeents in array one position to the left starting at index of trip that needs to be removed
                // this "deletes" the cleint at that index but will cause last 2 elements to be duplicates
                // avoid null in the middle of array
                for (int i = index; i < tripCount-1;i++){
                    trips[i] = trips[i+1];
                }
                // clears last duplicate client at the highest index of array
                trips[tripCount-1] = null;
               
                tripCount--;
                System.out.println("Trip deleted successfully!");

            case 4:
                listTrips();break;
            case 5:              


            default:
                    System.out.println("Invalid entry!");;

            
            }
            
        } while (tripChoice !=6);

   
    }
    public static void listTrips() {
    if (tripCount == 0) {
        System.out.println("No trips available.");
        return;
    }

        System.out.println("Trips list:");
        for (int i = 0; i < tripCount; i++) {
        System.out.println(i + ". " + trips[i].getDestination() +
                " | Duration: " + trips[i].getDuration() +
                " | Client: " + trips[i].getClient());
        }
    }


    public static void transportationMenu(Scanner in){
    System.out.println("Transportation menu not implemented yet.");
    }

    public static void accommodationMenu(Scanner in){
    System.out.println("Accommodation menu not implemented yet.");
    }   

    public static void additionalMenu(Scanner in){
    System.out.println("Additional operations not implemented yet.");
    }


}



