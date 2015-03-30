/***************************************************************************
 *   Copyright (C) 2010 by Marek Hakala   *
 *   hakala.marek@gmail.com   *
 *
 *   Semester project for BI-PJV @ CTU FIT
 *   Topic: FTP server implementation
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU Library General Public License as       *
 *   published by the Free Software Foundation; either version 2 of the    *
 *   License, or (at your option) any later version.                       *
 *                                                                         *
 *   This program is distributed in the hope that it will be useful,       *
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
 *   GNU General Public License for more details.                          *
 *                                                                         *
 *   You should have received a copy of the GNU Library General Public     *
 *   License along with this program; if not, write to the                 *
 *   Free Software Foundation, Inc.,                                       *
 *   59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.             *
 ***************************************************************************/

package jummyftpserver;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Class contains the implementation of passive FTP server.
 */
public class DTPServer {

    private int portNumber = 30000;
    private ServerSocket server = null;
    private Socket client = null;
    private OutputStreamWriter os = null;

    /**
     * Constructor for DTP passive FTP server. 
     * @param port Port for listen
     */
    public DTPServer(int port) {
        try {
            this.portNumber = port;
            server = new ServerSocket(portNumber);
            client = server.accept();
            server.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Method for send the list of directories and files to client.
     * @param fList List of directories and files
     * @throws IOException In case of error will be trown IOException exception.
     */
    public void sendList(List<String> fList) throws IOException {
        try {
            PrintWriter writer;
            writer = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));

            Iterator<String> fListIt = fList.iterator();
            while (fListIt.hasNext()) {
                writer.println(fListIt.next());
            }

            writer.flush();
            writer.close();
        } finally {
            client.close();
        }
    }
    /**
     * Method for send the file to client.
     * @param file Filename with path
     * @throws IOException In case of error will be trown IOException exception.
     */
    public void sendFile(String file) throws IOException {
        try {
            PrintWriter writer;
            os = new OutputStreamWriter(client.getOutputStream());

            FileInputStream fis = new FileInputStream(file);
            int i = 0;
            while ((i = fis.read()) != -1) {
                os.write(i);
            }
            
            fis.close();
            os.close();
            client.close();
            server.close();
        } finally {
            client.close();
        }
    }

    /**
     * Getter for port number.
     * @return Return FTP server port number.
     */
    public int getPort() {
        return this.portNumber;
    }

    /**
     * Method for generate the random port number.
     * @return Return random port number.
     */
    public static int randomPortNumber() {
        Random randomGenerator = new Random();
        return 30000 + randomGenerator.nextInt(25000);
    }
}
