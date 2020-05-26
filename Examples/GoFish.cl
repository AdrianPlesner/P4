include StdLib;
Setup{
    Card {
    }
    Player{
        // Alle spillere starter med 7 kort hver, fra bunken.
        bool ai = false;
        int score = 0;
        Construct player(string name, bool ai ){
            this.name = name;
            this.ai = ai;
        }
    }
//public er “spillepladen” (tilgængelig for alle spillere)
    Game{
        player current;
        // Der er en bunke i spillet, trækbunken til når man fisker
        List typeof card Deck = ShuffleDeck(GetStdDeck(0));
        // Definer spillere, initaliserer en List<Player> Players
        List typeof player Players = InitPlayers();
        while Players == null | Players.length < 2 {
            MessageAll("You need at least 2 players to play");
            Players = InitPlayers();
        }
        //Sæt start spiller
        current = Players.index(0);
        for p in Players {
            p.hand = Deck.take(7);
        }
        int f = 5+3*8, h = 78 % 43 + 43 / (234-43);
    }
}
// Mulige træk
Moves{
    // input = et kort ( fra egen hånd) og en anden spiller
    Function ChooseMove(card c, player p) typeof bool{
        // Tjek alle kort i den anden spiller hånd om de har samme værdi og overræk dem der er til den spiller hvis tur det er
        bool result = false;
        List typeof card TakeList = {};
        for pCard in p.hand {

            if pCard.value == c.value {
                Message(current, p.name + " had " + pCard.suit + ", " + pCard.value + "\n");
                TakeList.add(pCard);
                result = true;
            }
        }
        for pCard in TakeList{
            pCard.transfer(p, current);
        }

        //Hvis hånden på den spiller man trak fra nu er tom, trækker de et kort fra bunken.
        if p.hand.length == 0 & Deck.length > 0{
            p.hand = Deck.take(1);
         }

         return result;
    }
}
// Definer en tur. Efter Setup vil denne kode loopes indtil endcondition
Turn{
    Message(current,"It is your turn!\n");
    bool continue = true;
    player chosenPlayer;
    card chosenCard;
    CheckForTrick(current);
    while current.hand.length > 0 & continue {
        chosenPlayer = choosePlayer(current, Players);
        chosenCard = chooseCard(current.hand);
        continue = ChooseMove(chosenCard,chosenPlayer);
        CheckForTrick(current);
    }
    // Når man kommer ud af loopet har man endten ikke flere kort, eller man har fået fisk. I begge tilfælde trækker
    // man et kort fra bunken og turen går videre til den spiller man sidst har spurgt
    Message(current, chosenPlayer.name + " did not have any card of value "+ chosenCard.value +"! Go Fish!!\n");
    if Deck.length > 0{
        current.hand.add(Deck.take(1).index(0));
    }
    CheckForTrick(current);
    current = chosenPlayer;
}
EndCondition{
	// Spillet slutter hvis alle spillere har en hånd med 0 kort.
	MessageAll("The Score is:\n");
	for p in Players{
	    MessageAll(p.name + ": " + p.score + "\n");
	}
    bool end = true;
	for p in Players {
		if p.hand.length == 0 {
		    end &= true;
		}
		else {
		    end &= false;
		}
    }
    return end;
	// Vinderen(e) er de(n) spiller(e) med flest point
}

Function choosePlayer(player c, List typeof player l) typeof player{
    int i = 0;
    List typeof player n;
    for p in l {
        if p != c {
            n.add(p);
        }
    }
    MessageAll("Choose a player by number:\n");
    for p in n {
        MessageAll( i + ": " + p.name + "\n");
        i += 1;
    }
    return n.index(ParseInt(Read()));
}

Function chooseCard(List typeof card l) typeof card{
    int i = 0;
        MessageAll("Choose a card by number:\n");
        for c in l {
            MessageAll( i + ": " + c.suit + ", " + c.value + "\n");
            i += 1;
        }
        return l.index(ParseInt(Read()));
}


Function CheckForTrick(player p) typeof void{
    List typeof card trick;
    for int i = 1; i < 14 ; i += 1 {
        trick.clear();
        for c in p.hand {
            if i == c.value {
                trick.add(c);
            }
        }
        if trick.length == 4 {
            for c in trick {
                p.hand.remove(c);
            }
            p.score += 1;
            MessageAll(p.name + " got trick of " + trick.index(0).value);
        }
    }
}

Function InitPlayers() typeof List typeof player {
    List typeof player result = {};
    MessageAll("So who is playing today?");
    string input = "y";
    while input == "y" {
        MessageAll("Already in the game is:");
        for p in result {
            MessageAll(p.name);
        }
        string name = AskAll("Next players name: ");
        bool ai;

        string aiS = "";
        while aiS == ""{
            aiS = AskAll("Are they an ai (y/n)");
            if aiS == "y" {
                ai = true;
            }
            else if aiS == "n" {
                ai = false;
            }
            else {
                MessageAll("Thats not right");
                aiS = "";
            }
        }
        result.add(player(name,ai));
        input = AskAll("Are there any more players?(y/n)");
    }
    return result;
}

Function find(string name, List typeof player ps) typeof player{
    for p in ps {
        if(p.name == name)
        {
            return p;
        }
    }
    return null;
}
