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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Structure class for represent configuration section for in-memory use.
 */
public class ConfSection {
    /**
     * Section name
     */
    private String name;
    /**
     * Hash map of keys and values
     */
    private Map<String, String> items = new HashMap<String, String>();

    /**
     * Constructor method for configuration section.
     * @param name Name of section
     */
    public ConfSection(String name) {
        this.name = name;
    }

    /**
     * Getter for conf section name.
     * @return Return the section name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter for number of values.
     * @return Return the number of items.
     */
    public int getCount() {
        return (int) this.items.size();
    }

    /**
     * Getter for value by the key.
     * @return Return the value if key exist otherwise return empty string.
     */
    public String getValue(String key) {
        Iterator it = this.items.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<String, String> pairs = (Map.Entry<String, String>) it.next();

            if (key.equals(pairs.getKey())) {
                return pairs.getValue();
            }
        }
       return "";
    }

    /**
     * Method for append new pair of key and value.
     * @param key Key
     * @param value Value
     * @return Return true if process of append was successfull otherwise return false.
     */
    public boolean append(String key, String value) {
        items.put(key, value);
        return true;
    }

    /**
     * Method for remove pair by key.
     * @param key Key
     * @return Return true if pair was sucessfully deleted otherwise return false.
     */
    public boolean remove(String key) {
        Iterator it = this.items.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<String, String> pairs = (Map.Entry<String, String>) it.next();

            if (key.equals(pairs.getKey())) {
                it.remove();
                return true;
            }
        }
        return false;
    }
}
