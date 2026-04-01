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
            } catch (InvalidClientDataException ex) {
                ErrorLogger.log(ex.getMessage());
                throw ex;
            }
    }
    public void deleteClient(String id)throws EntityNotFoundException{
        int indexToRemove = -1;
            for (int i=0;i<clients.size();i++){
                if (clients.get(i).getClientID().equalsIgnoreCase(id)){
                    clients.remove(i);
                    indexToRemove = i;
                    break;
                }
            }
        if (indexToRemove == -1) {
            EntityNotFoundException ex = new EntityNotFoundException("Client not found with ID: " + id);
            ErrorLogger.log(ex.getMessage());
            throw ex;
        }
       
       

    }
       // ---------- Transportation operations ----------
    public void addTransportation(Transportation t) {
        
        transportations.add(t);
    }

    public Transportation findTransportById(String id) throws EntityNotFoundException {
        for (int i = 0; i < transportations.size(); i++) {
            if (transportations.get(i).getTripId().equals(id)) {
                return transportations.get(i);
            }
        }
        EntityNotFoundException e = new EntityNotFoundException("Transport not found with ID: " + id);
        ErrorLogger.log(e.getMessage());
        throw e;
    }

    public void removeTransportation(String id) throws EntityNotFoundException {
        int index = -1;
        for (int i = 0; i < transportations.size(); i++) {
            if (transportations.get(i).getTripId().equals(id)) {
                transportations.remove(i);
                index = i;
                break;
            }
        }
        if (index == -1) {
            EntityNotFoundException e = new EntityNotFoundException("Transport not found with ID: " + id);
            ErrorLogger.log(e.getMessage());
            throw e;
        }
        
    }

    // ---------- Accommodation operations ----------
    public void addAccommodation(Accommadation a) {
        
        accommadations.add(a);
    }

    

    public void removeAccommodation(String id) throws EntityNotFoundException {
        int index = -1;
        for (int i = 0; i < accommadations.size(); i++) {
            if (accommadations.get(i).getAccommId().equals(id)) {
                accommadations.remove(i);
                index = i;
                break;
            }
        }
        if (index == -1) {
            EntityNotFoundException e = new EntityNotFoundException("Accommodation not found with ID: " + id);
            ErrorLogger.log(e.getMessage());
            throw e;
        }
        
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
                accommodation = findAccommodationById(accommodationId);
            }

            // Create and store the trip
            try {
                Trip trip = new Trip(destination, duration, basePrice, client, transport, accommodation);
                trips.add(trip);
            } catch (InvalidTripDataException ex) {
                ErrorLogger.log("Invalid trip data: " + ex.getMessage());
                throw ex;
            }
    }

    public void deleteTrip(String tripId) throws EntityNotFoundException {
        int index = -1;
        for (int i = 0; i < trips.size(); i++) {
            if (trips.get(i).getTripId().equals(tripId)) {
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
                removeTransportation(transport.getTripId());
            } catch (EntityNotFoundException ex) {
                ErrorLogger.log("Error: Associated transport not found while deleting trip: " + ex.getMessage());
            }
        }

        // Remove associated accommodation 
        Accommadation accommodation = trips.get(index).getAccommadation();
        if (accommodation != null) {
            try {
                removeAccommodation(accommodation.getAccommId());
            } catch (EntityNotFoundException ex) {
                ErrorLogger.log("Error: Associated accommodation not found while deleting trip: " + ex.getMessage());
            }
        }
        trips.remove(index);

    }

    


    public Accommadation findAccommodationById(String id) throws EntityNotFoundException {
        for (int i = 0; i < accommadations.size(); i++) {
            if (accommadations.get(i).getAccommId().equals(id)) {
                return accommadations.get(i);
            }
        }
        EntityNotFoundException e = new EntityNotFoundException("Accommodation not found with ID: " + id);
        ErrorLogger.log(e.getMessage());
        throw e;
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
            if (clients.get(i).getClientID().equals(clientId)) {
                return true;
            }
        }
        return false;
    }

    public Client findClientById(String id)throws EntityNotFoundException{
        for (int i=0;i<clients.size();i++){
            if (clients.get(i).getClientID().equals(id)){
                return clients.get(i);
            }
        }
        EntityNotFoundException ex = new EntityNotFoundException("Client not found with ID: " + id);
        ErrorLogger.log(ex.getMessage());
        throw ex;
    }

    //----------------------------------
    // LOAD ALL DATA 
    //----------------------------------
    /* TODO LATER 
    public  void loadAllData() {
    // Reset counts to avoid mixing with existing data
    clientCount = 0;
    accommadationCount = 0;
    transportCount = 0;
    tripCount = 0;

    try {
        // Load clients 
        clientCount = ClientFileManager.loadClients(clients);     
        //  Load accommodations 
        accommadationCount = AccommodationFileManager.loadAccommodations(accommadations, 0);
        //  Load transports 
        transportCount = TransportationFileManager.loadTransportations(transportations);
        //  Load trips 
        tripCount = TripFileManager.loadTrips(trips, clients, clientCount,
                                              transportations, transportCount,
                                              accommadations, accommadationCount);

        System.out.println("Data loaded succesfully!");
       

       
    } catch (IOException ex) {
        ErrorLogger.log("Error loading data: " + ex.getMessage());
        
    }
    }   */
    //----------------------------------
    // SAVE ALL DATA 
    //----------------------------------
    /*  TODO LATER 
        public  void saveAllData() {
        try {
            ClientFileManager.saveClients(clients, clientCount);
            

            AccommodationFileManager.saveAccommodations(accommadations, accommadationCount);
            

            TransportationFileManager.saveTransportations(transportations, transportCount);
           

            TripFileManager.saveTrips(trips, tripCount, clients, clientCount,
                                    transportations, transportCount,
                                    accommadations, accommadationCount);
           

            System.out.println("Data saved succesfully!");
            } catch (IOException ex) {
           
            
        }
    }
        */
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
    
}
