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

public class Main {

    public static final String DEFAULT_CONF_PATH = "../conf/default.conf";
    public static final String DEFAULT_USERLIST_FILE = "../conf/userlist.conf";
    public static final String WELCOME_MESSAGE = "Welcome to My FTP Server";
    public static final String VERSION = "1.0.0-unstable";
    public static final String APP_NAME = "DummyFTPServer";
    public static final String DEFAULT_DELIMITER = ":";
    public static final String USERLIST_DELIMITER = ";";

    public static void main(String[] args) {
        /* File Path */
        String configFile = "";
        String userListFile = "";

        /* Usage help */
        String usage = "\n    Usage:  jfsd -c <file> -u <userfile> -v --help\n\n"
                + "    -h, --help      :   Show this help\n"
                + "    -v              :   Show version\n"
                + "    -c <file>       :   Set config file\n"
                + "    -u <userfile>   :   Set user list file\n\n";

        System.out.println("[+] JummyFTPServer v. " + VERSION);

        /* Parsing input args */
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-v")) {
                System.out.println("\n    Version: " + VERSION + "\n");
                break;
            } else if (args[i].equals("-c")) {
                if (!args[(i + 1)].isEmpty()) {
                    configFile = args[++i];
                    System.out.println("[+] Set config file : " + configFile);
                } else {
                    unsupportedOptions(args[i], usage);
                    System.exit(1);
                }
            } else if (args[i].equals("-u")) {
                if (!args[(i + 1)].isEmpty()) {
                    userListFile = args[++i];
                    System.out.println("[+] Set user list file : " + userListFile);
                } else {
                    unsupportedOptions(args[i], usage);
                    System.exit(1);
                }
            } else {
                unsupportedOptions(args[i], usage);
                System.exit(1);
            }
        }

        try {
            Core core = Core.Instance();

            if (configFile.isEmpty()) {
                configFile = DEFAULT_CONF_PATH;
            }

            if (userListFile.isEmpty()) {
                userListFile = DEFAULT_USERLIST_FILE;
            }

            core.loadConfiguration(configFile, userListFile);
            core.startServer();

        } catch (ErrorException ex) {
            System.out.println(ex.what());

            if (ex.isCritical()) {
                System.exit(1);
            }
        }
    }

    /**
     * Support method for print args with help.
     * @param args Input args for program
     * @param usage Help manual
     */
    public static void unsupportedOptions(String args, String usage) {
        System.out.println("\n      Unsupported option : " + args + "\n" + usage);
        System.exit(0);
    }
}
