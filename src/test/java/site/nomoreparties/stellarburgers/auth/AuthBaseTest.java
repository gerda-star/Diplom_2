package site.nomoreparties.stellarburgers.auth;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import site.nomoreparties.stellarburgers.AuthHttpClient;
import site.nomoreparties.stellarburgers.pojo.UserDTO;

import static org.hamcrest.CoreMatchers.equalTo;
import static site.nomoreparties.stellarburgers.config.AppConfig.API_URL;

public class AuthBaseTest {

    protected final AuthHttpClient authHttpClient = new AuthHttpClient(API_URL);
    ValidatableResponse response;
    protected UserDTO userDTO;
    protected  String token = "";

    @Before
    public void DataPreparing() {
        userDTO = generateUser();
    }

    @Step("Формирвоание данных для создания пользователя")
    public UserDTO generateUser() {
        String email = RandomStringUtils.randomAlphabetic(8) + "@gmail.com";
        String password = RandomStringUtils.randomAlphanumeric(4);
        String name = RandomStringUtils.randomAlphanumeric(4);
        return new UserDTO(email, password, name);
    }

    @Step("Создание пользователя и сохраняем токен: post запрос к ендпоинту auth/register")
    public void createUser(UserDTO userDTO) {
        response = authHttpClient.register(userDTO);
        token = response.extract().path("accessToken").toString();
    }

    @Step("Удаление созданного пользователя: delete запрос к ендпоинту auth/user")
    public void deleteCreatedUser(String token) {
        authHttpClient.deleteUser(token);
    }

    @Step("Проверка, правильный ли статус и текст в ответе")
    public void stepCompareStatusAndText(ValidatableResponse response, int expectedStatus, String field, String expectedMessage){
        response.assertThat()
                .statusCode(expectedStatus)
                .and()
                .body(field, equalTo(expectedMessage));
    }

    @After
    public void deleteCreatedUser() {
        deleteCreatedUser(token);

    }
}
