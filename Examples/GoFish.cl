Setup{
// Definer spillere, initaliserer en List<Player> Players
	List typeof Player Players = {
		"Adrian", false;
		"Frederik", false;
		"Johan", false;
		"Lasse", false;
		"Oliver", false;
		"Rikke", false;
    };

    //Sæt start spiller
    turn.player = Players.find("Adrian");

	//public er “spillepladen” (tilgængelig for alle spillere)
    Public{
        // Der er en bunke i spillet, trækbunken til når man fisker
        List typeof Card Deck = GetStdDeck(0);

    }
    // private er en players hånd.
    Private{
	    // Alle spillere starter med 7 kort hver, fra bunken.
        List typeof Card hand = deck.take(7);
        int score = 0;
    }
}
// Mulige træk
Moves{
    // input = et kort ( fra egen hånd) og en anden spiller
    bool ChooseMove(Card c, Player player){
        // Tjek alle kort i den anden spiller hånd om de har samme værdi og overræk dem der er til den spiller hvis tur det er
        bool result = false;
        for pCard in player.hand {
            if pCard.value == c.val {
                pCard.transfer(player, turn.player);
                result = true;
            }
        }
        // Tjekker for stik..(not implemented)
        CheckForTrick(player.hand);
        //Hvis hånden på den spiller man trak fra nu er tom, trækker de et kort fra bunken.
        if(player.hand.length == 0){
            player.hand = deck.take(1);
         }
         return result;
    }
}
// Definer en tur. Efter Setup vil denne kode loopes indtil endcondition
Turn{
    bool continue = true;
    Player chosen = null;
    while player.hand.length > 0 & continue {
        chosen = chooseFrom(players);
        continue = ChooseMove(chooseFrom(player.hand),chosen);
    }
    // Når man kommer ud af loopet har man endten ikke flere kort, eller man har fået fisk. I begge tilfælde trækker
    // man et kort fra bunken og turen går videre til den spiller man sidst har spurgt
    player.hand.add(deck.take(1));
    turn.player = chosen;
}
EndCondition{
	// Spillet slutter hvis alle spillere har en hånd med 0 kort.

	for p in Players {
		p.hand.length == (0);
    }
	// Vinderen(e) er de(n) spiller(e) med flest point
}

void CheckForTrick(Player p){
    List typeof Card trick;
    for int i = 1; i <= 13; i += 1 {
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


