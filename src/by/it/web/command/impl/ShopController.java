package by.it.web.command.impl;

import by.it.dao.ProductTvDao;
import by.it.dao.impl.ProductTvDaoImpl;
import by.it.entities.ProductTv;
import by.it.services.ProductService;
import by.it.services.ServiceException;
import by.it.services.impl.ProductServiceImpl;
import by.it.web.command.Controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class ShopController implements Controller {

    ProductService productService = ProductServiceImpl.getInstance();


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String minmax = req.getParameter("minmax");
        String[] fabricator = req.getParameterValues("fabricator");
        String price = req.getParameter("price");
        String diagonal = req.getParameter("diagonal");
        try {
            req.getSession().setAttribute("products", productService.sort(minmax, fabricator, price, diagonal));
            RequestDispatcher dispatcherAp = req.getRequestDispatcher(MAIN_PAGE);
            dispatcherAp.forward(req, resp);
            req.getSession().setAttribute("serviceMsg",null);
        }catch (ServiceException e){
            req.getSession().setAttribute("serviceMsg",e.getMessage());
            req.getSession().setAttribute("products", "");
            RequestDispatcher dispatcherAp = req.getRequestDispatcher(MAIN_PAGE);
            dispatcherAp.forward(req, resp);
        }
    }
}
