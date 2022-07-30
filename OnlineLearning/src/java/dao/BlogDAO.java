package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import model.Blog;
import model.BlogCategory;
import model.Gender;
import model.Role;

public class BlogDAO extends DBContext {

    public ArrayList<Blog> listAllBlogByPage(int cid, int page) {
        ArrayList<Blog> list = new ArrayList<>();
        try {
            String sql = "select b.*, a.FirstName, a.LastName, a.ProfilePictureUrl from [Blog] b,"
                    + " [Account] a where b.AuthorID = a.AccountID and b.Display = 1 \n"
                    + "order by b.createdDate desc, b.BlogID desc offset ? rows fetch next 5 rows only";
            if (cid != 0) {
                sql = "select b.*, cb.BlogCategoryID, bc.Name, a.FirstName, a.LastName, a.ProfilePictureUrl  "
                        + "from [Blog] b, [BlogCategoryBlog] cb, [BlogCategory] bc , [Account] a\n"
                        + "where b.BlogID = cb.BlogID and cb.BlogCategoryID = bc.BlogCategoryID "
                        + "and a.AccountID = b.AuthorID and bc.BlogCategoryID = ? and b.Display = 1"
                        + "order by b.createdDate desc, b.BlogID desc offset ? rows fetch next 5 rows only";
            }
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, page);
            if (cid != 0) {
                stm.setInt(1, cid);
                stm.setInt(2, page);
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {

                Account account = new Account();
                account.setAccountID(rs.getInt("AuthorID"));
                account.setFirstName(rs.getString("FirstName"));
                account.setLastName(rs.getString("LastName"));
                account.setProfilePictureUrl(rs.getString("ProfilePictureUrl"));

                Blog blog = new Blog();

                blog.setBlogID(rs.getInt("BlogID"));
                blog.setTitle(rs.getString("Title"));
                blog.setDescription(rs.getString("Description"));
                blog.setContent(rs.getString("Content"));
                blog.setCreatedDate(rs.getDate("CreatedDate"));
                blog.setAuthorID(account);
                blog.setDisplay(rs.getBoolean("Display"));
                blog.setThumbnailUrl(rs.getString("ThumbnailUrl"));
                blog.setNumberOfView(rs.getInt("NumberOfView"));

                list.add(blog);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int getSizeBlog(int bcid) {
        try {
            String sql = "select COUNT(*) [count] from [Blog] Where Display = 1";
            if (bcid != 0) {
                sql = "select COUNT(*) [count] from [Blog] b, [BlogCategoryBlog] cb, [BlogCategory] bc "
                        + "where b.BlogID = cb.BlogID and cb.BlogCategoryID = bc.BlogCategoryID\n"
                        + " and b.Display = 1 and bc.BlogCategoryID = ?";
            }
            PreparedStatement stm = connection.prepareStatement(sql);
            if (bcid != 0) {
                stm.setInt(1, bcid);
            }
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public ArrayList<Blog> listAllBlogBySearch(String search, int page) {
        ArrayList<Blog> list = new ArrayList<>();
        try {
            String sql = "select b.*, a.FirstName, a.LastName, (a.LastName +' '+ a.FirstName) as [fullname], a.ProfilePictureUrl "
                    + "from [Blog] b, [Account] a where b.AuthorID = a.AccountID"
                    + " and b.Display = 1 and \n"
                    + "(Title like ? or [Description] like ? "
                    + "or (a.LastName +' '+ a.FirstName) like ? or b.CreatedDate like ?)\n"
                    + "order by b.createdDate desc, b.BlogID desc offset ? rows fetch next 5 rows only";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, "%" + search + "%");
            stm.setString(2, "%" + search + "%");
            stm.setString(3, "%" + search + "%");
            stm.setString(4, "%" + search + "%");
            stm.setInt(5, page);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {

                Account account = new Account();
                account.setAccountID(rs.getInt("AuthorID"));
                account.setFirstName(rs.getString("FirstName"));
                account.setLastName(rs.getString("LastName"));
                account.setProfilePictureUrl(rs.getString("ProfilePictureUrl"));

                Blog blog = new Blog();

                blog.setBlogID(rs.getInt("BlogID"));
                blog.setTitle(rs.getString("Title"));
                blog.setDescription(rs.getString("Description"));
                blog.setContent(rs.getString("Content"));
                blog.setCreatedDate(rs.getDate("CreatedDate"));
                blog.setAuthorID(account);
                blog.setDisplay(rs.getBoolean("Display"));
                blog.setThumbnailUrl(rs.getString("ThumbnailUrl"));
                blog.setNumberOfView(rs.getInt("NumberOfView"));

                list.add(blog);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;

    }

    public int getSizeBlogBySearch(String search) {
        try {
            String sql = "select COUNT(*) [count] from [Blog] b, [Account] a "
                    + "where b.AuthorID = a.AccountID and b.Display = 1 and \n"
                    + "(b.Title like ? or b.[Description] like ? or (a.LastName +' '+ a.FirstName) like ?)";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, "%" + search + "%");
            stm.setString(2, "%" + search + "%");
            stm.setString(3, "%" + search + "%");
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("count");
            }

        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public int getSizeBlogByWeek(String search) {
        Date[] dates = convertWeekdays(search);
        try {
            String sql = "select COUNT(*) [count] from [Blog] where Display = 1 "
                    + "and CreatedDate between ? and ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setDate(1, dates[0]);
            stm.setDate(2, dates[1]);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("count");
            }

        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public Blog getBlogByID(int bid) {
        try {
            String sql = "select b.*, a.FirstName, a.LastName, a.ProfilePictureUrl from [Blog] b, [Account] a "
                    + "where b.AuthorID = a.AccountID and b.BlogID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, bid);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {

                Account account = new Account();
                account.setAccountID(rs.getInt("AuthorID"));
                account.setFirstName(rs.getString("FirstName"));
                account.setLastName(rs.getString("LastName"));
                account.setProfilePictureUrl(rs.getString("ProfilePictureUrl"));

                Blog blog = new Blog();
                blog.setBlogID(rs.getInt("BlogID"));
                blog.setTitle(rs.getString("Title"));
                blog.setDescription(rs.getString("Description"));
                blog.setContent(rs.getString("Content"));
                blog.setCreatedDate(rs.getDate("CreatedDate"));
                blog.setAuthorID(account);
                blog.setDisplay(rs.getBoolean("Display"));
                blog.setThumbnailUrl(rs.getString("ThumbnailUrl"));
                blog.setNumberOfView(rs.getInt("NumberOfView"));

                ArrayList<BlogCategory> listCategory = new BlogCategoryDAO().getCategoryByID(bid);

                blog.setBlogCategories(listCategory);
                return blog;
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int insertBlog(Blog blog) throws SQLException {
        String sql = "insert into [Blog] (Title,[Description],Content,CreatedDate,AuthorID,Display,ThumbnailUrl,NumberOfView) "
                + "values (?,?,?,?,?,?,?,?)";
        PreparedStatement stm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stm.setString(1, blog.getTitle());
        stm.setString(2, blog.getDescription());
        stm.setString(3, blog.getContent());
        stm.setDate(4, blog.getCreatedDate());
        stm.setInt(5, blog.getAuthorID().getAccountID());
        stm.setBoolean(6, blog.isDisplay());
        stm.setString(7, blog.getThumbnailUrl());
        stm.setInt(8, blog.getNumberOfView());
        stm.executeUpdate();
        ResultSet rs = stm.getGeneratedKeys();
        if (rs.next()) {
            int productId = Integer.parseInt(rs.getString(1));
            return productId;
        }
        return -1;
    }

    public void updateBlog(Blog blog) {
        try {
            String sql = "update [Blog] set Title = ?, [Description] = ?, Content = ?, CreatedDate = ?, "
                    + "AuthorID = ?, Display = ?, ThumbnailUrl = ? where BlogID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, blog.getTitle());
            stm.setString(2, blog.getDescription());
            stm.setString(3, blog.getContent());
            stm.setDate(4, blog.getCreatedDate());
            stm.setInt(5, blog.getAuthorID().getAccountID());
            stm.setBoolean(6, blog.isDisplay());
            stm.setString(7, blog.getThumbnailUrl());
            stm.setInt(8, blog.getBlogID());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getBlogIdNewest() {
        try {
            String sql = "select top 1 blogid from blog \n"
                    + "order by CreatedDate desc, blogid desc";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                return rs.getInt("blogid");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public ArrayList<Blog> getAllBlogs() {
        ArrayList<Blog> blogs = new ArrayList<>();
        try {
            String sql = "select * from blog b\n"
                    + "join account a on \n"
                    + "a.AccountID = b.AuthorID\n"
                    + " where blogid in(\n"
                    + "select b.BlogID from Blog b join BlogCategoryBlog bcb\n"
                    + "on bcb.BlogID = b.BlogID join BlogCategory bc\n"
                    + "on bc.BlogCategoryID = bcb.BlogCategoryID\n"
                    + "group by b.blogid)";

            PreparedStatement stm = connection.prepareStatement(sql);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Account account = new Account();
                account.setAccountID(rs.getInt("AuthorID"));
                account.setProfilePictureUrl(rs.getString("ProfilePictureUrl"));
                account.setFirstName(rs.getString("FirstName"));
                account.setLastName(rs.getString("LastName"));

                Blog blog = new Blog();
                blog.setBlogID(rs.getInt("BlogID"));
                blog.setTitle(rs.getString("Title"));
                blog.setDescription(rs.getString("Description"));
                blog.setContent(rs.getString("Content"));
                blog.setCreatedDate(rs.getDate("CreatedDate"));
                blog.setAuthorID(account);
                blog.setDisplay(rs.getBoolean("Display"));
                blog.setThumbnailUrl(rs.getString("ThumbnailUrl"));
                blog.setNumberOfView(rs.getInt("NumberOfView"));

                ArrayList<BlogCategory> blogCategories = new BlogCategoryDAO().getCategoryByID(blog.getBlogID());

                blog.setBlogCategories(blogCategories);

                blogs.add(blog);

            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return blogs;
    }
    
    public ArrayList<Blog> getAllBlogsForUser() {
        ArrayList<Blog> blogs = new ArrayList<>();
        try {
            String sql = "select * from blog b\n"
                    + "join account a on \n"
                    + "a.AccountID = b.AuthorID\n"
                    + " where b.Display = 1 and blogid in(\n"
                    + "select b.BlogID from Blog b join BlogCategoryBlog bcb\n"
                    + "on bcb.BlogID = b.BlogID join BlogCategory bc\n"
                    + "on bc.BlogCategoryID = bcb.BlogCategoryID\n"
                    + "group by b.blogid)";

            PreparedStatement stm = connection.prepareStatement(sql);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Account account = new Account();
                account.setAccountID(rs.getInt("AuthorID"));
                account.setProfilePictureUrl(rs.getString("ProfilePictureUrl"));
                account.setFirstName(rs.getString("FirstName"));
                account.setLastName(rs.getString("LastName"));

                Blog blog = new Blog();
                blog.setBlogID(rs.getInt("BlogID"));
                blog.setTitle(rs.getString("Title"));
                blog.setDescription(rs.getString("Description"));
                blog.setContent(rs.getString("Content"));
                blog.setCreatedDate(rs.getDate("CreatedDate"));
                blog.setAuthorID(account);
                blog.setDisplay(rs.getBoolean("Display"));
                blog.setThumbnailUrl(rs.getString("ThumbnailUrl"));
                blog.setNumberOfView(rs.getInt("NumberOfView"));

                ArrayList<BlogCategory> blogCategories = new BlogCategoryDAO().getCategoryByID(blog.getBlogID());

                blog.setBlogCategories(blogCategories);

                blogs.add(blog);

            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return blogs;
    }

    public int count() {
        try {
            String sql = "SELECT count(*) as Total FROM Blog";

            PreparedStatement stm = connection.prepareStatement(sql);

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("Total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public void deleteBlog(int id) {
        String sql = "DELETE FROM [Blog]\n"
                + "      WHERE [Blog].BlogID = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Blog> getMostViewedBlogs() {
        ArrayList<Blog> blogs = new ArrayList<>();
        try {
            String sql = "SELECT TOP 3 * FROM [Blog] JOIN Account ON AuthorID = AccountID WHERE Display = 1 ORDER BY NumberOfView DESC";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Blog blog = new Blog();
                blog.setBlogID(rs.getInt("BlogID"));
                blog.setTitle(rs.getString("Title"));
                blog.setDescription(rs.getString("Description"));
                blog.setContent(rs.getString("Content"));
                blog.setCreatedDate(rs.getDate("CreatedDate"));

                Account account = new Account();
                Role role = new Role();

                role.setId(rs.getInt("RoleID"));
                account.setAccountID(rs.getInt("AccountID"));
                account.setFirstName(rs.getString("FirstName"));
                account.setLastName(rs.getString("LastName"));
                account.setEmail(rs.getString("Email"));
                account.setProfilePictureUrl(rs.getString("ProfilePictureUrl"));
                account.setRole(role);
                account.setCreatedTime(rs.getTimestamp("CreatedTime"));
                account.setModifiedTime(rs.getTimestamp("ModifiedTime"));
                account.setPhone(rs.getString("Phone"));
                account.setAddress(rs.getString("Address"));
                account.setGender(Gender.of(rs.getBoolean("Gender")));
                account.setBalance(rs.getBigDecimal("Balance"));

                blog.setAuthorID(account);
                blog.setThumbnailUrl(rs.getString("ThumbnailUrl"));
                blog.setNumberOfView(rs.getInt("NumberOfView"));
                blogs.add(blog);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return blogs;
    }

    public ArrayList<Blog> getAllBlogs_DistinctDisplay() {
        ArrayList<Blog> blogs = new ArrayList<>();
        try {
            String sql = "select distinct display from Blog";

            PreparedStatement stm = connection.prepareStatement(sql);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Blog blog = new Blog();
                blog.setDisplay(rs.getBoolean("Display"));

                blogs.add(blog);

            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return blogs;
    }

    public ArrayList<Blog> search(String title) {
        ArrayList<Blog> blogs = new ArrayList<>();
        BlogCategoryDAO blogCategoryDAO = new BlogCategoryDAO();
        try {
            String sql = "select b.*, bc.* from Blog b join BlogCategoryBlog bcb\n"
                    + "on bcb.BlogID = b.BlogID join BlogCategory bc\n"
                    + "on bc.BlogCategoryID = bcb.BlogCategoryID\n"
                    + "where b.Title like ?";

            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, "%" + title + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Account account = new Account();
                account.setAccountID(rs.getInt("AuthorID"));

                Blog blog = new Blog();
                blog.setBlogID(rs.getInt("BlogID"));
                blog.setTitle(rs.getString("Title"));
                blog.setDescription(rs.getString("Description"));
                blog.setContent(rs.getString("Content"));
                blog.setCreatedDate(rs.getDate("CreatedDate"));
                blog.setAuthorID(account);
                blog.setDisplay(rs.getBoolean("Display"));
                blog.setThumbnailUrl(rs.getString("ThumbnailUrl"));
                blog.setNumberOfView(rs.getInt("NumberOfView"));

                ArrayList<BlogCategory> blogCategories = new BlogCategoryDAO().getCategoryByID(blog.getBlogID());

                blog.setBlogCategories(blogCategories);

                blogs.add(blog);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return blogs;
    }

    public ArrayList<Blog> searchBy_Filter(int cid, int sid) {
        ArrayList<Blog> blogs = new ArrayList<>();
        BlogCategoryDAO blogCategoryDAO = new BlogCategoryDAO();
        try {
            String sql = "select Blog.AuthorID as author, Blog.BlogID as blogID, Blog.Content as content, Blog.CreatedDate, Blog.Description,\n"
                    + "Blog.Display , Blog.NumberOfView, Blog.Title, Blog.ThumbnailUrl, BlogCategory.BlogCategoryID,\n"
                    + "BlogCategory.Description, BlogCategory.IconUrl, BlogCategory.Name, BlogCategory.ThumbnailUrl\n"
                    + "FROM Blog join BlogCategoryBlog on blog.BlogID = BlogCategoryBlog.BlogID\n"
                    + "join BlogCategory on BlogCategoryBlog.BlogCategoryID = BlogCategory.BlogCategoryID \n";
            if (cid != -1 && sid != -1) {
                sql += "where Blog.Display = ? and BlogCategory.BlogCategoryID = ?";

            } else if (cid != -1) {
                sql += "where BlogCategory.BlogCategoryID = ?";

            } else if (cid == -1) {
                sql += "where Blog.Display = ?";

            }
            PreparedStatement stm = connection.prepareStatement(sql);
            if (cid != -1 && sid != -1) {
                stm.setInt(1, sid);
                stm.setInt(2, cid);

            } else if (cid != -1) {
                stm.setInt(1, cid);
            } else if (cid == -1) {
                stm.setInt(1, sid);
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Account account = new Account();
                account.setAccountID(rs.getInt("author"));
                Blog blog = new Blog();
                blog.setBlogID(rs.getInt("blogID"));
                blog.setTitle(rs.getString("Title"));
                blog.setDescription(rs.getString("Description"));
                blog.setContent(rs.getString("content"));
                blog.setCreatedDate(rs.getDate("CreatedDate"));
                blog.setAuthorID(account);
                blog.setDisplay(rs.getBoolean("Display"));
                blog.setThumbnailUrl(rs.getString("ThumbnailUrl"));
                blog.setNumberOfView(rs.getInt("NumberOfView"));
                ArrayList<BlogCategory> blogCategories = blogCategoryDAO.getCategoryByID(rs.getInt("blogID"));
                blog.setBlogCategories(blogCategories);
                blogs.add(blog);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return blogs;
    }

    public ArrayList<Blog> getListBlogRelated(int blogID) {
        ArrayList<Blog> blogs = new ArrayList<>();
        try {
            String sql = "SELECT TOP 3 b.* FROM dbo.Blog b JOIN dbo.BlogCategoryBlog bcb\n"
                    + "ON bcb.BlogID = b.BlogID\n"
                    + "WHERE b.display = 1 and b.BlogID != ? AND bcb.BlogCategoryID\n"
                    + "IN (SELECT BlogCategoryID FROM dbo.BlogCategoryBlog WHERE BlogID = ?)\n"
                    + "ORDER BY b.CreatedDate desc";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, blogID);
            stm.setInt(2, blogID);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Account account = new Account();
                account.setAccountID(rs.getInt("AuthorID"));

                Blog blog = new Blog();
                blog.setBlogID(rs.getInt("BlogID"));
                blog.setTitle(rs.getString("Title"));
                blog.setDescription(rs.getString("Description"));
                blog.setContent(rs.getString("Content"));
                blog.setCreatedDate(rs.getDate("CreatedDate"));
                blog.setAuthorID(account);
                blog.setDisplay(rs.getBoolean("Display"));
                blog.setThumbnailUrl(rs.getString("ThumbnailUrl"));
                blog.setNumberOfView(rs.getInt("NumberOfView"));

                blogs.add(blog);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return blogs;
    }

    public ArrayList<Blog> listBlogMostView(int cid) {
        ArrayList<Blog> list = new ArrayList<>();
        try {
            String sql = "select top(3) b.*, cb.BlogCategoryID, bc.Name, a.FirstName, a.LastName, a.ProfilePictureUrl "
                    + "from [Blog] b, [BlogCategoryBlog] cb, [BlogCategory] bc , [Account] a \n"
                    + "where b.BlogID = cb.BlogID and cb.BlogCategoryID = bc.BlogCategoryID and a.AccountID = b.AuthorID "
                    + "and b.Display = 1 order by b.NumberOfView desc";
            if (cid != 0) {
                sql = "select top(3) b.*, cb.BlogCategoryID, bc.Name, a.FirstName, a.LastName, a.ProfilePictureUrl  "
                        + "from [Blog] b, [BlogCategoryBlog] cb, [BlogCategory] bc , [Account] a\n"
                        + "where b.BlogID = cb.BlogID and cb.BlogCategoryID = bc.BlogCategoryID "
                        + "and a.AccountID = b.AuthorID and bc.BlogCategoryID = ? and b.Display = 1"
                        + "order by b.NumberOfView desc";
            }
            PreparedStatement stm = connection.prepareStatement(sql);
            if (cid != 0) {
                stm.setInt(1, cid);
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {

                Account account = new Account();
                account.setAccountID(rs.getInt("AuthorID"));
                account.setFirstName(rs.getString("FirstName"));
                account.setLastName(rs.getString("LastName"));
                account.setProfilePictureUrl(rs.getString("ProfilePictureUrl"));

                Blog blog = new Blog();

                blog.setBlogID(rs.getInt("BlogID"));
                blog.setTitle(rs.getString("Title"));
                blog.setDescription(rs.getString("Description"));
                blog.setContent(rs.getString("Content"));
                blog.setCreatedDate(rs.getDate("CreatedDate"));
                blog.setAuthorID(account);
                blog.setDisplay(rs.getBoolean("Display"));
                blog.setThumbnailUrl(rs.getString("ThumbnailUrl"));
                blog.setNumberOfView(rs.getInt("NumberOfView"));

                list.add(blog);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public void increaseNumberOfView(int id, int newNumberOfView) {
        try {
            String sql = "UPDATE [dbo].[Blog] SET [NumberOfView] = ? WHERE BlogID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, newNumberOfView);
            stm.setInt(2, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Blog> search_byDate(String fromDate, String toDate) {
        ArrayList<Blog> blogs = new ArrayList<>();
        BlogCategoryDAO blogCategoryDAO = new BlogCategoryDAO();
        try {
            String sql = "select b.*, bc.* from Blog b join BlogCategoryBlog bcb\n"
                    + "on bcb.BlogID = b.BlogID join BlogCategory bc\n"
                    + "on bc.BlogCategoryID = bcb.BlogCategoryID\n"
                    + "where b.CreatedDate  BETWEEN ? AND ?";

            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, fromDate);
            stm.setString(2, toDate);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Account account = new Account();
                account.setAccountID(rs.getInt("AuthorID"));

                Blog blog = new Blog();
                blog.setBlogID(rs.getInt("BlogID"));
                blog.setTitle(rs.getString("Title"));
                blog.setDescription(rs.getString("Description"));
                blog.setContent(rs.getString("Content"));
                blog.setCreatedDate(rs.getDate("CreatedDate"));
                blog.setAuthorID(account);
                blog.setDisplay(rs.getBoolean("Display"));
                blog.setThumbnailUrl(rs.getString("ThumbnailUrl"));
                blog.setNumberOfView(rs.getInt("NumberOfView"));

                ArrayList<BlogCategory> blogCategories = new BlogCategoryDAO().getCategoryByID(blog.getBlogID());

                blog.setBlogCategories(blogCategories);

                blogs.add(blog);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return blogs;
    }

    public ArrayList<Blog> listAllBlogByWeek(String search, int page) {
        Date[] dates = convertWeekdays(search);
        ArrayList<Blog> list = new ArrayList<>();
        try {
            String sql = "select b.*, a.FirstName, a.LastName, (a.LastName +' '+ a.FirstName) as [fullname], a.ProfilePictureUrl "
                    + "from [Blog] b, [Account] a where b.AuthorID = a.AccountID and b.Display = 1 and "
                    + "b.CreatedDate between ? and ? order by b.createdDate desc, b.BlogID desc offset ? rows fetch next 5 rows only";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setDate(1, dates[0]);
            stm.setDate(2, dates[1]);
            stm.setInt(3, page);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {

                Account account = new Account();
                account.setAccountID(rs.getInt("AuthorID"));
                account.setFirstName(rs.getString("FirstName"));
                account.setLastName(rs.getString("LastName"));
                account.setProfilePictureUrl(rs.getString("ProfilePictureUrl"));

                Blog blog = new Blog();

                blog.setBlogID(rs.getInt("BlogID"));
                blog.setTitle(rs.getString("Title"));
                blog.setDescription(rs.getString("Description"));
                blog.setContent(rs.getString("Content"));
                blog.setCreatedDate(rs.getDate("CreatedDate"));
                blog.setAuthorID(account);
                blog.setDisplay(rs.getBoolean("Display"));
                blog.setThumbnailUrl(rs.getString("ThumbnailUrl"));
                blog.setNumberOfView(rs.getInt("NumberOfView"));

                list.add(blog);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public Date[] convertWeekdays(String search) {
        //convert string to weekdays
        String[] dateArr = search.split("-");

        Date[] dates = new Date[2];
        int year = Integer.parseInt(dateArr[0]);
        int week = Integer.parseInt(dateArr[1].substring(1));

        LocalDate dateMonday = LocalDate.now()
                .with(WeekFields.ISO.weekBasedYear(), year)
                .with(WeekFields.ISO.weekOfWeekBasedYear(), week)
                .with(WeekFields.ISO.dayOfWeek(), DayOfWeek.MONDAY.getValue());
        LocalDate dateSunday = LocalDate.now()
                .with(WeekFields.ISO.weekBasedYear(), year)
                .with(WeekFields.ISO.weekOfWeekBasedYear(), week)
                .with(WeekFields.ISO.dayOfWeek(), DayOfWeek.SUNDAY.getValue());
        Date monday = Date.valueOf(dateMonday);
        Date sunday = Date.valueOf(dateSunday);
        dates[0] = monday;
        dates[1] = sunday;
        return dates;
    }

    public ArrayList<Blog> getListBlogByCategory(ArrayList<Integer> listSearchId) {
        ArrayList<Blog> list = new ArrayList<>();

        String sql = "Select * from blog where BlogID\n"
                + "in (\n"
                + "	select b.BlogID from Blog b join BlogCategoryBlog bcb\n"
                + "	on b.BlogID = bcb.BlogID join BlogCategory bc\n"
                + "	on bcb.BlogCategoryID = bc.BlogCategoryID\n"
                + "	join BlogSubCategory bsc\n"
                + "	on bsc.BlogCategoryID = bc.BlogCategoryID\n";

        for (int i = 0; i < listSearchId.size(); i++) {
            if (i == 0) {
                sql += " Where bsc.BlogSubCategoryID = ? ";
            } else {
                sql += " or bsc.BlogSubCategoryID = ? ";
            }
        }
        sql += "	group by b.BlogID \n"
                + ")";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            for (int i = 0; i < listSearchId.size(); i++) {
                ps.setInt(i + 1, listSearchId.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Blog blog = mappingData(rs);
                ArrayList<BlogCategory> cate = new BlogCategoryDAO().getCategoryByID(blog.getBlogID());
                blog.setBlogCategories(cate);
                list.add(blog);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public ArrayList<Blog> getListBlogByCategoryForUser(ArrayList<Integer> listSearchId) {
        ArrayList<Blog> list = new ArrayList<>();

        String sql = "Select * from blog where Display = 1 and BlogID\n"
                + "in (\n"
                + "	select b.BlogID from Blog b join BlogCategoryBlog bcb\n"
                + "	on b.BlogID = bcb.BlogID join BlogCategory bc\n"
                + "	on bcb.BlogCategoryID = bc.BlogCategoryID\n"
                + "	join BlogSubCategory bsc\n"
                + "	on bsc.BlogCategoryID = bc.BlogCategoryID\n";

        for (int i = 0; i < listSearchId.size(); i++) {
            if (i == 0) {
                sql += " Where bsc.BlogSubCategoryID = ? ";
            } else {
                sql += " or bsc.BlogSubCategoryID = ? ";
            }
        }
        sql += "	group by b.BlogID \n"
                + ")";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            for (int i = 0; i < listSearchId.size(); i++) {
                ps.setInt(i + 1, listSearchId.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Blog blog = mappingData(rs);
                ArrayList<BlogCategory> cate = new BlogCategoryDAO().getCategoryByID(blog.getBlogID());
                blog.setBlogCategories(cate);
                list.add(blog);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<Blog> getListBlogByStatus(boolean isDisplay) {
        ArrayList<Blog> list = new ArrayList<>();
        String sql;
        if (isDisplay == true) {
            sql = "Select * from Blog where display = 1";
        } else {
            sql = "Select * from Blog where display = 0";
        }

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Blog blog = mappingData(rs);
                ArrayList<BlogCategory> cate = new BlogCategoryDAO().getCategoryByID(blog.getBlogID());
                blog.setBlogCategories(cate);
                list.add(blog);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<Blog> getListBlogByDate(Date date) {
        ArrayList<Blog> list = new ArrayList<>();
        String sql = "Select * from blog where createdDate = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDate(1, date);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Blog blog = mappingData(rs);
                ArrayList<BlogCategory> cate = new BlogCategoryDAO().getCategoryByID(blog.getBlogID());
                blog.setBlogCategories(cate);
                list.add(blog);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public ArrayList<Blog> getListBlogByDateForUser(Date date) {
        ArrayList<Blog> list = new ArrayList<>();
        String sql = "Select * from blog where display = 1 and createdDate = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDate(1, date);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Blog blog = mappingData(rs);
                ArrayList<BlogCategory> cate = new BlogCategoryDAO().getCategoryByID(blog.getBlogID());
                blog.setBlogCategories(cate);
                list.add(blog);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public Blog mappingData(ResultSet rs) throws SQLException {
        Account account = new Account();
        account.setAccountID(rs.getInt("AuthorID"));

        Blog blog = new Blog();
        blog.setBlogID(rs.getInt("BlogID"));
        blog.setTitle(rs.getString("Title"));
        blog.setDescription(rs.getString("Description"));
        blog.setContent(rs.getString("Content"));
        blog.setCreatedDate(rs.getDate("CreatedDate"));
        blog.setAuthorID(account);
        blog.setDisplay(rs.getBoolean("Display"));
        blog.setThumbnailUrl(rs.getString("ThumbnailUrl"));
        blog.setNumberOfView(rs.getInt("NumberOfView"));

        return blog;
    }

    public ArrayList<Blog> getListBlogByTxt(String txtSearch) {
        ArrayList<Blog> list = new ArrayList<>();
        String sql = "Select * from blog where title like ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + txtSearch + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Blog blog = mappingData(rs);
                ArrayList<BlogCategory> cate = new BlogCategoryDAO().getCategoryByID(blog.getBlogID());
                blog.setBlogCategories(cate);
                list.add(blog);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
