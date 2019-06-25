package by.it.web.servlet;

import by.it.web.command.ControllerType;
import by.it.web.handler.HandlerRequest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/shop")
public class SinglServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ControllerType controllerType = HandlerRequest.getCommand(req);
        controllerType.getController().execute(req, resp);
    }
}
