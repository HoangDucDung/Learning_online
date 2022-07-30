package controller.management;

import dao.BlogCategoryBlogDAO;
import dao.BlogCategoryDAO;
import dao.BlogDAO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.Account;
import model.Blog;
import model.BlogCategory;
import model.BlogCategoryBlog;
import model.BlogSubCategory;

@WebServlet(name = "PostDetailController", urlPatterns = {"/management/post-detail"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 10,
        maxFileSize = 1024 * 1024 * 50,
        maxRequestSize = 1024 * 1024 * 100
)
public class PostDetailController extends HttpServlet {

    private static final long SerialVersionUID = 1L;
    private static final String UPLOAD_DIR = "img";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");

        int bid = Integer.parseInt(request.getParameter("id"));
        Blog blog = new BlogDAO().getBlogByID(bid);
        request.setAttribute("blog", blog);

        ArrayList<BlogCategory> listCategory = new BlogCategoryDAO().getAllBlogCategory();
        request.setAttribute("listCategory", listCategory);
        ArrayList<BlogSubCategory> listSubCategory = new BlogCategoryDAO().getAllBlogSubCategory();
        request.setAttribute("listSubCategory", listSubCategory);

        request.getRequestDispatcher("../view/post-detail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        String action = request.getParameter("action");
        if (action == null) {
            String idTxt = request.getParameter("id");
            if (idTxt != null) {
                Blog blog = new BlogDAO().getBlogByID(Integer.parseInt(idTxt));
                request.setAttribute("blog", blog);

                ArrayList<BlogCategory> listCategoryOfBlog = new BlogCategoryDAO().getCategoryByID(blog.getBlogID());
                request.setAttribute("listCategoryOfBlog", listCategoryOfBlog);
            }
            ArrayList<BlogCategory> listCategory = new BlogCategoryDAO().getAllBlogCategory();
            request.setAttribute("listCategory", listCategory);
            ArrayList<BlogSubCategory> listSubCategory = new BlogCategoryDAO().getAllBlogSubCategory();
            request.setAttribute("listSubCategory", listSubCategory);

            request.getRequestDispatcher("../view/post-edit-add.jsp").forward(request, response);
        } else {
            int id = Integer.parseInt(request.getParameter("id"));
            if (id == 0) {
                addBlogToData(request, response);
            } else {
                editBlogToData(request, response);
            }

        }

    }

    private void addBlogToData(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String content = request.getParameter("content");
        String thumbnailUrl = uploadFile(request);
        boolean display = request.getParameter("status").equals("1");

        String[] arrayCate = request.getParameterValues("search-category");
        String[] arraySubCate = request.getParameterValues("search-sub-category");

        ArrayList<String> arrCate = new ArrayList<>();
        arrCate.addAll(Arrays.asList(arrayCate));
        ArrayList<String> arrSubCate = new ArrayList<>();
        arrSubCate.addAll(Arrays.asList(arraySubCate));

        Account account = (Account) request.getSession().getAttribute("account");

        Blog blog = new Blog();
        blog.setTitle(title);
        blog.setDescription(description);
        blog.setContent(content);
        blog.setCreatedDate(Date.valueOf(LocalDate.now()));
        blog.setAuthorID(account);
        blog.setDisplay(display);
        blog.setThumbnailUrl(thumbnailUrl);
        blog.setNumberOfView(0);
        int blogID = -1;
        try {
            blogID = new BlogDAO().insertBlog(blog);
        } catch (SQLException ex) {
            Logger.getLogger(PostDetailController.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (String cate : arrCate) {
            Blog newBlog = new Blog();
            newBlog.setBlogID(blogID);

            BlogCategory bc = new BlogCategory();
            bc.setBlogCategoryID(Integer.parseInt(cate));

            BlogCategoryBlog blogCategoryBlog = new BlogCategoryBlog();
            blogCategoryBlog.setBlogID(newBlog);
            blogCategoryBlog.setBlogCategoryID(bc);

            new BlogCategoryBlogDAO().insertBlogCategoryBlog(blogCategoryBlog);
        }

        for (String sub : arrSubCate) {
            new BlogCategoryBlogDAO().insertBlogSubCategoryBlog(blogID, Integer.parseInt(sub));
        }

        response.sendRedirect("../management/post");
    }

    private void editBlogToData(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int blogId = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String content = request.getParameter("content");
        String thumbnailUrl = uploadFile(request);
        if(thumbnailUrl.isEmpty()) {
            thumbnailUrl = new BlogDAO().getBlogByID(blogId).getThumbnailUrl();
        }
        boolean display = request.getParameter("status").equals("1");

        String[] arrayCate = request.getParameterValues("search-category");
        String[] arraySubCate = request.getParameterValues("search-sub-category");

        ArrayList<String> arrCate = new ArrayList<>();
        arrCate.addAll(Arrays.asList(arrayCate));
        ArrayList<String> arrSubCate = new ArrayList<>();
        arrSubCate.addAll(Arrays.asList(arraySubCate));

        Account account = (Account) request.getSession().getAttribute("account");

        Blog blog = new Blog();
        blog.setBlogID(blogId);
        blog.setTitle(title);
        blog.setDescription(description);
        blog.setContent(content);
        blog.setCreatedDate(Date.valueOf(LocalDate.now()));
        blog.setAuthorID(account);
        // edit
        blog.setDisplay(display);
        blog.setThumbnailUrl(thumbnailUrl);
        blog.setNumberOfView(0);

        new BlogDAO().updateBlog(blog);

        new BlogCategoryBlogDAO().deleteByBlogID(blogId);
        new BlogCategoryBlogDAO().deleteSubByBlogID(blogId);

        for (String cate : arrCate) {
            Blog newBlog = new Blog();
            newBlog.setBlogID(blogId);

            BlogCategory bc = new BlogCategory();
            bc.setBlogCategoryID(Integer.parseInt(cate));

            BlogCategoryBlog blogCategoryBlog = new BlogCategoryBlog();
            blogCategoryBlog.setBlogID(newBlog);
            blogCategoryBlog.setBlogCategoryID(bc);

            new BlogCategoryBlogDAO().insertBlogCategoryBlog(blogCategoryBlog);
        }

        for (String sub : arrSubCate) {
            new BlogCategoryBlogDAO().insertBlogSubCategoryBlog(blogId, Integer.parseInt(sub));
        }

//        response.getWriter().write("Edit successfully");
//        response.getWriter().flush();
        response.sendRedirect("../management/post");
    }

    private String uploadFile(HttpServletRequest request) throws IOException, ServletException {
        String fileName = "";
        try {
            Part filePart = request.getPart("thumbnail");
            fileName = (String) getFileName(filePart);
            String applicationPath = request.getServletContext().getRealPath("");
            String basePath = applicationPath + File.separator + UPLOAD_DIR + File.separator;
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                File outputFilePath = new File(basePath + fileName);
                inputStream = filePart.getInputStream();
                outputStream = new FileOutputStream(outputFilePath);
                int read = 0;
                final byte[] bytes = new byte[1024];
                while ((read = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
            } catch (IOException e) {
                fileName = "";
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }

        } catch (IOException | ServletException e) {
            fileName = "";
        }
        return fileName;
    }

    private String getFileName(Part part) {
        final String partHeader = part.getHeader("content-disposition");
        System.out.println("*****partHeader :" + partHeader);
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }

        return null;
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    public ArrayList<BlogCategory> getListCategoryBlog() {
        ArrayList<BlogCategory> listCategoryBlog = new BlogCategoryDAO().getAllBlogCategory();
        return listCategoryBlog;
    }

}
