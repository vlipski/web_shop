package by.it.web.handler;

import by.it.web.command.ControllerType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.it.web.command.ControllerType.SHOP;

public class HandlerRequest {

    public static ControllerType getCommand(HttpServletRequest req) {
        String param = req.getParameter("command");
        if ("".equals(param)) {
            param = "shop";
        }

        ControllerType type = ControllerType.getByPageName(param);
        HttpSession session = req.getSession();
        String pageName = (String) session.getAttribute("pageName");
        if (pageName != null) {
            session.setAttribute("pageName", type.getPageName());
            session.setAttribute("pagePath", type.getPagePath());
        } else {
            session.setAttribute("pageName", SHOP.getPageName());
            session.setAttribute("pagePath", SHOP.getPagePath());
        }
        return type;
    }
}
