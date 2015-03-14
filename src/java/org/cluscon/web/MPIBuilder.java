/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cluscon.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.cluscon.control.Runner;

/**
 *
 * @author rilian-la-te
 */
public class MPIBuilder extends HttpServlet {

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
        String dir = (String) this.getServletContext().getAttribute("dir");
        String username = (String) this.getServletContext().getAttribute("username");
        if (username == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        response.setIntHeader("Refresh", 10);
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println(GenHTML.headWithTitle("Builder", username));
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Builder</title>");
            out.println("</head>");
            out.println("<body>");
            File directory = new File(dir);
            String[] extensions = {"cpp", "c"};
            String oldfile = request.getParameterMap().containsKey("buildingfile") ? request.getParameter("buildingfile") : null;
//            for(Object file : FileUtils.listFiles(directory,extensions, false)){
//                               File f = (File) file;
//                   Runner.BuildFile(f.getName(), "out");
//            }
            for (File f : directory.listFiles()) {
                out.println(f.getName());
                if (f.canExecute()) {
                    out.print("<form name=\"Run\" action=\"MPIRunnerServlet\"><INPUT TYPE=\"hidden\" NAME=\"command\" value=" + f.getAbsolutePath() + "><INPUT TYPE=\"SUBMIT\" VALUE=\"Run\"> </form>");
                }
                out.println("<br>");
            }
            out.println("<form name=\"Build\" action=\"MPIbuild\">");
            out.println("<INPUT TYPE=\"text\" NAME=\"buildingfile\" value=" + request.getParameter("buildingfile") + ">");

            out.println("<INPUT TYPE=\"SUBMIT\" VALUE=\"Build\">");
            out.println("</form>");
            //FIXME: hardcoded
//            String path="/run/mpi/";
            //FIXME
            String file = request.getParameterMap().containsKey("buildingfile") ? request.getParameter("buildingfile") : null;
            if (file != null) {
                out.println("Building: " + file);
                out.println("<br>");
                out.println("Output:");
                out.println("<br>");
                BufferedReader[] reader = Runner.BuildFile(dir + file, dir + file.replace(".", ""));
                String printer;
                while ((printer = reader[0].readLine()) != null) {
                    out.print(printer);
                    out.println("<br>");
                }
                while ((printer = reader[1].readLine()) != null) {
                    out.print(printer);
                    out.println("<br>");
                }
                //           out.println("<h1>Servlet Builder at " + request.getContextPath() + "</h1>");
                out.println("</body>");
                out.println("</html>");
            }
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
