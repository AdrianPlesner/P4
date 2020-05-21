.class Game
.super java/lang/Object
.field public static current Lplayer;
.field public static Deck Ljava/util/LinkedList;
.field public static Players Ljava/util/LinkedList;

.method public <init>()V
	aload_0
	invokeonvirtual java/lang/Object/<init>()V
	return
.end method

new java/util/LinkedList
	dup
	invokespecial java/util/LinkedList/<init>()V
	putfield List/list Ljava/util/LinkedList
	iconst_0
	putfield List/length I

.method public static main([Ljava/lang/String)V
	new player
	ldc 0
	invokestatic Game/GetStdDeck(I)Ljava/util/LinkedList;
	putstatic Game/Deck Ljava/util/LinkedList;
	invokestatic Game/InitPlayers()Ljava/util/LinkedList;
	putstatic Game/Players Ljava/util/LinkedList;
loop1:
	getstatic Game/Players Ljava/util/LinkedList;
	aconst_null
	if_acmpeq true2
	iconst_0
	goto done2
true2:
	iconst_1
done2:
	getstatic Game/Players Ljava/util/LinkedList;
	getfield java/util/LinkedList/length I
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
	invokestatic Game/InitPlayers()Ljava/util/LinkedList;
	putstatic Game/Players Ljava/util/LinkedList;
	goto 1
done1:
	ldc "Adrian"
	getstatic Game/Players Ljava/util/LinkedList;
	invokestatic Game/find(Ljava/lang/String;Ljava/util/LinkedList;)Lplayer;
	putstatic Game/current Lplayer;
	iconst_0
loop4:
	dup
	getstatic Game/Players Ljava/util/LinkedList;
	dup2
	invokevirtual java/util/LinkedList/size()I
	isub
	ifgt done4
	swap
	invokevirtual java/util/LinkedList/get(I)Ljava/lang/Object;
	checkcast player
	astore0
	aload 0
	getstatic Game/Deck Ljava/util/LinkedList;
	ldc 7
	invokevirtual java/util/LinkedList/take(I)Ljava/util/LinkedList;
	putfield java/util/LinkedList/hand Ljava/util/LinkedList;
	iconst_1
	iadd
	goto loop4
done4:
.end method

.method public static CheckForTrick(Lplayer;)V
.limit stack 3
.limit locals 5
	new java/util/LinkedList
	dup
	invokespecial java/util/LinkedList/<init>()V
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
	invokevirtual java/util/LinkedList/clear()V
	iconst_0
loop3:
	dup
	aload 0
	getfield player/hand Ljava/util/LinkedList;
	dup2
	invokevirtual java/util/LinkedList/size()I
	isub
	ifgt done3
	swap
	invokevirtual java/util/LinkedList/get(I)Ljava/lang/Object;
	checkcast card
	astore3
	aload 2
	aload 3
	getfield java/util/LinkedList/value I
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
	invokevirtual java/util/LinkedList/add(Lcard;)V
	goto done5
done5:
	iconst_1
	iadd
	goto loop3
done3:
	aload 1
	getfield java/util/LinkedList/length I
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
	invokevirtual java/util/LinkedList/size()I
	isub
	ifgt done8
	swap
	invokevirtual java/util/LinkedList/get(I)Ljava/lang/Object;
	checkcast card
	astore3
	aload 0
	getfield player/hand Ljava/util/LinkedList;
	aload 3
	invokevirtual java/util/LinkedList/remove(Lcard;)V
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

.method public static InitPlayers()Ljava/util/LinkedList;
.limit stack 6
.limit locals 6
	new java/util/LinkedList
	dup
	invokespecial java/util/LinkedList/<init>()V
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
	invokevirtual java/util/LinkedList/size()I
	isub
	ifgt done10
	swap
	invokevirtual java/util/LinkedList/get(I)Ljava/lang/Object;
	checkcast player
	astore2
	aload 2
	aload 2
	getfield java/util/LinkedList/ai Z
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
	invokevirtual java/util/LinkedList/add(Lplayer;)V
	ldc "Are there any more players?"
	invokestatic Game/AskAll(Ljava/lang/String;)Ljava/lang/String;
	astore 1
	goto 9
done9:
	aload 0
	areturn
.end method

.method public static find(Ljava/lang/String;Ljava/util/LinkedList;)Lplayer;
.limit stack 2
.limit locals 3
	iconst_0
loop13:
	dup
	aload 1
	dup2
	invokevirtual java/util/LinkedList/size()I
	isub
	ifgt done13
	swap
	invokevirtual java/util/LinkedList/get(I)Ljava/lang/Object;
	checkcast player
	astore2
	aload 2
	getfield java/util/LinkedList/name Ljava/lang/String;
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
	getfield player/hand Ljava/util/LinkedList;
	dup2
	invokevirtual java/util/LinkedList/size()I
	isub
	ifgt done15
	swap
	invokevirtual java/util/LinkedList/get(I)Ljava/lang/Object;
	checkcast card
	astore3
	aload 3
	getfield java/util/LinkedList/value I
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
	aload -1
	invokevirtual java/util/LinkedList/transfer(Lplayer;Lplayer;)V
	iconst_1 
	istore 2
	goto done17
done17:
	iconst_1
	iadd
	goto loop15
done15:
	aload 1
	getfield player/hand Ljava/util/LinkedList;
	invokestatic Game/CheckForTrick(Lplayer;)V
	aload 1
	getfield player/hand Ljava/util/LinkedList;
	getfield Ljava/util/LinkedList;/length I
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
	aload -1
	ldc 1
	invokevirtual java/util/LinkedList/take(I)Ljava/util/LinkedList;
	putfield player/hand Ljava/util/LinkedList;
	goto done19
done19:
	aload 2
	ireturn
.end method

