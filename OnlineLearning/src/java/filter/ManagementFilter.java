package filter;

import dao.PermissionDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import model.Permission;
import model.Role;

public class ManagementFilter implements Filter {

    private FilterConfig filterConfig = null;

    public ManagementFilter() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        Account loggedAccount = (Account) req.getSession().getAttribute("account");

        if (loggedAccount == null) {
            resp.sendError(401);
        } else {
            Role role = loggedAccount.getRole();

            List<Permission> permissions = new PermissionDAO().findPermissionOfRole(role.getId());
            
            boolean isAllow = permissions.stream()
                    .filter(p -> req.getMethod().equals(p.getMethod())
                    && req.getRequestURL().toString().equals(p.getRequestUrl()))
                    .findFirst().isPresent();
            
            if (role.getName().equals("ADMIN") || isAllow) {
                chain.doFilter(request, response);
            } else {
                resp.sendError(403);
            }
        }
    }

    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    public void destroy() {
    }

    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }
}
