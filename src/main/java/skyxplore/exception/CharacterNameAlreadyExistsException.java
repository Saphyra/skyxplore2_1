package skyxplore.exception;

public class CharacterNameAlreadyExistsException extends RuntimeException {
    public CharacterNameAlreadyExistsException(String message){
        super(message);
    }
}
