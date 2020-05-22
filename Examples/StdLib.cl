Setup{
    Card {
        string suit;
        int value;

        Construct card(string s, int i){
            this.suit = s;
            this.value = i;
        }
        Function transfer(player from, player to) typeof void{
            from.hand.remove(this);
            to.hand.add(this);
        }
    }
    Player{
        string name;
        List typeof card hand;
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

Function AskAll(string s) typeof string{
    MessageAll(s);
    return Read();
}

Function Ask(player p, string s) typeof string{
    Message(p,s);
    return Read();
}

Function ParseInt(string s) typeof int{
    int result = 0;
    for int i = s.length-1; i >= 0; i-=1 {
        int power = s.length - 1 - i;
        switch s.index(i) {
            case "1": {
                result += 1 * Power(10,power);
            }
            case "2": {
                result +=  2 * Power(10,power);
            }
            case "3": {
                result += 3 * Power(10,power);
            }
            case "4": {
                result += 4 * Power(10,power);
            }
            case "5": {
                result += 5 * Power(10,power);
            }
            case "6": {
                result += 6 * Power(10,power);
            }
            case "7": {
                result += 7 * Power(10,power);
            }
            case "8": {
                result += 8 * Power(10,power);
            }
            case "9": {
                result += 9 * Power(10,power);
            }
            case "0": {
                result += 0;
            }
            default: {
                return 0;
            }
        }
    }
    return result;
}

Function Power(int n, int power) typeof int{
    int result;

    if power == 0 {
        return 1;
    }
    else if power > 0{
        result = n;
        for int i = 1; i < power; i+=1 {
            result *= n;
        }
    }
    else{
        // power < 0
        result = 1;
        for int i = 0; i < power; i+=1 {
            result /= n;
        }
    }
    return result;
}

Function Message(player p, string s) typeof void{
    MessageAll(p.name + ": " + s);
}
