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

import static by.it.web.command.Controller.MAIN_PAGE;

public class AddToBasketController implements Controller{

    BasketService basketService = BasketServiceImpl.getInstance();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        User user = (User)req.getSession().getAttribute("user");
        long idProduct = NumberUtils.toLong(req.getParameter("idProduct"));
        if(idProduct == 0){
            req.getSession().setAttribute("serviceMsg",null);
            RequestDispatcher dispatcherAp = req.getRequestDispatcher(MAIN_PAGE);
            dispatcherAp.forward(req, resp);
            return;
        }
        Basket basket = basketService.createBasket(new Basket(user.getIdUser(),idProduct,1));
        if(basket == null){
            req.setAttribute("serviceMsg", "basket.noSelectedItemInStock");
            RequestDispatcher dispatcherAp = req.getRequestDispatcher(MAIN_PAGE);
            dispatcherAp.forward(req, resp);
        }else {
            req.getSession().setAttribute("serviceMsg","basket.itemAddToBasket");
            resp.sendRedirect(req.getContextPath() + "?command=shop");
        }
    }
}
