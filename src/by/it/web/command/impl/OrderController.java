package by.it.web.command.impl;

import by.it.entities.User;
import by.it.services.OrderService;
import by.it.services.impl.OrderServiceImpl;
import by.it.web.command.Controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OrderController implements Controller {

    OrderService orderService = OrderServiceImpl.getInstance();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User user = (User) req.getSession().getAttribute("user");
        req.setAttribute("listOrders", orderService.selectOrderByIdUser(user.getIdUser()));
        if (req.getParameter("download") != null) {
            resp.setHeader("Content-Disposition", "attachment; filename=orders.html");
        }
        RequestDispatcher dispatcherAp = req.getRequestDispatcher(MAIN_PAGE);
        dispatcherAp.forward(req, resp);
    }
}
