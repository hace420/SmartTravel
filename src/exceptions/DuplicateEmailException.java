//------------------------------------------
// Assignment (2)
// Question: ()
// Written by: (Christian Buckley 40329967)
//------------------------------------------
package exceptions;

public class DuplicateEmailException extends RuntimeException{
        public DuplicateEmailException(String message) {
        super(message);
    }
}
