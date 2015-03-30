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

import cz.hakala.dpo.dragonland.api.CharacterFactory;
import cz.hakala.dpo.dragonland.api.CharacterInterface;
import cz.hakala.dpo.dragonland.api.CommandInterface;

public class CommandNewGame implements CommandInterface {

	@Override
	public String name() {
		return "newgame";
	}

	@Override
	public String execute(String[] params) {
		if (params.length != 3)
			return help();

		GameApplication app = GameApplication.getInstance();
		CharacterFactoryPool pool = app.getCharacterFactoryPool();
		CharacterFactory factory = pool.getByFactoryName(params[1]);

		if (factory == null)
                        return "Creating a new character failed! Type of character wasn't found.";

		CharacterInterface character = factory.construct(params[2]);

		if (character == null)
			return "It wasn't possible create a character!";

		PlayerInfo playerInfo = new PlayerInfo(character, FactoryRoomPool
				.getInstance().getRoom());
		GameApplication.getInstance().setPlayerInfo(playerInfo);

		GameApplication.getInstance().changeState();
		return "Created new character with nickname: " + character.name() + "["
				+ character.type() + "]\n New game started!\n Enjoy it ;)";
	}

	@Override
	public String help() {
		return "newgame character_type nickname - start game";
	}
}
