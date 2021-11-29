package recipes.businesslayer.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipes.persistence.UserRepository;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User findUser(String email) {
        return userRepository.findUserByEmail(email);
    }
}
