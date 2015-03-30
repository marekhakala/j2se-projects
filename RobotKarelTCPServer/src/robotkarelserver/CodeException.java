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

public class CodeException extends Exception {
    private CODE code;

    public CodeException(CODE code) {
        this.code = code;
    }

    public CODE getCode() {
        return code;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CodeException other = (CodeException) obj;
        if (this.code != other.code) {
            return false;
        }
        return true;
    }

    public enum CODE {
        SALUTE(200),
        SUCCESS(210),
        OK(240),
        UNKNOWN_COMMAND(500),
        FAILURE(530),
        PICKUP_IS_NOT_POSSIBLE(550),
        CPU_ISOK(571),
        ROBOT_IS_BROKEN(572),
        CPU_FAIL(580);

        private int _value;

        CODE(int value) {
            _value = value;
        }

        public int value() {
            return _value;
        }
    }
}
