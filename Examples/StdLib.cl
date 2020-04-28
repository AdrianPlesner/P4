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
    for int i = 0; i < jokers; i+=1 {
        result.add(card("joker",0));
    }
    return result;
}

Function chooseFrom(List typeof void l) typeof List typeof void {
    // print
    MessageAll("Please choose one of the following by typing the number:");
    for int i = 0; i < l.length; i+= 1 {
        MessageAll(l.index(i));
    }
    // input
    string s = Read();
    // return element
}

Function AskAll(string s) typeof string{
    MessageAll(s);
    return Read();
}

Function Ask(player p, string s) typeof string{
    Message(p,s);
    return Read();
}

Function ParseInt(string s) typeof int{

}
