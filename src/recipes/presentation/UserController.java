package recipes.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import recipes.businesslayer.user.User;
import recipes.businesslayer.user.UserService;

import javax.validation.Valid;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder encoder;

    @PostMapping("/api/register")
    @ResponseStatus(HttpStatus.OK)
    public void postUser(@Valid @RequestBody User user) {
        User inTable = userService.findUser(user.getEmail());

        if (inTable != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with this email already exists.");

        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole("USER");
        userService.saveUser(user);
    }
}
