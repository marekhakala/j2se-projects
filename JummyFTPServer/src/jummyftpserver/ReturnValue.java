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
 * This is crate class for return value for settings quering.
 */
public class ReturnValue {
    private String key = "";
    private String value = "";

    /**
     * Constructor
     */
    public ReturnValue() {
        this.key = "";
        this.value = "";
    }

    /**
     * Constructor with params
     * @param key Key
     * @param value Value
     */
    public ReturnValue(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Setter for key.
     * @param key Key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Setter for value.
     * @param value Value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Getter for key.
     * @return Key
     */
    public String getKey() {
        return key;
    }

    /**
     * Getter for value.
     * @return Value
     */
    public String getValue() {
        return value;
    }
}
