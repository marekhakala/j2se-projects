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

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Structure class for represent the user object.
 */
public class User {
    /**
     * Username
     */
    private String username;
    /**
     * Username password
     */
    private String password;
    /**
     * Password format type
     */
    private PasswordType passwordType;
    /**
     * Home directory path
     */
    private String homeDirectory;

    /**
     *  Constructor method for create user object.
     *  @param username Username
     *  @param password Password
     *  @param format Password format type
     *  @param homeDirectory Home directory path
     */
    public User(String username, String password,
            PasswordType format, String homeDirectory) {
        this.username = username;
        this.password = password;
        this.passwordType = format;
        this.homeDirectory = homeDirectory;
    }

    /**
     * Getter method for username.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Getter method for home directory path.
     */
    public String getHomeDirectory() {
        return this.homeDirectory;
    }

    /**
     * Password check method.
     * @param password Input plaintext password
     * @param format The password format
     * @return If the input password is equil to user password than method return the boolean value true otherwise false.
     */
    public boolean checkPassword(String password, PasswordType format) {
        boolean plain = false;
        String type = "plain";
        byte[] defaultBytes = password.getBytes();


        switch (this.passwordType) {
            case MD5:
                type = "MD5";
                break;
            case SHA1:
                type = "SHA1";
                break;
            case PLAIN:
                plain = true;
                break;
            default:
                return false;
        }

        if (!plain) {
            try {
                MessageDigest algorithm = MessageDigest.getInstance(type);
                algorithm.reset();
                algorithm.update(defaultBytes);
                byte messageDigest[] = algorithm.digest();

                StringBuffer hexString = new StringBuffer();

                for (int i = 0; i < messageDigest.length; i++) {
                    hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
                }

                return this.password.equals(hexString.toString());

            } catch (NoSuchAlgorithmException ex) {
                return false;
            }
        }
        return this.password.equals(password);
    }

    /**
     * Method for validation of user home directory path.
     * @return If the home directory not exist than method return boolean value false otherwise true.
     */
    public boolean isValidPath() {
        File directory = new File(this.homeDirectory);
        return directory.exists();
    }

    /**
     * Static method for validation of input path.
     * @param path Input directory path
     */
    public static boolean isValidPath(String path) {
        File directory = new File(path);
        return directory.exists();
    }
}
