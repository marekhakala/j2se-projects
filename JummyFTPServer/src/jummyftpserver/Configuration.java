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

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Structure class for represent configuration for in-memory use.
 */
public class Configuration {
    /**
     * Reference on file object
     */
    private File configFile = null;
    /**
     * Config file path
     */
    private String configPath;
    /**
     * The hash map of config sections.
     */
    private Map<String, ConfSection> sections = new HashMap<String, ConfSection>();

    /**
     * Constructor method for init conf file for reading.
     * @param configFile Conf file path
     */
    public Configuration(String configFile) {
        this.configPath = configFile;
        this.configFile = new File(this.configPath);

        System.out.println("[D] Loading configuration (parsing content) ...");
        System.out.println("[D] is_open == " + configFile + " | filename == " + configFile);
    }

    /**
     * Method for add value into the conf sections. If isn't selected section it's used the global section.
     * @param key Key name
     * @param value Value for key name
     * @param sectionName Section name
     * @return Return true if process of add was was successfull otherwise return false.
     */
    public boolean appendToSection(String key, String value, String sectionName) {
        ConfSection section = null;
        section = getSection(sectionName);

        if (section == null) {
            section = new ConfSection(sectionName);
            sections.put(sectionName, section);
        }

        section.append(key, value);
        return true;

    }

    /**
     * Method for clean the list of conf sections.
     * @return Return true if the list of conf sections was successfully deleted otherwise return false.
     */
    public boolean clean() {
        if (!sections.isEmpty()) {
            sections.clear();
        }
        return false;
    }

    /**
     * Method for load configuration file.
     * @return Return true if the conf file was successfully loaded otherwise return false.
     */
    public boolean loadConfiguration() throws IOException {
        if (configFile == null) {
            configFile = new File(this.configPath);
        }

        clean();

        FileInputStream fis = null;
        BufferedInputStream bis = null;
        DataInputStream dis = null;

        try {
            fis = new FileInputStream(configFile);
            bis = new BufferedInputStream(fis);
            dis = new DataInputStream(bis);

            String section = "global";

            while (dis.available() != 0) {
                String line = dis.readLine();

                if (line.length() > 0) {
                    if (line.charAt(0) == '[') {
                        parseSection(line, section);
                    } else if (line.charAt(0) != '#') {
                        parseValue(section, line);
                    }
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
     * The purpose of this method is get the conf section ref.
     * @param name Name of conf section
     * @return Return pointer for conf section if section not exist return null pointer.
     */
    public ConfSection getSection(String name) {
        Iterator it = this.sections.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<String, ConfSection> pairs = (Map.Entry<String, ConfSection>) it.next();
            if (name.equals(pairs.getValue().getName())) {
                return pairs.getValue();
            }
        }

        return null;
    }

    /**
     * Method for get name of section. 
     * @param input Input text string.
     * @param section Output pointer for save the conf section ref.
     * @return Return true if conf section was found otherwise return false.
     */
    private boolean parseSection(String input, String section) {
        String name;
        String w = input;

        if (w.charAt(0) == '[' && w.charAt(w.length() - 1) == ']') {
            name = w.substring(1, w.length() - 1);
        } else {
            return false;
        }

        System.out.println("[D] SECTION -> name = " + name);

        section = name;
        return true;
    }

    /**
     * The purpose of this method is parsing the key and value from text string.
     * @param sectionName Name of section
     * @param input Input text for parser
     * @return Return true if parse found the key and value otherwise return false.
     */
    private boolean parseValue(String sectionName, String input) {
        String[] in = null;
        String key;
        String value;

        in = input.split("=");

        key = in[0];
        if (key.isEmpty()) {
            return false;
        }

        value = in[1];
        if (value.isEmpty()) {
            return false;
        }

        System.out.println("[D] SECTION = (" + sectionName + ") :: VALUE -> key = "
                + key + " | value = " + value);
        appendToSection(key, value, sectionName);
        return true;
    }
}
