//------------------------------------------
// Assignment (2)
// Question: ()
// Written by: (Christian Buckley 40329967)
//------------------------------------------
// Driver forSmart Travel program includs menu drien interface and testing scenario with hard coded values
// Currently max size for any array in program in 50 
package driver;
import client.*;
import exceptions.*;
import java.io.IOException;
import java.util.Scanner;
import persistence.ClientFileManager;
import persistence.ErrorLogger;
import travel.*;

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

        
        Scanner in = new Scanner(System.in);
        menuDriven(in);

        
    
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
            System.out.println("\n6. List All Data Summary (Prints all the trip data)");
            System.out.println("\n7. Load All Data (output/data/*.csv) (Loads all data from csvs and logs errors if any)");
            System.out.println("\n8. Save All Data (output/data/*.csv) (Saves all data from csvs and logs errors if any)");
            System.out.println("\n9. Run Predefined Scenario (Hardcode clients, trips, transports, accommodations to show all exceptions)");
            System.out.println("\n10. Generate Dashboard ← HTML + charts (Generates charts and complete dashboard)");
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
                case 6: listTrips();break;
                case 7: loadAllData();break;
                case 8: saveAllData();break;
                case 9: predefinedScenerio(in);break;
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

                try {
                    if (emailDuplicateCheck(email, null)) {
                        throw new DuplicateEmailException("Email "+email+" is already registered to a client");
                    }
                    Client newClient = new Client(first, last, email);
                if (clientCount < clients.length) {
                    clients[clientCount] = newClient;
                    clientCount++;
                    System.out.println("Client " + first + " " + last + " added successfully!");
                } else {
                    System.out.println("Client list is full!");
                }
                } catch (InvalidClientDataException ex) {
                    System.out.println("Error: "+ex.getMessage());
                } catch (DuplicateEmailException ex){
                    System.out.println("Error: "+ex.getMessage());
                }
                
                break;

            case 2:
                listClients();
                System.out.println("Select client index you would like to edit: ");
                int choice = 0;
                choice = in.nextInt();
                in.nextLine();
                // checking if choice is valid
                try {
                    findClientByIndex(choice);
                } catch (EntityNotFoundException ex) {
                    System.out.println("Error: Client not found");
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
                        try {
                            clients[choice].setFirstName(f);
                        } catch (InvalidClientDataException ex) {
                            System.out.println("Error: "+ex.getMessage());
                        }                      
                        break;

                    case 2:
                         String l;
                        System.out.println("Enter last name: ");
                        l = in.nextLine();
                        try {
                            clients[choice].setLastName(l);
                        } catch (InvalidClientDataException ex) {
                            System.out.println("Error: "+ex.getMessage());
                        }
                        break;

                    case 3:
                         String e;
                        System.out.println("Enter email address: ");
                        e = in.nextLine();
                        try {
                            if (emailDuplicateCheck(e, null)) {
                            throw new DuplicateEmailException("Email "+e+" is already registered to a client");}
                            clients[choice].setEmailAddress(e);
                        } catch (InvalidClientDataException ex) {
                            System.out.println("Error: "+ex.getMessage());
                        } catch (DuplicateEmailException ex){
                        System.out.println("Error: "+ex.getMessage());
                        }
                       
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

                 try {
                    findClientByIndex(choice3);
                } catch (EntityNotFoundException ex) {
                    System.out.println("Error: Client not found");
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
                        try {
                        findClientByIndex(clientIndex);
                    } catch (EntityNotFoundException ex) {
                        System.out.println("Error: Client not found");
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
                                try {
                                    transport = new Flight(company, depart, arrive, airline, luggage, ticketCost, luggageCost);
                                } catch (InvalidTransportDataException ex) {
                                    System.out.println("Error: "+ ex.getMessage());
                                }
                                
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
                                try {
                                    transport = new Bus(company, depart, arrive, n, stops, c);
                                } catch (InvalidTransportDataException ex) {
                                    System.out.println("Error: "+ex.getMessage());
                                }
                                
                                break;
                                }
                            if (transportCount < transportations.length){
                                 transportations[transportCount] = transport;
                                 transportCount++;
                            } else {
                                System.out.println("No more trips can be entered the list is full");
                            }
                            

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

                            try {
                            acc = new Hotel(name, location, price, stars, fees); 
                            } catch (InvalidAccommodationDataException ex) {
                                 System.out.println("Error: "+ex.getMessage());
                            }
                                                    
                            break;
                        case 2:
                            // Hostel attributes
                            System.out.println("Enter number of beds");
                            int beds = in.nextInt();
                            in.nextLine();
                            System.out.println("Enter additional fees");
                            double fee = in.nextDouble();
                            in.nextLine();
                            try {
                                acc = new Hostel(name, location, price, beds, fee);
                            } catch (InvalidAccommodationDataException ex) {
                                System.out.println("Error: "+ex.getMessage());
                            }
                            
                            break;                            
                        default:
                            System.out.println("Invalid option!");break;
                    }
                    if (accommadationCount < accommadations.length){
                        accommadations[accommadationCount] = acc;
                        accommadationCount++;
                    } else {
                        System.out.println("Accommodation list full cannot add more");
                    }

                }
                if (tripCount < trips.length) {
                    try {
                        trips[tripCount++] = new Trip(destination, duration, basePrice, selectedClient, transport, acc);
                        System.out.println("Trip successfully created!");
                    } catch (InvalidTripDataException ex) {
                         System.out.println("Error: "+ex.getMessage());
                    }
                
                
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

                    try {
                        findTripByIndex(editIndex);
                    } catch (EntityNotFoundException ex) {
                        System.out.println("Error: Trip not found");
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
                            try {
                                tripToEdit.setDuration(newDuration);
                            } catch (InvalidTripDataException ex) {
                                System.out.println("Error: "+ex.getMessage());
                            }
                            
                            break;

                        case 3:
                            System.out.print("Enter new base price: ");
                            double newPrice = in.nextDouble();
                            in.nextLine();
                            try {
                                tripToEdit.setBasePrice(newPrice);
                            } catch (InvalidTripDataException ex) {
                                System.out.println("Error: "+ex.getMessage());
                            }
                            
                            break;

                        case 4:
                            listClients();
                            System.out.print("Select new client index: ");
                            int newClientIndex = in.nextInt();
                            in.nextLine();

                            try {
                                Client newClient = findClientByIndex(newClientIndex);
                                tripToEdit.setClient(newClient);
                                System.out.println("Client updated successfully!");
                            } catch (InvalidTripDataException ex) {
                                System.out.println("Error: " + ex.getMessage());
                                break;
                            } catch (EntityNotFoundException ex) {
                                System.out.println("Error: Client not found");
                                break;
                            }

                           
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
                try {
                    findTripByIndex(index);
                    } catch (EntityNotFoundException ex) {
                        System.out.println("Error: Trip not found");
                        break;
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
                break;

            case 4:
                listTrips();break;
            case 5:   
            listClients();
            System.out.println("Enter index of client to search for");
            int searchIndex = in.nextInt();
            in.nextLine();
            try {
                findClientByIndex(searchIndex);
                } catch (EntityNotFoundException ex) {
                    System.out.println("Error: Client not found");
                    break;
                }
            
              
            
            boolean found = false;
            Client selectedClient1 = clients[searchIndex];
            System.out.println("Trips found for client "+ clients[searchIndex].toString());
            for (int i = 0; i < tripCount;i++ ){
                if (trips[i].getClient() == selectedClient1) {
                    try {
                        System.out.println("Destonation: " + trips[i].getDestination()+
                                        "\nDuration: " + trips[i].getDuration()+
                                        "\nPrice: "+ trips[i].calculateTotalCost(trips[i].getDuration()));
                                        found = true;
                    } catch (InvalidAccommodationDataException ex) {
                        System.out.println("Error: "+ex.getMessage());
                    }
                    
                }
            }
            if (!found){
                System.out.println("No trips found for that client");
            }
            break;
            default:
                    System.out.println("Invalid entry!");;

            
            }
            
        } while (tripChoice !=6);

   
    }
    

    //----------------------------------
    // TRANSPORTATION MENU
    //----------------------------------
    public static void transportationMenu(Scanner in){
        System.out.println("1. Add a transportation option");
        System.out.println("2. Remove a transportation option");
        System.out.println("3. List transportations by type");
        int menuChoice = in.nextInt();
        in.nextLine();
        switch (menuChoice) {
            case 1:
                // --- adding trips ---
                if (tripCount == 0) {
                 System.out.println("No trips available to add transportation.");
                 return;
                }

                    System.out.println("Select trip you would like to add transportation to:");
                    listTrips();
                    int tripIndex = in.nextInt();
                    in.nextLine();

                    try {
                    findTripByIndex(tripIndex);
                    } catch (EntityNotFoundException ex) {
                        System.out.println("Error: Trip not found");
                        break;
                    }

                    Trip selectedTrip = trips[tripIndex];
                    Transportation transport = null;

                    // --- choosing tranport type --- 
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
                        // flight - attributes
                        System.out.print("Enter airline name: ");
                        String airline = in.nextLine();
                        System.out.print("Enter luggage allowance: ");
                        double luggage = in.nextDouble();
                        System.out.print("Enter ticket cost: ");
                        double ticketCost = in.nextDouble();
                        System.out.print("Enter luggage cost: ");
                        double luggageCost = in.nextDouble();
                        in.nextLine();
                        try {
                            transport = new Flight(company, depart, arrive, airline, luggage, ticketCost, luggageCost);
                        } catch (InvalidTransportDataException ex) {
                            System.out.println("Error: "+ex.getMessage());
                        }
                        
                        break;

                    case 2:
                        // train - attributes
                        System.out.println("Enter train type (1. High-speed 2. Long-Distance 3. Economy)");
                        int type = in.nextInt();
                        TrainType trainType = TrainType.ECONOMY;
                        switch (type) {
                            case 1: trainType = TrainType.HIGH_SPEED; break;
                            case 2: trainType = TrainType.LONG_DISTANCE; break;
                            case 3: trainType = TrainType.ECONOMY; break;
                        }
                        System.out.println("Enter seat class (1. First class 2. Business 3. Economy)");
                        int seat = in.nextInt();
                        SeatClass seatclass = SeatClass.ECONOMY;
                        switch (seat) {
                            case 1: seatclass = SeatClass.FIRST_CLASS; break;
                            case 2: seatclass = SeatClass.BUSINESS; break;
                            case 3: seatclass = SeatClass.ECONOMY; break;
                        }
                        System.out.println("Enter train cost:");
                        double cost = in.nextDouble();
                        in.nextLine();
                        transport = new Train(company, depart, arrive, trainType, seatclass, cost);
                        break;

                    case 3:
                        // bus - attributes
                        System.out.print("Enter bus company name: ");
                        String busName = in.nextLine();
                        System.out.print("Enter number of stops: ");
                        int stops = in.nextInt();
                        System.out.print("Enter bus cost (base 20$ + 1$ per stop): ");
                        double busCost = in.nextDouble();
                        in.nextLine();
                        
                        try {
                            transport = new Bus(company, depart, arrive, busName, stops, busCost);
                        } catch (InvalidTransportDataException ex) {
                            System.out.println("Error: "+ex.getMessage());
                        }
                        
                        break;

                        default:
                        System.out.println("Invalid transportation type!");
                        return;
                    }

                    // Add transport to global array (
                    if (transportCount < transportations.length) {
                        transportations[transportCount++] = transport;
                    } else {
                        System.out.println("Transportation list is full!");
                    }

                    // adding transport to trip
                    selectedTrip.setTransportation(transport); 

                    System.out.println("Transportation added to trip successfully!");
                   
                    break;
            case 2:
                // remove a transport option
                if (transportCount == 0){
                    System.out.println("No transportations options available to remove");break;
                }
                System.out.println("Select index of trip you would like to remove transportation from");
                listTrips();
                int tripRemoveIndex = in.nextInt();
                in.nextLine();
                try {
                    findTripByIndex(tripRemoveIndex);
                    } catch (EntityNotFoundException ex) {
                        System.out.println("Error: Trip not found");
                        break;
                    }
                Trip tripToRemoveTransport = trips[tripRemoveIndex];

                if (tripToRemoveTransport.getTransportation() == null) {
                    System.out.println("This trip has no transportation assigned.");break;                    
                }

                Transportation toRemove = tripToRemoveTransport.getTransportation();

                // removing specific transportaion option from global array
                for (int i = 0; i < transportCount; i++) {
                if (transportations[i] == toRemove) {
                    // shift remaining elements left
                    for (int j = i; j < transportCount - 1; j++) {
                        transportations[j] = transportations[j + 1];
                    }
                    transportations[transportCount - 1] = null;
                    transportCount--;
                    break;
                    }              
                }
                // removing tranportation option from specified trip
                tripToRemoveTransport.setTransportation(null);
                System.out.println("Transportation removed from trip successfully!");
                break;
            case 3:
                // Listing options
                if (transportCount == 0) {
                System.out.println("No transportation options available.");
                break;
                }

                System.out.println("Select type to list:");
                System.out.println("1. Flight");
                System.out.println("2. Train");
                System.out.println("3. Bus");

                int typeChoice = in.nextInt();
                in.nextLine();
                boolean found = false;

                for (int i = 0; i < transportCount; i++){
                    Transportation temp = transportations[i];
                    switch (typeChoice) {
                        case 1 :
                            // search for flight
                            if (temp instanceof Flight){
                                System.out.println(temp);
                                found = true;
                            }
                             break;
                        case 2:
                            // search for Train
                            if (temp instanceof Train){
                                System.out.println(temp);
                                found = true;
                            }
                            break;
                        case 3:
                            // search for bus
                            if (temp instanceof Bus){
                                System.out.println(temp);
                                found = true;
                            }
                            break;
                            
                        
                        default:
                            System.out.println("Invalid selection.");break;
                    }
                }
                if (!found){
                    System.out.println("No transportation of selected type found.");
                }
                break;
                
                
            default:
                System.out.println("Invalid selection.");break;
        }
       

    }    
    
    //----------------------------------
    // ACCOMMODATION MENU
    //----------------------------------
    public static void accommodationMenu(Scanner in){
    System.out.println("1. Add a acommodation option");
    System.out.println("2. Remove a accommodation option");
    System.out.println("3. List accommodations by type");
    int menuChoice = in.nextInt();
    in.nextLine();
    switch (menuChoice) {
        case 1:
            // adding accommodation
            if (tripCount == 0) {
                 System.out.println("No trips available to add accommadation.");
                 return;
                }

                    System.out.println("Select trip you would like to add accommadation to:");
                    listTrips();
                    int tripIndex = in.nextInt();
                    in.nextLine();

                    try {
                    findTripByIndex(tripIndex);
                    } catch (EntityNotFoundException ex) {
                        System.out.println("Error: Trip not found");
                        break;
                    }

                    Trip selectedTrip = trips[tripIndex];
                    Accommadation acc = null;

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
                            try {
                                acc = new Hotel(name, location, price, stars, fees);  
                            } catch (InvalidAccommodationDataException ex) {
                                System.out.println("Error: "+ex.getMessage());
                            }
                                                   
                            break;
                        case 2:
                            // Hostel attributes
                            System.out.println("Enter number of beds");
                            int beds = in.nextInt();
                            in.nextLine();
                            System.out.println("Enter additional fees");
                            double fee = in.nextDouble();
                            in.nextLine();
                            try {
                                acc = new Hostel(name, location, price, beds, fee);
                            } catch (InvalidAccommodationDataException ex) {
                                System.out.println("Error: "+ex.getMessage());
                            }
                            
                            break;                            
                        default:
                            System.out.println("Invalid option!");return;
                    }
                    if (accommadationCount < accommadations.length){
                        accommadations[accommadationCount] = acc;
                        accommadationCount++;
                        selectedTrip.setAccommadation(acc);
                        System.out.println("Acommodation added to trip!");
                    } else {
                        System.out.println("Accommodation list full cannot add more");
                    }
                break;
        case 2:
            // remove accommadation
            if (accommadationCount ==0){
                System.out.println("There are no accommadation available to remove");break;
            }
            System.out.println("Enter index of trip you would like the accommdation to be removed from");
            listTrips();
            int accRemoveIndex = in.nextInt();
            in.nextLine();
            try {
                    findTripByIndex(accRemoveIndex);
                    } catch (EntityNotFoundException ex) {
                        System.out.println("Error: Trip not found");
                        break;
                    }

            Trip tripRemoveAcc = trips[accRemoveIndex];
            if (tripRemoveAcc.getAccommadation() == null){
                System.out.println("There is no accommadation assigned to this trip");break;
            }
            Accommadation accTemp = tripRemoveAcc.getAccommadation();
            // removing specific accommadation option from global array
                for (int i = 0; i < accommadationCount; i++) {
                if (accommadations[i] == accTemp) {
                    // shift remaining elements left
                    for (int j = i; j < accommadationCount - 1; j++) {
                        accommadations[j] = accommadations[j + 1];
                    }
                    accommadations[accommadationCount - 1] = null;
                    accommadationCount--;
                    break;
                    }              
                }
                // removing tranportation option from specified trip
                tripRemoveAcc.setAccommadation(null);
                System.out.println("Accommadation removed from trip successfully!");
                break;
        case 3:
             // Listing options
             if (accommadationCount == 0) {
                System.out.println("No accommadation options available.");
                break;
                }

                System.out.println("Select type to list:");
                System.out.println("1. Hotel");
                System.out.println("2. Hostel");
               

                int typeChoice = in.nextInt();
                in.nextLine();
                boolean found = false;
                if (typeChoice != 1 && typeChoice != 2) {
                System.out.println("Invalid selection.");
                break;
                }


                for (int i =0; i < accommadationCount ;i++){
                    Accommadation temp = accommadations[i];
                    switch (typeChoice) {
                        case 1:
                            // searching for hotels
                            if (temp instanceof Hotel){
                                System.out.println(temp);
                                found = true;
                            }                            
                            break;
                        case 2: 
                            // searching for Hostels
                            if (temp instanceof Hostel){
                                System.out.println(temp);
                                found = true;
                            }
                            break;
                        default:
                            System.out.println("Invalid selection.");break;
                    }
                }
                if (!found){
                    System.out.println("No accommadation of selected type found.");
                }
                break;
        default:
           System.out.println("Invalid selection.");break;
    }

    }   
    //----------------------------------
    // ADDITIONAL MENU
    //----------------------------------
    public static void additionalMenu(Scanner in){
    int choice = 0;
    do {
        System.out.println("===== ADDITIONAL OPERATIONS =====");
        System.out.println("1. Display the most expensive trip");
        System.out.println("2. Calculate and display total cost of a trip");
        System.out.println("3. Create a deep copy of the transportation array");
        System.out.println("4. Create a deep copy of the accommodation array");
        System.out.println("5. Return to main menu");
        System.out.print("Choice: ");
        choice = in.nextInt();
        in.nextLine();

        switch (choice) {
            case 1:
                // show most expensive trip
                if(tripCount == 0){
                    System.out.println("No trips available.");
                    break;
                }
                Trip expensiveTrip = trips[0];
                double highestCost =0;
                try {
                    highestCost = expensiveTrip.calculateTotalCost(expensiveTrip.getDuration());
                } catch (InvalidAccommodationDataException ex) {
                    System.out.println("Error: "+ex.getMessage());
                }
                

                for (int i =0; i < tripCount;i++){
                    try {
                        double tempCost = trips[i].calculateTotalCost(trips[i].getDuration());
                        if (tempCost > highestCost){
                            highestCost =tempCost;
                            expensiveTrip = trips[i];
                    }
                    } catch (InvalidAccommodationDataException ex) {
                        System.out.println("Error: "+ex.getMessage());

                    }
                    
                }
                System.out.println("Most expensive trip: \n" + expensiveTrip.toString());
                System.out.println("With a cost of: " + highestCost);                
                break;
            case 2:
                // calculate cost of selected trip
                if(tripCount == 0){
                    System.out.println("No trips available.");
                    break;
                }
                System.out.println("Enter index of trip you would like to calculate cost for");
                listTrips();
                int indexChoice = in.nextInt();
                in.nextLine();
                if (indexChoice < 0 || indexChoice >= tripCount){
                    System.out.println("Invalid choice");break;
                }
                Trip temp = trips[indexChoice];
                try {
                    System.out.println(temp.calculateTotalCost(temp.getDuration()));
                } catch (InvalidAccommodationDataException ex) {
                    System.out.println("Error: "+ex.getMessage());
                }
                
                break;
            case 3:
                // copy transportation array
                Transportation[] copyTransportations = new Transportation[transportCount];
                for (int i =0;i<transportCount;i++){
                    if (transportations[i] instanceof Flight){
                        Flight flightTemp = (Flight) transportations[i];
                        copyTransportations[i] = new Flight(flightTemp);
                    } else if (transportations[i] instanceof Train){
                        Train trainTemp = (Train) transportations[i];
                        copyTransportations[i] = new Train(trainTemp);
                    }else if (transportations[i] instanceof Bus){
                        Bus busTemp = (Bus) transportations[i];
                        copyTransportations[i] = new Bus(busTemp);
                    }

                }
                System.out.println("Transportation array deep-copied.");
                break;
            case 4:
                // deep copy accommadations array
                Accommadation[] copyAccommadations = new Accommadation[accommadationCount];
                for (int i=0;i<accommadationCount;i++){
                    if (accommadations[i] instanceof Hotel){
                        Hotel hotelTemp = (Hotel) accommadations[i];
                        copyAccommadations[i] = new Hotel(hotelTemp);
                    } else if (accommadations[i] instanceof Hostel){
                        Hostel hostelTemp = (Hostel) accommadations[i];
                        copyAccommadations[i] = new Hostel(hostelTemp);
                    }
                }
                System.out.println("Accommadations array deep-copied.");
                break;
            case 5:
                //exit 
                System.out.println("Returning to main menu...");
                break;


            default:
                System.out.println("Invalid selection.");break;
        }

    } while (choice != 5);
    }

//----------------------------------
// LOAD ALL DATA 
//----------------------------------
    private static void loadAllData() {
        try {
            int newCount = ClientFileManager.loadClients(clients);
            clientCount = newCount;
            System.out.println("Clients loaded successfully. Total clients: " + clientCount);
           
        } catch (IOException e) {
            ErrorLogger.log("Error loading clients: " + e.getMessage());
            System.out.println("Error loading clients. Check logs.");
        }
    } 
    
//----------------------------------
// SAVE ALL DATA 
//----------------------------------
    private static void saveAllData() {
        try {
            ClientFileManager.saveClients(clients, clientCount);
            System.out.println("Clients saved successfully.");
           
        } catch (IOException e) {
            ErrorLogger.log("Error saving clients: " + e.getMessage());
            System.out.println("Error saving clients. Check logs.");
        }
    }
// --- HELPER METHODS ---
    // method for cleint menu to list clients
    public static void listClients(){
        System.out.println("Clients list:");
        for (int i = 0; i < clientCount; i++) {
        System.out.println(i + ". " + clients[i].getFirstName() + " " + clients[i].getLastName() + " " + clients[i].getEmailAddress());
        }
    } 

    // mehtod for trip menu to list all trips
    public static void listTrips() {
    if (tripCount == 0) {
        System.out.println("No trips available.");
        return;
    }

    System.out.println("Trips list:");
    for (int i = 0; i < tripCount; i++) {
        Trip t = trips[i];
        System.out.println(i + ". Destination: " + t.getDestination() +
                " | Duration: " + t.getDuration() +
                " | Client: " + t.getClient().getFirstName() + " " + t.getClient().getLastName());

        // show full transportation details
        if (t.getTransportation() != null) {
            System.out.println("    Transportation Details:\n" + t.getTransportation());
        } else {
            System.out.println("    Transportation: None");
        }

        // show full accommodation details
        if (t.getAccommadation() != null) {
            System.out.println("    Accommodation Details:\n" + t.getAccommadation());
        } else {
            System.out.println("    Accommodation: None");
        }
    }
    
}
// --- deep copy transportation array ---
    public static Transportation[] copyTransportationArray(Transportation[] original) {

    if (original == null) {
        return null;
    }

    Transportation[] copy = new Transportation[original.length];

    for (int i = 0; i < original.length; i++) {

        if (original[i] != null) {

            if (original[i] instanceof Flight) {
                copy[i] = new Flight((Flight) original[i]);

            } else if (original[i] instanceof Train) {
                copy[i] = new Train((Train) original[i]);

            } else if (original[i] instanceof Bus) {
                copy[i] = new Bus((Bus) original[i]);
            }
        }
    }

    return copy;
  }
// --- Find Client by email (EntityNotFoundException handled) ---
    private static Client findClientByEmail(String email) throws EntityNotFoundException {
    for (int i = 0; i < clientCount; i++) {
        if (clients[i].getEmailAddress().equalsIgnoreCase(email)) {
            return clients[i];
        }
    }
    throw new EntityNotFoundException("Error: Client not found");
    }

// --- Find Client by index (EntityNotFoundException handled) ---
    private static Client findClientByIndex(int index) throws EntityNotFoundException {
    if (index < 0 || index >= clientCount) {
        throw new EntityNotFoundException("Error: Client not found");
    }
    return clients[index];
    }

// --- Find Trip by index (EntityNotFoundException handled) ---
    private static Trip findTripByIndex(int index) throws EntityNotFoundException {
    if (index < 0 || index >= tripCount) {
        throw new EntityNotFoundException("Error: Trip not found");
    }
    return trips[index];
    } 

// --- Checking for duyplicate emails (DuplicateEmailException handled ) ---
    private static boolean emailDuplicateCheck(String email, Client currentClient) {
    for (int i = 0; i < clientCount; i++) {
        if (clients[i] != currentClient && 
            clients[i].getEmailAddress().equalsIgnoreCase(email)) {
            return true;
        }
    }
    return false;
}

  
    //----------------------------------
    // Hard Coded testing scenerio 
    //----------------------------------

    public static void predefinedScenerio(Scanner in){
        System.out.println("=== Predefined Testing Scenario ===");

        // --- Step 1: Create clients ---
        Client c1 = null;
        try {
           c1 = new Client("Alice", "Smith", "alice@gmail.com");
        } catch (InvalidClientDataException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        Client c2 =null;
        try {
            c2 = new Client("Bob", "Bobertson", "bob@outlook.com");
        } catch (InvalidClientDataException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        Client c3 = null;
        try {
            c3  = new Client("Chris", "Williams", "chris@live.com");
        } catch (InvalidClientDataException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
       

        Client[] testClientArray = new Client[3];  // --- step 4 creating arrays ----
        testClientArray[0] = c1;
        testClientArray[1] = c2;
        testClientArray[2] = c3;
        int testClientCount = 3;

        // --- Step 1: Create transportation ---
        Flight f1 = null;
        try {
            f1 = new Flight("airCanada", "Montreal", "Paris", "airCanada", 20, 150, 30);
        } catch (InvalidTransportDataException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        Flight f2 =null;
        try {
            f2 = new Flight("WestJet", "Toronto", "New York", "WestJet", 25, 180, 40);
        } catch (InvalidTransportDataException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        

        Train t1 = new Train("Exo", "laval", "Toronto", TrainType.HIGH_SPEED, SeatClass.FIRST_CLASS, 120);
        Train t2 = new Train("ViaRail", "Moncton", "Detroit", TrainType.LONG_DISTANCE, SeatClass.ECONOMY, 80);

        Bus b1 = null;
        try {
            b1 = new Bus("Exo", "Terrebbonne", "Montreal", "Exo", 3, 23);
        } catch (InvalidTransportDataException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        Bus b2 = null;
        try {
           b2 = new Bus("BusCO", "montreal", "laval", "BusCO", 5, 25);
        } catch (InvalidTransportDataException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        Transportation[] tesTransportationsArray = new Transportation[7]; // --- step 4 creating arrays ----
        tesTransportationsArray[0] = f1; 
        tesTransportationsArray[1] = f2;
        tesTransportationsArray[2] = t1; 
        tesTransportationsArray[3] = t2;
        tesTransportationsArray[4] = b1; 
        tesTransportationsArray[5] = b2;
        int testTransportCount = 7;

        // --- Step 1: Create accommodations ---

        Hotel h1 = null;
        try {
            h1= new Hotel("Hilton", "Paris", 100, 4, 20);
        } catch (InvalidAccommodationDataException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        Hotel h2 = null;
        try {
          h2  = new Hotel("Imperia", "Montreal", 120, 5, 25);
        } catch (InvalidAccommodationDataException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        Hostel hs1 = null;
        try {
            hs1= new Hostel("Auberge du Plateau", "Paris", 50, 3, 10);
        } catch (InvalidAccommodationDataException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        Hostel hs2 = null;
        try {
          hs2 = new Hostel("HostelCo", "Toronto", 60, 4, 15);
        } catch (InvalidAccommodationDataException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        Accommadation[] testAccommadations = new Accommadation[4]; // --- step 4 creating arrays ----
        testAccommadations[0] = h1; 
        testAccommadations[1] = h2;
        testAccommadations[2] = hs1; 
        testAccommadations[3] = hs2;
        int testAccommadationCount = 4;

        // --- Step 1: Create trips ---
        Trip tr1 = null;
        try {
           tr1 = new Trip("Paris", 5, 200, c1, f1, h1);
        } catch (InvalidTripDataException ex) {
            System.out.println("Error:" +ex.getMessage());
        }

        Trip tr2 = null;
        try {
            tr2 = new Trip("Toronto", 4, 150, c2, t1, hs1);
        } catch (InvalidTripDataException ex) {
            System.out.println("Error:" +ex.getMessage());
        }

        Trip tr3 = null;
        try {
           tr3 = new Trip("Montreal", 6, 180, c3, b1, h2);
        } catch (InvalidTripDataException ex) {
            System.out.println("Error:" +ex.getMessage());
        }
        
       
         

        Trip[] testTripArray = new Trip[3]; // --- step 4 creating arrays ----
        testTripArray[0] = tr1; 
        testTripArray[1] = tr2; 
        testTripArray[2] = tr3;
        int testTripCount = 3;

        // --- Display all created objects --- 
        System.out.println("\n--- Clients ---");
        for (int i = 0; i < testClientCount; i++) {
            System.out.println("\n"+testClientArray[i]);
        }
        System.out.println("\n--- Trips ---");
        for (int i = 0; i < testTripCount; i++) {
            System.out.println(testTripArray[i]);
        }
        System.out.println("\n--- Transportations ---");
        for (int i = 0; i < testTransportCount; i++) System.out.println(tesTransportationsArray[i]);

        System.out.println("\n--- Accommodations ---");
        for (int i = 0; i < testAccommadationCount; i++) System.out.println(testAccommadations[i]);

        // --- testing equals method ---

        // Compare objects from different classes.
        System.out.println("----------------------testing equals method--------------------------");
        System.out.println("Client vs Trip:\n");
        System.out.println(c1.toString()+"\n"+tr1.toString());
        System.out.println("\nequals method on these 2 object is: "+c1.equals(tr1));   

        // Compare objects of the same class with different attributes
        System.out.println("\nTwo Flights with different attributes:");
        System.out.println(f1.toString()+"\n"+f2.toString());
        System.out.println("\nequals method on these 2 object is: "+f1.equals(f2)); 

        // Compare objects of the same class with identical attributes
          Flight f3 = null;
          try {
              f3 = new Flight("WestJet", "Toronto", "New York", "WestJet", 25, 180, 40);// creating copy of f2 to compare against
          } catch (InvalidTransportDataException ex) {
          System.out.println("Error:" +ex.getMessage());
          }
       
        tesTransportationsArray[6] = f3;
        System.out.println("\nTwo Flights with identical attributes:");
        System.out.println(f2.toString()+"\n"+f3.toString());
        System.out.println("\nequals method on these 2 object is: "+f2.equals(f3));

        // --- Demonstrating Polymorphism ---
        
        System.out.println("\n--- Polymorphic Total Cost Calculations ---");

            for (int i = 0; i < testTripArray.length; i++) {
                Trip trip = testTripArray[i];

                Transportation transport = trip.getTransportation();
                Accommadation accommodation = trip.getAccommadation();

                double transportCost = transport.calculateTotalCost(trip.getDuration());
                double accommodationCost = 0;
                try {
                    accommodationCost = accommodation.calculateTotalCost(trip.getDuration());
                } catch (InvalidAccommodationDataException ex) {
                    System.out.println("Error:" +ex.getMessage());
                }

                double totalCost = 0;
                try {
                    totalCost = trip.calculateTotalCost(trip.getDuration()); 
                } catch (InvalidAccommodationDataException ex) {
                    System.out.println("Error:" +ex.getMessage());
                }

                System.out.println("\nTrip to " + trip.getDestination() +
                                " for client " + trip.getClient().getFirstName());

                System.out.println("transport" + " -> Cost: " + transportCost);
                System.out.println("accommodation" + " -> Cost: " + accommodationCost);
                System.out.println(trip.toString());
                
            }

        // --- Displaying most Expensive "test" trip ---
        System.out.println("\n------ Displaying most Expensive test trip ------");
        Trip expensiveTrip = testTripArray[0];
                double highestCost = 0;
                try {
                    expensiveTrip.calculateTotalCost(expensiveTrip.getDuration());
                } catch (InvalidAccommodationDataException ex) {
                    System.out.println("Error:" +ex.getMessage());
                }
                for (int i =0; i < testTripCount;i++){
                    double tempCost = 0;
                    try {
                        testTripArray[i].calculateTotalCost(testTripArray[i].getDuration());
                    } catch (InvalidAccommodationDataException ex) {
                        System.out.println("Error:" +ex.getMessage());
                    }
                    if (tempCost > highestCost){
                        highestCost =tempCost;
                        expensiveTrip = testTripArray[i];
                    }
                }
                System.out.println("Most expensive trip: \n" + expensiveTrip.toString());
                System.out.println("With a cost of: " + highestCost);       
        
        
        // --- deep copy of the transportation array. Modify at least one object in the copied array Display both arrays to demonstrate that the original array is unchanged. ---
        System.out.println("\n------ deep copy of the transportation array ------");
        Transportation[] copyTransportations = new Transportation[testTransportCount];
                for (int i =0;i<testTransportCount;i++){
                    if (tesTransportationsArray[i] instanceof Flight){
                        Flight flightTemp = (Flight) tesTransportationsArray[i];
                        copyTransportations[i] = new Flight(flightTemp);
                    } else if (tesTransportationsArray[i] instanceof Train){
                        Train trainTemp = (Train) tesTransportationsArray[i];
                        copyTransportations[i] = new Train(trainTemp);
                    }else if (tesTransportationsArray[i] instanceof Bus){
                        Bus busTemp = (Bus) tesTransportationsArray[i];
                        copyTransportations[i] = new Bus(busTemp);
                    }

                }
             
                copyTransportations[1].setArrivalCity("Berlin");
                copyTransportations[1].setDepartureCity("Vancouver");
                copyTransportations[1].setCompanyName("AirplaneCo");
                System.out.println("\n------Original-------");
                for (int i = 0; i < testTransportCount; i++) System.out.println(tesTransportationsArray[i]); // display original array
                System.out.println("\n------Modified-------");
                for (int i = 0; i < testTransportCount; i++) System.out.println(copyTransportations[i]);    // display modified array
                




    }









    }