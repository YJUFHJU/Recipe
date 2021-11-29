package recipes.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipes.businesslayer.user.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findUserByEmail(String email);
}
