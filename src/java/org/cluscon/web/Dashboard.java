/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cluscon.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.cluscon.database.DataStruct;
import org.cluscon.database.Storage;
import org.cluscon.system.NetTool;

/**
 *
 * @author best
 */
public class Dashboard extends HttpServlet {

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
            throws ServletException, IOException, ClassNotFoundException {
        response.setIntHeader("Refresh", 5);
//        Storage restoredStorage = new Storage();
//        try {
//            restoredStorage = (Storage) StringSerializer.fromString(request.getAttribute("storage").toString());
//            restoredStorage = (Storage) request.getAttribute("storage");
//        } catch (Exception e) {
//            restoredStorage.addPair(0, "Illegal request!", "");
//        }
        Storage restoredStorage=(Storage) this.getServletContext().getAttribute("storage");
        String username = (String) this.getServletContext().getAttribute("username");
                if (username ==null){
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println(GenHTML.headWithTitle("ClusCon",username));

        out.println("<BODY>");
        out.println("<CENTER>");

        out.println("<table id=\"dash_table\">");
        out.println("<tr>");
        out.println("<td>ID</td>");
        out.println("<td>Availability</td>");
        out.println("<td>IP</td>");
        out.println("</tr>");

        // Daily forecast rows
        for (Object obj : restoredStorage) {
            DataStruct pair =(DataStruct) obj;
            if (((Integer) pair.first % 2) == 0) {
                out.println("<TR id=\"tr_odd\">");
            } else {
                out.println("<TR id=\"tr_even\">");
            }
            out.println("<TD style=\"width:20%\">");
            out.println(pair.first);
            out.println("</TD>");
            out.println("<TD>");
            if (NetTool.pingHost((String) pair.second)) {
                out.println("<img src=\"good.png\" alt=\"Smiley face\"height=\"20\" width=\"20\">");
            } else {
                out.println("<img src=\"bad.png\" alt=\"Smiley face\"height=\"20\" width=\"20\">");
            }
            out.println("</TD>");
            out.println("<TD>");
            out.println(pair.second);
            out.println("</TD>");
            out.println("</TR>");
        }
        // Close the still-open tags
        out.println("</TABLE>");
        out.println("</CENTER>");
        out.println("</BODY></HTML>");

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
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
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
