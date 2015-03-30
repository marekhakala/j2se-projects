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

import robotkarelserver.ClientInfo.COMMAND;

public class ProtocolClient {
    private String text;
    private String param;
    private ClientInfo.COMMAND cmd;

    public ProtocolClient(String res) throws CodeException {
        if (!parseRes(res)) {
            throw new CodeException(CodeException.CODE.UNKNOWN_COMMAND);
        }
    }

    boolean parseRes(String res) {
        String tmp[] = res.split(" ");
        cmd = stringToCommand(tmp[0]);

        if(tmp.length > 1)
            param = tmp[1];
        else
            param = "";

        if (validateCommand(cmd))
            return true;

        System.out.println(cmd);
        return false;
    }

    int parseResREPAIR() throws CodeException {
        int index = Integer.getInteger(param);

        if(index < 1 || index > 9)
            throw new CodeException(CodeException.CODE.UNKNOWN_COMMAND);
        return index;
    }

    public COMMAND getCmd() {
        return cmd;
    }
    public String getParam() {
        return param;
    }

    ClientInfo.COMMAND stringToCommand(String input) {
        if (input.toLowerCase().equals("krok")) {
            return ClientInfo.COMMAND.STEP;
        } else if (input.toLowerCase().equals("vlevo")) {
            return ClientInfo.COMMAND.TURNLEFT;
        } else if (input.toLowerCase().equals("zvedni")) {
            return ClientInfo.COMMAND.PICKUP;
        } else if (input.toLowerCase().equals("opravit")) {
            return ClientInfo.COMMAND.REPAIR;
        } else {
            return ClientInfo.COMMAND.NONE;
        }
    }

    boolean validateCommand(ClientInfo.COMMAND cmd) {
        return cmd == ClientInfo.COMMAND.STEP
                || cmd == ClientInfo.COMMAND.TURNLEFT
                || cmd == ClientInfo.COMMAND.PICKUP
                || cmd == ClientInfo.COMMAND.REPAIR;
    }
}
