package by.enot.eshop.filter;

import by.enot.eshop.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//check if user has rights to access admin area
@WebFilter("/admin/*")
public class IsAdminFilter extends AbstractFilter {

    private static final Logger log = Logger.getLogger(IsAdminFilter.class);


    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        User currUser = (User) request.getSession().getAttribute("User");
        if (currUser != null && currUser.getIsAdmin().equals("Y")){
            log.info("Admin access granted to: " + currUser.getName() + " " + request.getRemoteAddr());
            filterChain.doFilter(request, response);
        }else{
            //if not admin, redirect to main page
            log.info("unauthorized access from : " + request.getRemoteAddr());
            response.sendRedirect("/eshop");
        }
    }
}
