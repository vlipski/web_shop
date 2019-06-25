package by.it.web.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class AuthFiltr implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String param = req.getParameter("command");
        if ("addProduct".equals(param) || "basketDto".equals(param) || "deleteFromBasket".equals(param)
                || "orders".equals(param) || "createOrders".equals(param)) {
            if((req.getSession().getAttribute("user") == null)){
                resp.sendRedirect(req.getContextPath() + "?command=authorization");
                return;
            }
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
