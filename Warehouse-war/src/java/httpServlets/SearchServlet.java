package httpServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
import topology.activeobject.IFuture;
import topology.activeobject.SearchResult;

/**
 * Trieda dediaca od HttpServletu, ktorá slúži na obslúženie vstupu od používateľa.
 * Zabezpečuje obslúženie udalosti vyhľadávania.
 * @author Gabo
 */
@WebServlet(name = "SearchServlet", urlPatterns = {"/search"})
public class SearchServlet extends HttpServlet {
    
    @EJB
    private Database database;
    @EJB 
    private IFunctionality proxy;
    private SearchResult searchresult;
    private IFuture searchFututre = null;
    private static final Logger LOGGER = Logger.getLogger(SearchServlet.class.getName());

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
        searchresult = (SearchResult)searchFututre.get();
        List<String> resultList = makeResult(searchresult);
        LOGGER.log(Level.INFO, "..............................Quantity: {0}..............................", searchresult.getWarehouseID());
        request.setAttribute("searchresult", resultList);
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
        searchFututre = proxy.search(search);
        if(searchFututre != null){
            LOGGER.log(Level.INFO, "..............................Search:Future succesfully returned..............................");
        }
        doGet(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    private List<String> makeResult(SearchResult result){
        List<String> resultList = new ArrayList<String>();
        String id = result.getWarehouseID();
        List<MasterDataEntity> masterData = result.getMasterData();
        for(MasterDataEntity master : masterData){
            resultList.add(("WAREHOUSE:"+id+": "+master.getId()+" ,quantity="+Integer.toString(master.getQuantity())));
        }
        return resultList;
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
