/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cluscon.system;

/**
 *
 * @author best
 */
public class HostOSvalidator {

    private static String OS = System.getProperty("os.name").toLowerCase();

    public static String main() {

        if (isWindows()) {
            return ("win");
        } else if (isMac()) {
            return ("mac");
        } else if (isUnix()) {
            return ("nix");
        } else if (isSolaris()) {
            return ("solaris");
        } else {
            return ("");
        }
    }

    public static boolean isWindows() {

        return (OS.indexOf("win") >= 0);

    }

    public static boolean isMac() {

        return (OS.indexOf("mac") >= 0);

    }

    public static boolean isUnix() {

        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);

    }

    public static boolean isSolaris() {

        return (OS.indexOf("sunos") >= 0);

    }
}
