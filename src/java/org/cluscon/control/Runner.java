/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cluscon.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 *
 * @author rilian-la-te
 */
public class Runner {

    String command = null;
                    String path=null;
            Integer number = null;
            String filename = null;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public BufferedReader[] run() throws IOException {
        Process p = Runtime.getRuntime().exec(command);
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

        BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        BufferedReader[] out = {stdInput,stdError};
        return out;
    }        
    
    public static BufferedReader[] runCommand(String command) throws IOException {
        Process p = Runtime.getRuntime().exec(command);
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

        BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        BufferedReader[] out = {stdInput,stdError};
        return out;
    }
    public BufferedReader[] MPIrun() throws IOException{
        return Runner.runCommand("mpirun -np "+number.toString()+" --machinefile "+filename+" "+path+command);
    }
    
    public static BufferedReader[] MPIrunCommand(String machinefile, String folder, Integer number,String command) throws IOException {
                return Runner.runCommand("mpirun -np "+number.toString()+" --machinefile "+machinefile+" "+folder+command);
    }
    public static BufferedReader[] BuildFile(String file, String command) throws IOException{
        return Runner.runCommand("mpiCC "+file+" -o "+command);
    }
}