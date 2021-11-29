package recipes.businesslayer.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;
import recipes.businesslayer.recipe.Recipe;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@NoArgsConstructor
@Entity
@Table(name="user")
public class User {

    @Id
    @Pattern(regexp = "\\S+@\\S+\\.\\S+")
    @NotBlank
    private String email;

    @Column(name = "password", length = 255)
    @NotBlank
    @Size(min = 8)
    private String password;

    @Column(name="role")
    @JsonIgnore
    private String role;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Recipe> recipeList;

    public User(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Recipe> getRecipeList() {
        return recipeList;
    }

    public void setRecipeList(List<Recipe> recipeList) {
        this.recipeList = recipeList;
    }

    @Override
    public String toString() {
        return String.format("user: {email: %s;" +
                "\npassword: %s;" +
                "\nrole: %s;" +
                "\nrecipes: %s}",
                email, password, role, recipeList);
    }
}
