package by.it.web.command.impl;

import by.it.entities.User;
import by.it.services.ServiceException;
import by.it.services.UserService;
import by.it.services.impl.UserServiceImpl;
import by.it.web.command.Controller;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class AuthorizationController implements Controller {
    UserService userService = UserServiceImpl.getInstance();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if (StringUtils.isEmpty(login) || StringUtils.isEmpty(password)) {
            req.setAttribute("errorMsg", "authorization.enterLoginAndPassword");
            req.getSession().setAttribute("pagePath", "product/authorization.jsp");
            req.getSession().setAttribute("pageName", "authorization");
            req.getSession().setAttribute("serviceMsg",null);
            RequestDispatcher dispatcher = req.getRequestDispatcher(MAIN_PAGE);
            dispatcher.forward(req, resp);
            return;
        }
        try {
            User user = userService.authorization(login, password);
            if (user == null) {
                req.setAttribute("errorMsg", "authorization.invalidLoginAndPassword");
                req.getSession().setAttribute("serviceMsg",null);
                RequestDispatcher dispatcher = req.getRequestDispatcher(MAIN_PAGE);
                dispatcher.forward(req, resp);
                return;
            }
            req.getSession().setAttribute("user", user);
            req.getSession().setAttribute("serviceMsg","authorization.wasSuccessful");
            resp.sendRedirect(req.getContextPath() + "?command=shop");
        } catch (ServiceException e) {
            req.setAttribute("errorMsg", "authorization.enterLoginAndPassword");
            RequestDispatcher dispatcher = req.getRequestDispatcher(MAIN_PAGE);
            dispatcher.forward(req, resp);
            System.out.println(e);
        }
    }
}
