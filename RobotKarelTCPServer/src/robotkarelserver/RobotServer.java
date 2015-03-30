/***************************************************************************
 *   Copyright (C) 2011 by Marek Hakala   *
 *   hakala.marek@gmail.com   *
 *
 *   Semester project for BI-PSI @ CTU FIT
 *   Topic: Robot Karel TCP server
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

package robotkarelserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RobotServer {
    private int clientIdPoll = 0;
    private ServerSocket socket = null;
    private int m_port;

    public RobotServer(int port) {
        m_port = port;
    }

    public void init() {
        try {
            socket = new ServerSocket(m_port);
            System.out.println("[::] Starting the Karel TCP server on port " + m_port + " ... ;-)");
        } catch (IOException e) {
            System.out.println("[::][ERR] Selected port " + m_port + " is occupied.");
            System.out.println(e);
            System.exit(2);
        }
    }

    public void start() {
        while(true) {
            try {
                Socket clientSocket = socket.accept();
                ClientInfo info = new ClientInfo(clientIdPoll++, clientSocket);
            } catch (Exception e) {
                System.out.println("[::] Client can't connect ...");
                System.out.println(e);
            }
        }
    }
    public int getClientIdPoll() {
        return clientIdPoll;
    }
    public int getPort() {
        return m_port;
    }
}
