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
import cz.hakala.dpo.dragonland.core.BasicItem;
import cz.hakala.dpo.dragonland.core.NPC;

public class RoomBuilderElfMine extends RoomBuilder {

	public RoomBuilderElfMine() {
		super();
	}

	@Override
	public void buildProperty() {
		room.setName("Elf mine");
	}

	@Override
	public void buildItems() {
		room.items().add(new BasicItem("Cupboard", (double) 984, false));
		room.items().add(new Key("Key", (double) 134, true));
		room.items().add(new BasicItem("Pot", (double) 452, true));
	}

	@Override
	public void buildNPC() {
		room.people().add(new NPC("Tatar", (double) 912));
		room.people().add(new NPC("Ursar", (double) 856));
		room.people().add(new NPC("Argas", (double) 1062));
	}
}
