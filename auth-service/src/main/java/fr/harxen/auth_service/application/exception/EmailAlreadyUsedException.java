package fr.harxen.auth_service.application.exception;

public class EmailAlreadyUsedException extends RuntimeException{
    public EmailAlreadyUsedException(){
        super("Email already used !");
    }
}
