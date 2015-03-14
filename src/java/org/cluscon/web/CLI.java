/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cluscon.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.cluscon.control.Runner;

/**
 * SUBMITSUBMIT
 *
 * @author best
 */
public class CLI extends HttpServlet {

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
        String username = (String) this.getServletContext().getAttribute("username");
        if (username == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        PrintWriter out = response.getWriter();
        try {
            out.println(GenHTML.headWithTitle("ClusCon", username));
            if (!"root".equals(username)) {
                out.println("<h1>User " + username + " does not have enough rights! </h1>");
                out.close();
                return;
            }
            out.println("<body>");
            out.println("<form name=\"Run\" action=\"CLI\">");
            out.println("<INPUT TYPE=\"text\" NAME=\"command\" value=" + request.getParameter("command") + ">");

            out.println("<INPUT TYPE=\"SUBMIT\" VALUE=\"Submit\">");
            out.println("</form>");
            Runner runner = new Runner();
            runner.setCommand(request.getParameter("command"));
            out.println("Running: " + runner.getCommand());
            out.println("<br>");
            out.println("Output:");
            BufferedReader[] reader = runner.run();
            String printer;
            while ((printer = reader[0].readLine()) != null) {
                out.print(printer);
                out.println("<br>");
            }
            while ((printer = reader[1].readLine()) != null) {
                out.print(printer);
                out.println("<br>");
            }
            out.println("<h1>Servlet RunnerServlet at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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
