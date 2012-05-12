/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package httpServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import persistence.Database;
import persistence.MasterDataEntity;
import topology.activeobject.IFunctionality;

/**
 *
 * @author Gabriel Cervenak
 */
@WebServlet(name = "MasterDataServlet", urlPatterns = {"/masterData"})
public class MasterDataServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(MasterDataServlet.class.getName());
    @EJB
    private IFunctionality proxy;
    @EJB
    private Database database;

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
            out.println("<title>Wrong Input</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h3><font color=\"red\">Master data with such ID exist!</font></h3>");
            out.println("<br><a href=\"masterData.jsp\">Back</a>");
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
        request.getRequestDispatcher("/masterData.jsp").forward(request, response);
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
        if (request.getParameter("command").equals("Insert")) {
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            if (name != null && description != null) {
                name = name.toUpperCase();
                if (checkName(name) == true) {
                    processRequest(request, response);
                } else {
                    MasterDataEntity masterData = new MasterDataEntity(name, description);
                    proxy.insertMasterData(masterData);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                    }
                }
            }
        }
        if (request.getParameter("command").equals("Remove")) {

            String value = (String) request.getParameter("selectMD");
            LOGGER.log(Level.INFO, "&&&&&&&&&&&&&&&&&&&&.....Selected value:{0} .......@@@@@@@@@@@@@@@@@@", value);
            proxy.removeMasterData(value);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
            }
        }
        request.getRequestDispatcher("/masterData.jsp").forward(request, response);
    }

    //Metóda vráti true ak sa daný reťazec nachádza ako ID v master datach
    private boolean checkName(String name) {
        boolean result = false;
        List<MasterDataEntity> masterDatas = database.getMasterData();
        for (MasterDataEntity entity : masterDatas) {
            if (entity.getId().equals(name)) {
                result = true;
            }
        }
        return result;
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
