package site.nomoreparties.stellarburgers.orders;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import site.nomoreparties.stellarburgers.pojo.OrderDTO;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CreateOrderTest extends OrderBaseTest{

    @Test
    @DisplayName("Создание заказа без авторизации")
    public void CreateOrderWithoutAuth() {
        OrderDTO orderDTO = getIngredient();
        ValidatableResponse response = createOrder(orderDTO, "");
        response.assertThat()
                .statusCode(200)
                .and()
                .body("order.number", notNullValue());
    }

    @Test
    @DisplayName("Создание заказа c авторизацией")
    public void CreateOrderWithAuth() {
        String token = userSteps.createUser(userSteps.generateUser());
        OrderDTO orderDTO = getIngredient();
        ValidatableResponse response =createOrder(orderDTO, token);
        userSteps.deleteCreatedUser();
        response.assertThat()
                .statusCode(200)
                .and()
                .body("order.number", notNullValue());

    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    @Description("Запрос отправляется с пустой json структурой, проверяются статус ошибки (400) и текст ошибки (Ingredient ids must be provided)")
    public void CreateOrderWithoutIngredients() {
        OrderDTO orderDTO = new OrderDTO();
        ValidatableResponse response = createOrder(orderDTO, "");
        response.assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Ingredient ids must be provided"));
    }
    @Test
    @DisplayName("Создание заказа с невалидным хешем ингредиента")
    public void CreateOrderWithInvalidIngredients() {
        OrderDTO orderDTO = getInvalidIngredient();
        ValidatableResponse response = createOrder(orderDTO, "");
        response.assertThat()
                .statusCode(500);
    }




}
