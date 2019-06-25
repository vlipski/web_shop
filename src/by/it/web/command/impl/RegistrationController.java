package by.it.web.command.impl;

import by.it.entities.User;
import by.it.services.ServiceException;
import by.it.services.UserService;
import by.it.services.impl.UserServiceImpl;
import by.it.web.command.Controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RegistrationController implements Controller {
    UserService userService = UserServiceImpl.getInstance();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        if("true".equals(req.getParameter("first_request"))){
            req.setAttribute("errorMsg", "registration.fillInAllFields");
            req.getSession().setAttribute("pagePath", "product/registration.jsp");
            req.getSession().setAttribute("pageName", "registration");
            RequestDispatcher dispatcher = req.getRequestDispatcher(MAIN_PAGE);
            dispatcher.forward(req, resp);
            return;
        }
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String repeatPassword = req.getParameter("repeatPassword");
        String name = req.getParameter("name");
        if ("".equals(login) || "".equals(password) || "".equals(repeatPassword)
                || login == null || password == null || repeatPassword == null) {
            req.setAttribute("errorMsg", "registration.nonFillAllFields");
            RequestDispatcher dispatcher = req.getRequestDispatcher(MAIN_PAGE);
            dispatcher.forward(req, resp);
            return;
        }
        if (!password.equals(repeatPassword)) {
            req.setAttribute("errorMsg", "registration.passwordsDoNotMatch");
            req.setAttribute("name",name);
            RequestDispatcher dispatcher = req.getRequestDispatcher(MAIN_PAGE);
            dispatcher.forward(req, resp);
            return;
        }
        try {
            User user = userService.createUser(name, login,password);
            req.getSession().setAttribute("user", user);
            req.getSession().setAttribute("serviceMsg","registration.wasSuccessful");
            resp.sendRedirect(req.getContextPath() + "?command=shop");
        } catch (ServiceException e) {
            req.setAttribute("errorMsg", e.getMessage());
            RequestDispatcher dispatcher = req.getRequestDispatcher(MAIN_PAGE);
            dispatcher.forward(req, resp);
        }
    }
}
