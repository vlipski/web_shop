package by.it.web.command.impl;

import by.it.entities.Basket;
import by.it.entities.User;
import by.it.services.BasketService;
import by.it.services.impl.BasketServiceImpl;
import by.it.web.command.Controller;
import org.apache.commons.lang3.math.NumberUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteFromBasket implements Controller {

    BasketService basketService = BasketServiceImpl.getInstance();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User user = (User)req.getSession().getAttribute("user");
        long idBasket = NumberUtils.toLong(req.getParameter("idBasket"));
        basketService.deleteItemBasket(idBasket);
        req.getSession().setAttribute("serviceMsg",null);
        req.getSession().setAttribute("basketDto", basketService.selectBasketDtoByIdUser(user.getIdUser()));
        resp.sendRedirect(req.getContextPath() + "?command=basketDto");

    }
}
