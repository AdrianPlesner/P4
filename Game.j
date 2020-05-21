.class Game
.super java/lang/Object
.field public static current Lplayer;
.field public static Deck LList;
.field public static Players LList;
.field public static nums LList;
.field public static n I

.method public <init>()V
	aload_0
	invokenonvirtual java/lang/Object/<init>()V
	return
.end method

.method public static main([Ljava/lang/String)V
	new player
	ldc 0
	invokestatic Game/GetStdDeck(I)LList;
	putstatic Game/Deck LList;
	invokestatic Game/InitPlayers()LList;
	putstatic Game/Players LList;
loop1:
	getstatic Game/Players LList;
	aconst_null
	if_acmpeq true2
	iconst_0
	goto done2
true2:
	iconst_1
done2:
	getstatic Game/Players LList;
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
	invokestatic Game/MessageAll(Ljava/lang/String;)V
	invokestatic Game/InitPlayers()LList;
	putstatic Game/Players LList;
	goto 1
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
	putstatic Game/nums LList;
	getstatic Game/nums LList;
	ldc 2
	invokevirtual List/index(I)Ljava/lang/Object;
	putstatic Game/n I
	ldc "Adrian"
	getstatic Game/Players LList;
	invokestatic Game/find(Ljava/lang/String;LList;)Lplayer;
	putstatic Game/current Lplayer;
	iconst_0
loop4:
	dup
	getstatic Game/Players LList;
	dup2
	getfield List/length I
	isub
	ifgt done4
	swap
	invokevirtual List/index(I)Ljava/lang/Object;
	checkcast player
	astore0
	aload 0
	getstatic Game/Deck LList;
	ldc 7
	invokevirtual List/take(I)LList;
	putfield List/hand LList;
	iconst_1
	iadd
	goto loop4
done4:
.end method

.method public static CheckForTrick(Lplayer;)V
.limit stack 3
.limit locals 5
	new List
	dup
	invokespecial List/<init>()V
	astore 1
	ldc 1
	istore 2
loop1:
	aload 2
	ldc 14
	if_icmplt true2
	iconst_0
	goto done2
true2:
	iconst_1
done2:
	ifeq done1
	aload 1
	invokevirtual List/clear()V
	iconst_0
loop3:
	dup
	aload 0
	getfield player/hand LList;
	dup2
	getfield List/length I
	isub
	ifgt done3
	swap
	invokevirtual List/index(I)Ljava/lang/Object;
	checkcast card
	astore3
	aload 2
	aload 3
	getfield List/value I
	if_icmpeq true4
	iconst_0
	goto done4
true4:
	iconst_1
done4:
	ifgt true5
	goto done5
true5:
	aload 1
	aload 3
	invokevirtual List/add(Lcard;)V
	goto done5
done5:
	iconst_1
	iadd
	goto loop3
done3:
	aload 1
	getfield List/length I
	ldc 4
	if_icmpeq true6
	iconst_0
	goto done6
true6:
	iconst_1
done6:
	ifgt true7
	goto done7
true7:
	iconst_0
loop8:
	dup
	aload 1
	dup2
	getfield List/length I
	isub
	ifgt done8
	swap
	invokevirtual List/index(I)Ljava/lang/Object;
	checkcast card
	astore3
	aload 0
	getfield player/hand LList;
	aload 3
	invokevirtual List/remove(Lcard;)V
	iconst_1
	iadd
	goto loop8
done8:
	aload 0
	getfield player/score I
	ldc 1
	iadd
	aload 0
	ldc 1
	putfield player/score I
	goto done7
done7:
	aload 2
	ldc 1
	iadd
	ldc 1
	istore 2
	goto loop1
done1:
	return
.end method

.method public static InitPlayers()LList;
.limit stack 6
.limit locals 6
	new List
	dup
	invokespecial List/<init>()V
	astore 0
	ldc "So who is playing today?"
	invokestatic Game/MessageAll(Ljava/lang/String;)V
	ldc "y"
	astore 1
loop9:
	aload 1
	ldc "y"
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	ifeq done9
	ldc "Already in the game is:"
	invokestatic Game/MessageAll(Ljava/lang/String;)V
	iconst_0
loop10:
	dup
	aload 0
	dup2
	getfield List/length I
	isub
	ifgt done10
	swap
	invokevirtual List/index(I)Ljava/lang/Object;
	checkcast player
	astore2
	aload 2
	aload 2
	getfield List/ai Z
	invokestatic Game/Message(Lplayer;Ljava/lang/String;)V
	iconst_1
	iadd
	goto loop10
done10:
	ldc "Next players name: "
	invokestatic Game/AskAll(Ljava/lang/String;)Ljava/lang/String;
	astore 2
	ldc "Are they an ai (y/n)"
	invokestatic Game/AskAll(Ljava/lang/String;)Ljava/lang/String;
	astore 3
	iconst_0
	istore 4
	aload 3
	ldc "y"
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	ifgt true11
	aload 3
	ldc "n"
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	ifgt else12
	ldc "Thats not right"
	invokestatic Game/MessageAll(Ljava/lang/String;)V
	aconst_null
	areturn
	goto done11
true11:
	iconst_1 
	istore 4
	goto done11
else12:
	iconst_0 
	istore 4
	goto done11
done11:
	aload 0
	new player
	dup
	aload 2
	aload 4
	invokespecial player/<init(Ljava/lang/String;Z)V
	invokevirtual List/add(Lplayer;)V
	ldc "Are there any more players?"
	invokestatic Game/AskAll(Ljava/lang/String;)Ljava/lang/String;
	astore 1
	goto 9
done9:
	aload 0
	areturn
.end method

.method public static find(Ljava/lang/String;LList;)Lplayer;
.limit stack 2
.limit locals 3
	iconst_0
loop13:
	dup
	aload 1
	dup2
	getfield List/length I
	isub
	ifgt done13
	swap
	invokevirtual List/index(I)Ljava/lang/Object;
	checkcast player
	astore2
	aload 2
	getfield List/name Ljava/lang/String;
	aload 0
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	ifgt true14
	goto done14
true14:
	aload 2
	areturn
	goto done14
done14:
	iconst_1
	iadd
	goto loop13
done13:
	aconst_null
	areturn
.end method

.method public static ChooseMove(Lcard;Lplayer;)Z
.limit stack 4
.limit locals 4
	iconst_0 
	istore 2
	iconst_0
loop15:
	dup
	aload 1
	getfield player/hand LList;
	dup2
	getfield List/length I
	isub
	ifgt done15
	swap
	invokevirtual List/index(I)Ljava/lang/Object;
	checkcast card
	astore3
	aload 3
	getfield List/value I
	aload 0
	getfield card/value I
	if_icmpeq true16
	iconst_0
	goto done16
true16:
	iconst_1
done16:
	ifgt true17
	goto done17
true17:
	aload 3
	aload 1
	getstatic Game/current Lplayer;
	invokevirtual List/transfer(Lplayer;Lplayer;)V
	iconst_1 
	istore 2
	goto done17
done17:
	iconst_1
	iadd
	goto loop15
done15:
	aload 1
	getfield player/hand LList;
	invokestatic Game/CheckForTrick(Lplayer;)V
	aload 1
	getfield player/hand LList;
	getfield LList;/length I
	ldc 0
	if_icmpeq true18
	iconst_0
	goto done18
true18:
	iconst_1
done18:
	ifgt true19
	goto done19
true19:
	aload 1
	getstatic Game/Deck Lplayer;
	ldc 1
	invokevirtual player/take(I)LList;
	putfield player/hand LList;
	goto done19
done19:
	aload 2
	ireturn
.end method

.method public static GetStdDeck(I)LList;
.limit stack 6
.limit locals 7
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
loop20:
	aload 3
	ldc 13
	if_icmplt true21
	iconst_0
	goto done21
true21:
	iconst_1
done21:
	ifeq done20
	ldc 0
	istore 4
loop22:
	aload 4
	ldc 4
	if_icmplt true23
	iconst_0
	goto done23
true23:
	iconst_1
done23:
	ifeq done22
	new card
	dup
	aload 1
	aload 4
	invokevirtual List/index(I)Ljava/lang/Object;
	aload 3
	invokespecial card/<init(Ljava/lang/String;I)V
	astore 5
	aload 2
	aload 5
	invokevirtual List/add(Lcard;)V
	aload 4
	ldc 1
	iadd
	ldc 1
	istore 4
	goto loop22
done22:
	aload 3
	ldc 1
	iadd
	ldc 1
	istore 3
	goto loop20
done20:
	ldc 0
	istore 3
loop24:
	aload 3
	aload 0
	if_icmplt true25
	iconst_0
	goto done25
true25:
	iconst_1
done25:
	ifeq done24
	aload 2
	new card
	dup
	ldc "joker"
	ldc 0
	invokespecial card/<init(Ljava/lang/String;I)V
	invokevirtual List/add(Lcard;)V
	aload 3
	ldc 1
	iadd
	ldc 1
	istore 3
	goto loop24
done24:
	aload 2
	areturn
.end method

.method public static chooseFrom(LList;)Ljava/lang/Object;
.limit stack 5
.limit locals 3
	ldc "Please choose one of the following by typing the number:"
	invokestatic Game/MessageAll(Ljava/lang/String;)V
	ldc 0
	istore 1
loop26:
	aload 1
	aload 0
	getfield List/length I
	if_icmplt true27
	iconst_0
	goto done27
true27:
	iconst_1
done27:
	ifeq done26
	aload 1
	invokestatic Game/MessageAll(Ljava/lang/String;)V
	aload 1
	ldc 1
	iadd
	ldc 1
	istore 1
	goto loop26
done26:
	invokestatic Game/Read()Ljava/lang/String;
	astore 1
	aload 0
	aload 1
	invokestatic Game/ParseInt(Ljava/lang/String;)I
	invokevirtual List/index(I)Ljava/lang/Object;
	areturn
.end method

.method public static AskAll(Ljava/lang/String;)Ljava/lang/String;
.limit stack 3
.limit locals 1
	aload 0
	invokestatic Game/MessageAll(Ljava/lang/String;)V
	invokestatic Game/Read()Ljava/lang/String;
	areturn
.end method

.method public static Ask(Lplayer;Ljava/lang/String;)Ljava/lang/String;
.limit stack 4
.limit locals 2
	aload 0
	aload 1
	invokestatic Game/Message(Lplayer;Ljava/lang/String;)V
	invokestatic Game/Read()Ljava/lang/String;
	areturn
.end method

.method public static ParseInt(Ljava/lang/String;)I
.limit stack 5
.limit locals 4
	ldc 0
	istore 1
	aload 0
	getfield java/lang/String/length I
	ldc 1
	isub
	istore 2
loop28:
	aload 2
	ldc 0
	if_icmpge true29
	iconst_0
	goto done29
true29:
	iconst_1
done29:
	ifeq done28
	aload 0
	getfield java/lang/String/length I
	ldc 1
	isub
	aload 2
	isub
	istore 3
	aload 0
	aload 2
	invokevirtual java/lang/String/index(V)Ljava/lang/String;
	dup
	ldc "1"
	invokevirtual java/lang/String/equals(Ljava/lang/Object)Z
	iconst_1
	isub
	ifeq case30
	dup
	ldc "2"
	invokevirtual java/lang/String/equals(Ljava/lang/Object)Z
	iconst_1
	isub
	ifeq case31
	dup
	ldc "3"
	invokevirtual java/lang/String/equals(Ljava/lang/Object)Z
	iconst_1
	isub
	ifeq case32
	dup
	ldc "4"
	invokevirtual java/lang/String/equals(Ljava/lang/Object)Z
	iconst_1
	isub
	ifeq case33
	dup
	ldc "5"
	invokevirtual java/lang/String/equals(Ljava/lang/Object)Z
	iconst_1
	isub
	ifeq case34
	dup
	ldc "6"
	invokevirtual java/lang/String/equals(Ljava/lang/Object)Z
	iconst_1
	isub
	ifeq case35
	dup
	ldc "7"
	invokevirtual java/lang/String/equals(Ljava/lang/Object)Z
	iconst_1
	isub
	ifeq case36
	dup
	ldc "8"
	invokevirtual java/lang/String/equals(Ljava/lang/Object)Z
	iconst_1
	isub
	ifeq case37
	dup
	ldc "9"
	invokevirtual java/lang/String/equals(Ljava/lang/Object)Z
	iconst_1
	isub
	ifeq case38
	dup
	ldc "0"
	invokevirtual java/lang/String/equals(Ljava/lang/Object)Z
	iconst_1
	isub
	ifeq case39
	ldc 0
	ireturn
	goto done30
case30:
	aload 1
	ldc 1
	ldc 10
	aload 3
	invokestatic Game/Power(II)I
	imul
	iadd
	ldc 1
	ldc 10
	aload 3
	invokestatic Game/Power(II)I
	imul
	istore 1
	goto done30
case31:
	aload 1
	ldc 2
	ldc 10
	aload 3
	invokestatic Game/Power(II)I
	imul
	iadd
	ldc 2
	ldc 10
	aload 3
	invokestatic Game/Power(II)I
	imul
	istore 1
	goto done30
case32:
	aload 1
	ldc 3
	ldc 10
	aload 3
	invokestatic Game/Power(II)I
	imul
	iadd
	ldc 3
	ldc 10
	aload 3
	invokestatic Game/Power(II)I
	imul
	istore 1
	goto done30
case33:
	aload 1
	ldc 4
	ldc 10
	aload 3
	invokestatic Game/Power(II)I
	imul
	iadd
	ldc 4
	ldc 10
	aload 3
	invokestatic Game/Power(II)I
	imul
	istore 1
	goto done30
case34:
	aload 1
	ldc 5
	ldc 10
	aload 3
	invokestatic Game/Power(II)I
	imul
	iadd
	ldc 5
	ldc 10
	aload 3
	invokestatic Game/Power(II)I
	imul
	istore 1
	goto done30
case35:
	aload 1
	ldc 6
	ldc 10
	aload 3
	invokestatic Game/Power(II)I
	imul
	iadd
	ldc 6
	ldc 10
	aload 3
	invokestatic Game/Power(II)I
	imul
	istore 1
	goto done30
case36:
	aload 1
	ldc 7
	ldc 10
	aload 3
	invokestatic Game/Power(II)I
	imul
	iadd
	ldc 7
	ldc 10
	aload 3
	invokestatic Game/Power(II)I
	imul
	istore 1
	goto done30
case37:
	aload 1
	ldc 8
	ldc 10
	aload 3
	invokestatic Game/Power(II)I
	imul
	iadd
	ldc 8
	ldc 10
	aload 3
	invokestatic Game/Power(II)I
	imul
	istore 1
	goto done30
case38:
	aload 1
	ldc 9
	ldc 10
	aload 3
	invokestatic Game/Power(II)I
	imul
	iadd
	ldc 9
	ldc 10
	aload 3
	invokestatic Game/Power(II)I
	imul
	istore 1
	goto done30
case39:
	aload 1
	ldc 0
	iadd
	ldc 0
	istore 1
	goto done30
done30:
	aload 2
	ldc 1
	isub
	ldc 1
	istore 2
	goto loop28
done28:
	aload 1
	ireturn
.end method

.method public static Power(II)I
.limit stack 3
.limit locals 5
	iconst_0
	istore 2
	aload 1
	ldc 0
	if_icmpeq true40
	iconst_0
	goto done40
true40:
	iconst_1
done40:
	ifgt true41
	aload 1
	ldc 0
	if_icmpgt true43
	iconst_0
	goto done43
true43:
	iconst_1
done43:
	ifgt else43
	ldc 1
	istore 2
	ldc 0
	istore 3
loop44:
	aload 3
	aload 1
	if_icmplt true45
	iconst_0
	goto done45
true45:
	iconst_1
done45:
	ifeq done44
	aload 2
	aload 0
	idiv
	aload 0
	istore 2
	aload 3
	ldc 1
	iadd
	ldc 1
	istore 3
	goto loop44
done44:
	goto done41
true41:
	ldc 1
	ireturn
	goto done41
else42:
	aload 0
	istore 2
	ldc 1
	istore 3
loop46:
	aload 3
	aload 1
	if_icmplt true47
	iconst_0
	goto done47
true47:
	iconst_1
done47:
	ifeq done46
	aload 2
	aload 0
	imul
	aload 0
	istore 2
	aload 3
	ldc 1
	iadd
	ldc 1
	istore 3
	goto loop46
done46:
	goto done41
done41:
	aload 2
	ireturn
.end method

