package site.nomoreparties.stellarburgers.orders;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import site.nomoreparties.stellarburgers.IngredientsHttpClient;
import site.nomoreparties.stellarburgers.OrderHttpClient;
import site.nomoreparties.stellarburgers.auth.AuthBaseTest;
import site.nomoreparties.stellarburgers.pojo.OrderDTO;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static site.nomoreparties.stellarburgers.config.AppConfig.API_URL;

public class OrderBaseTest {

    protected final OrderHttpClient orderHttpClient = new OrderHttpClient(API_URL);
    protected final IngredientsHttpClient ingredientsHttpClient = new IngredientsHttpClient(API_URL);

    AuthBaseTest userSteps = new AuthBaseTest();

    @Step("Получение состава заказа из существующих ингредиентов (первый ингредиент из списка)")
    public OrderDTO getIngredient(){
        ValidatableResponse response = ingredientsHttpClient.getIngredients();
        ArrayList ingredients = response.extract().path("data");
        LinkedHashMap firstIngr = (LinkedHashMap) ingredients.get(0);
        String id = firstIngr.get("_id").toString();
        return new OrderDTO(new String[] {id});
    }

    @Step("Получение состава заказа с невалидным хешем (id=INVALID)")
    public OrderDTO getInvalidIngredient(){
        String id = "INVALID";
        return new OrderDTO(new String[] {id});
    }

    @Step("Создание заказа: post запрос к ендпоинту /orders")
    public ValidatableResponse createOrder(OrderDTO orderDTO, String token) {
        return orderHttpClient.createOrder(orderDTO, token);
    }

    @Step("Получение заказов пользователя: get запрос к ендпоинту /orders")
    public ValidatableResponse getUserOrders(String token) {
        return orderHttpClient.getUserOrders(token);
    }

    @Step("Создание заказа с сохранением номера")
    public String createOrderAndGetNum(String token) {
        OrderDTO orderDTO = getIngredient();
        return  createOrder(orderDTO, token).extract().path("order.number").toString();

    }


}
