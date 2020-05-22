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
        Function whatevs() typeof void{
            List typeof int l = {1;2;3;};
            for i in l {
                i += 1;
            }
        }
    }
//public er “spillepladen” (tilgængelig for alle spillere)
    Game{
        player current;
        // Der er en bunke i spillet, trækbunken til når man fisker
        List typeof card Deck = GetStdDeck(0);
        // Definer spillere, initaliserer en List<Player> Players
        List typeof player Players = InitPlayers();
        while Players == null | Players.length < 2 {
            MessageAll("You need at least 2 players to play");
            Players = InitPlayers();
        }
        List typeof int nums = {1;2;3;};
        int n = nums.index(2);
        //Sæt start spiller
        current = find("Adrian", Players);
        for p in Players {
            p.hand = Deck.take(7);
        }
    }
}
// Mulige træk
Moves{
    // input = et kort ( fra egen hånd) og en anden spiller
    Function ChooseMove(card c, player p) typeof bool{
        // Tjek alle kort i den anden spiller hånd om de har samme værdi og overræk dem der er til den spiller hvis tur det er
        bool result = false;
        for pCard in p.hand {
            if pCard.value == c.value {
                pCard.transfer(p, current);
                result = true;
            }
        }
        CheckForTrick(p);
        //Hvis hånden på den spiller man trak fra nu er tom, trækker de et kort fra bunken.
        if(p.hand.length == 0){
            p.hand = Deck.take(1);
         }
         return result;
    }
}
// Definer en tur. Efter Setup vil denne kode loopes indtil endcondition
Turn{
    bool continue = true;
    player chosen;
    while current.hand.length > 0 & continue {
        chosen = choosePlayer(Players);
        continue = ChooseMove(chooseCard(current.hand),chosen);
    }
    // Når man kommer ud af loopet har man endten ikke flere kort, eller man har fået fisk. I begge tilfælde trækker
    // man et kort fra bunken og turen går videre til den spiller man sidst har spurgt
    current.hand.add(Deck.take(1));
    current = chosen;
}
EndCondition{
	// Spillet slutter hvis alle spillere har en hånd med 0 kort.
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

Function choosePlayer(List typeof player l) typeof player{
    int i = 0;
    MessageAll("Choose a player by number:\n");
    for p in l {
        MessageAll( i + ": " + p.name + "\n");
        i += 1;
    }
    return l.index(ParseInt(Read()));
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
        }
    }
}

Function InitPlayers() typeof List typeof player {
    List typeof player result;
    MessageAll("So who is playing today?");
    string input = "y";
    while(input == "y"){
        MessageAll("Already in the game is:");
        for p in result {
            MessageAll(p.name);
        }
        string name = AskAll("Next players name: ");
        string aiS = AskAll("Are they an ai (y/n)");
        bool ai;
        if aiS == "y" {
            ai = true;
        }
        else if aiS == "n" {
            ai = false;
        }
        else {
            MessageAll("Thats not right");
            return null;
        }
        result.add(player(name,ai));
        input = AskAll("Are there any more players?");
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
