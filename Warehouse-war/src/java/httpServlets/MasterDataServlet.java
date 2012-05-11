/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package httpServlets;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
                MasterDataEntity masterData = new MasterDataEntity(name, description);
                try {
                    proxy.insertMasterData(masterData);
                } catch (Exception e) {
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
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
