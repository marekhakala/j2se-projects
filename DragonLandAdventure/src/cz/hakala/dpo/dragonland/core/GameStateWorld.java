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

import java.util.ArrayList;
import java.util.List;

import cz.hakala.dpo.dragonland.api.CommandInterface;
import cz.hakala.dpo.dragonland.api.GameStateInterface;

public class GameStateWorld implements GameStateInterface {
	private List<CommandInterface> commands = new ArrayList<CommandInterface>();

	public GameStateWorld() {
		init();
	}

	public void init() {
		commands.add(new CommandCurrentRoom());
		commands.add(new CommandUnlockDoor());
		commands.add(new CommandLockDoor());

		commands.add(new CommandDropItem());
		commands.add(new CommandGetItem());

		commands.add(new CommandBuyItem());
		commands.add(new CommandSellItem());

		commands.add(new CommandHelp());

		commands.add(new CommandPlayerInfo());
		commands.add(new CommandUseDoor());
	}

	public CommandInterface get(String name) {
		for (CommandInterface command : commands) {
			if (command.name().equals(name))
				return command;
		}
		return null;
	}

	public String help() {
		String output = "-=Help=- : \n\n";
		output += "\tItem for win the game: " + GameApplication.getInstance().getWinItem().name() + "\n\n";
		
		for (CommandInterface command : commands) {
			output += command.help() + "\n";
		}
		return output;
	}
}
