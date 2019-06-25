package by.it.web.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Controller {
    String MAIN_PAGE ="/WEB-INF/view/shop/shop.jsp";
    void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException;
}

