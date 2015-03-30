/***************************************************************************
 *   Copyright (C) 2011 by Marek Hakala   *
 *   hakala.marek@gmail.com   *
 *
 *   Semester project for BI-PSI @ CTU FIT
 *   Topic: Robot Karel TCP server
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

package robotkarelserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class ClientInfo implements Runnable {

    public enum COMMAND {STEP, TURNLEFT, PICKUP, REPAIR, NONE}
    
    private int id = 0;
    private Socket socket = null;
    private Thread thread = null;
    private BufferedReader socketInput;
    private PrintStream socketOutput;
    private RobotInfo robotInfo;
    private String data = new String();

    public ClientInfo(int id, Socket socket) {
        this.id = id;
        this.socket = socket;

        robotInfo.setRandomName();
        robotInfo.setRandomSecret();
        robotInfo.setRandomPosition();

        init();
    }

    private void init() {
        thread = new Thread(this);
        thread.start();
    }

    public void run() {
        clientAccept();
    }
    
    public String clientAnswer(String input) throws CodeException {
        int offset = 0;

        try {
            offset = findName();
            ProtocolClient res = new ProtocolClient(input.substring(offset));

                switch(res.getCmd()) {
                case STEP: {
                      robotInfo.step();

                      String out = "240 OK "; // 530, 580, 572
                      out += robotInfo.toString();
                    return out;
                }
                case TURNLEFT: {
                    robotInfo.rotationLeft();
                    String out = "240 OK ";
                    out += robotInfo.toString();
                    return out;
                }
                case PICKUP: {
                    String out = "210 USPECH "; // 550
                    out += robotInfo.getSecret();
                    return out;
                }
                case REPAIR: {
                    robotInfo.repair(res.parseResREPAIR());
                    String out = "240 OK "; // 571
                    out += robotInfo.toString();
                    return out;
                    }
                default: {
                    throw new CodeException(CodeException.CODE.UNKNOWN_COMMAND);
                }
            }
        } catch (CodeException code) {
            switch(code.getCode()) {
                case CPU_FAIL: {
                    String out = "580 SELHANI PROCESORU ";
                    out += robotInfo.getIndexCpu();
                    return out;
                    }
                    case CPU_ISOK: {
                    return "571 PROCESOR FUNGUJE";
                }
                case PICKUP_IS_NOT_POSSIBLE: {
                    return "550 NELZE ZVEDNOUT ZNACKU";
                }
                case FAILURE: {
                    return "530 HAVARIE";
                }
                case ROBOT_IS_BROKEN: {
                    return "572 ROBOT SE ROZPADL";
                }
                default: { // UNKNOWN_COMMAND
                    return "500 NEZNAMY PRIKAZ";
                }
            }
        }
    }

    public int findName() throws CodeException {
        int i = 0;

        for (i = 0; i < (int) robotInfo.getName().length(); i++) {
            if (robotInfo.getName().charAt(i) != data.charAt(i)) {
                throw new CodeException(CodeException.CODE.UNKNOWN_COMMAND);
            }
        }

        if (data.charAt(i) != ' ') {
            throw new CodeException(CodeException.CODE.UNKNOWN_COMMAND);
        }

        return robotInfo.getName().length();
    }

    public boolean checkName() throws CodeException {
        for (int i = 0; i < (int) robotInfo.getName().length(); i++) {
            if (robotInfo.getName().charAt(i) != data.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public void clientAccept() {
        String ans = "200 Ahoj, tady je robot verze 1.6.\nOslovuj mne ";
        ans += robotInfo.getName() + ".\r\n";

        System.out.println("[::][" + "]  New client connection ...");
        this.socketOutput.print(ans);
    }
    
    private String readLine() {
        String req = new String();
        int c;
        int lastC = -1;

        try {
            while ((c = socketInput.read()) != -1) {
                if ((char) lastC == '\r' && (char) c == '\n') {
                    return req;
                }

                if (req.length() > 110) {
                    continue;
                }

                req += (char) c;
                lastC = c;
            }

            if (c < 0) {
                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return req;
    }

    public String getData() {
        return data.substring(0, data.length() - 2);
    }

    int getLen() {
        return data.length();
    }

    private void disconnect() {
        try {
            System.out.println("[::] Terminates the connection ...");
            socketOutput.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
