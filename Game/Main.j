.class Game/Main
.super java/lang/Object
.field public static current LGame/player;
.field public static Deck LGame/List;
.field public static Pile LGame/List;
.field public static Players LGame/List;
.field public static playerindex I
.field public static knocked LGame/player;
.field public static lastTurn Z

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
	new Game/List
	dup
	invokespecial Game/List/<init>()V
	putstatic Game/Main/Pile LGame/List;
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
	ldc 3
	invokevirtual Game/List/take(I)LGame/List;
	putfield Game/player/hand LGame/List;
	; end loop body
	iconst_1
	iadd
	goto loop4
done4:
	pop2
	pop
	getstatic Game/Main/Deck LGame/List;
	ldc 1
	invokevirtual Game/List/take(I)LGame/List;
	putstatic Game/Main/Pile LGame/List;
	ldc 0
	putstatic Game/Main/playerindex I
	aconst_null
	putstatic Game/Main/knocked LGame/player;
	iconst_0 
	putstatic Game/Main/lastTurn Z
Turn:
	ldc "\n\n\n\n\n\n\n\n\n\n"
	invokestatic Game/Main/MessageAll(Ljava/lang/String;)V
	; begin if
	getstatic Game/Main/knocked LGame/player;
	aconst_null
	if_acmpne true5
	iconst_0
	goto done5
true5:
	iconst_1
done5:
	ifgt true6
	goto done6
true6:
	; begin if body
	getstatic Game/Main/knocked LGame/player;
	getfield Game/player/name Ljava/lang/String;
	ldc " has knocked!"
	invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;
	invokestatic Game/Main/MessageAll(Ljava/lang/String;)V
	; end if body
	goto done6
done6:
	; end if
	getstatic Game/Main/current LGame/player;
	getstatic Game/Main/knocked LGame/player;
	if_acmpeq true7
	iconst_0
	goto done7
true7:
	iconst_1
done7:
	putstatic Game/Main/lastTurn Z
	getstatic Game/Main/current LGame/player;
	ldc "It is your turn\n"
	invokestatic Game/Main/Message(LGame/player;Ljava/lang/String;)V
	getstatic Game/Main/current LGame/player;
	ldc "In your hand is :"
	invokestatic Game/Main/Message(LGame/player;Ljava/lang/String;)V
	iconst_0
loop8:
	dup
	getstatic Game/Main/current LGame/player;
	getfield Game/player/hand LGame/List;
	dup2
	getfield Game/List/length I
	isub
	ifge done8
	swap
	invokevirtual Game/List/index(I)Ljava/lang/Object;
	checkcast Game/card
	astore 0
	; begin loop body
	aload 0
	getfield Game/card/suit Ljava/lang/String;
	ldc ", "
	invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;
	aload 0
	getfield Game/card/value I
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
	invokestatic Game/Main/TakeCard()V
	invokestatic Game/Main/Discard()V
	; begin if
	getstatic Game/Main/knocked LGame/player;
	aconst_null
	if_acmpeq true9
	iconst_0
	goto done9
true9:
	iconst_1
done9:
	ifgt true10
	goto done10
true10:
	; begin if body
	invokestatic Game/Main/Knock()V
	; end if body
	goto done10
done10:
	; end if
	invokestatic Game/Main/EndCondition()Z
	ifeq Turn
	 return
.end method

.method public static EndCondition()Z
.limit stack 7
.limit locals 7
	iconst_0 
	istore 0
	aconst_null
	astore 1
	getstatic Game/Main/current LGame/player;
	getfield Game/player/hand LGame/List;
	invokestatic Game/Main/checkForTrick(LGame/List;)Z
	istore 0
	; begin if
	iload 0
	ifgt true11
	goto done11
true11:
	; begin if body
	getstatic Game/Main/current LGame/player;
	astore 1
	; end if body
	goto done11
done11:
	; end if
	; begin if
	getstatic Game/Main/lastTurn Z
	ifgt true12
	goto done12
true12:
	; begin if body
	iconst_1 
	istore 0
	new Game/List
	dup
	invokespecial Game/List/<init>()V
	astore 2
	iconst_0
loop13:
	dup
	getstatic Game/Main/Players LGame/List;
	dup2
	getfield Game/List/length I
	isub
	ifge done13
	swap
	invokevirtual Game/List/index(I)Ljava/lang/Object;
	checkcast Game/player
	astore 3
	; begin loop body
	aload 2
	aload 3
	getfield Game/player/hand LGame/List;
	invokestatic Game/Main/calcScore(LGame/List;)I
	new java/lang/Integer
	dup_x1
	swap
	invokespecial java/lang/Integer/<init>(I)V
	invokevirtual Game/List/add(Ljava/lang/Object;)V
	; end loop body
	iconst_1
	iadd
	goto loop13
done13:
	pop2
	pop
	ldc 0
	istore 3
	ldc 0
	ldc 1
	isub
	istore 4
	ldc 0
	istore 5
loop14:
	iload 5
	getstatic Game/Main/Players LGame/List;
	getfield Game/List/length I
	if_icmplt true15
	iconst_0
	goto done15
true15:
	iconst_1
done15:
	ifeq done14
	getstatic Game/Main/Players LGame/List;
	iload 5
	invokevirtual Game/List/index(I)Ljava/lang/Object;
	checkcast Game/player
	getfield Game/player/name Ljava/lang/String;
	ldc " had score: "
	invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;
	aload 2
	iload 5
	invokevirtual Game/List/index(I)Ljava/lang/Object;
	checkcast java/lang/Integer
	invokevirtual java/lang/Integer/intValue()I
	invokestatic java/lang/String/valueOf(I)Ljava/lang/String;
	invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;
	invokestatic Game/Main/MessageAll(Ljava/lang/String;)V
	; begin if
	aload 2
	iload 5
	invokevirtual Game/List/index(I)Ljava/lang/Object;
	checkcast java/lang/Integer
	invokevirtual java/lang/Integer/intValue()I
	iload 3
	if_icmpgt true16
	iconst_0
	goto done16
true16:
	iconst_1
done16:
	ifgt true17
	goto done17
true17:
	; begin if body
	aload 2
	iload 5
	invokevirtual Game/List/index(I)Ljava/lang/Object;
	checkcast java/lang/Integer
	invokevirtual java/lang/Integer/intValue()I
	istore 3
	iload 5
	istore 4
	; end if body
	goto done17
done17:
	; end if
	iload 5
	ldc 1
	iadd
	istore 5
	goto loop14
done14:
	getstatic Game/Main/Players LGame/List;
	iload 4
	invokevirtual Game/List/index(I)Ljava/lang/Object;
	checkcast Game/player
	astore 1
	; end if body
	goto done12
done12:
	; end if
	; begin if
	iload 0
	ifgt true18
	; begin else
	getstatic Game/Main/playerindex I
	ldc 1
	iadd
	putstatic Game/Main/playerindex I
	; begin if
	getstatic Game/Main/playerindex I
	getstatic Game/Main/Players LGame/List;
	getfield Game/List/length I
	if_icmpeq true19
	iconst_0
	goto done19
true19:
	iconst_1
done19:
	ifgt true20
	goto done20
true20:
	; begin if body
	ldc 0
	putstatic Game/Main/playerindex I
	; end if body
	goto done20
done20:
	; end if
	getstatic Game/Main/Players LGame/List;
	getstatic Game/Main/playerindex I
	invokevirtual Game/List/index(I)Ljava/lang/Object;
	checkcast Game/player
	putstatic Game/Main/current LGame/player;
	; end else
	goto done18
true18:
	; begin if body
	ldc "The Winner is "
	aload 1
	getfield Game/player/name Ljava/lang/String;
	invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;
	invokestatic Game/Main/MessageAll(Ljava/lang/String;)V
	; end if body
	goto done18
done18:
	; end if
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
loop1:
	aload 1
	ldc "y"
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	ifeq done1
	ldc "Already in the game is:"
	invokestatic Game/Main/MessageAll(Ljava/lang/String;)V
	iconst_0
loop2:
	dup
	aload 0
	dup2
	getfield Game/List/length I
	isub
	ifge done2
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
	goto loop2
done2:
	pop2
	pop
	ldc "Next players name: "
	invokestatic Game/Main/AskAll(Ljava/lang/String;)Ljava/lang/String;
	astore 2
	iconst_0
	istore 3
	ldc ""
	astore 4
loop3:
	aload 4
	ldc ""
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	ifeq done3
	ldc "Are they an ai (y/n)"
	invokestatic Game/Main/AskAll(Ljava/lang/String;)Ljava/lang/String;
	astore 4
	; begin if
	aload 4
	ldc "y"
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	ifgt true4
	aload 4
	ldc "n"
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	ifgt else5
	; begin else
	ldc "Thats not right"
	invokestatic Game/Main/MessageAll(Ljava/lang/String;)V
	ldc ""
	astore 4
	; end else
	goto done4
true4:
	; begin if body
	iconst_1 
	istore 3
	; end if body
	goto done4
	; begin else if body
 else5:
	iconst_0 
	istore 3
	; end else if body
	goto done4
done4:
	; end if
	goto loop3
done3:
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
	goto loop1
done1:
	aload 0
	areturn
.end method

.method public static checkForTrick(LGame/List;)Z
.limit stack 4
.limit locals 3
	aload 0
	invokestatic Game/Main/calcScore(LGame/List;)I
	istore 1
	iload 1
	ldc 31
	if_icmpeq true6
	iconst_0
	goto done6
true6:
	iconst_1
done6:
	ireturn
.end method

.method public static calcScore(LGame/List;)I
.limit stack 6
.limit locals 10
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
	dup
	ldc 0
	new java/lang/Integer
	dup_x1
	swap
	invokespecial java/lang/Integer/<init>(I)V
	invokevirtual Game/List/add(Ljava/lang/Object;)V
	dup
	ldc 0
	new java/lang/Integer
	dup_x1
	swap
	invokespecial java/lang/Integer/<init>(I)V
	invokevirtual Game/List/add(Ljava/lang/Object;)V
	dup
	ldc 0
	new java/lang/Integer
	dup_x1
	swap
	invokespecial java/lang/Integer/<init>(I)V
	invokevirtual Game/List/add(Ljava/lang/Object;)V
	dup
	ldc 0
	new java/lang/Integer
	dup_x1
	swap
	invokespecial java/lang/Integer/<init>(I)V
	invokevirtual Game/List/add(Ljava/lang/Object;)V
	astore 2
	iconst_0
loop7:
	dup
	aload 0
	dup2
	getfield Game/List/length I
	isub
	ifge done7
	swap
	invokevirtual Game/List/index(I)Ljava/lang/Object;
	checkcast Game/card
	astore 3
	; begin loop body
	aload 3
	getfield Game/card/suit Ljava/lang/String;
	aload 1
	invokestatic Game/Main/find(Ljava/lang/String;LGame/List;)I
	istore 4
	; begin if
	iload 4
	ldc 0
	if_icmpge true8
	iconst_0
	goto done8
true8:
	iconst_1
done8:
	ifgt true9
	goto done9
true9:
	; begin if body
	aload 3
	getfield Game/card/value I
	istore 5
	aload 2
	iload 4
	invokevirtual Game/List/index(I)Ljava/lang/Object;
	checkcast java/lang/Integer
	invokevirtual java/lang/Integer/intValue()I
	istore 6
	; begin if
	iload 5
	ldc 1
	if_icmpeq true10
	iconst_0
	goto done10
true10:
	iconst_1
done10:
	ifgt true11
	iload 5
	ldc 10
	if_icmpgt true13
	iconst_0
	goto done13
true13:
	iconst_1
done13:
	ifgt else12
	; begin else
	iload 6
	iload 5
	iadd
	istore 6
	; end else
	goto done11
true11:
	; begin if body
	iload 6
	ldc 11
	iadd
	istore 6
	; end if body
	goto done11
	; begin else if body
 else12:
	iload 6
	ldc 10
	iadd
	istore 6
	; end else if body
	goto done11
done11:
	; end if
	aload 2
	iload 4
	iload 6
	new java/lang/Integer
	dup_x1
	swap
	invokespecial java/lang/Integer/<init>(I)V
	invokevirtual Game/List/set(ILjava/lang/Object;)V
	; end if body
	goto done9
done9:
	; end if
	; end loop body
	iconst_1
	iadd
	goto loop7
done7:
	pop2
	pop
	ldc 0
	istore 3
	iconst_0
loop14:
	dup
	aload 2
	dup2
	getfield Game/List/length I
	isub
	ifge done14
	swap
	invokevirtual Game/List/index(I)Ljava/lang/Object;
	checkcast java/lang/Integer
	invokevirtual java/lang/Integer/intValue()I
	istore 4
	; begin loop body
	; begin if
	iload 4
	iload 3
	if_icmpgt true15
	iconst_0
	goto done15
true15:
	iconst_1
done15:
	ifgt true16
	goto done16
true16:
	; begin if body
	iload 4
	istore 3
	; end if body
	goto done16
done16:
	; end if
	; end loop body
	iconst_1
	iadd
	goto loop14
done14:
	pop2
	pop
	iload 3
	ireturn
.end method

.method public static find(Ljava/lang/String;LGame/List;)I
.limit stack 4
.limit locals 5
	ldc 0
	ldc 1
	isub
	istore 2
	ldc 0
	istore 3
loop17:
	iload 3
	aload 1
	getfield Game/List/length I
	if_icmplt true18
	iconst_0
	goto done18
true18:
	iconst_1
done18:
	ifeq done17
	; begin if
	aload 1
	iload 3
	invokevirtual Game/List/index(I)Ljava/lang/Object;
	checkcast java/lang/String
	aload 0
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	ifgt true19
	goto done19
true19:
	; begin if body
	iload 3
	istore 2
	; end if body
	goto done19
done19:
	; end if
	iload 3
	ldc 1
	iadd
	istore 3
	goto loop17
done17:
	iload 2
	ireturn
.end method

.method public static TakeCard()V
.limit stack 10
.limit locals 4
	ldc ""
	astore 0
	aconst_null
	astore 1
	iconst_1 
	istore 2
loop20:
	iload 2
	ifeq done20
	getstatic Game/Main/Pile LGame/List;
	getstatic Game/Main/Pile LGame/List;
	getfield Game/List/length I
	ldc 1
	isub
	invokevirtual Game/List/index(I)Ljava/lang/Object;
	checkcast Game/card
	astore 1
	getstatic Game/Main/current LGame/player;
	ldc "On top of the pile is: "
	aload 1
	getfield Game/card/suit Ljava/lang/String;
	invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;
	ldc ", "
	invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;
	aload 1
	getfield Game/card/value I
	invokestatic java/lang/String/valueOf(I)Ljava/lang/String;
	invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;
	ldc "\n"
	invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;
	ldc "Draw from pile(p) or draw from deck(d) ?"
	invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;
	invokestatic Game/Main/Ask(LGame/player;Ljava/lang/String;)Ljava/lang/String;
	astore 0
	; begin if
	aload 0
	ldc "p"
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	aload 0
	ldc "d"
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	iadd
	ifgt true21
	goto done21
true21:
	; begin if body
	iconst_0 
	istore 2
	; end if body
	goto done21
done21:
	; end if
	goto loop20
done20:
	; begin if
	aload 0
	ldc "p"
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	ifgt true22
	aload 0
	ldc "d"
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	ifgt else23
	; begin else
	ldc "Something went wrong"
	invokestatic Game/Main/MessageAll(Ljava/lang/String;)V
	; end else
	goto done22
true22:
	; begin if body
	getstatic Game/Main/current LGame/player;
	getfield Game/player/hand LGame/List;
	aload 1
	invokevirtual Game/List/add(Ljava/lang/Object;)V
	getstatic Game/Main/Pile LGame/List;
	aload 1
	invokevirtual Game/List/remove(Ljava/lang/Object;)V
	; end if body
	goto done22
	; begin else if body
 else23:
	getstatic Game/Main/current LGame/player;
	getfield Game/player/hand LGame/List;
	getstatic Game/Main/Deck LGame/List;
	ldc 1
	invokevirtual Game/List/take(I)LGame/List;
	ldc 0
	invokevirtual Game/List/index(I)Ljava/lang/Object;
	checkcast Game/card
	invokevirtual Game/List/add(Ljava/lang/Object;)V
	; end else if body
	goto done22
done22:
	; end if
	return
.end method

.method public static Knock()V
.limit stack 5
.limit locals 2
	ldc " "
	astore 0
loop24:
	aload 0
	ldc " "
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	ifeq done24
	getstatic Game/Main/current LGame/player;
	ldc "Do you wish to knock?(y/n)"
	invokestatic Game/Main/Ask(LGame/player;Ljava/lang/String;)Ljava/lang/String;
	astore 0
	; begin if
	aload 0
	ldc "y"
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	ifgt true25
	aload 0
	ldc "n"
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	iconst_1
	isub
	dup
	ifeq done27
	ineg
done27:
	ifgt else26
	goto done25
true25:
	; begin if body
	getstatic Game/Main/current LGame/player;
	putstatic Game/Main/knocked LGame/player;
	; end if body
	goto done25
	; begin else if body
 else26:
	ldc " "
	astore 0
	; end else if body
	goto done25
done25:
	; end if
	goto loop24
done24:
	return
.end method

.method public static Discard()V
.limit stack 8
.limit locals 7
	iconst_1 
	istore 0
	ldc 0
	istore 1
loop28:
	iload 0
	ifeq done28
	ldc "Choose a card to discard by number:\n"
	invokestatic Game/Main/MessageAll(Ljava/lang/String;)V
	ldc 0
	istore 2
loop29:
	iload 2
	getstatic Game/Main/current LGame/player;
	getfield Game/player/hand LGame/List;
	getfield Game/List/length I
	if_icmplt true30
	iconst_0
	goto done30
true30:
	iconst_1
done30:
	ifeq done29
	getstatic Game/Main/current LGame/player;
	getfield Game/player/hand LGame/List;
	iload 2
	invokevirtual Game/List/index(I)Ljava/lang/Object;
	checkcast Game/card
	astore 3
	iload 2
	ldc ": "
	swap
	invokestatic java/lang/String/valueOf(I)Ljava/lang/String;
	swap
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
	invokestatic Game/Main/MessageAll(Ljava/lang/String;)V
	iload 2
	ldc 1
	iadd
	istore 2
	goto loop29
done29:
	invokestatic Game/Main/Read()Ljava/lang/String;
	astore 2
	aload 2
	invokestatic Game/Main/ParseInt(Ljava/lang/String;)I
	istore 1
	; begin if
	iload 1
	ldc 0
	if_icmplt true31
	iconst_0
	goto done31
true31:
	iconst_1
done31:
	iload 1
	ldc 3
	if_icmpgt true32
	iconst_0
	goto done32
true32:
	iconst_1
done32:
	iadd
	ifgt true33
	; begin else
	iconst_0 
	istore 0
	; end else
	goto done33
true33:
	; begin if body
	ldc "Invalid card number, try again"
	invokestatic Game/Main/MessageAll(Ljava/lang/String;)V
	iconst_1 
	istore 0
	; end if body
	goto done33
done33:
	; end if
	goto loop28
done28:
	getstatic Game/Main/current LGame/player;
	getfield Game/player/hand LGame/List;
	iload 1
	invokevirtual Game/List/index(I)Ljava/lang/Object;
	checkcast Game/card
	astore 2
	getstatic Game/Main/current LGame/player;
	getfield Game/player/hand LGame/List;
	aload 2
	invokevirtual Game/List/remove(Ljava/lang/Object;)V
	getstatic Game/Main/Pile LGame/List;
	aload 2
	invokevirtual Game/List/add(Ljava/lang/Object;)V
	return
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
loop34:
	iload 3
	ldc 14
	if_icmplt true35
	iconst_0
	goto done35
true35:
	iconst_1
done35:
	ifeq done34
	ldc 0
	istore 4
loop36:
	iload 4
	ldc 4
	if_icmplt true37
	iconst_0
	goto done37
true37:
	iconst_1
done37:
	ifeq done36
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
	goto loop36
done36:
	iload 3
	ldc 1
	iadd
	istore 3
	goto loop34
done34:
	ldc 0
	istore 3
loop38:
	iload 3
	iload 0
	if_icmplt true39
	iconst_0
	goto done39
true39:
	iconst_1
done39:
	ifeq done38
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
	goto loop38
done38:
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
loop40:
	aload 0
	getfield Game/List/length I
	ldc 0
	if_icmpgt true41
	iconst_0
	goto done41
true41:
	iconst_1
done41:
	ifeq done40
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
	goto loop40
done40:
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
loop42:
	iload 2
	ldc 0
	if_icmpge true43
	iconst_0
	goto done43
true43:
	iconst_1
done43:
	ifeq done42
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
	ifeq case44
	dup
	ldc "2"
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	iconst_1
	isub
	ifeq case45
	dup
	ldc "3"
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	iconst_1
	isub
	ifeq case46
	dup
	ldc "4"
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	iconst_1
	isub
	ifeq case47
	dup
	ldc "5"
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	iconst_1
	isub
	ifeq case48
	dup
	ldc "6"
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	iconst_1
	isub
	ifeq case49
	dup
	ldc "7"
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	iconst_1
	isub
	ifeq case50
	dup
	ldc "8"
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	iconst_1
	isub
	ifeq case51
	dup
	ldc "9"
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	iconst_1
	isub
	ifeq case52
	dup
	ldc "0"
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	iconst_1
	isub
	ifeq case53
	pop
	ldc 0
	ireturn
	goto done44
case44:
	pop
	iload 1
	ldc 1
	ldc 10
	iload 3
	invokestatic Game/Main/Power(II)I
	imul
	iadd
	istore 1
	goto done44
case45:
	pop
	iload 1
	ldc 2
	ldc 10
	iload 3
	invokestatic Game/Main/Power(II)I
	imul
	iadd
	istore 1
	goto done44
case46:
	pop
	iload 1
	ldc 3
	ldc 10
	iload 3
	invokestatic Game/Main/Power(II)I
	imul
	iadd
	istore 1
	goto done44
case47:
	pop
	iload 1
	ldc 4
	ldc 10
	iload 3
	invokestatic Game/Main/Power(II)I
	imul
	iadd
	istore 1
	goto done44
case48:
	pop
	iload 1
	ldc 5
	ldc 10
	iload 3
	invokestatic Game/Main/Power(II)I
	imul
	iadd
	istore 1
	goto done44
case49:
	pop
	iload 1
	ldc 6
	ldc 10
	iload 3
	invokestatic Game/Main/Power(II)I
	imul
	iadd
	istore 1
	goto done44
case50:
	pop
	iload 1
	ldc 7
	ldc 10
	iload 3
	invokestatic Game/Main/Power(II)I
	imul
	iadd
	istore 1
	goto done44
case51:
	pop
	iload 1
	ldc 8
	ldc 10
	iload 3
	invokestatic Game/Main/Power(II)I
	imul
	iadd
	istore 1
	goto done44
case52:
	pop
	iload 1
	ldc 9
	ldc 10
	iload 3
	invokestatic Game/Main/Power(II)I
	imul
	iadd
	istore 1
	goto done44
case53:
	pop
	iload 1
	ldc 0
	iadd
	istore 1
	goto done44
done44:
	iload 2
	ldc 1
	isub
	istore 2
	goto loop42
done42:
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
	if_icmpeq true54
	iconst_0
	goto done54
true54:
	iconst_1
done54:
	ifgt true55
	iload 1
	ldc 0
	if_icmpgt true57
	iconst_0
	goto done57
true57:
	iconst_1
done57:
	ifgt else56
	; begin else
	ldc 1
	istore 2
	ldc 0
	istore 3
loop58:
	iload 3
	iload 1
	if_icmplt true59
	iconst_0
	goto done59
true59:
	iconst_1
done59:
	ifeq done58
	iload 2
	iload 0
	idiv
	istore 2
	iload 3
	ldc 1
	iadd
	istore 3
	goto loop58
done58:
	; end else
	goto done55
true55:
	; begin if body
	ldc 1
	ireturn
	; end if body
	goto done55
	; begin else if body
 else56:
	iload 0
	istore 2
	ldc 1
	istore 3
loop60:
	iload 3
	iload 1
	if_icmplt true61
	iconst_0
	goto done61
true61:
	iconst_1
done61:
	ifeq done60
	iload 2
	iload 0
	imul
	istore 2
	iload 3
	ldc 1
	iadd
	istore 3
	goto loop60
done60:
	; end else if body
	goto done55
done55:
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

