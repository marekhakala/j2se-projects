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

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import jummyftpserver.AccountManager.PasswordType;
import jummyftpserver.AccountManager.User;

/**
 * This class represent thread functionality for client.
 */
public class ServerThread extends Thread {
    private final String WELCOME_MESSAGE = "Welcome to My FTP Server";
    private final String APP_NAME = "JummyFTPServer";
    private final String VERSION = "1.0.0-unstable";
    DataInputStream is = null;
    PrintStream os = null;
    Socket clientSocket = null;
    /**
     * Flag for valid username
     */
    private boolean validUsername = false;
    /**
     * Flag for valid password
     */
    private boolean validPassword = false;
    /**
     * Flag for valid session
     */
    private boolean isLogged = false;
    /**
     * Pointer on logged user
     */
    private User loggedUser = null;
    /**
     * Object for handle operation with directories and files
     */
    private WorkPath pathPtr = null;
    /**
     * Type of transfer
     */
    private DTP_Mode mode = DTP_Mode.NONE;
    /**
     * Flag for waiting on DTP
     */
    private boolean flagWaitForDTP = false;
    /**
     * FTP command
     */
    private String globalCommand = "";
    /**
     * The filename for rename
     */
    private String rnfrNameCache = "";
    /**
     * Confirmation for continue in connection
     */
    private long transferContinueFrom = 0;
    /**
     * Main control tcp socket
     */
    private Socket socket = null;
    /**
     * Data socket for passive communication (DATA)
     */
    private DTPServer passvSocket = null;
    /**
     * File name
     */
    String fileName;

    /**
     * Method for create server socket.
     */
    public ServerThread(Socket clientSocket) {
        this.clientSocket = clientSocket;

        System.out.println("[+] New connection from " + clientSocket.getInetAddress().getHostAddress());
    }

    /**
     * Method for send welcome message to socket.
     */
    void welcomeMessage() {
        sendMessage("220", WELCOME_MESSAGE + " (" + APP_NAME + ") " + VERSION);
    }

    /**
     * Method for handle incoming message from socket.
     */
    @Override
    public void run() {
        String line;

        try {
            is = new DataInputStream(clientSocket.getInputStream());
            os = new PrintStream(clientSocket.getOutputStream());

            welcomeMessage();

            while (true) {
                line = is.readLine();

                if (line == null) {
                    break;
                }
                String[] commandList = line.split(" ");
                commandList[0] = commandList[0].toLowerCase();

                String command = "";
                String code = commandList[0];

                if (commandList.length > 1) {
                    command = line.substring(code.length() + 1);
                } else {
                    command = line;
                }

                if (code.equals("user")) {
                    System.out.println("user ::" + command);

                    if (commandList.length > 1 && (loggedUser = Core.Instance().getUser(command)) != null) {
                        validUsername = true;
                        pathPtr = new WorkPath(String.valueOf(Core.Instance().getUser(command).getHomeDirectory()));

                        sendMessage("331", "User name OK, need password.");
                    } else if (Core.Instance().isEnabledAnonymousAccess() && command.equals("anonymous")) {
                        pathPtr = new WorkPath(Core.Instance().getAnonymousHome());
                        validUsername = validPassword = isLogged = true;
                        sendMessage("331", "Anonymous login ok, send your complete email address as your password.");
                    } else {
                        pathPtr = null;
                        validUsername = validPassword = isLogged = false;
                        sendMessage("332", "Need account for login.");
                    }
                } else if (code.equals("pass")) {
                    if (validUsername) {
                        if (validPassword && loggedUser == null && Core.Instance().isEnabledAnonymousAccess()) {
                            sendMessage("230", "Anonymous access granted, restrictions apply.");
                            isLogged = true;
                        } else if (commandList.length > 1 && loggedUser.checkPassword(commandList[1], PasswordType.PLAIN)) {
                            sendMessage("230", "User logged in, proceed.");
                            isLogged = true;
                        } else {
                            sendMessage("530", "Not logged in.");
                        }
                    } else {
                        sendMessage("530", "Not logged in.");
                    }
                } else if (code.equals("noop")) {
                    sendMessage("200", "NOOP command successful.");
                } else if (code.equals("opts")) {
                    sendMessage("200", "OPTS command successful."); // FAKE
                } else if (isLogged) {
                    if (code.equals("pwd")) {
                        sendMessage("257", "\"" + pathPtr.pwd() + "\" is the current directory.");
                    } else if (code.equals("cwd")) {

                        if (commandList.length > 1 && pathPtr.cwd(command)) {
                            sendMessage("250", "Requested file action OK, completed.");
                        } else if (commandList.length == 1 && pathPtr.cwd()) {
                            sendMessage("250", "Requested file action OK, completed.");
                        } else {
                            sendMessage("550", "Requested action not taken.");
                        }

                    } else if (code.equals("cdup")) {

                        if (pathPtr.cdUp()) {
                            sendMessage("200", "Command OK.");
                        } else {
                            sendMessage("550", "Requested action not taken.");
                        }

                    } else if (code.equals("rmd")) {

                        if (commandList.length > 1 && pathPtr.rmd(command)) {
                            sendMessage("250", "Requested file action OK, completed.");
                        } else {
                            sendMessage("550", "Requested action not taken.");
                        }

                    } else if (code.equals("dele")) {

                        if (commandList.length > 1 && pathPtr.dele(command)) {
                            sendMessage("250", "Requested file action OK, completed.");
                        } else {
                            sendMessage("550", "Requested action not taken.");
                        }

                    } else if (code.equals("mkd")) {

                        if (commandList.length > 0 && pathPtr.mkd(command)) {
                            sendMessage("257", "directory \"" + command + "\" created.");
                        } else {
                            sendMessage("550", "Requested action not taken.");
                        }

                    } else if (code.equals("quit")) {
                        isLogged = false;
                        validUsername = false;
                        validPassword = false;
                        loggedUser = null;

                        sendMessage("221", "Service closing control connection.");

                        clientSocket.close();
                    } else if (code.equals("syst")) {
                        sendMessage("215", "UNIX Type: L8");
                    } else if (code.equals("type")) {
                        sendMessage("200", "Command OK."); // FAKE
                    } else if (code.equals("pasv")) {
                        int portNumber = DTPServer.randomPortNumber();

                        String ipv4 = clientSocket.getLocalAddress().getHostAddress();
                        String[] ip = ipv4.split("\\.");

                        String p1 = String.valueOf((portNumber >> 8) & 0xFF);
                        String p2 = String.valueOf(portNumber & 0xFF);

                        sendMessage("227", "Entering Passive Mode (" + ip[0] + "," + ip[1] + "," + ip[2] + "," + ip[3] + "," + p1 + "," + p2 + ").");
                        passvSocket = new DTPServer(portNumber);
                    } else if (code.equals("list")) {
                        globalCommand = command;
                        if (passvSocket != null) {
                            sendMessage("150", "Opening ASCII mode data connection for file list.");
                            passvSocket.sendList(pathPtr.list());
                            sendMessage("226", "Transfer complete");
                        } else {
                            sendMessage("426", "Connection closed; transfer aborted.");
                        }
                    } else if (code.equals("retr")) {
                        if (commandList.length == 1) {
                            sendMessage("500", "'RETR' not understood");
                        } else {
                            File file = new File(pathPtr.realFilePath(command));

                            if (!file.isFile()) {
                                mode = DTP_Mode.RETR; // FIREFOX HACK
                                sendMessage("550", "Not a regular file.");
                            } else {
                                mode = DTP_Mode.RETR;
                                fileName = command;

                                if (passvSocket != null) {
                                     sendMessage("150", "Opening BINARY mode data connection for "
                                             + fileName + " (" + String.valueOf(file.length()) + " bytes)");

                                    passvSocket.sendFile(pathPtr.realFilePath(command));
                                    sendMessage("226", "Transfer complete");
                                } else {
                                    sendMessage("426", "Connection closed; transfer aborted.");
                                }
                            }
                        }
                    } else if (code.equals("size")) {
                        if (commandList.length == 1) {
                            sendMessage("500", "'SIZE' not understood.");
                        } else {
                            File file = new File(pathPtr.realFilePath(command));

                            if (!file.isFile()) {
                                sendMessage("550", "Not a regular file.");
                            } else if (file.exists()) {
                                sendMessage("213", String.valueOf(file.length()));
                            } else {
                                sendMessage("451", "Requested action aborted. Local error in processing.");
                            }
                        }
                    } else if (code.equals("mdtm")) {
                        if (commandList.length == 1) {
                            sendMessage("500", "'MDTM' not understood");
                        } else {
                            File file = new File(pathPtr.realFilePath(command));

                            if (!file.isFile()) {
                                sendMessage("550", "Not a plain file.");
                            } else {
                                sendMessage("451", "Requested action aborted. Local error in processing.");
                            }
                        }
                    } else if (code.equals("abor")) {
                        if (mode != DTP_Mode.NONE) {
                            mode = DTP_Mode.NONE;
                            sendMessage("226", "Closing data connection.");
                        } else if (true) {
                            sendMessage("225", "Data connection open; no transfer in progress.");
                        } else {
                            sendMessage("226", "Closing data connection.");
                        }
                    } else if (code.equals("hello")) {
                        sendMessage("555", "Hello FTP world !!!.");
                    } else {
                        sendMessage("502", "Command not implemented.");
                    }
                } else {
                    sendMessage("550", "Requested action not taken.");
                }
            }
            System.out.println("[-] Disconnect.");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Method for send FTP message to TCP socket. 
     * @param commandCode FTP command code
     * @param message Command message
     */
    private void sendMessage(String code, String message) {
        this.os.println(code + " " + message);
    }
}
