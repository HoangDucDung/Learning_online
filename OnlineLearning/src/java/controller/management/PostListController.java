package controller.management;

import dao.BlogCategoryDAO;
import dao.BlogDAO;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Blog;
import model.BlogCategory;

@WebServlet(name = "PostListController", urlPatterns = {"/management/post-list"})

public class PostListController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BlogDAO blogDao = new BlogDAO();
        // get all blogs
        String fromDate_raw = request.getParameter("fromDate");
        String toDate_raw = request.getParameter("toDate");
        if(fromDate_raw == null || fromDate_raw.isEmpty()){
            fromDate_raw = "-1";
        }
        if(toDate_raw == null || toDate_raw.isEmpty()){
            toDate_raw = "-1";
        }

        String cid_raw = request.getParameter("cid");
        int cid = -1;
        if (cid_raw != null) {
            cid = Integer.parseInt(cid_raw);
        } else {
            cid = -1;
        }
        String display = request.getParameter("display");
        int sid = -1;
        if (display != null && display.equalsIgnoreCase("true")) {
            sid = 1;
        } else if (display != null && display.equalsIgnoreCase("false")) {
            sid = 0;
        } else {
            sid = -1;
        }
        String title = request.getParameter("search");
        String page_raw = request.getParameter("page");
        if (page_raw == null || page_raw.length() == 0) {
            page_raw = "1";
        }
        int page = Integer.parseInt(page_raw);

        int count = blogDao.count();
        int totalPage = count / 10;

        if (count % 10 != 0) {
            totalPage++;
        }
        ArrayList<BlogCategory> allBlogCategory = new ArrayList<>();
        if (title == null && sid == -1 && cid == -1 && fromDate_raw.equalsIgnoreCase("-1")
                && toDate_raw.equalsIgnoreCase("-1")) {
            ArrayList<Blog> blogs = blogDao.getAllBlogs();
            BlogCategoryDAO blogCategoryDAO = new BlogCategoryDAO();
            allBlogCategory = blogCategoryDAO.getAllBlogCategory_DistinctName();
            ArrayList<Blog> allBlogs_DistinctDisplay = blogDao.getAllBlogs_DistinctDisplay();
            ArrayList<Blog> listBlogsByPage = getListPage(page, blogs);
      
            request.setAttribute("listBlog", listBlogsByPage);        
        }
        if (title != null) {
            ArrayList<Blog> searchby_title = blogDao.search(title);
            ArrayList<Blog> listBlogsAfterSearch = getListPage(page, searchby_title);
            BlogCategoryDAO blogCategoryDAO = new BlogCategoryDAO();
            allBlogCategory = blogCategoryDAO.getAllBlogCategory_DistinctName();
            
            request.setAttribute("listBlog", listBlogsAfterSearch);          
        }
        if (sid != -1 || cid != -1) {
            ArrayList<Blog> filterResult = blogDao.searchBy_Filter(cid, sid);
            ArrayList<Blog> listBlogsAfterFilter = getListPage(page, filterResult);
            BlogCategoryDAO blogCategoryDAO = new BlogCategoryDAO();
            allBlogCategory = blogCategoryDAO.getAllBlogCategory_DistinctName();
            
            request.setAttribute("listBlog", listBlogsAfterFilter);
        }
        if (!fromDate_raw.equalsIgnoreCase("-1") || !toDate_raw.equalsIgnoreCase("-1")) {
            ArrayList<Blog> DateResult = blogDao.search_byDate(fromDate_raw, toDate_raw);
            ArrayList<Blog> listBlogsAfterDate = getListPage(page, DateResult);
            BlogCategoryDAO blogCategoryDAO = new BlogCategoryDAO();
            allBlogCategory = blogCategoryDAO.getAllBlogCategory_DistinctName();
            
            request.setAttribute("listBlog", listBlogsAfterDate);
        }

        request.setAttribute("cid", cid);
        request.setAttribute("display", request.getParameter("display"));
        request.setAttribute("allBlogCategory", allBlogCategory);
        request.setAttribute("request", request.getRequestURI() + "?");
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("page", page);
        ArrayList<Blog> allBlogs_DistinctDisplay = blogDao.getAllBlogs_DistinctDisplay();
        request.setAttribute("blogs", allBlogs_DistinctDisplay);
        request.getRequestDispatcher("../view/post-management.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    public ArrayList<Blog> getListPage(int index, ArrayList<Blog> allBlog) {
        ArrayList<Blog> blogs = new ArrayList<>();
        for (int i = 0; i < allBlog.size(); i++) {
            if (i >= ((index - 1) * 10) && i < (index * 10)) {
                blogs.add(allBlog.get(i));
            }
        }
        return blogs;
    }

}