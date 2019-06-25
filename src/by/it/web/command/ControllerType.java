package by.it.web.command;

import by.it.web.command.impl.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ControllerType {
    REGISTRATION("product/registration.jsp", "registration", new RegistrationController()),
    AUTHORIZATION("product/authorization.jsp", "authorization", new AuthorizationController()),
    LOGOUT("","logout",new LogoutController()),
    BASKET_DTO("product/basketDto.jsp", "basketDto", new BasketDtoController()),
    DELETE_FROM_BASKET("product/basketDto.jsp","deleteFromBasket",new DeleteFromBasket()),
    ADD_TO_BASKET("", "addProduct", new AddToBasketController()),
    CREATE_ORDER("product/basketDto.jsp","createOrders", new CreateOrderController()),
    ORDER("product/order.jsp","orders", new OrderController()),
    SHOP("product/product.jsp", "shop", new ShopController());

    private String pagePath;
    private String pageName;
    private Controller controller;

    public static ControllerType getByPageName(String page) {
        for (ControllerType type : ControllerType.values()) {
            if (type.pageName.equalsIgnoreCase(page)) {
                return type;
            }
        }
        return SHOP;
    }
}
