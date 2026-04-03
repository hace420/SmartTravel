//------------------------------------------
// Assignment (2)
// Question: ()
// Written by: (Christian Buckley 40329967)
//------------------------------------------
package service;
import client.Client;
import exceptions.*;
import persistence.*;
import travel.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SmartTravelService {
    private  List<Client> clients = new ArrayList<>();      // initializing arrays for storing information
    private  List<Trip> trips = new ArrayList<>(); 
    private  List<Transportation> transportations = new ArrayList<>(); 
    private  List<Accommadation> accommadations = new ArrayList<>(); 

    // repos
    private  Repository<Client> clientsRepo = new Repository<>();      
    private  Repository<Trip> tripsRepo = new Repository<>(); 
    private  Repository<Transportation> transportationsRepo = new Repository<>(); 
    private  Repository<Accommadation> accommadationsRepo = new Repository<>(); 

    // Rewcent list
    private RecentList<Trip> recentTrips = new RecentList<>();


    


    // ---- client methods ----
    public void addClient(String name,String lastname,String email)throws InvalidClientDataException, DuplicateEmailException{
        if (duplicateEmailCheck(email)) {
            DuplicateEmailException ex = new DuplicateEmailException("Error: Email already registered to a client.");
            ErrorLogger.log(ex.getMessage());
            throw ex;

            }
            
            try {
                Client c = new Client(name,lastname,email);
                clients.add(c);
                clientsRepo.add(c);
            } catch (InvalidClientDataException ex) {
                ErrorLogger.log(ex.getMessage());
                throw ex;
            }
    }
    public void deleteClient(String id)throws EntityNotFoundException{   
       Client toRemove = findClientById(id);
        clients.remove(toRemove);
        clientsRepo.removeById(id); 
    }


       // ---------- Transportation operations ----------
    public void addTransportation(Transportation t) {
        transportationsRepo.add(t);
        transportations.add(t);
        
    }

    public Transportation findTransportById(String id) throws EntityNotFoundException {
        for (int i = 0; i < transportations.size(); i++) {
            if (transportations.get(i).getId().equals(id)) {
                return transportations.get(i);
            }
        }
        EntityNotFoundException e = new EntityNotFoundException("Transport not found with ID: " + id);
        ErrorLogger.log(e.getMessage());
        throw e;
    }

    public void removeTransportation(String id) throws EntityNotFoundException {
        Transportation toRemove = findTransportById(id);
        transportationsRepo.removeById(id);
        transportations.remove(toRemove);
        
        
    }

    // ---------- Accommodation operations ----------
    public void addAccommodation(Accommadation a) {
        
        accommadations.add(a);
        accommadationsRepo.add(a);
    }

    public Accommadation findAccommadationById(String id) throws EntityNotFoundException{
        for (int i=0;i<accommadations.size();i++){
            if (accommadations.get(i).getId().equals(id)){
                return accommadations.get(i);
            }
        }
        EntityNotFoundException e = new EntityNotFoundException("Accommodation not found with ID: " + id);
        ErrorLogger.log(e.getMessage());
        throw e;
    }

    

    public void removeAccommodation(String id) throws EntityNotFoundException {
        Accommadation toRemove = findAccommadationById(id);
        accommadationsRepo.removeById(id);
        accommadations.remove(toRemove);
        
        
        
        
    }
    //---- trip methods ---------
    public void createTrip(String destination, int duration, double basePrice,
                       String clientId, String transportId, String accommodationId)
        throws InvalidTripDataException, EntityNotFoundException {  

            
            Client client = findClientById(clientId);

            
            Transportation transport = null;
            if (transportId != null && !transportId.isEmpty() && !"null".equals(transportId)) {
                transport = findTransportById(transportId);
            }

        
            Accommadation accommodation = null;
            if (accommodationId != null && !accommodationId.isEmpty() && !"null".equals(accommodationId)) {
                accommodation = findAccommadationById(accommodationId);
            }

            // Create and store the trip
            try {
                Trip trip = new Trip(destination, duration, basePrice, client, transport, accommodation);
                trips.add(trip);
                tripsRepo.add(trip);
                recentTrips.addRecent(trip);
            } catch (InvalidTripDataException ex) {
                ErrorLogger.log("Invalid trip data: " + ex.getMessage());
                throw ex;
            }
    }

    public void deleteTrip(String tripId) throws EntityNotFoundException {
        int index = -1;
        for (int i = 0; i < trips.size(); i++) {
            if (trips.get(i).getId().equals(tripId)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            EntityNotFoundException ex = new EntityNotFoundException("Trip not found with ID: " + tripId);
            ErrorLogger.log(ex.getMessage());
            throw ex;
        }

        // Remove associated transportation 
        Transportation transport = trips.get(index).getTransportation();
        if (transport != null) {
            try {
                removeTransportation(transport.getId());
            } catch (EntityNotFoundException ex) {
                ErrorLogger.log("Error: Associated transport not found while deleting trip: " + ex.getMessage());
            }
        }

        // Remove associated accommodation 
        Accommadation accommodation = trips.get(index).getAccommadation();
        if (accommodation != null) {
            try {
                removeAccommodation(accommodation.getId());
            } catch (EntityNotFoundException ex) {
                ErrorLogger.log("Error: Associated accommodation not found while deleting trip: " + ex.getMessage());
            }
        }
        trips.remove(index);
        tripsRepo.removeById(tripId);

    }



    public boolean duplicateEmailCheck(String email){
        for (int i=0;i<clients.size();i++){
            if (clients.get(i).getEmailAddress().equalsIgnoreCase(email)){
                return true;
            }
        }
        return false;
    }
    public boolean clientExists(String clientId) {
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getId().equals(clientId)) {
                return true;
            }
        }
        return false;
    }

    public Client findClientById(String id)throws EntityNotFoundException{
        for (Client c : clients){
            if (c.getId().equals(id)){
                return c;
            }
        }
        throw new EntityNotFoundException("Client now found with id "+id);
    }

    //----------------------------------
    // LOAD ALL DATA 
    //----------------------------------
     
    public  void loadAllData() {
    // Reset counts to avoid mixing with existing data
    clients.clear();
    trips.clear();
    transportations.clear();
    accommadations.clear();

    try {
        // Load clients 
        List<Client> loadedClients = GenericFileManager.load("output/data/clients.csv", Client.class);
        clients.addAll(loadedClients);
        int maxId = 1000;
        for (Client c : clients){
            int num = Integer.parseInt(c.getId().substring(1));
            if (num > maxId){
                maxId = num;
            }
            
        }  
        Client.updateNextID(maxId+1);
        //  Load accommodations 
        List<Accommadation> loadedAccommadations = GenericFileManager.load("output/data/accommodations.csv", Accommadation.class);
        accommadations.addAll(loadedAccommadations);
        int maxId2= 4000;
        for (Accommadation a : accommadations){
            int num2 = Integer.parseInt(a.getId().substring(1)); 
            if (num2 > maxId2){
                maxId2 =num2;
            }
            
        }
        Accommadation.updateAccommId(maxId2+1);
        //  Load transports 
        List<Transportation> loadedTransportations = GenericFileManager.load("output/data/transportations.csv", Transportation.class);
        transportations.addAll(loadedTransportations);
        int maxId3= 3000;
        for (Transportation t : transportations){
            int num3 = Integer.parseInt(t.getId().substring(1)); 
            if (num3 > maxId3){
                maxId3 =num3;
            }
        }
        Transportation.updateTripId(maxId3+1);


        //  Load trips 
        List<Trip> loadedTrips = GenericFileManager.load("output/data/trips.csv", Trip.class);
        trips.addAll(loadedTrips);

            for (Trip trip : trips) {
                try {
                    String clientId = trip.getClientIdTemp();
                    if (clientId != null && !clientId.isEmpty()) {
                    Client client = findClientById(clientId);
                    trip.setClient(client);
                }
                } catch (EntityNotFoundException ex) {
                    ErrorLogger.log(ex.getMessage());
                } catch (InvalidTripDataException ex){
                    ErrorLogger.log(ex.getMessage());
                }
                try {
                    String transportId = trip.getTransportIdTemp();
                    if (transportId != null && !transportId.isEmpty()) {
                    Transportation trans = findTransportById(transportId);
                    trip.setTransportation(trans);
                }
                } catch (EntityNotFoundException ex) {
                    ErrorLogger.log(ex.getMessage());
                }
                try {
                     String accId = trip.getAccommodationIdTemp();
                    if (accId != null && !accId.isEmpty()) {
                    Accommadation acc = findAccommadationById(accId);
                    trip.setAccommadation(acc);
                }
                } catch (EntityNotFoundException ex) {
                    ErrorLogger.log(ex.getMessage());
                }
            }
        int maxId4 = 2000;
        for (Trip t :trips){
            int num4 = Integer.parseInt(t.getId().substring(1));
            if (num4 > maxId4){
                maxId4 = num4;
            }
        }
        Trip.updateNextId(maxId4+1);

        // populate repos 
        for (Client c :clients) clientsRepo.add(c);
        for (Transportation t:transportations) transportationsRepo.add(t);
        for (Accommadation a:accommadations)accommadationsRepo.add(a);
        for (Trip tr: trips) tripsRepo.add(tr);
        
        
        // calculate total spend for each client
        for (Client client : clients) {
                double total = 0.0;
                for (Trip trip : trips) {
                    if (trip.getClient().equals(client)) {
                        total += trip.getTotalCost();
                    }
                }
                client.setTotalSpent(total);
            }


        System.out.println("Data loaded succesfully!");
       

       
    } catch (IOException ex) {
        ErrorLogger.log("Error loading data: " + ex.getMessage());
        System.out.println("Error loading date! Check logs");
        
    }
    }   
    //----------------------------------
    // SAVE ALL DATA 
    //----------------------------------
   
        public  void saveAllData() {
        try {
            GenericFileManager.save(clients, "output/data/clients.csv");
            GenericFileManager.save(transportations, "output/data/transportations.csv");
            GenericFileManager.save(accommadations, "output/data/accommodations.csv");
            GenericFileManager.save(trips, "output/data/trips.csv");           
            System.out.println("Data saved succesfully!");
            } catch (IOException ex) {
                ErrorLogger.log("Error loading data: " + ex.getMessage());
                System.out.println("Error loading date! Check logs");               
        }
    }

    // analytics helper methods
    public RecentList<Trip> getRecentTrips() { return recentTrips; }
    public Repository<Client> getClientRepo() { return clientsRepo; }
    public Repository<Trip> getTripRepo() { return tripsRepo; }
    public Repository<Transportation> getTransportRepo() { return transportationsRepo; }
    public Repository<Accommadation> getAccommodationRepo() { return accommadationsRepo; }

        
    // used for dashboard 
    public double calculateTripTotal(int index) {
    if (index < 0 || index >= trips.size()) {
        ErrorLogger.log("Invalid trip index: " + index);
        return 0.0;
    }
    try {
        return trips.get(index).calculateTotalCost(trips.get(index).getDuration());
    } catch (InvalidAccommodationDataException ex) {
        ErrorLogger.log("Error calculating trip total for index " + index + ": " + ex.getMessage());
        return 0.0;
    }
    }
    // ---------- Getters ----------
    public List<Client> getClients() { return clients; }
    public List<Trip>  getTrips() { return trips; }
    public List<Transportation>  getTransportations() { return transportations; }
    public List<Accommadation> getAccommodations() { return accommadations; }
    public int getClientCount(){return clients.size();}
    public int getTripCount(){return trips.size();}
    
}
