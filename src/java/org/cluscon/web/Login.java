/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cluscon.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Serg
 */
public class Login extends HttpServlet {

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

        //
        // Check if username parameter exists
        //
        String login = "";
        if (this.getServletContext().getAttribute("username")!=null)
            this.getServletContext().removeAttribute("username");
        boolean loginb=false,logini=false;
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
            } else logini=true;
            
            if (loginb){
                File dir = new File(this.getServletContext().getAttribute("BaseDir")+login+"/");
                if (!dir.exists()){
                    dir.mkdir();
                }
                this.getServletContext().setAttribute("dir", this.getServletContext().getAttribute("BaseDir")+login+"/");
                this.getServletContext().setAttribute("username", login);
                response.sendRedirect("dashboard");
            }
        }
            PrintWriter out = response.getWriter();

            out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
            out.println("<head>");
            out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />");
            out.println("<title>Login</title>");
            out.println("<link href=\"login-box.css\" rel=\"stylesheet\" type=\"text/css\" />");
            out.println("</head>");

            out.println("<body>");
            if (!loginb&& logini){
                out.println("Login incorrect for username "+login);
            }
            out.println("<div id=\"login-box\">");
            out.println("<FORM ACTION=\"login\" METHOD=\"POST\">");
            out.println("<H2>Login</H2>");
            out.println("<br />");
            out.println("<br />");
            out.println("<div id=\"login-box-name\" style=\"margin-top:20px;\">Username:</div><div id=\"login-box-field\" style=\"margin-top:20px;\"><input name=\"login\" class=\"form-login\" title=\"Username\" value=\"\" size=\"30\" maxlength=\"2048\" /></div>");
            out.println("<div id=\"login-box-name\">Password:</div><div id=\"login-box-field\"><input name=\"password\" type=\"password\" class=\"form-login\" title=\"Password\" value=\"\" size=\"30\" maxlength=\"2048\" /></div>");
            out.println("<br />");
            out.println("<!--<span class=\"login-box-options\"><input type=\"checkbox\" name=\"1\" value=\"1\"> Remember Me <a href=\"#\" style=\"margin-left:30px;\">Forgot password?</a></span>-->");
            out.println("<br />");
            out.println("<br />");

            out.println("<div id=\"login-button\">");
            out.println("<INPUT TYPE=\"SUBMIT\" value=\"SUBMIT\" NAME=\"Sumbit\">");
            out.println("</div>");
            out.println("</div>");
            out.println("</FORM>");
            out.println("</body>");
            out.println("</html>");
        
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
