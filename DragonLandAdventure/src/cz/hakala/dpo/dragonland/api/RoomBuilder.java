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

package cz.hakala.dpo.dragonland.api;

import cz.hakala.dpo.dragonland.core.Room;

// Abstract Room builder
public abstract class RoomBuilder {
	// Room instance object
	protected RoomInterface room = null;

	// Getter for room object
	public RoomInterface getRoom() {
		return room;
	}

	// Create a new room instance
	public void createRoomProduct() {
		room = new Room();
	}

	// Abstract method for create property
	public abstract void buildProperty();

	// Abstract method for create items
	public abstract void buildItems();

	// Abstract method for create NPC
	public abstract void buildNPC();
}