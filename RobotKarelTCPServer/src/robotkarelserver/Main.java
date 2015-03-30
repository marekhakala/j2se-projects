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

public class Main {
    public static int port = 3999;

    public static void main(String[] args) {
        if (args.length > 2) {
            port = new Integer(args[1]);
            serverMode();
        } else {
            usage();
        }
    }

    public static void serverMode() {
        if(port < 3000 || port > 3999)
            usage();

        RobotServer srv = new RobotServer(port);
        srv.init();
        srv.start();
    }

    public static void usage() {
        System.out.println(" [PORT] (Server mode)");
        System.exit(1);
    }
}
