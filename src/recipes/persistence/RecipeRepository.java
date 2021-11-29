package recipes.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipes.businesslayer.recipe.Recipe;

import java.util.List;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    Recipe findRecipeById(int id);

    void deleteRecipeById(int id);

    List<Recipe> findRecipeByNameContainingIgnoreCaseOrderByDateDesc(String inName);

    List<Recipe> findRecipeByCategoryIgnoreCaseOrderByDateDesc(String category);
}
