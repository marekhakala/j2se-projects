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

/**
  * This class contains general methods to throw error exceptions.
 */
public class ErrorException extends Exception {
    /**
     * Flag of critical error
     */
    private boolean critical;
    /**
     *  Error message
     */
    private String message;

    /**
     * Constructor method for create critical error message.
     * @param errorMessage Error message
     * @param critical Flag of critical error
     */
    public ErrorException(String errorMessage, boolean critical) {
        this.critical = critical;

        message = "[!] " + errorMessage;

        if(this.critical)
            message += "\n[-] Stopping server ...";
    }

    /**
     * Constructor method for create error message.
     * @param errorMessage Error message
     */
    public ErrorException(String errorMessage) {
        this(errorMessage, false);
    }

    /**
     * Getter for error message.
     * @return Return error message.
     */
    public String what() {
        return message;
    }

    /**
     * Getter for flag of critical error.
     * @return Return the flag of critical error
     */
    public boolean isCritical() {
        return critical;
    }
}
