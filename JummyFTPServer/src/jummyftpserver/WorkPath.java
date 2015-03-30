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

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

/**
 * This class represent business logic for operation with directories and files.
 */
public class WorkPath {

    private String homePath;
    private String currentPath;

    /**
     * Method for get file owner.
     * @param info File pointer
     */
    public static String getOwner(File info) {
        return "owner";
    }

    /**
     * Method for get group.
     * @param info File pointer
     */
    public static String getGroup(File info) {
        return "group";
    }

    /**
     * Constructor method for class with directories and files operation functions.
     * @param path working directory path
     */
    public WorkPath(String path) {
        homePath = path;
        currentPath = "/";
    }

    /**
     *  Method for get current directory path.
     */
    public String realPath() {
        if (homePath.charAt(homePath.length() - 1) != '/' && currentPath.charAt(0) != '/') {
            return homePath + "/" + currentPath;
        }
        return homePath + currentPath;
    }

    /**
     * Method provide translation ftp path to filesystem path.
     * @param filename File name
     */
    public String realFilePath(String filename) {

        if (filename.charAt(0) == '/') {
            if (homePath.charAt(homePath.length() - 1) != '/') {
                return homePath + filename;
            } else {
                return (homePath + filename.substring(1));
            }
        }

        if (realPath().charAt(realPath().length() - 1) != '/' && filename.charAt(0) != '/') {
            return realPath() + "/" + filename;
        }
        return realPath() + filename;
    }

    /**
     * Method for get current path.
     */
    public String pwd() {
        return currentPath;
    }

    /**
     * Method for change current directory path.
     */
    public boolean cwd() {
        return cwd(homePath);
    }

    /**
     * Method for change current durectory path.
     * @param path Path
     */
    public boolean cwd(String path) {
        String realPath;
        String newPath = path;

        if (newPath.equals("")) {
            newPath = "/";
        }

        if (newPath.charAt(0) != '/') {
            if (currentPath.charAt(currentPath.length() - 1) != '/') {
                newPath = currentPath + "/" + newPath;
            } else {
                newPath = currentPath + newPath;
            }
        } else if (!newPath.equals("/")) {
            newPath = path;
        }

        if (homePath.charAt(homePath.length() - 1) != '/' && newPath.charAt(0) != '/') {
            realPath = homePath + "/" + newPath;
        }
        
        realPath = homePath + newPath;
        File file = new File(realPath);

        if (file.exists()) {
            currentPath = newPath;
            return true;
        }

        return false;
    }

    /**
     * Method for create new directory.
     * @param name Name of new directory
     */
    public boolean mkd(String name) {
        String n = name;

        if (name.charAt(0) == '/') {
            cwd(name.substring(0, name.lastIndexOf("/")));
            n = name.substring(name.lastIndexOf("/"));
        }

        String createPath = realPath();

        if (createPath.charAt(createPath.length() - 1) != '/') {
            createPath += "/" + n;
        } else {
            createPath += n;
        }

        File directory = new File(createPath);
        return directory.mkdir();
    }

    /**
     * Method for remove directory.
     * @param name Name of directory
     */
    public boolean rmd(String name) {
        String n = name;

        if (name.charAt(name.length() - 1) == '/') {
            n = name.substring(0, (name.length() - 2));
        }

        if (n.contains("/")) {
            n = n.substring(n.lastIndexOf("/"));
        }

        String rmdPath = realPath();

        if (rmdPath.charAt(rmdPath.length() - 1) != '/') {
            rmdPath += "/" + n;
        } else {
            rmdPath += n;
        }

        File directory = new File(rmdPath);

        if (!directory.exists()) {
            return false;
        }

        return directory.delete();
    }

    /**
     * Method for delete file.
     * @param name File name
     */
    public boolean dele(String name) {
        File file = new File(realFilePath(name));
        return file.delete();
    }

    /**
     * Method for setup parent directory as current directory.
     */
    public boolean cdUp() {
        if (currentPath.equals("/")) {
            return false;
        }

        if (currentPath.charAt(0) != '/') {
            currentPath = "/" + currentPath;
        }

        String newPath = currentPath.substring(0, currentPath.lastIndexOf("/"));

        if (newPath.isEmpty()) {
            newPath = "/";
        }

        currentPath = newPath;
        return true;
    }

    /**
     * Method for rename file.
     * @param fromFileName Old file name
     * @param toFileName New file name
     */
    public boolean rename(String from, String to) {
        String o = from;
        String n = to;

        if (from.charAt(0) == '/') {
            cwd(from.substring(0, from.lastIndexOf("/")));
            o = from.substring(from.lastIndexOf("/"));
        }

        if (n.contains("/")) {
            n = to.substring(to.lastIndexOf("/"));
        }

        String oldPath = realPath();
        String newPath = realPath();

        if (oldPath.charAt(oldPath.length() - 1) != '/') {
            oldPath += "/" + o;
        } else {
            oldPath += o;
        }

        File oldF = new File(oldPath);

        if (newPath.charAt(newPath.length() - 1) != '/') {
            newPath += "/" + n;
        } else {
            newPath += n;
        }

        File newF = new File(newPath);
        return oldF.renameTo(newF);
    }

    /**
     * Method for check permission for write.
     * @param filename File name
     */
    public boolean isWritable(String filename) {
        // TODO
        File file = new File(filename);
        return true;
    }

    /**
     * Method for get list of files.
     */
    public List<String> list() {
        List<String> fList = new LinkedList<String>();

        File dir = new File(realPath());
        String[] chld = dir.list();

        if (chld != null) {
            int maxSizeLength = 0;
            int maxGroupLength = 5;
            int maxOwnerLength = 5;

            for (int i = 0; i < chld.length; i++) {
                String strPath = "";
                String fileName = chld[i];

                if(realPath().charAt(realPath().length()-1) != '/' && fileName.charAt(0) != '/')
                    strPath = realPath() + "/" + fileName;
                else 
                    strPath = realPath() + fileName;

                File file = new File(strPath);

                if(file.length() > maxSizeLength)
                    maxSizeLength = String.valueOf(file.length()).length();
            }

            for (int i = 0; i < chld.length; i++) {
                String line = "";
                String strPath = "";
                String fileName = chld[i];

                if(realPath().charAt(realPath().length()-1) != '/' && fileName.charAt(0) != '/')
                    strPath = realPath() + "/" + fileName;
                else
                    strPath = realPath() + fileName;

                File file = new File(strPath);
                Date date = new Date(dir.lastModified());

                String cmonth[] = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

                String month = cmonth[(date.getMonth()-1)];
                String day = String.valueOf(date.getDay());

                if(day.length() == 1)
                    day = "0" + day;

                Calendar cal = new GregorianCalendar();
                String year = String.valueOf(cal.get(Calendar.YEAR));

                long fileSize = file.length();

                String owner = WorkPath.getOwner(file);
                String group = WorkPath.getGroup(file);

                line += file.isDirectory() ? "d" : "-";

                line += (true) ? "r" : "-";
                line += (false) ? "w" : "-";
                line += (true) ? "x" : "-";

                line += (true) ? "r" : "-";
                line += (false) ? "w" : "-";
                line += (true) ? "x" : "-";

                line += (true) ? "r" : "-";
                line += (true) ? "w" : "-";
                line += (false) ? "x" : "-";

                line += "   1 ";
                line += owner;
                line += " ";

                for(int j = 0; j < (maxOwnerLength - owner.length()); j++) {
                    line += " ";
                }

                line += group;

                for(int j = 0; j < ((maxSizeLength + 5) - String.valueOf(fileSize).length()); j++) {
                    line += " ";
                }

                line += fileSize;
                line += " ";

                line += month;

                for(int j = 0; j < (6 - year.length()); j++) {
                    line += " ";
                }
                line += year;
                line += " ";

                line += fileName;
                fList.add(line);
            }
        }
        return fList;
    }
}
