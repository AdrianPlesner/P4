include StdLib;
Setup{
	Card{
	}
	Player{
		bool ai = false;
		Construct player(string name, bool ai) {
			this.name = name;
			this.ai = ai;
		}
	}
	Game{
		player current;
		List typeof card Deck = ShuffleDeck(GetStdDeck(0));
		List typeof card Pile = { };
		List typeof player Players = InitPlayers();
		while ((Players == null) | (Players.length < 2)) {
			MessageAll("You need at least 2 players to play");
			Players = InitPlayers();
		}
		current = Players.index(0);
		for p in Players {
			p.hand = Deck.take(3);
		}
		Pile = Deck.take(1);
		int playerindex = 0;
		player knocked = null;
		bool lastTurn = false;
	}
}
Moves{
	Function TakeCard() typeof void{
		string input = "";
		card draw;
		bool cont = true;
		while cont {
			draw = Pile.index((Pile.length - 1));
			input = Ask(current, ((((("On top of the pile is: " + draw.suit) + ", ") + draw.value) + "\n") + "Draw from pile(p) or draw from deck(d) ?"));
			if ((input == "p") | (input == "d")) {
				cont = false;
			}
		}
		if (input == "p") {
			current.hand.add(draw);
			Pile.remove(draw);
		}
		else if (input == "d") {
			current.hand.add(Deck.take(1).index(0));
		}
		else {
			MessageAll("Something went wrong");
		}
	}
	Function Knock() typeof void{
		string input = " ";
		while (input == " ") {
			input = Ask(current, "Do you wish to knock?(y/n)");
			if (input == "y") {
				knocked = current;
			}
			else if (input != "n") {
				input = " ";
			}
		}
	}
	Function Discard() typeof void{
		bool cont = true;
		int index = 0;
		while cont {
			MessageAll("Choose a card to discard by number:\n");
			for int i = 0 ; (i < current.hand.length) ; i += 1 {
				card c = current.hand.index(i);
				MessageAll((((((i + ": ") + c.suit) + ", ") + c.value) + "\n"));
			}
			string input = Read();
			index = ParseInt(input);
			if ((index < 0) | (index > 3)) {
				MessageAll("Invalid card number, try again");
				cont = true;
			}
			else {
				cont = false;
			}
		}
		card discard = current.hand.index(index);
		current.hand.remove(discard);
		Pile.add(discard);
	}
}
Turn{
	MessageAll("\n\n\n\n\n\n\n\n\n\n");
	if (knocked != null) {
		MessageAll((knocked.name + " has knocked!"));
	}
	lastTurn = (current == knocked);
	Message(current, "It is your turn\n");
	Message(current, "In your hand is :");
	for c in current.hand {
		MessageAll((((c.suit + ", ") + c.value) + "\n"));
	}
	TakeCard();
	Discard();
	if (knocked == null) {
		Knock();
	}
}
EndCondition{
	bool end = false;
	player winner = null;
	end = checkForTrick(current.hand);
	if end {
		winner = current;
	}
	if lastTurn {
		end = true;
		List typeof int scores = { };
		for p in Players {
			scores.add(calcScore(p.hand));
		}
		int high = 0;
		int index = (0 - 1);
		for int i = 0 ; (i < Players.length) ; i += 1 {
			MessageAll(((Players.index(i).name + " had score: ") + scores.index(i)));
			if (scores.index(i) > high) {
				high = scores.index(i);
				index = i;
			}
		}
		winner = Players.index(index);
	}
	if end {
		MessageAll(("The Winner is " + winner.name));
	}
	else {
		playerindex += 1;
		if (playerindex == Players.length) {
			playerindex = 0;
		}
		current = Players.index(playerindex);
	}
	return end;
}
Function InitPlayers() typeof List typeof player{
	List typeof player result = { };
	MessageAll("So who is playing today?");
	string input = "y";
	while (input == "y") {
		MessageAll("Already in the game is:");
		for p in result {
			MessageAll(p.name);
		}
		string name = AskAll("Next players name: ");
		bool ai;
		string aiS = "";
		while (aiS == "") {
			aiS = AskAll("Are they an ai (y/n)");
			if (aiS == "y") {
				ai = true;
			}
			else if (aiS == "n") {
				ai = false;
			}
			else {
				MessageAll("Thats not right");
				aiS = "";
			}
		}
		result.add(player(name, ai));
		input = AskAll("Are there any more players?(y/n)");
	}
	return result;
}
Function checkForTrick(List typeof card cards) typeof bool{
	int result = calcScore(cards);
	return (result == 31);
}
Function calcScore(List typeof card cards) typeof int{
	List typeof string suits = { "hearts"; "diamonds"; "clubs"; "spades"; };
	List typeof int scores = { 0; 0; 0; 0; };
	for c in cards {
		int id = find(c.suit, suits);
		if (id >= 0) {
			int v = c.value;
			int p = scores.index(id);
			if (v == 1) {
				p += 11;
			}
			else if (v > 10) {
				p += 10;
			}
			else {
				p += v;
			}
			scores.set(id, p);
		}
	}
	int high = 0;
	for s in scores {
		if (s > high) {
			high = s;
		}
	}
	return high;
}
Function find(string s, List typeof string list) typeof int{
	int result = (0 - 1);
	for int i = 0 ; (i < list.length) ; i += 1 {
		if (list.index(i) == s) {
			result = i;
		}
	}
	return result;
}
