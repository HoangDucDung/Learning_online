package controller.blog;

import dao.BlogCategoryDAO;
import dao.BlogDAO;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Blog;
import model.BlogCategory;
import model.BlogSubCategory;

@WebServlet(name = "BlogController", urlPatterns = {"/blog"})

public class BlogController extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        
        ArrayList<BlogCategory> listBC = new BlogCategoryDAO().getAllBlogCategory();
        request.setAttribute("listBC", listBC);

        ArrayList<BlogSubCategory> listSubCate = new BlogCategoryDAO().getAllBlogSubCategory();
        request.setAttribute("listSubCate", listSubCate);

        ArrayList<Blog> listBlog = new BlogDAO().getAllBlogsForUser();
        int totalCourse = listBlog.size();
        int numItem = 5;
        int totalpage = 0;
        if (totalCourse % numItem == 0) {
            totalpage = (int) totalCourse / numItem;
        } else {
            totalpage = (int) totalCourse / numItem + 1;
        }
        request.setAttribute("totalpage", totalpage);

        int page = 1;
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
        request.setAttribute("page", page);

        ArrayList<Blog> listBlogCurrent = new ArrayList<>();
        for (int i = 0; i < listBlog.size(); i++) {
            if (i >= (page - 1) * numItem && i < page * numItem) {
                listBlogCurrent.add(listBlog.get(i));
            }
        }
        request.setAttribute("blogList", listBlogCurrent);

        ArrayList<Blog> listMostView = new BlogDAO().getMostViewedBlogs();
        request.setAttribute("bcMostView", listMostView);

        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        request.getRequestDispatcher("view/blog.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        String dateJoin = request.getParameter("datejoin");
        String txtSearch = request.getParameter("txtSearch");
        String txtSearchId = request.getParameter("arraySearchId");
        int pageNum = Integer.parseInt(request.getParameter("pageNum"));

        ArrayList<Blog> listBlogSearchByCategory = new ArrayList<>();
        if (txtSearchId.isEmpty()) {
            listBlogSearchByCategory = new BlogDAO().getAllBlogsForUser();
        } else {
            String[] arraySearchId = txtSearchId.split(",");
            ArrayList<String> list = new ArrayList<>();
            list.addAll(Arrays.asList(arraySearchId));
            ArrayList<Integer> listSearchId = new ArrayList<>();
            for (String string : list) {
                listSearchId.add(Integer.parseInt(string));
            }
            listBlogSearchByCategory = new BlogDAO().getListBlogByCategoryForUser(listSearchId);
        }

        ArrayList<Blog> listBlogByDate = new ArrayList<>();
        if (dateJoin.isEmpty()) {
            listBlogByDate = new BlogDAO().getAllBlogsForUser();
        } else {
            Date dateCreated = Date.valueOf(dateJoin);
            listBlogByDate = new BlogDAO().getListBlogByDateForUser(dateCreated);
        }
        
        ArrayList<Blog> listBlogBySearch = new BlogDAO().getListBlogByTxt(txtSearch);
        
        ArrayList<Blog> listTemp = new ArrayList<>();
        for (Blog blog : listBlogSearchByCategory) {
            for (Blog blog1 : listBlogByDate) {
                if (blog.getBlogID() == blog1.getBlogID()) {
                    listTemp.add(blog);
                }
            }
        }
        
        ArrayList<Blog> listFinal = new ArrayList<>();
        for (Blog blog : listTemp) {
            for (Blog blog1 : listBlogBySearch) {
                if (blog.getBlogID() == blog1.getBlogID()) {
                    listFinal.add(blog);
                }
            }
        }

        int totalCourse = listFinal.size();
        int numItem = 5;
        int totalpage = 0;
        if (totalCourse % numItem == 0) {
            totalpage = (int) totalCourse / numItem;
        } else {
            totalpage = (int) totalCourse / numItem + 1;
        }

        ArrayList<Blog> listBlogCurrent = new ArrayList<>();
        for (int i = 0; i < listFinal.size(); i++) {
            if (i >= (pageNum - 1) * numItem && i < pageNum * numItem) {
                listBlogCurrent.add(listFinal.get(i));
            }
        }

        if (listBlogCurrent.isEmpty()) {
            response.getWriter().write("<p>No matching records found</p>");
            response.getWriter().write("<input type=\"hidden\" id=\"page-num\" value=\"" + pageNum + "\">");
        } else {
            response.getWriter().write("<ul class=\"blog-list\" id=\"container-left\">\n");
            for (Blog blog : listBlogCurrent) {
                response.getWriter().write("<li class=\"blog-item\">\n"
                        + "                                            <a href=\"blog-detail?blogID=" + blog.getBlogID() +" \">\n"
                        + "                                                <div class=\"blog-item-header\">\n"
                        + "                                                    <div class=\"blog-item-author\">\n"
                        + "                                                        <img src=\"img/" + blog.getAuthorID().getProfilePictureUrl() + "\"\n"
                        + "                                                             alt=\"\" class=\"blog-item-author-avatar\">\n"
                        + "                                                        <span class=\"blog-item-author-name\">" + blog.getAuthorID().getFirstName() + " " + blog.getAuthorID().getLastName() + "</span>\n"
                        + "                                                    </div>\n"
                        + "                                                </div>\n"
                        + "                                                <div class=\"blog-item-body\">\n"
                        + "                                                    <div class=\"blog-item-body-left\">\n"
                        + "                                                        <h3 class=\"blog-item-body-title\">" + blog.getTitle() + "</h3>\n"
                        + "                                                        <p class=\"blog-item-body-description\">" + blog.getDescription() + "</p>\n"
                        + "                                                        <p class=\"blog-item-body-time\">" + blog.getCreatedDate() + "</p>\n"
                        + "                                                    </div>\n"
                        + "                                                    <div class=\"blog-item-body-right\">\n"
                        + "                                                        <img src=\"img/" + blog.getThumbnailUrl() + "\"\n"
                        + "                                                             alt=\"\" class=\"blog-item-body-right-img\">\n"
                        + "                                                    </div>\n"
                        + "                                                </div>\n"
                        + "                                            </a>\n"
                        + "                                        </li>");
            }
             response.getWriter().write("</ul>\n");
            response.getWriter().write("<div class=\"pagination\">\n"
                    + "                <ul class=\"pagination-list\">\n"
                    + "                    <li>\n"
                    + "                        <button onclick=\"pagination(" + (pageNum - 1 == 0 ? 1 : pageNum - 1) + ")\" class=\"previous-btn\" >Previous</button>\n"
                    + "                    </li>\n");
            for (int i = 1; i <= totalpage; i++) {
                response.getWriter().write("<li>\n"
                        + "                            <button onclick=\"pagination(" + i + ")\" " + (i == pageNum ? ("class = \"paging-active page-num\"") : ("class=\"page-num\"")) + ">" + i + "</button>\n"
                        + "                        </li>");
            }
           
            response.getWriter().write("<li>\n"
                    + "                        <button onclick=\"pagination(" + (pageNum + 1 > totalpage ? totalpage : pageNum + 1) + ")\" class=\"next-btn\" >Next</button>\n"
                    + "                    </li>\n"
                    + "                </ul>\n"
                    + "                <input type=\"hidden\" id=\"page-num\" value=" + pageNum + ">\n"
                    + "            </div>");
        }
        response.getWriter().flush();
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
