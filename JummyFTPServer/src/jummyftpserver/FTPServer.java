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

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

/**
 * This class represent ftp control.
 */
public class FTPServer {

    /**
     * TCP port for listen
     */
    private static int port = 2121;
    /**
     * Instance for ftp server
     */
    private static FTPServer pInstance = null;
    private static Socket clientSocket = null;
    private static ServerSocket serverSocket = null;
    /**
     * Collection of client threads.
     */
    public static List<ServerThread> threadList = new LinkedList<ServerThread>();

    /**
     * Constructor method for ftp control class.
     * @param tcpPort TCP port for listening.
     */
    FTPServer(int tcpPort) {
        pInstance = this;
        port = tcpPort;
    }

    /**
     * Getter for tcp port.
     */
    public static int getPort() {
        return port;
    }

    /**
     * Method for start server.
     * @param tcpPort TCP port for listening.
     */
    public static void start(int tcpPort) throws IOException, ErrorException {
        try {
            serverSocket = new ServerSocket(tcpPort);
        } catch (IOException e) {
            System.out.println(e);
        }

        while (true) {
            try {
                threadList.add(new ServerThread(serverSocket.accept()));
                threadList.get(threadList.size() - 1).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method for stop server.
     */
    public static void stop() throws IOException {
        serverSocket.close();
    }

    /**
     * Method for restart server.
     */
    public static void restart() {
        try {
            FTPServer.stop();
            try {
                FTPServer.start(FTPServer.getPort());
            } catch (ErrorException e) {
                e.printStackTrace();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Method for get run state.
     */
    public static boolean isRunning() {
        return !serverSocket.isClosed();
    }
}
