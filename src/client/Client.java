import java.util.Scanner;
package client;

public class Client {
  private static int nextid = 1001;
  private String clientID;
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

  public Client(String firstName, String lastName, String emailAddress) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.emailAddress = emailAddress;
    this.clientID = "C" + nextid;
    nextid++;
  }

  // setters
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  // getters
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
    return ("Cleint Id: " + clientID +
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
