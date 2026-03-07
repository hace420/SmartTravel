package client;
import exceptions.InvalidClientDataException;

public class Client {
  private static int nextid = 1001;
  final private String clientID;
  private String firstName;
  private String lastName;
  private String emailAddress;

  // constructors
  public Client() {
    clientID = "C" + nextid;
    firstName = "";
    lastName = "";
    emailAddress = "";
    nextid++;
  }

  public Client(String firstName, String lastName, String emailAddress) throws InvalidClientDataException {

    if (firstName == null || firstName.isEmpty() || firstName.length() > 50)
        throw new InvalidClientDataException("Invalid first name");

    if (lastName == null || lastName.isEmpty() || lastName.length() > 50)
        throw new InvalidClientDataException("Invalid last name");

    if (emailAddress == null || emailAddress.length() > 100 ||
        !emailAddress.contains("@") || !emailAddress.contains(".") || emailAddress.contains(" "))
        throw new InvalidClientDataException("Invalid email format");
    

    this.firstName = firstName;
    this.lastName = lastName;
    this.emailAddress = emailAddress;
    this.clientID = "C" + nextid;
    nextid++;
  }

  public Client(Client other){
    this.firstName = other.firstName;
    this.lastName = other.lastName;
    this.emailAddress = other.emailAddress;
    this.clientID = "C" + nextid;
    nextid++;
  }

  // setters
  public void setFirstName(String firstName) throws InvalidClientDataException {
    if (firstName == null || firstName.isEmpty() || firstName.length() > 50)
        throw new InvalidClientDataException("Invalid first name");
    this.firstName = firstName;

  }

  public void setLastName(String lastName) throws InvalidClientDataException{
    if (lastName == null || lastName.isEmpty() || lastName.length() > 50)
        throw new InvalidClientDataException("Invalid last name");
    this.lastName = lastName;
  }

  public void setEmailAddress(String emailAddress) throws InvalidClientDataException{
    if (emailAddress == null || emailAddress.length() > 100 ||
        !emailAddress.contains("@") || !emailAddress.contains(".") || emailAddress.contains(" "))
        throw new InvalidClientDataException("Invalid email format");
    this.emailAddress = emailAddress;
  }

  // getters
  public String getClientID(){
    return clientID;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  @Override
  public String toString() {
    return ("Client Id: " + clientID +
        "\nFirst name: " + firstName +
        "\nLast name: " + lastName +
        "\nEmail Address: " + emailAddress);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }

    if (getClass() != obj.getClass()) {
      return false;
    }

    // Cast to Client
    Client compare = (Client) obj;

    // Compare all attributes besides clientID
    if (this.firstName.equalsIgnoreCase(compare.firstName) &&
        this.lastName.equalsIgnoreCase(compare.lastName) &&
        this.emailAddress.equalsIgnoreCase(compare.emailAddress)) {
      return true;
    } else {
      return false;
    }
  }

}
