package recipes.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NoRecipeException extends RuntimeException {

    public NoRecipeException(String cause) {
        super(cause);
    }
}
