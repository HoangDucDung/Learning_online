package controller.blog;

import dao.BlogCategoryDAO;
import dao.BlogDAO;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Blog;
import model.BlogCategory;

@WebServlet(name = "BlogController", urlPatterns = {"/blog"})

public class BlogController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        request.getRequestDispatcher("view/blog.jsp").forward(request, response);
    }

    private ArrayList<Blog> ListAllBlogBySearch(HttpServletRequest request, HttpServletResponse response, int offset, int bcID) {
        String search = (String) request.getParameter("search");
        ArrayList<Blog> blist = new BlogDAO().listAllBlogBySearch(search, offset);;
        if (request.getParameter("SearchWeek") != null) {
            //check if searchweek is different than empty string then enter
            if (!request.getParameter("SearchWeek").toString().equals("")) {
                search = (String) request.getParameter("SearchWeek");
                blist = new BlogDAO().listAllBlogByWeek(search, offset);
            }
        }
        if (blist.size() == 0 && request.getParameter("search") == null) {
            blist = new BlogDAO().listAllBlogByPage(bcID, offset);
        }
        System.out.println("blist: " + blist);
        return blist;
    }

    private void blogByCategory(HttpServletRequest request, HttpServletResponse response) {
        int bcID = 0;
        int page = 1;

        if (request.getParameter("bcid") != null) {
            bcID = Integer.parseInt(request.getParameter("bcid"));
        }

        //get size and dive page by blog category
        int totalpageProduct = new BlogDAO().getSizeBlog(bcID);
        //if search not null then reassign the value of totalpageProduct
        if (request.getParameter("search") != null) {
            totalpageProduct = new BlogDAO().getSizeBlogBySearch(request.getParameter("search"));
        } else if (request.getParameter("SearchWeek") != null) {
            //check if searchweek is different than empty string then enter
            if (!request.getParameter("SearchWeek").toString().equals("")) {
                totalpageProduct = new BlogDAO().getSizeBlogByWeek(request.getParameter("SearchWeek"));
            }
        }
        int totalpage = totalpageProduct / 5;
        if (totalpageProduct % 5 != 0) {
            totalpage += 1;
        }

        //check if page is not null and satisfy the value in the given range
        if (request.getParameter("page") != null) {
            try {
                page = Integer.parseInt(request.getParameter("page"));

            } catch (NumberFormatException e) {
                page = 1;
            }
            if (page < 1 || page > totalpage) {
                page = 1;
            }
        }
        //get offset from page to select 5 objects in database
        int offSet = (page - 1) * 5;

        ArrayList<Blog> bList = new BlogDAO().listAllBlogByPage(bcID, offSet);
        //assign value to arraylist
        if (request.getParameter("search") != null || request.getParameter("SearchWeek") != null) {
            //get value by search if search != null
            bList = ListAllBlogBySearch(request, response, offSet, bcID);
        }

        //Display the top 3 blogs with the most views by category
        ListBlogCategoryMostView(request, response, bcID);

        //setAttribute
        if (!bList.isEmpty()) {
            //if the returned array is an array of size = 0 then do not 
            //setAttribute "blogList" to return no value found (49 - 51 blog.jsp)
            request.setAttribute("blogList", bList);
        }
        request.setAttribute("page", page);
        request.setAttribute("totalpage", totalpage);
        request.setAttribute("search", request.getParameter("search"));
        request.setAttribute("searchweek", request.getParameter("SearchWeek"));

        //Set Attribute for url with bcid or with search and without bcid or search
        if (request.getParameter("bcid") != null) {
            request.setAttribute("bcid", request.getParameter("bcid"));
            //setAttribute for page.jsp link when bcid != 0
            request.setAttribute("request", request.getRequestURI() + "?bcid=" + bcID + "&");
        } else if (request.getParameter("search") != null) {
            //setAttribute for page.jsp link when search != 0
            request.setAttribute("request", request.getRequestURI() + "?search=" + request.getParameter("search") + "&");
        } else {
            request.setAttribute("request", request.getRequestURI() + "?");
        }

    }

    private void ListAllBlogCategory(HttpServletRequest request, HttpServletResponse response) {
        ArrayList<BlogCategory> bcList = new ArrayList<>();
        bcList = new BlogCategoryDAO().getAllBlogCategory();
        request.setAttribute("bcList", bcList);
    }

    private void ListBlogCategoryMostView(HttpServletRequest request, HttpServletResponse response, int bcid) {
        ArrayList<Blog> bcMostView = new ArrayList<>();
        bcMostView = new BlogDAO().listBlogMostView(bcid);
        request.setAttribute("bcMostView", bcMostView);
        request.setAttribute("bcMost", bcMostView.get(0));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        blogByCategory(request, response);
        ListAllBlogCategory(request, response);
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
