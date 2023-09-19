package site.nomoreparties.stellarburgers;


import io.restassured.response.ValidatableResponse;
import site.nomoreparties.stellarburgers.pojo.UserDTO;

public class AuthHttpClient extends BaseHttpClient {

    private final String url;

    public AuthHttpClient(String url) {
        super();
        this.url = url + "auth/";
    }

    public ValidatableResponse login(UserDTO userDTO) {
        return doPostRequest(url+"login", userDTO);
    }

    public ValidatableResponse register(UserDTO userDTO) {
        return doPostRequest(url+"register", userDTO);
    }

    public ValidatableResponse deleteUser(String token) {
        return doDeleteRequest(url + "user", token);
    }
    public ValidatableResponse getUser(String token) {
        return doGetRequest(url + "user", token);
    }

    public ValidatableResponse patchUser(UserDTO userDTO, String token) {
        return doPatchRequest(url + "user", userDTO, token);
    }

}
