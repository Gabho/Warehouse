/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package httpServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import persistence.Database;
import topology.activeobject.IFuture;
import topology.activeobject.IFunctionality;

/**
 *
 * @author Gabo
 */
@WebServlet(name = "SearchServlet", urlPatterns = {"/search"})
public class SearchServlet extends HttpServlet {
    
    @EJB
    private Database database;
    @EJB 
    private IFunctionality proxy;
    private int quantity;
    private IFuture quantityFututre = null;
    private static final Logger LOGGER = Logger.getLogger(SearchServlet.class.getName());

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /*
             * TODO output your page here. You may use following sample code.
             */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SearchServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SearchServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        quantity = (Integer)quantityFututre.get();
        LOGGER.log(Level.INFO, "..............................Quantity: {0}..............................", quantity);
        request.setAttribute("quantity", Integer.toString(quantity));
        request.getRequestDispatcher("/search.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String search = request.getParameter("searchString");
        if(proxy != null){
            LOGGER.log(Level.INFO, "..............................Search:Proxy succesfully initialized..............................");
        }
        quantityFututre = proxy.search(search);
        if(quantityFututre != null){
            LOGGER.log(Level.INFO, "..............................Search:Future succesfully returned..............................");
        }
        doGet(request, response);
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
