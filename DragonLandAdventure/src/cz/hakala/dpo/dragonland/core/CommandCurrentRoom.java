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

package cz.hakala.dpo.dragonland.core;

import java.util.List;

import cz.hakala.dpo.dragonland.api.CharacterInterface;
import cz.hakala.dpo.dragonland.api.CommandInterface;
import cz.hakala.dpo.dragonland.api.ItemInterface;
import cz.hakala.dpo.dragonland.api.MovePointInterface;
import cz.hakala.dpo.dragonland.api.RoomInterface;

public class CommandCurrentRoom implements CommandInterface {

        @Override
	public String name() {
		return "room";
	}

	protected String getInventoryList(CharacterInterface character) {
		List<ItemInterface> list = character.inventory();
		String output = "\t\t-- Inventory :\n";

		for (ItemInterface item : list)
			output += "\t\t " + item.name() + "\t\t\t " + item.cost() + "$\n";

		output += "---------------------------------------------\n";
		return output;
	}

	protected String getPersonInfo(CharacterInterface character) {
		String output = "\t " + character.name() + " [" + character.type()
				+ "] " + character.money() + "$\n";
		output += getInventoryList(character);
		return output;
	}

	protected String getPeopleList() {
		PlayerInfo currentPlayer = GameApplication.getInstance()
				.getPlayerInfo();
		RoomInterface room = currentPlayer.getRoom();
		List<CharacterInterface> list = room.people();

		if (list.size() < 1)
			return "";

		String output = "\t-- List of people :\n";

		for (CharacterInterface character : list)
			output += getPersonInfo(character);

		output += "---------------------------------------------\n";
		return output;
	}

	protected String getItemList() {
		PlayerInfo currentPlayer = GameApplication.getInstance()
				.getPlayerInfo();
		RoomInterface room = currentPlayer.getRoom();
		List<ItemInterface> list = room.items();

		if (list.size() < 1)
			return "";

		String output = "\t-- List of items :\n";

		for (ItemInterface item : list)
			output += "\t " + item.name() + "\t\t\t " + item.cost() + "$\n";

		output += "---------------------------------------------\n";
		return output;
	}

	protected String getDoorList() {
		PlayerInfo currentPlayer = GameApplication.getInstance()
				.getPlayerInfo();
		RoomInterface room = currentPlayer.getRoom();
		List<MovePointInterface> list = room.movePoints();

		if (list.size() < 1)
			return "";

		String output = "\t-- List of doors :\n";

		for (MovePointInterface item : list)
			output += "\t [" + item.name() + "] " + item.from().name() + " -> "
					+ item.to().name() + "\n";

		output += "---------------------------------------------\n";
		return output;
	}

	@Override
	public String execute(String[] params) {
		if (params.length != 1)
			return help();

		String output = "-=Room information=- :\n";
		output += "-- Name: "
				+ GameApplication.getInstance().getPlayerInfo().getRoom()
						.name() + "\n";
		output += getPeopleList();
		output += getItemList();
		output += getDoorList();

		return output;
	}

	@Override
	public String help() {
		return "room - return basic room information and list of people";
	}
}
