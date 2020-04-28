Setup{
    Card {
        string suit;
        int value;

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
    List typeof card result = {
        "Hearts", 1;
        "Hearts", 2;
    };
    return result;
}

Function chooseFrom(List typeof void l) typeof List typeof void {
    // print
    // input
    // return element
}