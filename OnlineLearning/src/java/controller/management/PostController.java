package controller.management;

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

@WebServlet(name = "PostController", urlPatterns = {"/management/post"})

public class PostController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ArrayList<Blog> listBlog = new BlogDAO().getAllBlogs();
        request.setAttribute("listBlog", listBlog);

        ArrayList<BlogCategory> listCategory = new BlogCategoryDAO().getAllBlogCategory();
        request.setAttribute("listCategory", listCategory);

        ArrayList<BlogSubCategory> listSubCategory = new BlogCategoryDAO().getAllBlogSubCategory();
        request.setAttribute("listSubCategory", listSubCategory);

        request.getRequestDispatcher("../view/post-management.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String datePicker = request.getParameter("datepicker");
        String status = request.getParameter("status");
        String txtSearchId = request.getParameter("arraySearchId");

        BlogDAO blogDao = new BlogDAO();

        ArrayList<Blog> listBlogSearchByCategory = new ArrayList<>();
        if (txtSearchId.isEmpty()) {
            listBlogSearchByCategory = blogDao.getAllBlogs();
        } else {
            String[] arraySearchId = txtSearchId.split(",");
            ArrayList<String> list = new ArrayList<>();
            list.addAll(Arrays.asList(arraySearchId));
            ArrayList<Integer> listSearchId = new ArrayList<>();
            for (String string : list) {
                listSearchId.add(Integer.parseInt(string));
            }
            listBlogSearchByCategory = blogDao.getListBlogByCategory(listSearchId);
        }

        ArrayList<Blog> listBlogSearchByStatus = new ArrayList<>();
        if (status.equalsIgnoreCase("All")) {
            listBlogSearchByStatus = blogDao.getAllBlogs();
        } else if (status.equalsIgnoreCase("Display")) {
            listBlogSearchByStatus = blogDao.getListBlogByStatus(true);
        } else {
            listBlogSearchByStatus = blogDao.getListBlogByStatus(false);
        }

        ArrayList<Blog> listBlogSearchByDate = new ArrayList<>();
        if (datePicker.isEmpty()) {
            listBlogSearchByDate = blogDao.getAllBlogs();
        } else {
            Date date = Date.valueOf(datePicker);
            listBlogSearchByDate = blogDao.getListBlogByDate(date);
        }

        ArrayList<Blog> listTemp = new ArrayList<>();
        for (Blog blog : listBlogSearchByCategory) {
            for (Blog blog2 : listBlogSearchByStatus) {
                if (blog.getBlogID() == blog2.getBlogID()) {
                    listTemp.add(blog);
                }
            }
        }

        ArrayList<Blog> listFinal = new ArrayList<>();
        for (Blog blog : listTemp) {
            for (Blog blog1 : listBlogSearchByDate) {
                if (blog.getBlogID() == blog1.getBlogID()) {
                    listFinal.add(blog);
                }
            }
        }

        for (Blog blog : listFinal) {
            response.getWriter().write("<tr>\n"
                    + "                                        <td>" + blog.getBlogID() + "</td>\n"
                    + "                                        <td style=\"width: 50px\"><img style=\"width: 100%\" class=\"img-thumbnail-blog\" src=\"../img/" + blog.getThumbnailUrl() + "\"></td>\n"
                    + "                                        <td>" + blog.getTitle() + "</td>\n"
                    + "\n"
                    + "                                        <td>\n");

            for (int i = 0; i < blog.getBlogCategories().size(); i++) {
                if (i == 0) response.getWriter().write(blog.getBlogCategories().get(0).getName());
                else response.getWriter().write(", " + blog.getBlogCategories().get(0).getName());
            }
            
            response.getWriter().write("                       </td>\n"
                    + "                                        <td>"+blog.getCreatedDate()+"</td>\n"
                    + "                                        <td>" + (blog.isDisplay() ? "Display" : "Hidden") + "</td>\n"
                    + "                                        <td><a class=\"text-primary myTable-change myTable-change1\" href=\"post-detail?id="+ blog.getBlogID() +"\" >View</a> / \n"
                    + "                                            <a class=\"text-danger myTable-change myTable-change2\" href=\"#\" onclick=\"deleteBlog("+ blog.getBlogID() +")\">Delete</a></td>\n"
                    + "                                    </tr>");
        }

        response.getWriter().flush();
    }
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        new BlogDAO().deleteBlog(id);
        ArrayList<Blog> list = new BlogDAO().getAllBlogs();
        
        for (Blog blog : list) {
            response.getWriter().write("<tr>\n"
                    + "                                        <td>" + blog.getBlogID() + "</td>\n"
                    + "                                        <td style=\"width: 50px\"><img style=\"width: 100%\" class=\"img-thumbnail-blog\" src=\"../img/" + blog.getThumbnailUrl() + "\"></td>\n"
                    + "                                        <td>" + blog.getTitle() + "</td>\n"
                    + "\n"
                    + "                                        <td>\n");

            for (int i = 0; i < blog.getBlogCategories().size(); i++) {
                if (i == 0) response.getWriter().write(blog.getBlogCategories().get(0).getName());
                else response.getWriter().write(", " + blog.getBlogCategories().get(0).getName());
            }
            
            response.getWriter().write("                       </td>\n"
                    + "                                        <td>"+blog.getCreatedDate()+"</td>\n"
                    + "                                        <td>"  + (blog.isDisplay() ? "Display" : "Hidden") +  "</td>\n"
                    + "                                        <td><a class=\"text-primary myTable-change myTable-change1\" href=\"post-detail?bid="+ blog.getBlogID() +"\" >Edit</a> / \n"
                    + "                                            <a class=\"text-danger myTable-change myTable-change2\" href=\"#\" onclick=\"deleteBlog("+ blog.getBlogID() +")\">Delete</a></td>\n"
                    + "                                    </tr>");
        }

        response.getWriter().flush();
        
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
