package site.nomoreparties.stellarburgers;

import io.restassured.response.ValidatableResponse;
import site.nomoreparties.stellarburgers.pojo.OrderDTO;

public class OrderHttpClient extends BaseHttpClient{
    private final String url;

    public OrderHttpClient(String url) {
        super();
        this.url = url+"orders/";
    }
    public ValidatableResponse createOrder(OrderDTO orderDTO, String token){
        return doPostRequest(url, orderDTO, token);
    }


    public ValidatableResponse getUserOrders(String token) {
        return doGetRequest(url, token);
    }
}
