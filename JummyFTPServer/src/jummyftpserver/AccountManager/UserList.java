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

package jummyftpserver.AccountManager;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Structure class for represent database users for in-memory use.
 */
public class UserList {
    /**
     * User list directory path
     */
    private String userListPath;
    /**
     * User list filename
     */
    File userListFile;
    /**
     * List of users
     */
    List<User> userList = new LinkedList<User>();

    /**
     *  Constructor method for setup users file path.
     */
    public UserList(String userListFile) {
        this.userListPath = userListFile;

        System.out.println("[D] Loading user list (parsing content) ...");
        System.out.println(" | filename == " + userListFile);
    }

    /**
     * Getter method for get user from database users.
     * @param username Username for search
     * @return Return the pointer on the user object from collection of users. If the username not exist than method return null ponter. 
     */
    public User getByUsername(String username) {
        Iterator<User> it = this.userList.iterator();

        while (it.hasNext()) {
            User user = it.next();
            if (username.equals(user.getUsername())) {
                return user;
            }
        }

        return null;
    }

    /**
     * Method for add new user object into database users.
     * @param User Input user object
     * @return Return true, when the user was successfully added otherwise return false.
     */
    public boolean add(User user) {
        if (contains(user.getUsername())) {
            return false;
        }

        userList.add(user);
        return true;
    }

    /**
     * Method for remove user object from database users.
     * @param User Input user object
     * @return Return the true, when the user was successfully deleted otherwise return false.
     */
    public boolean remove(User user) {
        Iterator<User> it = this.userList.iterator();

        while (it.hasNext()) {
            User rmUser = it.next();
            if (rmUser.getUsername().equals(rmUser.getUsername())) {
                it.remove();
                return true;
            }
        }

        return false;
    }

    /**
     * Method for load database users from file.
     * @return Return the true, when the user list was successfully loaded otherwise return false.
     */
    public boolean loadUserList() throws IOException {

        if (userListFile == null) {
            userListFile = new File(this.userListPath);
        }

        clean();

        FileInputStream fis = null;
        BufferedInputStream bis = null;
        DataInputStream dis = null;

        try {
            fis = new FileInputStream(userListFile);
            bis = new BufferedInputStream(fis);
            dis = new DataInputStream(bis);

            String section = "global";

            while (dis.available() != 0) {
                String line = dis.readLine();

                if (line.length() > 0 && line.charAt(0) != '#') {
                    parseUser(line);
                }
            }

            fis.close();
            bis.close();
            dis.close();

            return true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * The purpose of this method is deserialization of user object.
     * @param input Input string, which contain the user record.
     * @return Return the true, when the user was successfully loaded otherwise return false.
     */
    public boolean parseUser(String input) {
        boolean isEnabled;
        String username;
        String password;
        String homeDirectory;

        String[] in = input.split(";");

        if (in.length != 4) {
            return false;
        }

        if (in[0].isEmpty()) {
            return false;
        }
        username = in[0];

        if (in[1].isEmpty()) {
            return false;
        }
        password = in[1];

        if (in[2].isEmpty()) {
            return false;
        }
        homeDirectory = in[2];

        if (in[3].isEmpty()) {
            return false;
        }
        isEnabled = Boolean.valueOf(in[3]);

        System.out.println("[D] username = " + username + " | password = " + password
                + " | home directory = " + homeDirectory
                + " | isEnabled = " + isEnabled);

        if (isEnabled) {
            String pass = "";
            PasswordType type = null;

            if (parsePassword(password, pass, type)) {
                add(new User(username, pass, type, homeDirectory));
                return true;
            }
        }
        return false;

    }

    /**
     * Method for parse user list from file.
     * @param input Input line string
     * @param password Output password ref
     * @param type Password format type
     * @return Return true if the line was successfully parsed otherwise return false.
     */
    public boolean parsePassword(String input, String password, PasswordType type) {

        String[] in = input.split("|");

        if (in.length != 2) {
            return false;
        }

        if (in[1].toLowerCase().equals("md5")) {
            type = PasswordType.MD5;
        } else if (in[1].toLowerCase().equals("sha1")) {
            type = PasswordType.SHA1;
        } else {
            type = PasswordType.PLAIN;
        }

        password = in[0];
        return true;
    }

    /**
     * Method for checking uniqueness of username in database users.
     * @return Return true if the database users contain our username otherwise return false.
     */
    public boolean contains(String username) {
        if (this.getByUsername(username) != null) {
            return true;
        }
        return false;
    }

    /**
     * Method for calculate the number of users.
     * @return Return the numer of users.
     */
    public int count() {
        return userList.size();
    }

    /**
     * Method for check if the list is empty.
     * @return Return true if the database users is empty otherwise return false.
     */
    public boolean isEmpty() {
        return userList.isEmpty();
    }

    /**
     * Method for clean the list of users.
     * @return Return true if the database users was successfully deleted otherwise return false.
     */
    public boolean clean() {
        userList.clear();
        return true;
    }
}
