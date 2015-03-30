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

import java.util.List;

// Game room interface
public interface RoomInterface {
	// Get name of game room
	public String name();

	// Setter for set game room name
	public void setName(String name);

	// Get list of item in room
	public List<ItemInterface> items();

	// Get list of move point in room
	public List<MovePointInterface> movePoints();

	// Get list of people in room (example: NPC people)
	public List<CharacterInterface> people();

	// Drop item into room environment by string name
	public void dropItem(ItemInterface item);

	// Get item from room environment
	public ItemInterface getItem(String name);
}
