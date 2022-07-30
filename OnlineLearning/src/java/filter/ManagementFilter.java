package filter;

import dao.PermissionDAO;
import java.io.IOException;
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

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        Account account = (Account) req.getSession().getAttribute("account");
        
        if (account == null) {
            resp.sendError(401);
            return;
        }

        Role role = account.getRole();
        Permission permission = new Permission(0, null, req.getRequestURI(), req.getMethod());

        if (isRoleAllowed(role, permission)) {
            chain.doFilter(request, response);
            System.out.format("%s %s  %s ALLOWED\n", role, req.getMethod(), req.getRequestURI());

        } else {
            resp.sendError(403, "Access to this resource on the server is denied!");
            System.out.format("%s %s  %s DENIED\n", role, req.getMethod(), req.getRequestURI());
        }
    }

    private boolean isRoleAllowed(final Role role, final Permission permission) {
        if (role == null) {
            return false;
        }

        if (role.getName() == null || role.getName().isEmpty()) {
            return false;
        }

        if (role.getName().equals("ADMIN")) {
            return true;
        }

        return new PermissionDAO().isRoleAllowed(role, permission);
    }

    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }
}
