package site.nomoreparties.stellarburgers;

import io.restassured.response.ValidatableResponse;

public class IngredientsHttpClass  extends BaseHttpClient {

    private final String url;

    public IngredientsHttpClass(String url) {
        super();
        this.url = url + "ingredients/";
    }

    public ValidatableResponse getIngredients() {
        return doGetRequest(url);
    }

}
