package site.nomoreparties.stellarburgers.orders;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;

public class GetOrdersTest extends OrderBaseTest{

    @Test
    @DisplayName("Получение заказов авторизованного пользователя")
    public void getUserOrdersWithAuth() {
        String token = userSteps.createUser(userSteps.generateUser());
        String expectedOrderNum = createOrderAndGetNum(token);
        ValidatableResponse response = getUserOrders(token);
        userSteps.deleteCreatedUser();
        assertEquals(getLastOrderNum(response), expectedOrderNum);
    }

    @Test
    @DisplayName("Получение заказов пользователя без авторизации")
    public void getUserOrdersWithoutAuth() {
        String token = "";
        ValidatableResponse response = getUserOrders(token);
        response.assertThat()
                .statusCode(401)
                .and()
                .body("message", equalTo("You should be authorised"));
    }

    public String getLastOrderNum(ValidatableResponse response) {
        ArrayList orders = response.extract().path("orders");
        LinkedHashMap order = (LinkedHashMap) orders.get(orders.size()-1);
        return order.get("number").toString();
    }
}
