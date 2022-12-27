package exceptions;


public class UserNotfoundException extends RuntimeException{
    public UserNotfoundException(String s){
        super(s);
    }
}
