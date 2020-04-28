Setup{
    Card {
        string suit;
        int value;

        Function card(string s, int i) typeof card{
            this.suit = s;
            this.value = i;
            return this;
        }

        Function transfer(player from, player to) typeof void{
            from.hand.remove(this);
            to.hand.add(this);
        }
    }
    Player{
    }
    Game{
    }
}
Moves{
}
Turn{
}
EndCondition{
}
Function GetStdDeck(int jokers) typeof List typeof card {
    List typeof string suits = {"hearts"; "diamonds"; "clubs"; "spades";};
    List typeof card result;
    for int i = 0; i < 13 ; i+=1 {
        for int j = 0; j < 4 ; j+=1 {
            card c = card(suits.index(j),i);
            result.add(c);
        }
    }
    return result;
}

Function chooseFrom(List typeof void l) typeof List typeof void {
    // print
    // input
    // return element
}