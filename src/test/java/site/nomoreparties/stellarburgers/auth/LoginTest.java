package site.nomoreparties.stellarburgers.auth;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;

import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.pojo.UserDTO;


import static org.hamcrest.CoreMatchers.notNullValue;


public class LoginTest extends AuthBaseTest {

    @Before
    public void createUser() {
        createUser(userDTO);
    }

    @Test
    @DisplayName("Логин под существующим пользователем")
    @Description("Запрос на авторизацию вернул код 200 и accessToken не пустой ")
    public void SuccessLoginTest() {
        response = sendPostRequestLogin(userDTO);
        response.assertThat()
                .body("accessToken", notNullValue())
                .and()
                .statusCode(200);
    }

    @Test
    @DisplayName("Логин с неверным паролем")
    @Description("Запрос на авторизацию вернул код 401 и сообщение 'email or password are incorrect' ")
    public void LoginWithInvalidPasswordTest() {
        userDTO.setPassword(userDTO.getPassword()+'1');
        response = sendPostRequestLogin(userDTO);
        stepCompareStatusAndText(response, 401,"message", "email or password are incorrect");
    }

    @Test
    @DisplayName("Логин с неверным Email")
    @Description("Запрос на авторизацию вернул код 401 и сообщение 'email or password are incorrect' ")
    public void LoginWithInvalidEmailTest() {
        userDTO.setEmail(userDTO.getEmail()+'1');
        response = sendPostRequestLogin(userDTO);
        stepCompareStatusAndText(response, 401, "message","email or password are incorrect");
    }

    @Step("Авторизация: post запрос к ендпоинту auth/login")
    public ValidatableResponse sendPostRequestLogin(UserDTO userDTO){
        return authHttpClient.login(userDTO);
    }
}
