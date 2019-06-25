package by.it.web.command.impl;

import by.it.entities.Basket;
import by.it.entities.User;
import by.it.services.BasketService;
import by.it.services.OrderService;
import by.it.services.impl.BasketServiceImpl;
import by.it.services.impl.OrderServiceImpl;
import by.it.web.command.Controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class BasketDtoController implements Controller {

    BasketService basketService = BasketServiceImpl.getInstance();
    OrderService orderService = OrderServiceImpl.getInstance();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        User user = (User)req.getSession().getAttribute("user");
        req.getSession().setAttribute("serviceMsg",null);
        req.getSession().setAttribute("basketDto", basketService.selectBasketDtoByIdUser(user.getIdUser()));
        RequestDispatcher dispatcherAp = req.getRequestDispatcher(MAIN_PAGE);
        dispatcherAp.forward(req, resp);
    }
}
