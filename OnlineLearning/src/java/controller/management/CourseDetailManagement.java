/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.management;

import dao.CourseDAO;
import dao.PricePackageDAO;
import dao.SubjectCategoryDAO;
import dao.SubjectDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Course;
import model.CoursePricePackage;
import model.Subject;
import model.SubjectCategory;

/**
 *
 * @author FPTSHOP
 */
@WebServlet(name = "CourseDetailManagement", urlPatterns = {"/management/course-detail"})
public class CourseDetailManagement extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.getRequestDispatcher("/view/course-detail-management.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(request.getParameter("Cid") == null && request.getParameter("type") == null){
            getAllSubject(request, response);
            request.setAttribute("act", "add");
            request.getRequestDispatcher("/view/addedit-course.jsp").forward(request, response);
        } else if(request.getParameter("Cid") != null && request.getParameter("type") == null){
            request.setAttribute("act", "edit");
        } else{
            getCourseForViewEdit(request, response);
            request.setAttribute("act", "view");
            processRequest(request, response);  
        }
    }
    
    private static void getCourseForViewEdit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int Cid = Integer.parseInt(request.getParameter("Cid"));
        
        Course course = new CourseDAO().getCourseByCourseId(Cid);
        
        ArrayList<Subject> slist = new SubjectDAO().getSubjectsByCourseID(Cid);
        
        ArrayList<CoursePricePackage> listPrice = new PricePackageDAO().getListPricePackageOfCourse(Cid);

        request.setAttribute("course", course);
        request.setAttribute("subjectC", slist);
        request.setAttribute("listPrice", listPrice);
    }
    
    private static void getAllSubject(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<SubjectCategory> listSC = new SubjectCategoryDAO().getAllSubjectCategory();
        request.setAttribute("listSC", listSC);

        ArrayList<Subject> listSubject = new SubjectDAO().getAllSubjects();
        request.setAttribute("listSubject", listSubject);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
