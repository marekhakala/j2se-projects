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

import cz.hakala.dpo.dragonland.api.GameStateInterface;
import cz.hakala.dpo.dragonland.api.GameViewInterface;

public class ConsoleView implements GameViewInterface {

        public ConsoleView() {
	}

	@Override
	public void printTopicBox(String text) {
		System.out.println("==========================\n");
		System.out.println("|" + text + "\n");
		System.out.println("==========================\n");
	}

	public void printSystemMessage(String text) {
		printText(">> " + text);
	}

	public void printCommandMessage(String text) {
		printText(":: " + text);
	}

	@Override
	public void printText(String text) {
		printText(text, true);
	}

	@Override
	public void printText(String text, boolean newLine) {

		if (newLine)
			System.out.println(text);
		else
			System.out.print(text);
	}

	@Override
	public void printLine(Integer n) {
		for (int i = 0; i < n; i++)
			printText("-", false);
	}

	@Override
	public void printHelp(GameStateInterface state) {
		printText(state.help(), false);
	}
}
