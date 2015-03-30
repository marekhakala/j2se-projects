/***************************************************************************
 *   Copyright (C) 2012 by Marek Hakala   *
 *   hakala.marek@gmail.com   *
 *
 *   Semester project for MI-DPO @ CTU FIT
 *   Topic: Adventure Game Engine - DragonLand 
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

package cz.hakala.dpo.dragonland.game;

import cz.hakala.dpo.dragonland.api.RoomBuilder;
import cz.hakala.dpo.dragonland.core.*;

public class RoomBuilderLongPlain extends RoomBuilder {

	public RoomBuilderLongPlain() {
		super();
	}

	@Override
	public void buildProperty() {
		room.setName("Long plain");
	}

	@Override
	public void buildItems() {
		room.items().add(new BasicItem("Table", (double) 1120, false));
		room.items().add(new BasicItem("Gold", (double) 5320, true));
		room.items().add(new BasicItem("Armor", (double) 815, true));
		room.items().add(new BasicItem("Longbow", (double) 120, true));
		room.items().add(new BasicItem("Crossbow", (double) 220, false));
	}

	@Override
	public void buildNPC() {
		NPC npc1 = new NPC("Lukas", (double) 1453);
		room.people().add(npc1);
		npc1.inventory().add(new Key("Key", (double) 120, true));

		room.people().add(new NPC("Krain", (double) 1270));
		room.people().add(new NPC("Rotr", (double) 1108));
	}
}
