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

public class CommandPlayerInfo implements CommandInterface {

        @Override
	public String name() {
		return "player";
	}

	protected String getPlayerInfo() {
		PlayerInfo currentPlayer = GameApplication.getInstance()
				.getPlayerInfo();
		CharacterInterface character = currentPlayer.getCharacter();

		String output = "\t-- Player info :\n";

		output += "\t Nickname \t\t: " + character.name() + "\n";
		output += "\t Character \t\t: " + character.type() + "\n";
		output += "\t Money \t\t: " + character.money() + "$\n";
		output += "---------------------------------------------\n";
		return output;
	}

	protected String getInventoryList() {
		PlayerInfo currentPlayer = GameApplication.getInstance()
				.getPlayerInfo();
		CharacterInterface character = currentPlayer.getCharacter();
		List<ItemInterface> list = character.inventory();

		String output = "\t-- Inventory:\n";

		for (ItemInterface item : list)
			output += "\t " + item.name() + "\t\t\t " + item.cost() + "$\n";

		output += "---------------------------------------------\n";
		return output;
	}

	@Override
	public String execute(String[] params) {
		if (params.length != 1)
			return help();

		String output = "-=Player info=- :\n";
		output += getPlayerInfo();
		output += getInventoryList();

		return output;
	}

	@Override
	public String help() {
		return "player - Show the player info and inventory";
	}
}
