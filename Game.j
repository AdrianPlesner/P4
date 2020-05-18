.class Game
.super java/lang/Object
.method public <init>()V
aload_0
invokeonvirtual java/lang/Object/<init>()V
return
.end method

.method public static main([Ljava/lang/String)V
Ljava/util/LinkedList;Ljava/util/LinkedList;astore -1
astore 
Ziadd
iconst_2
isub
ifeq true1
iconst_0
goto done1
true1:
iconst_1
done1:
istore -1
astore 
.end method

.method public static CheckForTrick(Lplayer;)V
.limit stack 3
.limit locals 5
Ljava/util/LinkedList;Iiadd
istore -1
iadd
istore 
.end method

.method public static InitPlayers()Ljava/util/LinkedList;
.limit stack 6
.limit locals 6
Ljava/util/LinkedList;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Zistore -1
istore -1
astore -1
.end method

.method public static find(Ljava/lang/String;Ljava/util/LinkedList;)Lplayer;
.limit stack 2
.limit locals 3
.end method

.method public static ChooseMove(Lcard;Lplayer;)Z
.limit stack 4
.limit locals 4
Zistore -1
astore 
.end method

