.class Main
.super java/lang/Object
.field public static current Lplayer;
.field public static Deck LList;
.field public static Players LList;
.field public static nums LList;
.field public static n I
.field public static continue Z
.field public static chosen Lplayer;

.method public <init>()V
	aload_0
	invokenonvirtual java/lang/Object/<init>()V
	return
.end method

.method public static main([Ljava/lang/String;)V
.limit stack 6
	new player
	ldc 0
	invokestatic Main/GetStdDeck(I)LList;
	putstatic Main/Deck LList;
	invokestatic Main/InitPlayers()LList;
	putstatic Main/Players LList;
loop1:
	getstatic Main/Players LList;
	aconst_null
	if_acmpeq true2
	iconst_0
	goto done2
true2:
	iconst_1
done2:
	getstatic Main/Players LList;
	getfield List/length I
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
	invokestatic Main/MessageAll(Ljava/lang/String;)V
	invokestatic Main/InitPlayers()LList;
	putstatic Main/Players LList;
	goto loop1
done1:
	new List
	dup
	invokespecial List/<init>()V
	dup
	ldc 1
	new java/lang/Integer
	dup_x1
	swap
	invokespecial java/lang/Integer/<init>(I)V
	invokevirtual List/add(Ljava/lang/Object;)V
	dup
	ldc 2
	new java/lang/Integer
	dup_x1
	swap
	invokespecial java/lang/Integer/<init>(I)V
	invokevirtual List/add(Ljava/lang/Object;)V
	dup
	ldc 3
	new java/lang/Integer
	dup_x1
	swap
	invokespecial java/lang/Integer/<init>(I)V
	invokevirtual List/add(Ljava/lang/Object;)V
	putstatic Main/nums LList;
	getstatic Main/nums LList;
	ldc 2
	invokevirtual List/index(I)Ljava/lang/Object;
	checkcast java/lang/Integer
	invokevirtual java/lang/Integer/intValue()I
	putstatic Main/n I
	ldc "Adrian"
	getstatic Main/Players LList;
	invokestatic Main/find(Ljava/lang/String;LList;)Lplayer;
	putstatic Main/current Lplayer;
	iconst_0
loop4:
	dup
	getstatic Main/Players LList;
	dup2
	getfield List/length I
	isub
	ifgt done4
	swap
	invokevirtual List/index(I)Ljava/lang/Object;
	checkcast player
	astore 0
	; begin loop body
	aload 0
	getstatic Main/Deck LList;
	ldc 7
	invokevirtual List/take(I)LList;
	putfield List/hand LList;
	; end loop body
	iconst_1
	iadd
	goto loop4
done4:
	pop2
	pop
Turn:
	iconst_1 
	putstatic Main/continue Z
	new player
loop5:
	getstatic Main/current Lplayer;
	getfield player/hand LList;
	getfield List/length I
	ldc 0
	if_icmpgt true6
	iconst_0
	goto done6
true6:
	iconst_1
done6:
	getstatic Main/continue Z
	iadd
	iconst_2
	if_icmpeq true7
	iconst_0
	goto done7
true7:
	iconst_1
done7:
	ifeq done5
	getstatic Main/Players LList;
	invokestatic Main/choosePlayer(LList;)Lplayer;
	putstatic Main/chosen Lplayer;
	getstatic Main/current Lplayer;
	getfield player/hand LList;
	invokestatic Main/chooseCard(LList;)Lcard;
	getstatic Main/chosen Lplayer;
	invokestatic Main/ChooseMove(Lcard;Lplayer;)Z
	putstatic Main/continue Z
	goto loop5
done5:
	getstatic Main/current Lplayer;
	getfield player/hand LList;
	getstatic Main/Deck LList;
	ldc 1
	invokevirtual List/take(I)LList;
	invokevirtual List/add(Ljava/lang/Object;)V
	getstatic Main/chosen Lplayer;
	putstatic Main/current Lplayer;
	invokestatic Main/EndConditon()Z
	ifeq Turn
	 return
.end method

.method public static EndCondition()Z
.limit stack 6
	iconst_1 
	istore 0
	iconst_0
loop8:
	dup
	getstatic Main/Players LList;
	dup2
	getfield List/length I
	isub
	ifgt done8
	swap
	invokevirtual List/index(I)Ljava/lang/Object;
	checkcast player
	astore 1
	; begin loop body
	; begin if
	aload 1
	getfield player/hand LList;
	getfield List/length I
	ldc 0
	if_icmpeq true9
	iconst_0
	goto done9
true9:
	iconst_1
done9:
	ifgt true10
	; begin else
	iload 0
	iconst_0 
	iadd
	iconst_2
	isub
	ifeq true11
	iconst_0
	goto done11
true11:
	iconst_1
done11:
	istore 0
	; end else
	goto done10
true10:
	; begin if body
	iload 0
	iconst_1 
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
	iload 0
	ireturn
.end method

.method public static MessageAll(Ljava/lang/String;)V
.limit stack 2
.limit locals 1 
   aload 0
   getstatic java/lang/System/out Ljava/io/PrintStream;
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

.method public static choosePlayer(LList;)Lplayer;
.limit stack 7
.limit locals 4
	ldc 0
	istore 1
	ldc "Choose a player by number:\n"
	invokestatic Main/MessageAll(Ljava/lang/String;)V
	iconst_0
loop2:
	dup
	aload 0
	dup2
	getfield List/length I
	isub
	ifgt done2
	swap
	invokevirtual List/index(I)Ljava/lang/Object;
	checkcast player
	astore 2
	; begin loop body
	iload 1
	ldc ": "
	swap
	invokestatic java/lang/String/valueOf(I)Ljava/lang/String;
	swap
	invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;
	aload 2
	getfield player/name Ljava/lang/String;
	invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;
	ldc "\n"
	invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;
	invokestatic Main/MessageAll(Ljava/lang/String;)V
	iload 1
	ldc 1
	iadd
	istore 1
	; end loop body
	iconst_1
	iadd
	goto loop2
done2:
	pop2
	pop
	aload 0
	invokestatic Main/Read()Ljava/lang/String;
	invokestatic Main/ParseInt(Ljava/lang/String;)I
	invokevirtual List/index(I)Ljava/lang/Object;
	checkcast player
	areturn
.end method

.method public static chooseCard(LList;)Lcard;
.limit stack 9
.limit locals 4
	ldc 0
	istore 1
	ldc "Choose a card by number:\n"
	invokestatic Main/MessageAll(Ljava/lang/String;)V
	iconst_0
loop3:
	dup
	aload 0
	dup2
	getfield List/length I
	isub
	ifgt done3
	swap
	invokevirtual List/index(I)Ljava/lang/Object;
	checkcast card
	astore 2
	; begin loop body
	iload 1
	ldc ": "
	swap
	invokestatic java/lang/String/valueOf(I)Ljava/lang/String;
	swap
	invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;
	aload 2
	getfield card/suit Ljava/lang/String;
	invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;
	ldc ", "
	invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;
	aload 2
	getfield card/value I
	invokestatic java/lang/String/valueOf(I)Ljava/lang/String;
	invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;
	ldc "\n"
	invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;
	invokestatic Main/MessageAll(Ljava/lang/String;)V
	iload 1
	ldc 1
	iadd
	istore 1
	; end loop body
	iconst_1
	iadd
	goto loop3
done3:
	pop2
	pop
	aload 0
	invokestatic Main/Read()Ljava/lang/String;
	invokestatic Main/ParseInt(Ljava/lang/String;)I
	invokevirtual List/index(I)Ljava/lang/Object;
	checkcast card
	areturn
.end method

.method public static CheckForTrick(Lplayer;)V
.limit stack 6
.limit locals 6
	new List
	dup
	invokespecial List/<init>()V
	astore 1
	ldc 1
	istore 2
loop4:
	iload 2
	ldc 14
	if_icmplt true5
	iconst_0
	goto done5
true5:
	iconst_1
done5:
	ifeq done4
	aload 1
	invokevirtual List/clear()V
	iconst_0
loop6:
	dup
	aload 0
	getfield player/hand LList;
	dup2
	getfield List/length I
	isub
	ifgt done6
	swap
	invokevirtual List/index(I)Ljava/lang/Object;
	checkcast card
	astore 3
	; begin loop body
	; begin if
	iload 2
	aload 3
	getfield card/value I
	if_icmpeq true7
	iconst_0
	goto done7
true7:
	iconst_1
done7:
	ifgt true8
	goto done8
true8:
	; begin if body
	aload 1
	aload 3
	invokevirtual List/add(Ljava/lang/Object;)V
	; end if body
	goto done8
done8:
	; end if
	; end loop body
	iconst_1
	iadd
	goto loop6
done6:
	pop2
	pop
	; begin if
	aload 1
	getfield List/length I
	ldc 4
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
	iconst_0
loop11:
	dup
	aload 1
	dup2
	getfield List/length I
	isub
	ifgt done11
	swap
	invokevirtual List/index(I)Ljava/lang/Object;
	checkcast card
	astore 3
	; begin loop body
	aload 0
	getfield player/hand LList;
	aload 3
	invokevirtual List/remove(Ljava/lang/Object;)V
	; end loop body
	iconst_1
	iadd
	goto loop11
done11:
	pop2
	pop
	aload 0
	getfield player/score I
	ldc 1
	iadd
	aload 0
	; end if body
	goto done10
done10:
	; end if
	iload 2
	ldc 1
	iadd
	istore 2
	goto loop4
done4:
	return
.end method

.method public static InitPlayers()LList;
.limit stack 6
.limit locals 7
	new List
	dup
	invokespecial List/<init>()V
	astore 0
	ldc "So who is playing today?"
	invokestatic Main/MessageAll(Ljava/lang/String;)V
	ldc "y"
	astore 1
loop12:
	aload 1
	ldc "y"
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	ifeq done12
	ldc "Already in the game is:"
	invokestatic Main/MessageAll(Ljava/lang/String;)V
	iconst_0
loop13:
	dup
	aload 0
	dup2
	getfield List/length I
	isub
	ifgt done13
	swap
	invokevirtual List/index(I)Ljava/lang/Object;
	checkcast player
	astore 2
	; begin loop body
	aload 2
	getfield player/name Ljava/lang/String;
	invokestatic Main/MessageAll(Ljava/lang/String;)V
	; end loop body
	iconst_1
	iadd
	goto loop13
done13:
	pop2
	pop
	ldc "Next players name: "
	invokestatic Main/AskAll(Ljava/lang/String;)Ljava/lang/String;
	astore 2
	ldc "Are they an ai (y/n)"
	invokestatic Main/AskAll(Ljava/lang/String;)Ljava/lang/String;
	astore 3
	iconst_0
	istore 4
	; begin if
	aload 3
	ldc "y"
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	ifgt true14
	aload 3
	ldc "n"
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	ifgt else15
	; begin else
	ldc "Thats not right"
	invokestatic Main/MessageAll(Ljava/lang/String;)V
	aconst_null
	areturn
	; end else
	goto done14
true14:
	; begin if body
	iconst_1 
	istore 4
	; end if body
	goto done14
	; begin else if body
 else15:
	iconst_0 
	istore 4
	; end else if body
	goto done14
done14:
	; end if
	aload 0
	new player
	dup
	aload 2
	iload 4
	invokespecial player/<init>(Ljava/lang/String;Z)V
	invokevirtual List/add(Ljava/lang/Object;)V
	ldc "Are there any more players?"
	invokestatic Main/AskAll(Ljava/lang/String;)Ljava/lang/String;
	astore 1
	goto loop12
done12:
	aload 0
	areturn
.end method

.method public static find(Ljava/lang/String;LList;)Lplayer;
.limit stack 6
.limit locals 4
	iconst_0
loop16:
	dup
	aload 1
	dup2
	getfield List/length I
	isub
	ifgt done16
	swap
	invokevirtual List/index(I)Ljava/lang/Object;
	checkcast player
	astore 2
	; begin loop body
	; begin if
	aload 2
	getfield player/name Ljava/lang/String;
	aload 0
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	ifgt true17
	goto done17
true17:
	; begin if body
	aload 2
	areturn
	; end if body
	goto done17
done17:
	; end if
	; end loop body
	iconst_1
	iadd
	goto loop16
done16:
	pop2
	pop
	aconst_null
	areturn
.end method

.method public static ChooseMove(Lcard;Lplayer;)Z
.limit stack 6
.limit locals 5
	iconst_0 
	istore 2
	iconst_0
loop18:
	dup
	aload 1
	getfield player/hand LList;
	dup2
	getfield List/length I
	isub
	ifgt done18
	swap
	invokevirtual List/index(I)Ljava/lang/Object;
	checkcast card
	astore 3
	; begin loop body
	; begin if
	aload 3
	getfield card/value I
	aload 0
	getfield card/value I
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
	aload 3
	aload 1
	getstatic Main/current Lplayer;
	invokevirtual card/transfer(Lplayer;Lplayer;)V
	iconst_1 
	istore 2
	; end if body
	goto done20
done20:
	; end if
	; end loop body
	iconst_1
	iadd
	goto loop18
done18:
	pop2
	pop
	aload 1
	invokestatic Main/CheckForTrick(Lplayer;)V
	; begin if
	aload 1
	getfield player/hand LList;
	getfield List/length I
	ldc 0
	if_icmpeq true21
	iconst_0
	goto done21
true21:
	iconst_1
done21:
	ifgt true22
	goto done22
true22:
	; begin if body
	aload 1
	getstatic Main/Deck LList;
	ldc 1
	invokevirtual List/take(I)LList;
	putfield player/hand LList;
	; end if body
	goto done22
done22:
	; end if
	iload 2
	ireturn
.end method

.method public static GetStdDeck(I)LList;
.limit stack 6
.limit locals 8
	new List
	dup
	invokespecial List/<init>()V
	dup
	ldc "hearts"
	invokevirtual List/add(Ljava/lang/Object;)V
	dup
	ldc "diamonds"
	invokevirtual List/add(Ljava/lang/Object;)V
	dup
	ldc "clubs"
	invokevirtual List/add(Ljava/lang/Object;)V
	dup
	ldc "spades"
	invokevirtual List/add(Ljava/lang/Object;)V
	astore 1
	new List
	dup
	invokespecial List/<init>()V
	astore 2
	ldc 0
	istore 3
loop23:
	iload 3
	ldc 13
	if_icmplt true24
	iconst_0
	goto done24
true24:
	iconst_1
done24:
	ifeq done23
	ldc 0
	istore 4
loop25:
	iload 4
	ldc 4
	if_icmplt true26
	iconst_0
	goto done26
true26:
	iconst_1
done26:
	ifeq done25
	new card
	dup
	aload 1
	iload 4
	invokevirtual List/index(I)Ljava/lang/Object;
	checkcast java/lang/String
	iload 3
	invokespecial card/<init>(Ljava/lang/String;I)V
	astore 5
	aload 2
	aload 5
	invokevirtual List/add(Ljava/lang/Object;)V
	iload 4
	ldc 1
	iadd
	istore 4
	goto loop25
done25:
	iload 3
	ldc 1
	iadd
	istore 3
	goto loop23
done23:
	ldc 0
	istore 3
loop27:
	iload 3
	iload 0
	if_icmplt true28
	iconst_0
	goto done28
true28:
	iconst_1
done28:
	ifeq done27
	aload 2
	new card
	dup
	ldc "joker"
	ldc 0
	invokespecial card/<init>(Ljava/lang/String;I)V
	invokevirtual List/add(Ljava/lang/Object;)V
	iload 3
	ldc 1
	iadd
	istore 3
	goto loop27
done27:
	aload 2
	areturn
.end method

.method public static AskAll(Ljava/lang/String;)Ljava/lang/String;
.limit stack 3
.limit locals 2
	aload 0
	invokestatic Main/MessageAll(Ljava/lang/String;)V
	invokestatic Main/Read()Ljava/lang/String;
	areturn
.end method

.method public static Ask(Lplayer;Ljava/lang/String;)Ljava/lang/String;
.limit stack 4
.limit locals 3
	aload 0
	aload 1
	invokestatic Main/Message(Lplayer;Ljava/lang/String;)V
	invokestatic Main/Read()Ljava/lang/String;
	areturn
.end method

.method public static ParseInt(Ljava/lang/String;)I
.limit stack 6
.limit locals 5
	ldc 0
	istore 1
	aload 0
	getfield java/lang/String/length I
	ldc 1
	isub
	istore 2
loop29:
	iload 2
	ldc 0
	if_icmpge true30
	iconst_0
	goto done30
true30:
	iconst_1
done30:
	ifeq done29
	aload 0
	getfield java/lang/String/length I
	ldc 1
	isub
	iload 2
	isub
	istore 3
	aload 0
	iload 2
	invokevirtual java/lang/String/index(I)Ljava/lang/String;
	dup
	ldc "1"
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	iconst_1
	isub
	ifeq case31
	dup
	ldc "2"
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	iconst_1
	isub
	ifeq case32
	dup
	ldc "3"
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	iconst_1
	isub
	ifeq case33
	dup
	ldc "4"
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	iconst_1
	isub
	ifeq case34
	dup
	ldc "5"
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	iconst_1
	isub
	ifeq case35
	dup
	ldc "6"
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	iconst_1
	isub
	ifeq case36
	dup
	ldc "7"
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	iconst_1
	isub
	ifeq case37
	dup
	ldc "8"
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	iconst_1
	isub
	ifeq case38
	dup
	ldc "9"
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	iconst_1
	isub
	ifeq case39
	dup
	ldc "0"
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	iconst_1
	isub
	ifeq case40
	pop
	ldc 0
	ireturn
	goto done31
case31:
	iload 1
	ldc 1
	ldc 10
	iload 3
	invokestatic Main/Power(II)I
	imul
	iadd
	istore 1
	goto done31
case32:
	iload 1
	ldc 2
	ldc 10
	iload 3
	invokestatic Main/Power(II)I
	imul
	iadd
	istore 1
	goto done31
case33:
	iload 1
	ldc 3
	ldc 10
	iload 3
	invokestatic Main/Power(II)I
	imul
	iadd
	istore 1
	goto done31
case34:
	iload 1
	ldc 4
	ldc 10
	iload 3
	invokestatic Main/Power(II)I
	imul
	iadd
	istore 1
	goto done31
case35:
	iload 1
	ldc 5
	ldc 10
	iload 3
	invokestatic Main/Power(II)I
	imul
	iadd
	istore 1
	goto done31
case36:
	iload 1
	ldc 6
	ldc 10
	iload 3
	invokestatic Main/Power(II)I
	imul
	iadd
	istore 1
	goto done31
case37:
	iload 1
	ldc 7
	ldc 10
	iload 3
	invokestatic Main/Power(II)I
	imul
	iadd
	istore 1
	goto done31
case38:
	iload 1
	ldc 8
	ldc 10
	iload 3
	invokestatic Main/Power(II)I
	imul
	iadd
	istore 1
	goto done31
case39:
	iload 1
	ldc 9
	ldc 10
	iload 3
	invokestatic Main/Power(II)I
	imul
	iadd
	istore 1
	goto done31
case40:
	iload 1
	ldc 0
	iadd
	istore 1
	goto done31
done31:
	iload 2
	ldc 1
	isub
	istore 2
	goto loop29
done29:
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
	if_icmpeq true41
	iconst_0
	goto done41
true41:
	iconst_1
done41:
	ifgt true42
	iload 1
	ldc 0
	if_icmpgt true44
	iconst_0
	goto done44
true44:
	iconst_1
done44:
	ifgt else43
	; begin else
	ldc 1
	istore 2
	ldc 0
	istore 3
loop45:
	iload 3
	iload 1
	if_icmplt true46
	iconst_0
	goto done46
true46:
	iconst_1
done46:
	ifeq done45
	iload 2
	getstatic Main/n I
	idiv
	istore 2
	iload 3
	ldc 1
	iadd
	istore 3
	goto loop45
done45:
	; end else
	goto done42
true42:
	; begin if body
	ldc 1
	ireturn
	; end if body
	goto done42
	; begin else if body
 else43:
	getstatic Main/n I
	istore 2
	ldc 1
	istore 3
loop47:
	iload 3
	iload 1
	if_icmplt true48
	iconst_0
	goto done48
true48:
	iconst_1
done48:
	ifeq done47
	iload 2
	getstatic Main/n I
	imul
	istore 2
	iload 3
	ldc 1
	iadd
	istore 3
	goto loop47
done47:
	; end else if body
	goto done42
done42:
	; end if
	iload 2
	ireturn
.end method

.method public static Message(Lplayer;Ljava/lang/String;)V
.limit stack 5
.limit locals 3
	aload 0
	getfield player/name Ljava/lang/String;
	ldc ": "
	invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;
	aload 1
	invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;
	invokestatic Main/MessageAll(Ljava/lang/String;)V
	return
.end method

