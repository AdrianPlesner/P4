include StdLib;
Setup{
    Card {
    }
    Player{
        // Alle spillere starter med 7 kort hver, fra bunken.
        string name;
        bool ai = false;
        List typeof card hand;
        int score = 0;
        Function player(string name, bool ai )typeof player{
            this.name = name;
            this.ai = ai;
            return this;
        }
    }
//public er “spillepladen” (tilgængelig for alle spillere)
    Game{
        // Der er en bunke i spillet, trækbunken til når man fisker
        List typeof card Deck = GetStdDeck(0);
        // Definer spillere, initaliserer en List<Player> Players
        List typeof player Players = {
            player("Adrian", false);
            player("Frederik", false);
            player("Johan", false);
            player("Lasse", false);
            player("Oliver", false);
            player("Rikke", false);
        };
        //Sæt start spiller
        turn.current = Players.find("Adrian");
        for p in Players {
            p.hand = Deck.take(7);
        }
        int a = Deck.index(0).value;
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
                pCard.transfer(p, turn.current);
                result = true;
            }
        }
        CheckForTrick(p.hand);
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
    while turn.current.hand.length > 0 & continue {
        chosen = chooseFrom(Players);
        continue = ChooseMove(chooseFrom(turn.current.hand),chosen);
    }
    // Når man kommer ud af loopet har man endten ikke flere kort, eller man har fået fisk. I begge tilfælde trækker
    // man et kort fra bunken og turen går videre til den spiller man sidst har spurgt
    turn.current.hand.add(Deck.take(1));
    turn.current = chosen;
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


