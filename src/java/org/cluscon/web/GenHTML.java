/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cluscon.web;

/**
 *
 * @author best
 */
public class GenHTML {

    /*    public static final String DOCTYPE =
     "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">"; */
    public static final String DOCTYPE = "<!DOCTYPE HTML>";

    public static String headWithTitle(String title, String username) {
        return (DOCTYPE + "\n"
                + "<HTML>\n"
                + "<HEAD><TITLE>" + title + "</TITLE>"
                + "<link href=\"style.css\" rel=\"stylesheet\" type=\"text/css\">"
                + "<div id='cssmenu'>\n"
                + "<ul>\n"
                + "   <li class='active'><a href='dashboard'><span>Dashboard</span></a></li>\n"
                + "   <li class='has-sub'><a href='#'><span>Managment</span></a>\n"
                + "      <ul>\n"
                + "         <li class='last'><a href='CLI'><span>CLI</span></a></li>\n"
                + "      </ul>\n"
                + "   </li>\n"
                + "   <li class='has-sub'><a href='#'><span>MPI</span></a>\n"
                + "      <ul>\n"
                + "         <li><a href='FileUploader'><span>Upload</span></a></li>\n"
                + "         <li><a href='MPIbuild'><span>Build</span></a></li>\n"
                + "         <li class='last'><a href='MPIRunnerServlet'><span>MPIRunnerServlet</span></a></li>\n"
                + "      </ul>\n"
                + "   </li>\n"
//                + "   <li><a href='/TextEditor'><span>TextEditor</span></a></li>\n"
                + "   <li><a href='#'><span>About</span></a></li>\n"
                + "   <li class='last'><a href='login'><span>Logout " + username + "</span></a></li>\n"
                + "</ul>\n"
                + "</div>" + "\n");

    }
    // Other utilities will be shown later...
}
