package recipes.businesslayer.recipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipes.persistence.RecipeRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public int saveRecipe(Recipe recipe) {
        recipe.setDate(LocalDateTime.now());
        recipeRepository.save(recipe);
        return recipe.getId();
    }

    public Recipe findRecipeById(int id) {
        return recipeRepository.findRecipeById(id);
    }

    public void deleteRecipeById(int id) {
        recipeRepository.deleteRecipeById(id);
    }

    public List<Recipe> findRecipesByName(String inName) {
        return recipeRepository.findRecipeByNameContainingIgnoreCaseOrderByDateDesc(inName);
    }

    public List<Recipe> findRecipesByCategory(String category) {
        return recipeRepository.findRecipeByCategoryIgnoreCaseOrderByDateDesc(category);
    }
}
