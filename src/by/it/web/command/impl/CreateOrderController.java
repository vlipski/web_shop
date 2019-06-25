package by.it.web.command.impl;

import by.it.entities.Order;
import by.it.entities.User;
import by.it.services.BasketService;
import by.it.services.OrderService;
import by.it.services.ServiceException;
import by.it.services.impl.BasketServiceImpl;
import by.it.services.impl.OrderServiceImpl;
import by.it.web.command.Controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CreateOrderController implements Controller {

    public static final String SERVICE_MSG = "serviceMsg";
    OrderService orderService = OrderServiceImpl.getInstance();
    BasketService basketService = BasketServiceImpl.getInstance();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        if (req.getParameter("locale") != null) {
            RequestDispatcher dispatcherAp = req.getRequestDispatcher(MAIN_PAGE);
            dispatcherAp.forward(req, resp);
            return;
        }
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        try {
            Order order = orderService.createOrder(user.getIdUser());
            if (order == null) {
                req.setAttribute(SERVICE_MSG, "basket.noSelectedItemInStock");
                RequestDispatcher dispatcherAp = req.getRequestDispatcher(MAIN_PAGE);
                dispatcherAp.forward(req, resp);
                return;
            }
            req.setAttribute("listOrders", orderService.selectOrderByIdUser(user.getIdUser()));
            req.setAttribute(SERVICE_MSG, "basket.orderIsReady");
            req.getSession().setAttribute("basketDto", basketService.selectBasketDtoByIdUser(user.getIdUser()));
            RequestDispatcher dispatcherAp = req.getRequestDispatcher(MAIN_PAGE);
            dispatcherAp.forward(req, resp);
        } catch (ServiceException e) {
            req.setAttribute(SERVICE_MSG, e.getMessage());
            RequestDispatcher dispatcherAp = req.getRequestDispatcher(MAIN_PAGE);
            dispatcherAp.forward(req, resp);
        }
    }
}
