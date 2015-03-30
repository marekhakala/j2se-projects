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
import jummyftpserver.AccountManager.User;
import jummyftpserver.AccountManager.UserList;

/**
 * Class represent the implementation of FTP core than ensures cooperation between settings and tcp parts.
 */
public class Core {

    /**
     * Reference for core instance (Singleton pattern)
     */
    private static Core pInstance;
    /**
     * Reference for configuration instance
     */
    private Configuration configuration;
    /**
     * Database users (Collection)
     */
    private UserList userList;
    /**
     * Flag for enable anonymous access
     */
    private boolean enableAnonymous = true;

    /**
     * Private constructor for FTP server core.
     */
    private Core() {
        configuration = null;
        userList = null;
        enableAnonymous = false;
    }

    /**
     * Method for create/retrieve instace of core class (Singleton pattern). 
     * @return Return core class instance
     */
    public static Core Instance() {
        if (pInstance == null)
            pInstance = new Core();
        return pInstance;
    }

    /**
     * Method for load configuration file and database users.
     * @param configurationPath Configuration file path
     * @param userListPath Database users file path
     * @return Return true if loading process was successfull otherwise return false.
     */
    public boolean loadConfiguration(String configurationPath, String userListPath) throws ErrorException {
        configuration = new Configuration(configurationPath);

        try {
            if (!configuration.loadConfiguration()) {
                throw new ErrorException("Bad configuration file.");
            }
        } catch (IOException ex) {
            throw new ErrorException("Bad configuration file.");
        }

        userList = new UserList(userListPath);

        try {
            if (!userList.loadUserList()) {
                throw new ErrorException("Bad user list file.");
            }
        } catch (IOException ex) {
            throw new ErrorException("Bad user list file.");
        }

        ReturnValue anonymous = new ReturnValue();
        if (getSettingsValue("global/anonymous", anonymous) && Core.stringToBool(anonymous.getValue())) {
            System.out.println("[D] Anonymous [enable]");
            enableAnonymous = true;
        } else {
            System.out.println("[D] Anonymous [disable]");
            enableAnonymous = false;
        }

        return true;
    }

    /**
     * Method for start the FTP server into listen mode.
     * @return Return true if start of server was successfull otherwise return false.
     */
    public boolean startServer() throws ErrorException {
        ReturnValue portValue = new ReturnValue();

        if (!getSettingsValue("global/port", portValue)) {
            throw new ErrorException("Port is not set.", true);
        }

        if (!isEnabledAnonymousAccess() && getUserCount() == 0) {
            throw new ErrorException("The user list is empty.", true);
        }

        try {
            FTPServer.start(2121);
        } catch (IOException ex) {
            // do nothing :P
        }

        return true;
    }

    /**
     * Method for get config value by section and key.  
     * @param sectionAndKey Section and key
     * @param value Input pointer for return output value
     * @return Return true if the value exist otherwise return false.
     */
    public boolean getSettingsValue(String sectionAndKey, ReturnValue value) {
        String[] items = (sectionAndKey.split("/"));
        return getSettingsValue(items[0], items[1], value);
    }

    /**
     * Method for get config value by section and key.  
     * @param sectionAndKey Section and key
     * @param value Input pointer for return output value
     * @return Return true if the value exist otherwise return false.
     */
    public boolean getSettingsValue(String sectionName, String key, ReturnValue value) {
        ConfSection section = configuration.getSection(sectionName);

        if (section == null)
            return false;

        String val = section.getValue(key);

        if (val.isEmpty()) {
            return false;
        }

        value.setKey(key);
        value.setValue(val);
        return true;
    }

    /**
     * Method for get home directory for anonymous user. 
     * @return Return the home directory path for anonymous user.
     */
    public String getAnonymousHome() {
        ReturnValue anonymousValue = new ReturnValue();

        if (!getSettingsValue("global/anonymousPath", anonymousValue)) {
            return "/tmp";
        }
        return anonymousValue.getValue();
    }

    /**
     * Method for get the user object by username. 
     * @param username Username
     * @return Return the user object if user not exist than return null pointer.
     */
    public User getUser(String username) {
        return userList.getByUsername(username);
    }

    /**
     * Method for get number of users in database users.
     * @return Return number of users.
     */
    public int getUserCount() {
        return userList.count();
    }

    /**
     * Getter for flag of permision for anonymous access.
     */
    boolean isEnabledAnonymousAccess() {
        return enableAnonymous;
    }

    /**
     * Purpose of this static method is validation of port number.
     * @param port tcp port number
     * @return Return true if selected port is in allowed range otherwise return false.
     */
    public static boolean validatePortRange(int port) {
        if ((port > 0 && port <= 1023)
                || (port >= 1024 && port <= 49151)
                || (port >= 49152 && port <= 65535)) {
            return true;
        }
        return false;
    }

    /**
     * Purpose of this static method is conversion string to boolean.
     * @param str Input text string
     * @return Return true if conversion process was successfull otherwise return false.
     */
    public static boolean stringToBool(String str) {

        if (str.toLowerCase().equals("true")) {
            return true;
        }
        return false;
    }
}
