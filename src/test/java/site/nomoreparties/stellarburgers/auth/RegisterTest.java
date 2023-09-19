package site.nomoreparties.stellarburgers.auth;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import site.nomoreparties.stellarburgers.pojo.UserDTO;

import static org.hamcrest.CoreMatchers.notNullValue;

public class RegisterTest extends AuthBaseTest {


    @Test
    @DisplayName("Создание уникального пользователя")
    @Description("Запрос на регистрацию вернул код 200 и accessToken не пустой ")
    public void SuccessRegisterTest() {
//        userDTO = new UserDTO("strucheva_20@yandex.ru", "111111", "test");
        response = sendPostRequestRegister(userDTO);
        token = response.extract().path("accessToken").toString();
        response.assertThat()
                .body("accessToken", notNullValue())
                .and()
                .statusCode(200);
    }

    @Test
    @DisplayName("Создание пользователя, который уже зарегистрирован")
    @Description("Запрос на регистрацию вернул код 403 и сообщение 'User already exists'")
    public void RegisterExistingUserTest() {
        createUser(userDTO);
        response = sendPostRequestRegister(userDTO);
        stepCompareStatusAndText(response, 403,"message","User already exists" );
    }

    @Test
    @DisplayName("Создание пользователя без заполнения email")
    @Description("Запрос на создание пользователя без email возвращает статус 403 и сообщение 'Email, password and name are required fields'")
    public void RegisterWithoutEmailTest() {
        userDTO.setEmail(null);
        response = sendPostRequestRegister(userDTO);
        stepCompareStatusAndText(response, 403,"message","Email, password and name are required fields" );
    }
    @Test
    @DisplayName("Создание пользователя без заполнения Password")
    @Description("Запрос на создание пользователя без Password возвращает статус 403 и сообщение 'Email, password and name are required fields'")
    public void RegisterWithoutPasswordTest() {
        userDTO.setPassword(null);
        response = sendPostRequestRegister(userDTO);
        stepCompareStatusAndText(response, 403,"message","Email, password and name are required fields" );
    }
    @Test
    @DisplayName("Создание пользователя без заполнения Name")
    @Description("Запрос на создание пользователя без Name возвращает статус 403 и сообщение 'Email, password and name are required fields'")
    public void RegisterWithoutNameTest() {
        userDTO.setName(null);
        response = sendPostRequestRegister(userDTO);
        stepCompareStatusAndText(response, 403,"message","Email, password and name are required fields" );
    }


    @Step("Регистрация: post запрос к ендпоинту auth/register")
    public ValidatableResponse sendPostRequestRegister(UserDTO userDTO){
        return authHttpClient.register(userDTO);
    }


}
