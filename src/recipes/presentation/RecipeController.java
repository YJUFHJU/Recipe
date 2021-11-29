package recipes.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import recipes.businesslayer.recipe.Recipe;
import recipes.businesslayer.recipe.RecipeService;
import recipes.businesslayer.user.User;
import recipes.businesslayer.user.UserDetailsImpl;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@RestController
public class RecipeController {

    @Autowired
    RecipeService recipeService;


    @PostMapping("/api/recipe/new")
    public Map<String, Integer> postRecipe(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                           @Valid @RequestBody Recipe recipe) {
        if (userDetails == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        recipe.setId(null);
        recipe.setUser(new User(userDetails.getUsername(), userDetails.getPassword(), userDetails.getRole()));
        recipeService.saveRecipe(recipe);
        return Map.of("id", recipe.getId());
    }

    @PutMapping("/api/recipe/{id}")
    public ResponseEntity<?> putRecipe(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                       @Valid @RequestBody Recipe recipe,
                                       @NotNull @Min(1) @PathVariable Integer id) {
        if (userDetails == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        Recipe inTableRecipe = getRecipe(userDetails, id);

        if (!inTableRecipe.getUser().getEmail().equals(userDetails.getUsername()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You have no access to manage this recipe.");

        recipe.setId(id);
        recipe.setUser(new User(userDetails.getUsername(), userDetails.getPassword(), userDetails.getRole()));
        recipeService.saveRecipe(recipe);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/api/recipe/{id}")
    public Recipe getRecipe(@AuthenticationPrincipal UserDetails userDetails,
                            @NotNull @Min(1) @PathVariable Integer id) {

        if (userDetails == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        Recipe recipe = recipeService.findRecipeById(id);

        if (recipe == null)
            throw new NoRecipeException(null);

        return recipe;
    }

    @GetMapping(path = "/api/recipe/search")
    public List<Recipe> searchRecipes(@AuthenticationPrincipal UserDetails userDetails,
                                      @RequestParam(required = false, name = "name") String name,
                                      @RequestParam(required = false, name = "category") String category) {
        if (userDetails == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        if ((name == null && category == null) || (name != null && category != null))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        if (name != null)
            return recipeService.findRecipesByName(name.toLowerCase());
        else
            return recipeService.findRecipesByCategory(category.toLowerCase());
    }

    @Transactional
    @DeleteMapping("/api/recipe/{id}")
    public ResponseEntity<?> deleteRecipe(@AuthenticationPrincipal UserDetails userDetails,
                                          @NotNull @Min(1) @PathVariable Integer id) {
        if (userDetails == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        Recipe recipe = getRecipe(userDetails, id);

        if (!recipe.getUser().getEmail().equals(userDetails.getUsername()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You have no access to manage this recipe.");

        recipeService.deleteRecipeById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
