/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cluscon.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author rilian-la-te
 */
public class FileUploader extends HttpServlet {

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
        boolean isMultiPart = ServletFileUpload.isMultipartContent(request);
        String username = (String) this.getServletContext().getAttribute("username");
        if (username == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        System.out.println("Inside Servlet");
        String dir = (String) this.getServletContext().getAttribute("dir");
        String file = null;
        if (!isMultiPart) {
            System.out.println("Form type is not multipart/form-data");
            System.out.println("File Not Uploaded");
        } else {
            FileItemFactory dfit = (FileItemFactory) new DiskFileItemFactory();
            ServletFileUpload sfu = new ServletFileUpload((org.apache.commons.fileupload.FileItemFactory) dfit);
            List aList = null;
            try {
                aList = sfu.parseRequest(request);
                System.out.println("Items: " + aList);
            } catch (org.apache.commons.fileupload.FileUploadException ex) {
                Logger.getLogger(FileUploader.class.getName()).log(Level.SEVERE, null, ex);
            }
            Iterator itr = aList.iterator();
            while (itr.hasNext()) {
                FileItem fi = (FileItem) itr.next();
                if (fi.isFormField()) {
                } else {
                    System.out.println("File Name: " + fi.getFieldName());
                    System.out.println("File Size: " + fi.getSize());

                    try {
                        file = fi.getName();
                        File f = new File(dir, fi.getName());
                        fi.write(f);
                    } catch (Exception e) {
                    }
                    System.out.println("It's Not Form Item;");
                }
            }
        }
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println(GenHTML.headWithTitle("Uploader", username));
            out.println("<head>");
            out.println("<title>Servlet Uploader</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<form action=\"FileUploader\" method=\"POST\"enctype=\"multipart/form-data\">");
            out.println("<input type=\"file\" name=\"file\">");
            out.println("<input type=\"submit\" name=\"upload\" value=\"Upload\">");
            out.println("</form>");
            out.println(file != null ? ("<h1>File uploaded to " + dir + file + "</h1>") : "");
            out.println(file != null ? ("<form name=\"Build\" action=\"MPIbuild\"><INPUT TYPE=\"SUBMIT\" VALUE=\"Build\"></form>") : "");
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
