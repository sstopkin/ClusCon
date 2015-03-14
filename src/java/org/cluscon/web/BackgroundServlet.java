/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cluscon.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.cluscon.system.NetTool;

/**
 *
 * @author rilian-la-te
 */
public class BackgroundServlet extends HttpServlet {

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
        PrintWriter out = response.getWriter();
        if (request.getParameterMap().containsKey("action")){
            String action=request.getParameter("action");
            if ("login".equals(action)){
                boolean login = doLogin(request,response);
                out.print(Boolean.toString(login));
            } 
            if ("logout".equals(action)){
                doLogout(request,response);
            } else {
            String username = (String) this.getServletContext().getAttribute("username");
                if (username == null) out.print("nologin");
            }
            if (action.equals("filemanager")){
                String JSONret = doFileManagement(request,response);
                out.print(JSONret);
            }
            if ("pinger".equals(action)){
                String JSONret = doPing(request,response);
                out.print(JSONret);
            }
        } else {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
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

    private boolean doLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");
         String login;
        if (this.getServletContext().getAttribute("username")!=null)
            this.getServletContext().removeAttribute("username");
        boolean loginb;
        if ((request.getParameterMap().containsKey("login")) || (request.getParameterMap().containsKey("password"))) {
            login = request.getParameter("login");
            String pwd = request.getParameter("password");
            MessageDigest md=null;
            try {
                md = MessageDigest.getInstance("SHA-512");
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
            byte [] input=pwd.getBytes();
            String hash = new BigInteger(1,md.digest(input)).toString(16);
            if (login.equals("root") && (hash.equals("509bdc46d0a8713d8e117e4e95734b88eab17306711dde2b3c5e2b4e312fec976842d1a791b06cc3aebbeffe0e1b242c040540d35f85385bcbb28a29508beeb0"))) {
                loginb = true;
            } else if (login.equals("user") && (hash.equals("1c50ab60c2cebb875c56a2dab7accd17de4c8940deb0d158d628dc103fca18af78dd0fe95129123fb1408989a282544c6b22843c3dc443d835f6886802a9a9fa"))) {
                loginb = true;
            } else return false;
            
            if (loginb){
                File dir = new File(this.getServletContext().getAttribute("BaseDir")+login+"/");
                if (!dir.exists()){
                    dir.mkdir();
                }
                this.getServletContext().setAttribute("dir", this.getServletContext().getAttribute("BaseDir")+login+"/");
                this.getServletContext().setAttribute("username", login);
                return true;
            }
        }
        return false;
    }

    private String doFileManagement(HttpServletRequest request, HttpServletResponse response) {
                String currentDir = request.getParameter("currentDir");
                String userDir=(String) this.getServletContext().getAttribute("dir");
                String list = request.getParameter("list");
                String dir = request.getParameter("dir");
                String fmaction = request.getParameter("fmaction");
                if (list.equals("true")){
                    
                }
                return "error";
            }

    private String doPing(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html");
        String firstIP=(String) this.getServletContext().getAttribute("firstIP");
        String lastIP=(String) this.getServletContext().getAttribute("lastIP");
        List<String[]> ips = NetTool.AliveArray(firstIP, lastIP);
        StringBuilder sb = new StringBuilder();
        sb.append("<tbody id=\"removable\">");
        for (String[] row: ips) {
            sb.append("<tr>");
            sb.append("<td>").append(row[0]).append("</td>");
            if("true".equals(row[1]))
            sb.append("<td>").append("<span class=\"badge badge-success\">+</span>").append("</td>");
            else sb.append("<td>").append("<span class=\"badge badge-important\">-</span>").append("</td>");
            sb.append("</tr>");
           }
        sb.append("</tbody>");
        return sb.toString();
        
    }

    private void doLogout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/plain");
        this.getServletContext().removeAttribute("username");
 //       request.getRequestDispatcher("/ClusCon/web/login.html").forward(request, response);
    }
}

