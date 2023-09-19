package site.nomoreparties.stellarburgers.auth;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.pojo.UserDTO;


public class UserTest extends AuthBaseTest{

    @Before
    public void createUser() {
        createUser(userDTO);
    }

    @Test
    @DisplayName("Изменение данных пользователя с авторизацией")
    public void ChangeDataUserTest() {
         UserDTO DTOWithNewData = prepareNewDataForUser();
         response = sendPatchRequestNewData(DTOWithNewData, token);
         stepCompareStatusAndText(response, 200, "user.name", DTOWithNewData.getName());
    }


    @Test
    @DisplayName("Изменение данных пользователя без авторизации")
    public void ChangeNameUserWithoutAuthTest() {
        UserDTO DTOWithNewData = prepareNewDataForUser();
        response = sendPatchRequestNewData(DTOWithNewData, "");
        stepCompareStatusAndText(response, 401, "message", "You should be authorised");
    }

    @Step("Подготовка тела запроса с измененными данными")
    public UserDTO prepareNewDataForUser() {
        UserDTO dto = new UserDTO();
        dto.setName(userDTO.getName()+"_New");
        dto.setEmail(userDTO.getEmail()+"_New");
        return dto;
    }

    @Step("Изменение данных о пользователе: patch запрос к ендпоинту auth/user")
    public ValidatableResponse sendPatchRequestNewData(UserDTO dto, String token){
        return authHttpClient.patchUser(dto, token);
    }

}
