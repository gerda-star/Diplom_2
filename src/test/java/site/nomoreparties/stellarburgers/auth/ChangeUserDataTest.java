package site.nomoreparties.stellarburgers.auth;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.pojo.UserDTO;

import static org.hamcrest.CoreMatchers.equalTo;


public class ChangeUserDataTest extends AuthBaseTest{

    @Before
    public void createUser() {
        createUser(userDTO);
    }

    @Test
    @DisplayName("Изменение имени пользователя")
    public void ChangeNameUserTest() {
         UserDTO DTOWithNewData = prepareNewNameForUser();
         response = sendPatchRequestNewData(DTOWithNewData, token);
         stepCompareStatusAndText(response, 200, "user.name", DTOWithNewData.getName());
    }
    @Test
    @DisplayName("Изменение почты пользователя")
    public void ChangeEmailUserTest() {
        UserDTO DTOWithNewData = prepareNewEmailForUser();
        response = sendPatchRequestNewData(DTOWithNewData, token);
        stepCompareStatusAndText(response, 200, "user.email", DTOWithNewData.getEmail().toLowerCase());
    }
    @Test
    @DisplayName("Изменение имени и почты пользователя")
    public void ChangeDataUserTest() {
        UserDTO DTOWithNewData = prepareNewDataForUser();
        response = sendPatchRequestNewData(DTOWithNewData, token);
        response.assertThat()
                .statusCode(200)
                .and()
                .body("user.name", equalTo(DTOWithNewData.getName()))
                .and()
                .body("user.email", equalTo(DTOWithNewData.getEmail().toLowerCase()));
    }


    @Test
    @DisplayName("Изменение данных пользователя без авторизации")
    public void ChangeDataUserWithoutAuthTest() {
        UserDTO DTOWithNewData = prepareNewDataForUser();
        response = sendPatchRequestNewData(DTOWithNewData, "");
        stepCompareStatusAndText(response, 401, "message", "You should be authorised");
    }

    @Step("Подготовка тела запроса с измененными данными пользователя")
    public UserDTO prepareNewDataForUser() {
        UserDTO dto = new UserDTO();
        dto.setName(userDTO.getName()+"_New");
        dto.setEmail(userDTO.getEmail()+"_New");
        return dto;
    }

    @Step("Подготовка тела запроса с измененным именем")
    public UserDTO prepareNewNameForUser() {
        UserDTO dto = new UserDTO();
        dto.setName(userDTO.getName()+"_New");
        return dto;
    }
    @Step("Подготовка тела запроса с измененной почтой")
    public UserDTO prepareNewEmailForUser() {
        UserDTO dto = new UserDTO();
        dto.setEmail(userDTO.getEmail()+"_New");
        return dto;
    }

    @Step("Изменение данных о пользователе: patch запрос к ендпоинту auth/user")
    public ValidatableResponse sendPatchRequestNewData(UserDTO dto, String token){
        return authHttpClient.patchUser(dto, token);
    }

}
