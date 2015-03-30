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

import java.io.IOException;

import cz.hakala.dpo.dragonland.api.CommandInterface;
import cz.hakala.dpo.dragonland.api.GameStateInterface;
import cz.hakala.dpo.dragonland.api.GameViewInterface;
import cz.hakala.dpo.dragonland.api.ItemInterface;
import cz.hakala.dpo.dragonland.api.RoomInterface;

public class GameApplication {
	// Main game instance
	protected static GameApplication instance = null;
	// Game name
	protected String gameName = "";

	// Game states
	protected GameStateInterface menuState = new GameStateMenu();
	protected GameStateInterface worldState = new GameStateWorld();
	protected GameStateInterface state = menuState;

	// Game output view
	protected GameViewInterface output = new ConsoleView();
	// Game input controller
	protected InputController input = new InputController(output);

	// Character factory pool
	protected CharacterFactoryPool characterFactory = new CharacterFactoryPool();

	// Current game player info object
	protected PlayerInfo info = null;

	// Item to take for win the game
	protected ItemInterface winItem = null;

	// Hide constructor
	protected GameApplication() {
	}

	// Singleton static method for get single instance
	public static GameApplication getInstance() {
		if (instance == null)
			instance = new GameApplication();
		return instance;
	}

	// Setter for setup game name
	public void setupName(String name) {
		this.gameName = name;
	}

	// Insert method for populate room pool
	public void insertRoom(RoomInterface room) {
		FactoryRoomPool.getInstance().insert(room);
	}

	// Getter for game player info
	public PlayerInfo getPlayerInfo() {
		return info;
	}

	// Setter for setup game player info
	public void setPlayerInfo(PlayerInfo playerInfo) {
		info = playerInfo;
	}

	// Init method for start game instance
	public void init() {
		output.printTopicBox(gameName);
		output.printLine(20);
		output.printText("");
		output.printHelp(state);
		output.printText("\n Option: ");

		try {
			input.readInputLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Return current game state - world or menu
	public GameStateInterface getCurrentState() {
		return state;
	}

	// Game state switcher
	public void changeState() {
		if (state == menuState)
			state = worldState;
		else
			state = menuState;
	}

	// Setter for setup winner item in game
	public void setupWinItem(ItemInterface item) {
		winItem = item;
	}

	// Getter for setup winner item
	public ItemInterface getWinItem() {
		return winItem;
	}

	// Get Command from current game state
	public CommandInterface getCommand(String name) {
		return state.get(name);
	}

	// Get character factory pool
	public CharacterFactoryPool getCharacterFactoryPool() {
		return characterFactory;
	}

	// Game state is win
	public void gameEnd() {
		output.printText("The game was successfully ended. ;)");
		System.exit(0);
	}
}
