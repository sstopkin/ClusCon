package org.cluscon.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.cluscon.control.Runner;
import org.cluscon.database.Storage;

/**
 *
 * @author best
 */
public class NetTool {

    public static String getIPAddress() {
        String ip = null;
        try {
            InetAddress address = InetAddress.getLocalHost();
            ip = address.getHostAddress();
        } catch (UnknownHostException e) {
        }
        return ip;
    }

    public static boolean pingHost(String host) {
        try {
            Process p1;
            if (HostOSvalidator.main().equals("win")) {
                p1 = java.lang.Runtime.getRuntime().exec("ping -n 1 -w 1" + host);
            } else {
                p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 -W 1 -w 1 " + host);
            }
            int returnVal = p1.waitFor();
            boolean reachable = (returnVal == 0);
            if (reachable) {
                return true;
            }
        } catch (Exception ex) {
            Logger.getLogger(NetTool.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static String FindMACbyIP(String ip) throws IOException {
        String mac = "";
        String macAddr = "";
        Runner r = new Runner();
        r.setCommand("arp -a " + ip);
        BufferedReader[] reader = r.run();
        String printer;
        while ((printer = reader[0].readLine()) != null) {
            mac = mac.concat(printer);
        }
        while ((printer = reader[1].readLine()) != null) {
            mac = mac.concat(printer);
        }
        Pattern p = Pattern.compile("([0-9a-f]{2}:){5}[0-9a-f]{2}");
        Matcher m = p.matcher(mac);
        while (m.find()) {
            macAddr = m.group(0);
        }
        return macAddr;
    }

    public static void portScan() throws UnknownHostException {
        String host = "localhost";
        InetAddress inetAddress = InetAddress.getByName(host);

        String hostName = inetAddress.getHostName();
        for (int port = 0; port <= 65535; port++) {
            try {
                Socket socket = new Socket(hostName, port);
                String text = hostName + " is listening on port " + port;
                System.out.println(text);
                socket.close();
            } catch (IOException e) {
                String s = hostName + " is not listening on port " + port;
                System.out.println(s);
            }
        }
    }

    public static List<String[]> AliveArray(String firstIP, String lastIP) {
        try {
            long byteFirstIP = NetTool.ipToLong(firstIP);
            long byteLastIP = NetTool.ipToLong(lastIP);
            List<String[]> list = new LinkedList<String[]>();
                for (long tempIP=byteFirstIP;tempIP<byteLastIP;tempIP++){
                String curIP = NetTool.longToIp(tempIP);
                String [] a = new String[2];
                a[0]=curIP;
                if (NetTool.pingHost(curIP)) {
                    a[1]="true";
                }   else a[1]="false";
                list.add(a);

                }
            return list;
        } catch (UnknownHostException ex) {
            Logger.getLogger(NetTool.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
}

    
    
   public static long ipToLong(String ip) throws UnknownHostException {
        byte[] octets = InetAddress.getByName(ip).getAddress();
        long result = 0;
        for (byte octet : octets) {
            result <<= 8;
            result |= octet & 0xff;
        }
        return result;
    }
   public static String longToIp (Long ip) throws UnknownHostException{
       byte [] octets = new byte [4];
       octets[0]=(byte) (ip/0xffffff);
       octets[1]=(byte) ((ip&0x00ff0000)/0xffff);
       octets[2]=(byte) ((ip&0x0000ff00)/0xff);
       octets[3]=(byte) (ip&0x000000ff);
       return InetAddress.getByAddress(octets).getHostAddress();
   }
}

