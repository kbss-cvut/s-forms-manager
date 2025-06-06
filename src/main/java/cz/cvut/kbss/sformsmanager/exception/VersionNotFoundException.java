package cz.cvut.kbss.sformsmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class VersionNotFoundException extends RuntimeException {

    public VersionNotFoundException(String message) {
        super(message);
    }
}
