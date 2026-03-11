package service;
import client.Client;
import exceptions.*;
import persistence.*;
import travel.*;
import java.io.IOException;

public class SmartTravelService {
    private  Client[] clients = new Client[50];       // initializing arrays for storing information
    private  Trip[] trips = new Trip[50];
    private  Transportation[] transportations = new Transportation[50];
    private  Accommadation[] accommadations = new Accommadation[50];

    private  int clientCount = 0;                  // used to track if space is available in array 
    private  int tripCount = 0;
    private  int transportCount = 0;
    private  int accommadationCount = 0;

    public void addClient(String name,String lastname,String email)throws InvalidClientDataException, DuplicateEmailException{
        if (duplicateEmailCheck(email)) {
            DuplicateEmailException ex = new DuplicateEmailException("Error: Email already registered to a client.");
            ErrorLogger.log(ex.getMessage());
            throw ex;
            }
            
            try {
                Client c = new Client(name,lastname,email);
                clients[clientCount++]=c;
            } catch (InvalidClientDataException ex) {
                ErrorLogger.log(ex.getMessage());
                throw ex;
            }
    }
       // ---------- Transportation operations ----------
    public void addTransportation(Transportation t) {
        
        transportations[transportCount++] = t;
    }

    public Transportation findTransportById(String id) throws EntityNotFoundException {
        for (int i = 0; i < transportCount; i++) {
            if (transportations[i].getTripId().equals(id)) {
                return transportations[i];
            }
        }
        EntityNotFoundException e = new EntityNotFoundException("Transport not found with ID: " + id);
        ErrorLogger.log(e.getMessage());
        throw e;
    }

    public void removeTransportation(String id) throws EntityNotFoundException {
        int index = -1;
        for (int i = 0; i < transportCount; i++) {
            if (transportations[i].getTripId().equals(id)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            EntityNotFoundException e = new EntityNotFoundException("Transport not found with ID: " + id);
            ErrorLogger.log(e.getMessage());
            throw e;
        }
        // Shift elements left
        for (int i = index; i < transportCount - 1; i++) {
            transportations[i] = transportations[i + 1];
        }
        transportations[transportCount - 1] = null;
        transportCount--;
    }

    // ---------- Accommodation operations ----------
    public void addAccommodation(Accommadation a) {
        
        accommadations[accommadationCount++] = a;
    }

    

    public void removeAccommodation(String id) throws EntityNotFoundException {
        int index = -1;
        for (int i = 0; i < accommadationCount; i++) {
            if (accommadations[i].getAccommId().equals(id)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            EntityNotFoundException e = new EntityNotFoundException("Accommodation not found with ID: " + id);
            ErrorLogger.log(e.getMessage());
            throw e;
        }
        // Shift elements left
        for (int i = index; i < accommadationCount - 1; i++) {
            accommadations[i] = accommadations[i + 1];
        }
        accommadations[accommadationCount - 1] = null;
        accommadationCount--;
    }
    // trip methods
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
        trips[tripCount++] = trip;
    } catch (InvalidTripDataException ex) {
        ErrorLogger.log("Invalid trip data: " + ex.getMessage());
        throw ex;
    }
    }

    


    public Accommadation findAccommodationById(String id) throws EntityNotFoundException {
        for (int i = 0; i < accommadationCount; i++) {
            if (accommadations[i].getAccommId().equals(id)) {
                return accommadations[i];
            }
        }
        EntityNotFoundException e = new EntityNotFoundException("Accommodation not found with ID: " + id);
        ErrorLogger.log(e.getMessage());
        throw e;
    }

    public boolean duplicateEmailCheck(String email){
        for (int i=0;i<clientCount;i++){
            if (clients[i].getEmailAddress().equalsIgnoreCase(email)){
                return true;
            }
        }
        return false;
    }
    public boolean clientExists(String clientId) {
        for (int i = 0; i < clientCount; i++) {
            if (clients[i].getClientID().equals(clientId)) {
                return true;
            }
        }
        return false;
    }

    public Client findClientById(String id)throws EntityNotFoundException{
        for (int i=0;i<clientCount;i++){
            if (clients[i].getClientID().equals(id)){
                return clients[i];
            }
        }
        EntityNotFoundException ex = new EntityNotFoundException("Client not found with ID: " + id);
        ErrorLogger.log(ex.getMessage());
        throw ex;
    }

    //----------------------------------
    // LOAD ALL DATA 
    //----------------------------------
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
       

       
    } catch (IOException ex) {
        ErrorLogger.log("Error loading data: " + ex.getMessage());
        
    }
    }   
    //----------------------------------
    // SAVE ALL DATA 
    //----------------------------------
        public  void saveAllData() {
        try {
            ClientFileManager.saveClients(clients, clientCount);
            

            AccommodationFileManager.saveAccommodations(accommadations, accommadationCount);
            

            TransportationFileManager.saveTransportations(transportations, transportCount);
           

            TripFileManager.saveTrips(trips, tripCount, clients, clientCount,
                                    transportations, transportCount,
                                    accommadations, accommadationCount);
           

           
            } catch (IOException ex) {
           
            
        }
    }
    // ---------- Getters ----------
    public Client[] getClients() { return clients; }
    public int getClientCount() { return clientCount; }
    public Trip[] getTrips() { return trips; }
    public int getTripCount() { return tripCount; }
    public Transportation[] getTransportations() { return transportations; }
    public int getTransportCount() { return transportCount; }
    public Accommadation[] getAccommodations() { return accommadations; }
    public int getAccommodationCount() { return accommadationCount; }
    
}
