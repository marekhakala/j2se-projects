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

import java.util.Random;

public final class RobotInfo {
    private String name;
    private String secret;
    private RobotPosition robotPosition;
    private boolean isDamaged;
    private int indexCpu;
    private int numberStep;

    public RobotInfo() {
        init();
    }

    public RobotInfo(int x, int y) {
        this.setPosition(x, y);
        robotPosition.isSetDirection = false;
    }

    public RobotInfo(int x, int y, DIRECTION direction) {
        this(x, y);
        robotPosition.direction = direction;
        robotPosition.isSetDirection = true;
    }

    private void init() {
        robotPosition.x = robotPosition.y = 0;
        robotPosition.direction = new DIRECTION();
        robotPosition.isSet = robotPosition.isSetDirection = false;
        indexCpu = -1;
        isDamaged = false;
        numberStep = 0;
    }

    public void setPosition(RobotInfo pos) {
        robotPosition.x = pos.robotPosition.x;
        robotPosition.y = pos.robotPosition.y;
        robotPosition.isSet = true;
    }

    void setPosition(RobotPosition pos) {
        robotPosition.x = pos.x;
        robotPosition.y = pos.y;
        robotPosition.isSet = true;
    }

    void setPosition(int x, int y) {
        robotPosition.x = x;
        robotPosition.y = y;
        robotPosition.isSet = true;
    }

    public String getName() {
        return name;
    }

    public int getNumberStep() {
        return numberStep;
    }


    void step() throws CodeException {
        if(isDamaged)
            throw new CodeException(CodeException.CODE.ROBOT_IS_BROKEN);

        setRandomState();

        switch(robotPosition.direction.direct) {
            case NORTH: robotPosition.y++; numberStep++; break;
            case SOUTH: robotPosition.y--; numberStep++; break;
            case EAST: robotPosition.x++; numberStep++; break;
            case WEST: robotPosition.x--; numberStep++; break;
            default: break;
        }

        if(robotPosition.x <= -23 || robotPosition.x >= 23
           || robotPosition.y <= -23 || robotPosition.y >= 23)
            throw new CodeException(CodeException.CODE.FAILURE);
    }

    public int numberRotation(DIRECTION from, DIRECTION to) {
        int i = 0;
        DIRECTION dir = new DIRECTION(from);

        for(;;) {
            if (dir.isDirect(to)) break;
            dir.nextDirect();
            i++;
        }
        return i;
    }

    public DIRECTION rotationLeft() {
        robotPosition.direction.nextDirect();
        return robotPosition.direction;
    }

    public void repair(int index) throws CodeException {

        if(index < 1 || index > 9)
            throw new CodeException(CodeException.CODE.UNKNOWN_COMMAND);

        if((isDamaged && index != indexCpu) || !isDamaged)
                throw new CodeException(CodeException.CODE.CPU_ISOK);

        indexCpu = -1;
        isDamaged = false;
        numberStep = 0;
    }

    public void setRandomName() {
            Random rand = new Random();

            String names[] = new String[11];
            names[0] = "Charlie von Silicium";
            names[1] = "Elektronko";
            names[2] = "Karle";
            names[3] = "Magdaleno";
            names[4] = "Nadherosuperdepresivni robote Marvine, ktery mas tak hrozne moc spatnou naladu";
            names[5] = "Oooo, puvabna krasko";
            names[6] = "Plechaci";
            names[7] = "Uzasny robote, ktery umi skvele aproximovat PI a vi, ze jeho hodnota je zhruba 3,14159265358979323846264338327950288419716939937510582097494459230781640628620899862803482534211706798214808651328230664709384460955021223172535940812848111745028410270193852110555964452294895493038196442881097566593344612847564823378678316527120190914564856692346034861045432664821339360726024914127372458700660631558817488152092096282925409171536436789259036001133053054882046652138414695194151160943305727036575959195309218612738";
            names[8] = "Zazraku techniky";
            names[9] = "neschopny robote, co neumis vubec nic";
            names[10] = "skvely robote, ktery jsi kremikovym zazrakem";

            int index = (int)rand.nextInt(11);
            name = names[index];
    }

    public void setRandomSecret() {
            Random rand = new Random();

            String secrets[] = new String[26];
            secrets[0] = "Bare jede jerab.";
            secrets[1] = "Buzkova, ma Vok zub?";
            secrets[2] = "Dne moto: Palindrom i spacha psi mord, Nil a potom End.";
            secrets[3] = "Do haje si Jan Ales sel a naji se jahod.";
            secrets[4] = "Java horko ma, mokro Havaj.";
            secrets[5] = "Jelenovi pivo nelej.";
            secrets[6] = "Kobyla ma maly bok.";
            secrets[7] = "Kuna nese nanuk.";
            secrets[8] = "Na pet sidel hledi Stepan.";
            secrets[9] = "Nebe vola halo v Eben.";
            secrets[10] = "Nekouka zak u oken?";
            secrets[11] = "Nevypust supy ven.";
            secrets[12] = "Otoman na moto.";
            secrets[13] = "Po tenoru, Juro, netop!";
            secrets[14] = "Rek erotoman lapal na motore ker.";
            secrets[15] = "Rek: I lez a tal mecem lat a zel i ker.";
            secrets[16] = "Sabina hrava na varhany bas.";
            secrets[17] = "Sok v pH = HP v kos.";
            secrets[18] = "Tele veli mile velet.";
            secrets[19] = "Teleci v separe si zere reziser a pes vice let.";
            secrets[20] = "U dubu tu budu.";
            secrets[21] = "V elipse spi lev.";
            secrets[22] = "Vel salv pal, lap vlas lev.";
            secrets[23] = "Zebra, dar bez.";
            secrets[24] = "Zeman nema kamen na mez.";
            secrets[25] = "Zeman seno dones na mez.";

            int index = (int)rand.nextInt(26);
            secret = secrets[index];
    }

    public void setRandomPosition() {
        Random rand = new Random();

        robotPosition.x = (int)rand.nextInt(21);

        if ((int) rand.nextInt(1) == 1) {
            robotPosition.x *= -1;
        }

        robotPosition.y = (int) rand.nextInt(21);

        if ((int) rand.nextInt(1) == 1) {
            robotPosition.y *= -1;
        }
        robotPosition.isSet = true;

        int n = rand.nextInt(3);
        for(int i=0; i < n; ++i) { robotPosition.direction.nextDirect(); }
        robotPosition.isSetDirection = true;
    }

    public void setRandomState() throws CodeException {
        Random rand = new Random();
        int i = rand.nextInt(8);

        if ((i % 2) == 1 || numberStep > 9) {// damage
            indexCpu = i + 1;
            isDamaged = true;
            throw new CodeException(CodeException.CODE.CPU_FAIL);
        }
    }

    public boolean findDirection(RobotPosition pos) {
        if (pos.x == robotPosition.x && pos.y == robotPosition.y) {
            return true;
        }

        if (pos.x == robotPosition.x) {
            if (pos.y > robotPosition.y) {
                robotPosition.direction.direct = DIRECTION.DIRECT.SOUTH;
            } else {
                robotPosition.direction.direct = DIRECTION.DIRECT.NORTH;
            }
        } else if (pos.y == robotPosition.y) {
            if (pos.x > robotPosition.x) {
                robotPosition.direction.direct = DIRECTION.DIRECT.EAST;
            } else {
                robotPosition.direction.direct = DIRECTION.DIRECT.WEST;
            }
        }
        robotPosition.isSetDirection = true;

        if (!isCorrectDirection(pos)) {
            return false; // 2x90 rotation
        }
        return true;
    }

    public boolean isCorrectDirection(RobotPosition pos) {
        switch (robotPosition.direction.direct) {
            case WEST:
            case EAST: {
                if (pos.x > 0) {
                    if (robotPosition.direction.direct == DIRECTION.DIRECT.WEST) {
                        return true;
                    }
                    return false;
                } else {
                    if (robotPosition.direction.direct == DIRECTION.DIRECT.EAST) {
                        return true;
                    }
                    return false;
                }
            }
            case NORTH:
            case SOUTH: {
                if (pos.y > 0) {
                    if (robotPosition.direction.direct == DIRECTION.DIRECT.SOUTH) {
                        return true;
                    }
                    return false;
                } else {
                    if (robotPosition.direction.direct == DIRECTION.DIRECT.NORTH) {
                        return true;
                    }
                    return false;
                }
            }
            default:
                return false;
        }
    }

    public DIRECTION detectNewDirection(RobotPosition pos, int asix) { // FIXME
        DIRECTION dir = new DIRECTION();

        if (asix == 0) { // asix x
            if (pos.y > 0) {
                dir.direct = DIRECTION.DIRECT.NORTH;
                return dir;
            }
            dir.direct = DIRECTION.DIRECT.SOUTH;
            return dir;
        } else { // asix y
            if (pos.x > 0) {
                dir.direct = DIRECTION.DIRECT.WEST;
                return dir;
            }
            dir.direct = DIRECTION.DIRECT.EAST;
            return dir;
        }
    }

    public String getSecret() throws CodeException {
        if (robotPosition.x != 0 || robotPosition.y != 0) {
            throw new CodeException(CodeException.CODE.PICKUP_IS_NOT_POSSIBLE);
        }
        return secret;
    }

    public int getIndexCpu() {
        return indexCpu;
    }

    public void setDirection(DIRECTION direction) {
        this.robotPosition.direction = direction;
        this.robotPosition.isSetDirection = true;
    }

    @Override
    public String toString() {
        return "("+robotPosition.x+","+robotPosition.y+")";
    }
}
