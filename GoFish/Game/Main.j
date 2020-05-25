.class Game/Main
.super java/lang/Object
.field public static current LGame/player;
.field public static Deck LGame/List;
.field public static Players LGame/List;
.field public static f I
.field public static h I
.field public static continue Z
.field public static chosenPlayer LGame/player;
.field public static chosenCard LGame/card;

.method public <init>()V
	aload_0
	invokenonvirtual java/lang/Object/<init>()V
	return
.end method

.method public static main([Ljava/lang/String;)V
.limit stack 7
.limit locals 9
	aconst_null
	putstatic Game/Main/current LGame/player;
	ldc 0
	invokestatic Game/Main/GetStdDeck(I)LGame/List;
	invokestatic Game/Main/ShuffleDeck(LGame/List;)LGame/List;
	putstatic Game/Main/Deck LGame/List;
	invokestatic Game/Main/InitPlayers()LGame/List;
	putstatic Game/Main/Players LGame/List;
loop1:
	getstatic Game/Main/Players LGame/List;
	aconst_null
	if_acmpeq true2
	iconst_0
	goto done2
true2:
	iconst_1
done2:
	getstatic Game/Main/Players LGame/List;
	getfield Game/List/length I
	ldc 2
	if_icmplt true3
	iconst_0
	goto done3
true3:
	iconst_1
done3:
	iadd
	ifeq done1
	ldc "You need at least 2 players to play"
	invokestatic Game/Main/MessageAll(Ljava/lang/String;)V
	invokestatic Game/Main/InitPlayers()LGame/List;
	putstatic Game/Main/Players LGame/List;
	goto loop1
done1:
	getstatic Game/Main/Players LGame/List;
	ldc 0
	invokevirtual Game/List/index(I)Ljava/lang/Object;
	checkcast Game/player
	putstatic Game/Main/current LGame/player;
	iconst_0
loop4:
	dup
	getstatic Game/Main/Players LGame/List;
	dup2
	getfield Game/List/length I
	isub
	ifge done4
	swap
	invokevirtual Game/List/index(I)Ljava/lang/Object;
	checkcast Game/player
	astore 0
	; begin loop body
	aload 0
	getstatic Game/Main/Deck LGame/List;
	ldc 7
	invokevirtual Game/List/take(I)LGame/List;
	putfield Game/player/hand LGame/List;
	; end loop body
	iconst_1
	iadd
	goto loop4
done4:
	pop2
	pop
	ldc 5
	ldc 3
	ldc 8
	imul
	iadd
	putstatic Game/Main/f I
	ldc 78
	ldc 43
	irem
	ldc 43
	ldc 234
	ldc 43
	isub
	idiv
	iadd
	putstatic Game/Main/h I
Turn:
	getstatic Game/Main/current LGame/player;
	ldc "It is your turn!\n"
	invokestatic Game/Main/Message(LGame/player;Ljava/lang/String;)V
	iconst_1 
	putstatic Game/Main/continue Z
	aconst_null
	putstatic Game/Main/chosenPlayer LGame/player;
	aconst_null
	putstatic Game/Main/chosenCard LGame/card;
loop5:
	getstatic Game/Main/current LGame/player;
	getfield Game/player/hand LGame/List;
	getfield Game/List/length I
	ldc 0
	if_icmpgt true6
	iconst_0
	goto done6
true6:
	iconst_1
done6:
	getstatic Game/Main/continue Z
	iadd
	iconst_2
	if_icmpeq true7
	iconst_0
	goto done7
true7:
	iconst_1
done7:
	ifeq done5
	getstatic Game/Main/current LGame/player;
	getstatic Game/Main/Players LGame/List;
	invokestatic Game/Main/choosePlayer(LGame/player;LGame/List;)LGame/player;
	putstatic Game/Main/chosenPlayer LGame/player;
	getstatic Game/Main/current LGame/player;
	getfield Game/player/hand LGame/List;
	invokestatic Game/Main/chooseCard(LGame/List;)LGame/card;
	putstatic Game/Main/chosenCard LGame/card;
	getstatic Game/Main/chosenCard LGame/card;
	getstatic Game/Main/chosenPlayer LGame/player;
	invokestatic Game/Main/ChooseMove(LGame/card;LGame/player;)Z
	putstatic Game/Main/continue Z
	goto loop5
done5:
	getstatic Game/Main/current LGame/player;
	getstatic Game/Main/chosenPlayer LGame/player;
	getfield Game/player/name Ljava/lang/String;
	ldc " did not have any card of value "
	invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;
	getstatic Game/Main/chosenCard LGame/card;
	getfield Game/card/value I
	invokestatic java/lang/String/valueOf(I)Ljava/lang/String;
	invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;
	ldc "! Go Fish!!\n"
	invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;
	invokestatic Game/Main/Message(LGame/player;Ljava/lang/String;)V
	getstatic Game/Main/current LGame/player;
	getfield Game/player/hand LGame/List;
	getstatic Game/Main/Deck LGame/List;
	ldc 1
	invokevirtual Game/List/take(I)LGame/List;
	ldc 0
	invokevirtual Game/List/index(I)Ljava/lang/Object;
	checkcast Game/card
	invokevirtual Game/List/add(Ljava/lang/Object;)V
	getstatic Game/Main/chosenPlayer LGame/player;
	putstatic Game/Main/current LGame/player;
	invokestatic Game/Main/EndCondition()Z
	ifeq Turn
	 return
.end method

.method public static EndCondition()Z
.limit stack 7
.limit locals 3
	ldc "The Score is:\n"
	invokestatic Game/Main/MessageAll(Ljava/lang/String;)V
	iconst_0
loop8:
	dup
	getstatic Game/Main/Players LGame/List;
	dup2
	getfield Game/List/length I
	isub
	ifge done8
	swap
	invokevirtual Game/List/index(I)Ljava/lang/Object;
	checkcast Game/player
	astore 0
	; begin loop body
	aload 0
	getfield Game/player/name Ljava/lang/String;
	ldc ": "
	invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;
	aload 0
	getfield Game/player/score I
	invokestatic java/lang/String/valueOf(I)Ljava/lang/String;
	invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;
	ldc "\n"
	invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;
	invokestatic Game/Main/MessageAll(Ljava/lang/String;)V
	; end loop body
	iconst_1
	iadd
	goto loop8
done8:
	pop2
	pop
	iconst_1 
	istore 0
	iconst_0
loop9:
	dup
	getstatic Game/Main/Players LGame/List;
	dup2
	getfield Game/List/length I
	isub
	ifge done9
	swap
	invokevirtual Game/List/index(I)Ljava/lang/Object;
	checkcast Game/player
	astore 1
	; begin loop body
	; begin if
	aload 1
	getfield Game/player/hand LGame/List;
	getfield Game/List/length I
	ldc 0
	if_icmpeq true10
	iconst_0
	goto done10
true10:
	iconst_1
done10:
	ifgt true11
	; begin else
	iload 0
	iconst_0 
	iadd
	iconst_2
	isub
	ifeq true12
	iconst_0
	goto done12
true12:
	iconst_1
done12:
	istore 0
	; end else
	goto done11
true11:
	; begin if body
	iload 0
	iconst_1 
	iadd
	iconst_2
	isub
	ifeq true13
	iconst_0
	goto done13
true13:
	iconst_1
done13:
	istore 0
	; end if body
	goto done11
done11:
	; end if
	; end loop body
	iconst_1
	iadd
	goto loop9
done9:
	pop2
	pop
	iload 0
	ireturn
.end method

.method public static MessageAll(Ljava/lang/String;)V
.limit stack 2
.limit locals 1 
   getstatic java/lang/System/out Ljava/io/PrintStream;
   aload 0
   invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
   return
.end method

.method public static Read()Ljava/lang/String;
.limit stack 1
.limit locals 0
   invokestatic java/lang/System/console()Ljava/io/Console;
   invokevirtual java/io/Console/readLine()Ljava/lang/String;
   areturn
.end method

.method public static choosePlayer(LGame/player;LGame/List;)LGame/player;
.limit stack 7
.limit locals 7
	ldc 0
	istore 2
	new Game/List
	dup
	invokespecial Game/List/<init>()V
	astore 3
	iconst_0
loop1:
	dup
	aload 1
	dup2
	getfield Game/List/length I
	isub
	ifge done1
	swap
	invokevirtual Game/List/index(I)Ljava/lang/Object;
	checkcast Game/player
	astore 4
	; begin loop body
	; begin if
	aload 4
	aload 0
	if_acmpne true2
	iconst_0
	goto done2
true2:
	iconst_1
done2:
	ifgt true3
	goto done3
true3:
	; begin if body
	aload 3
	aload 4
	invokevirtual Game/List/add(Ljava/lang/Object;)V
	; end if body
	goto done3
done3:
	; end if
	; end loop body
	iconst_1
	iadd
	goto loop1
done1:
	pop2
	pop
	ldc "Choose a player by number:\n"
	invokestatic Game/Main/MessageAll(Ljava/lang/String;)V
	iconst_0
loop4:
	dup
	aload 3
	dup2
	getfield Game/List/length I
	isub
	ifge done4
	swap
	invokevirtual Game/List/index(I)Ljava/lang/Object;
	checkcast Game/player
	astore 4
	; begin loop body
	iload 2
	ldc ": "
	swap
	invokestatic java/lang/String/valueOf(I)Ljava/lang/String;
	swap
	invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;
	aload 4
	getfield Game/player/name Ljava/lang/String;
	invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;
	ldc "\n"
	invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;
	invokestatic Game/Main/MessageAll(Ljava/lang/String;)V
	iload 2
	ldc 1
	iadd
	istore 2
	; end loop body
	iconst_1
	iadd
	goto loop4
done4:
	pop2
	pop
	aload 3
	invokestatic Game/Main/Read()Ljava/lang/String;
	invokestatic Game/Main/ParseInt(Ljava/lang/String;)I
	invokevirtual Game/List/index(I)Ljava/lang/Object;
	checkcast Game/player
	areturn
.end method

.method public static chooseCard(LGame/List;)LGame/card;
.limit stack 9
.limit locals 4
	ldc 0
	istore 1
	ldc "Choose a card by number:\n"
	invokestatic Game/Main/MessageAll(Ljava/lang/String;)V
	iconst_0
loop5:
	dup
	aload 0
	dup2
	getfield Game/List/length I
	isub
	ifge done5
	swap
	invokevirtual Game/List/index(I)Ljava/lang/Object;
	checkcast Game/card
	astore 2
	; begin loop body
	iload 1
	ldc ": "
	swap
	invokestatic java/lang/String/valueOf(I)Ljava/lang/String;
	swap
	invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;
	aload 2
	getfield Game/card/suit Ljava/lang/String;
	invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;
	ldc ", "
	invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;
	aload 2
	getfield Game/card/value I
	invokestatic java/lang/String/valueOf(I)Ljava/lang/String;
	invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;
	ldc "\n"
	invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;
	invokestatic Game/Main/MessageAll(Ljava/lang/String;)V
	iload 1
	ldc 1
	iadd
	istore 1
	; end loop body
	iconst_1
	iadd
	goto loop5
done5:
	pop2
	pop
	aload 0
	invokestatic Game/Main/Read()Ljava/lang/String;
	invokestatic Game/Main/ParseInt(Ljava/lang/String;)I
	invokevirtual Game/List/index(I)Ljava/lang/Object;
	checkcast Game/card
	areturn
.end method

.method public static CheckForTrick(LGame/player;)V
.limit stack 6
.limit locals 6
	new Game/List
	dup
	invokespecial Game/List/<init>()V
	astore 1
	ldc 1
	istore 2
loop6:
	iload 2
	ldc 14
	if_icmplt true7
	iconst_0
	goto done7
true7:
	iconst_1
done7:
	ifeq done6
	aload 1
	invokevirtual Game/List/clear()V
	iconst_0
loop8:
	dup
	aload 0
	getfield Game/player/hand LGame/List;
	dup2
	getfield Game/List/length I
	isub
	ifge done8
	swap
	invokevirtual Game/List/index(I)Ljava/lang/Object;
	checkcast Game/card
	astore 3
	; begin loop body
	; begin if
	iload 2
	aload 3
	getfield Game/card/value I
	if_icmpeq true9
	iconst_0
	goto done9
true9:
	iconst_1
done9:
	ifgt true10
	goto done10
true10:
	; begin if body
	aload 1
	aload 3
	invokevirtual Game/List/add(Ljava/lang/Object;)V
	; end if body
	goto done10
done10:
	; end if
	; end loop body
	iconst_1
	iadd
	goto loop8
done8:
	pop2
	pop
	; begin if
	aload 1
	getfield Game/List/length I
	ldc 4
	if_icmpeq true11
	iconst_0
	goto done11
true11:
	iconst_1
done11:
	ifgt true12
	goto done12
true12:
	; begin if body
	iconst_0
loop13:
	dup
	aload 1
	dup2
	getfield Game/List/length I
	isub
	ifge done13
	swap
	invokevirtual Game/List/index(I)Ljava/lang/Object;
	checkcast Game/card
	astore 3
	; begin loop body
	aload 0
	getfield Game/player/hand LGame/List;
	aload 3
	invokevirtual Game/List/remove(Ljava/lang/Object;)V
	; end loop body
	iconst_1
	iadd
	goto loop13
done13:
	pop2
	pop
	aload 0
	getfield Game/player/score I
	ldc 1
	iadd
	aload 0
	swap
	putfield Game/player/score I
	; end if body
	goto done12
done12:
	; end if
	iload 2
	ldc 1
	iadd
	istore 2
	goto loop6
done6:
	return
.end method

.method public static InitPlayers()LGame/List;
.limit stack 6
.limit locals 7
	new Game/List
	dup
	invokespecial Game/List/<init>()V
	astore 0
	ldc "So who is playing today?"
	invokestatic Game/Main/MessageAll(Ljava/lang/String;)V
	ldc "y"
	astore 1
loop14:
	aload 1
	ldc "y"
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	ifeq done14
	ldc "Already in the game is:"
	invokestatic Game/Main/MessageAll(Ljava/lang/String;)V
	iconst_0
loop15:
	dup
	aload 0
	dup2
	getfield Game/List/length I
	isub
	ifge done15
	swap
	invokevirtual Game/List/index(I)Ljava/lang/Object;
	checkcast Game/player
	astore 2
	; begin loop body
	aload 2
	getfield Game/player/name Ljava/lang/String;
	invokestatic Game/Main/MessageAll(Ljava/lang/String;)V
	; end loop body
	iconst_1
	iadd
	goto loop15
done15:
	pop2
	pop
	ldc "Next players name: "
	invokestatic Game/Main/AskAll(Ljava/lang/String;)Ljava/lang/String;
	astore 2
	iconst_0
	istore 3
	ldc ""
	astore 4
loop16:
	aload 4
	ldc ""
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	ifeq done16
	ldc "Are they an ai (y/n)"
	invokestatic Game/Main/AskAll(Ljava/lang/String;)Ljava/lang/String;
	astore 4
	; begin if
	aload 4
	ldc "y"
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	ifgt true17
	aload 4
	ldc "n"
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	ifgt else18
	; begin else
	ldc "Thats not right"
	invokestatic Game/Main/MessageAll(Ljava/lang/String;)V
	ldc ""
	astore 4
	; end else
	goto done17
true17:
	; begin if body
	iconst_1 
	istore 3
	; end if body
	goto done17
	; begin else if body
 else18:
	iconst_0 
	istore 3
	; end else if body
	goto done17
done17:
	; end if
	goto loop16
done16:
	aload 0
	new Game/player
	dup
	aload 2
	iload 3
	invokespecial Game/player/<init>(Ljava/lang/String;Z)V
	invokevirtual Game/List/add(Ljava/lang/Object;)V
	ldc "Are there any more players?(y/n)"
	invokestatic Game/Main/AskAll(Ljava/lang/String;)Ljava/lang/String;
	astore 1
	goto loop14
done14:
	aload 0
	areturn
.end method

.method public static find(Ljava/lang/String;LGame/List;)LGame/player;
.limit stack 6
.limit locals 4
	iconst_0
loop19:
	dup
	aload 1
	dup2
	getfield Game/List/length I
	isub
	ifge done19
	swap
	invokevirtual Game/List/index(I)Ljava/lang/Object;
	checkcast Game/player
	astore 2
	; begin loop body
	; begin if
	aload 2
	getfield Game/player/name Ljava/lang/String;
	aload 0
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	ifgt true20
	goto done20
true20:
	; begin if body
	aload 2
	areturn
	; end if body
	goto done20
done20:
	; end if
	; end loop body
	iconst_1
	iadd
	goto loop19
done19:
	pop2
	pop
	aconst_null
	areturn
.end method

.method public static ChooseMove(LGame/card;LGame/player;)Z
.limit stack 10
.limit locals 5
	iconst_0 
	istore 2
	iconst_0
loop21:
	dup
	aload 1
	getfield Game/player/hand LGame/List;
	dup2
	getfield Game/List/length I
	isub
	ifge done21
	swap
	invokevirtual Game/List/index(I)Ljava/lang/Object;
	checkcast Game/card
	astore 3
	; begin loop body
	; begin if
	aload 3
	getfield Game/card/value I
	aload 0
	getfield Game/card/value I
	if_icmpeq true22
	iconst_0
	goto done22
true22:
	iconst_1
done22:
	ifgt true23
	goto done23
true23:
	; begin if body
	getstatic Game/Main/current LGame/player;
	aload 1
	getfield Game/player/name Ljava/lang/String;
	ldc " had "
	invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;
	aload 3
	getfield Game/card/suit Ljava/lang/String;
	invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;
	ldc ", "
	invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;
	aload 3
	getfield Game/card/value I
	invokestatic java/lang/String/valueOf(I)Ljava/lang/String;
	invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;
	ldc "\n"
	invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;
	invokestatic Game/Main/Message(LGame/player;Ljava/lang/String;)V
	aload 3
	aload 1
	getstatic Game/Main/current LGame/player;
	invokevirtual Game/card/transfer(LGame/player;LGame/player;)V
	iconst_1 
	istore 2
	; end if body
	goto done23
done23:
	; end if
	; end loop body
	iconst_1
	iadd
	goto loop21
done21:
	pop2
	pop
	getstatic Game/Main/current LGame/player;
	invokestatic Game/Main/CheckForTrick(LGame/player;)V
	; begin if
	aload 1
	getfield Game/player/hand LGame/List;
	getfield Game/List/length I
	ldc 0
	if_icmpeq true24
	iconst_0
	goto done24
true24:
	iconst_1
done24:
	ifgt true25
	goto done25
true25:
	; begin if body
	aload 1
	getstatic Game/Main/Deck LGame/List;
	ldc 1
	invokevirtual Game/List/take(I)LGame/List;
	putfield Game/player/hand LGame/List;
	; end if body
	goto done25
done25:
	; end if
	iload 2
	ireturn
.end method

.method public static GetStdDeck(I)LGame/List;
.limit stack 6
.limit locals 8
	new Game/List
	dup
	invokespecial Game/List/<init>()V
	dup
	ldc "hearts"
	invokevirtual Game/List/add(Ljava/lang/Object;)V
	dup
	ldc "diamonds"
	invokevirtual Game/List/add(Ljava/lang/Object;)V
	dup
	ldc "clubs"
	invokevirtual Game/List/add(Ljava/lang/Object;)V
	dup
	ldc "spades"
	invokevirtual Game/List/add(Ljava/lang/Object;)V
	astore 1
	new Game/List
	dup
	invokespecial Game/List/<init>()V
	astore 2
	ldc 1
	istore 3
loop26:
	iload 3
	ldc 14
	if_icmplt true27
	iconst_0
	goto done27
true27:
	iconst_1
done27:
	ifeq done26
	ldc 0
	istore 4
loop28:
	iload 4
	ldc 4
	if_icmplt true29
	iconst_0
	goto done29
true29:
	iconst_1
done29:
	ifeq done28
	new Game/card
	dup
	aload 1
	iload 4
	invokevirtual Game/List/index(I)Ljava/lang/Object;
	checkcast java/lang/String
	iload 3
	invokespecial Game/card/<init>(Ljava/lang/String;I)V
	astore 5
	aload 2
	aload 5
	invokevirtual Game/List/add(Ljava/lang/Object;)V
	iload 4
	ldc 1
	iadd
	istore 4
	goto loop28
done28:
	iload 3
	ldc 1
	iadd
	istore 3
	goto loop26
done26:
	ldc 0
	istore 3
loop30:
	iload 3
	iload 0
	if_icmplt true31
	iconst_0
	goto done31
true31:
	iconst_1
done31:
	ifeq done30
	aload 2
	new Game/card
	dup
	ldc "joker"
	ldc 0
	invokespecial Game/card/<init>(Ljava/lang/String;I)V
	invokevirtual Game/List/add(Ljava/lang/Object;)V
	iload 3
	ldc 1
	iadd
	istore 3
	goto loop30
done30:
	aload 2
	areturn
.end method

.method public static ShuffleDeck(LGame/List;)LGame/List;
.limit stack 8
.limit locals 4
	new Game/List
	dup
	invokespecial Game/List/<init>()V
	astore 1
loop32:
	aload 0
	getfield Game/List/length I
	ldc 0
	if_icmpgt true33
	iconst_0
	goto done33
true33:
	iconst_1
done33:
	ifeq done32
	aload 0
	ldc 0
	aload 0
	getfield Game/List/length I
	ldc 1
	isub
	invokestatic Game/Random/RandomIntRange(II)I
	invokevirtual Game/List/index(I)Ljava/lang/Object;
	checkcast Game/card
	astore 2
	aload 0
	aload 2
	invokevirtual Game/List/remove(Ljava/lang/Object;)V
	aload 1
	aload 2
	invokevirtual Game/List/add(Ljava/lang/Object;)V
	goto loop32
done32:
	aload 1
	areturn
.end method

.method public static AskAll(Ljava/lang/String;)Ljava/lang/String;
.limit stack 3
.limit locals 2
	aload 0
	invokestatic Game/Main/MessageAll(Ljava/lang/String;)V
	invokestatic Game/Main/Read()Ljava/lang/String;
	areturn
.end method

.method public static Ask(LGame/player;Ljava/lang/String;)Ljava/lang/String;
.limit stack 4
.limit locals 3
	aload 0
	aload 1
	invokestatic Game/Main/Message(LGame/player;Ljava/lang/String;)V
	invokestatic Game/Main/Read()Ljava/lang/String;
	areturn
.end method

.method public static ParseInt(Ljava/lang/String;)I
.limit stack 6
.limit locals 5
	ldc 0
	istore 1
	aload 0
	invokevirtual java/lang/String/length()I
	ldc 1
	isub
	istore 2
loop34:
	iload 2
	ldc 0
	if_icmpge true35
	iconst_0
	goto done35
true35:
	iconst_1
done35:
	ifeq done34
	aload 0
	invokevirtual java/lang/String/length()I
	ldc 1
	isub
	iload 2
	isub
	istore 3
	aload 0
	iload 2
	dup
	iconst_1
	iadd
	invokevirtual java/lang/String/substring(II)Ljava/lang/String;
	dup
	ldc "1"
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	iconst_1
	isub
	ifeq case36
	dup
	ldc "2"
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	iconst_1
	isub
	ifeq case37
	dup
	ldc "3"
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	iconst_1
	isub
	ifeq case38
	dup
	ldc "4"
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	iconst_1
	isub
	ifeq case39
	dup
	ldc "5"
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	iconst_1
	isub
	ifeq case40
	dup
	ldc "6"
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	iconst_1
	isub
	ifeq case41
	dup
	ldc "7"
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	iconst_1
	isub
	ifeq case42
	dup
	ldc "8"
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	iconst_1
	isub
	ifeq case43
	dup
	ldc "9"
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	iconst_1
	isub
	ifeq case44
	dup
	ldc "0"
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	iconst_1
	isub
	ifeq case45
	pop
	ldc 0
	ireturn
	goto done36
case36:
	pop
	iload 1
	ldc 1
	ldc 10
	iload 3
	invokestatic Game/Main/Power(II)I
	imul
	iadd
	istore 1
	goto done36
case37:
	pop
	iload 1
	ldc 2
	ldc 10
	iload 3
	invokestatic Game/Main/Power(II)I
	imul
	iadd
	istore 1
	goto done36
case38:
	pop
	iload 1
	ldc 3
	ldc 10
	iload 3
	invokestatic Game/Main/Power(II)I
	imul
	iadd
	istore 1
	goto done36
case39:
	pop
	iload 1
	ldc 4
	ldc 10
	iload 3
	invokestatic Game/Main/Power(II)I
	imul
	iadd
	istore 1
	goto done36
case40:
	pop
	iload 1
	ldc 5
	ldc 10
	iload 3
	invokestatic Game/Main/Power(II)I
	imul
	iadd
	istore 1
	goto done36
case41:
	pop
	iload 1
	ldc 6
	ldc 10
	iload 3
	invokestatic Game/Main/Power(II)I
	imul
	iadd
	istore 1
	goto done36
case42:
	pop
	iload 1
	ldc 7
	ldc 10
	iload 3
	invokestatic Game/Main/Power(II)I
	imul
	iadd
	istore 1
	goto done36
case43:
	pop
	iload 1
	ldc 8
	ldc 10
	iload 3
	invokestatic Game/Main/Power(II)I
	imul
	iadd
	istore 1
	goto done36
case44:
	pop
	iload 1
	ldc 9
	ldc 10
	iload 3
	invokestatic Game/Main/Power(II)I
	imul
	iadd
	istore 1
	goto done36
case45:
	pop
	iload 1
	ldc 0
	iadd
	istore 1
	goto done36
done36:
	iload 2
	ldc 1
	isub
	istore 2
	goto loop34
done34:
	iload 1
	ireturn
.end method

.method public static Power(II)I
.limit stack 3
.limit locals 6
	iconst_0
	istore 2
	; begin if
	iload 1
	ldc 0
	if_icmpeq true46
	iconst_0
	goto done46
true46:
	iconst_1
done46:
	ifgt true47
	iload 1
	ldc 0
	if_icmpgt true49
	iconst_0
	goto done49
true49:
	iconst_1
done49:
	ifgt else48
	; begin else
	ldc 1
	istore 2
	ldc 0
	istore 3
loop50:
	iload 3
	iload 1
	if_icmplt true51
	iconst_0
	goto done51
true51:
	iconst_1
done51:
	ifeq done50
	iload 2
	iload 0
	idiv
	istore 2
	iload 3
	ldc 1
	iadd
	istore 3
	goto loop50
done50:
	; end else
	goto done47
true47:
	; begin if body
	ldc 1
	ireturn
	; end if body
	goto done47
	; begin else if body
 else48:
	iload 0
	istore 2
	ldc 1
	istore 3
loop52:
	iload 3
	iload 1
	if_icmplt true53
	iconst_0
	goto done53
true53:
	iconst_1
done53:
	ifeq done52
	iload 2
	iload 0
	imul
	istore 2
	iload 3
	ldc 1
	iadd
	istore 3
	goto loop52
done52:
	; end else if body
	goto done47
done47:
	; end if
	iload 2
	ireturn
.end method

.method public static Message(LGame/player;Ljava/lang/String;)V
.limit stack 5
.limit locals 3
	aload 0
	getfield Game/player/name Ljava/lang/String;
	ldc ": "
	invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;
	aload 1
	invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;
	invokestatic Game/Main/MessageAll(Ljava/lang/String;)V
	return
.end method

