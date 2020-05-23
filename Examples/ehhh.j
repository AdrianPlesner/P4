.class public ehhh
.super java/lang/Object

.method public <init>()V
    aload 0
    invokenonvirtual java/lang/Object/<init>()V
    return
.end method

.method public static main([Ljava/lang/String;)V
.limit stack 6
.limit locals 1
    new card
    dup
    ldc "suit"
    ldc 2
    invokespecial Game/card/<init>(Ljava/lang/String;I)V
    new player
    dup
    ldc "hejsa"
    iconst_0
    invokespecial Game/player/<init>(Ljava/lang/String;Z)V
    invokestatic ehhh/add(Lcard;Lplayer;)I
    pop
    new List
    dup
    invokespecial List/<init>()V
    pop
    return
.end method

.method public static add(Lcard;Lplayer;)I
.limit stack 3
.limit locals 2
    iconst_0
    ireturn
.end method

.method public static what()Ljava/lang/Object;
.limit stack 1
.limit locals 1
    ldc "hej"
    astore 0
    aload 0
    areturn
.end method