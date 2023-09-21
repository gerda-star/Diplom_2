package site.nomoreparties.stellarburgers.pojo;

public class OrderDTO {
    private String[] ingredients;

    public OrderDTO(String[] ingredients) {
        this.ingredients = ingredients;
    }
    public OrderDTO() {
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }
}
