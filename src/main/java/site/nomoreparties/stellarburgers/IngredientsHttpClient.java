package site.nomoreparties.stellarburgers;

import io.restassured.response.ValidatableResponse;

public class IngredientsHttpClient extends BaseHttpClient {

    private final String url;

    public IngredientsHttpClient(String url) {
        super();
        this.url = url + "ingredients/";
    }

    public ValidatableResponse getIngredients() {
        return doGetRequest(url);
    }

}
