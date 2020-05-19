.class Game
.super java/lang/Object
.method public <init>()V
aload_0
invokeonvirtual java/lang/Object/<init>()V
return
.end method

.method public static main([Ljava/lang/String)V
invokestatic Game/GetStdDeck(I)Ljava/util/LinkedList;
astore 0
invokestatic Game/InitPlayers()Ljava/util/LinkedList;
astore 1
loop1:
aload 1aconst_null
aload 1ifeq done1
invokestatic Game/MessageAll(Ljava/lang/String;)V
aload 1invokestatic Game/InitPlayers()Ljava/util/LinkedList;
invokestatic Game/InitPlayers()Ljava/util/LinkedList;
astore 1
goto loop1
done1:
aload -1aload 1invokestatic Game/find(Ljava/lang/String;Ljava/util/LinkedList;)Lplayer;
aload 1invokestatic Game/find(Ljava/lang/String;Ljava/util/LinkedList;)Lplayer;
putfield Turn/current Lplayer;
iconst_0
loop2:
dup
aload 1dup2
invokevirtual java/util/LinkedList/size()I
isub
ifgt done2
swap
invokevirtual java/util/LinkedList/get(I)Ljava/lang/Object;
checkcast player
astore-1
aload -1aload -1aload -1putfield player/hand Ljava/util/LinkedList;
iconst_1
iadd
goto loop2
done2:
.end method

.method public static CheckForTrick(Lplayer;)V
.limit stack 3
.limit locals 5
new java/util/LinkedList
dup
invokespecial java/util/LinkedList/<init>()V
astore 0
istore 1
loop1:
iload 1ifeq done1
aload -1iconst_0
loop2:
dup
aload -1dup2
invokevirtual java/util/LinkedList/size()I
isub
ifgt done2
swap
invokevirtual java/util/LinkedList/get(I)Ljava/lang/Object;
checkcast card
astore-1
iload 1aload -1ifgt true3
goto done4
true3:
aload -1aload -1goto done4
done4:
iconst_1
iadd
goto loop4
done4:
aload -1ifgt true5
goto done6
true5:
iconst_0
loop7:
dup
aload -1dup2
invokevirtual java/util/LinkedList/size()I
isub
ifgt done7
swap
invokevirtual java/util/LinkedList/get(I)Ljava/lang/Object;
checkcast card
astore-1
aload -1aload -1iconst_1
iadd
goto loop7
done7:
aload -1iadd
putfield player/score I
goto done7
done7:
iload 1iadd
istore 1
goto loop1
done7:
return
.end method

.method public static InitPlayers()Ljava/util/LinkedList;
.limit stack 6
.limit locals 6
new java/util/LinkedList
dup
invokespecial java/util/LinkedList/<init>()V
astore 0
invokestatic Game/MessageAll(Ljava/lang/String;)V
astore 1
loop8:
aload 1ifeq done8
invokestatic Game/MessageAll(Ljava/lang/String;)V
iconst_0
loop9:
dup
aload -1dup2
invokevirtual java/util/LinkedList/size()I
isub
ifgt done9
swap
invokevirtual java/util/LinkedList/get(I)Ljava/lang/Object;
checkcast player
astore-1
aload -1aload -1invokestatic Game/Message(Lplayer;Ljava/lang/String;)V
iconst_1
iadd
goto loop9
done9:
invokestatic Game/AskAll(Ljava/lang/String;)Ljava/lang/String;
astore 2
invokestatic Game/AskAll(Ljava/lang/String;)Ljava/lang/String;
astore 3
iconst_0
istore 4
aload 3ifgt true10
aload 3ifgt else11
invokestatic Game/MessageAll(Ljava/lang/String;)V
aconst_null
areturn
goto done12
true10:
iload 4istore 4
goto done12
else11:
iload 4istore 4
goto done12
done12:
aload -1new player
dup
aload 2iload 4invokespecial player/<init(Ljava/lang/String;Z)V
aload 1invokestatic Game/AskAll(Ljava/lang/String;)Ljava/lang/String;
invokestatic Game/AskAll(Ljava/lang/String;)Ljava/lang/String;
astore 1
goto loop8
done12:
aload -1areturn
.end method

.method public static find(Ljava/lang/String;Ljava/util/LinkedList;)Lplayer;
.limit stack 2
.limit locals 3
iconst_0
loop13:
dup
aload -1dup2
invokevirtual java/util/LinkedList/size()I
isub
ifgt done13
swap
invokevirtual java/util/LinkedList/get(I)Ljava/lang/Object;
checkcast player
astore-1
aload -1aload -1ifgt true14
goto done15
true14:
aload -1areturn
goto done15
done15:
iconst_1
iadd
goto loop15
done15:
aconst_null
areturn
.end method

.method public static ChooseMove(Lcard;Lplayer;)Z
.limit stack 4
.limit locals 4
istore 0
iconst_0
loop16:
dup
aload -1dup2
invokevirtual java/util/LinkedList/size()I
isub
ifgt done16
swap
invokevirtual java/util/LinkedList/get(I)Ljava/lang/Object;
checkcast card
astore-1
aload -1aload -1ifgt true17
goto done18
true17:
aload -1aload -1aload -1iload -1istore -1
goto done18
done18:
iconst_1
iadd
goto loop18
done18:
aload -1invokestatic Game/CheckForTrick(Lplayer;)V
aload -1ifgt true19
goto done20
true19:
aload -1aload -1aload -1putfield player/hand Ljava/util/LinkedList;
goto done20
done20:
iload -1ireturn
.end method

