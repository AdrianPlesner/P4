.class card
.super java/Lang/Object
.field public suit Ljava/lang/String;
.field public value I
.method public <init>(Ljava/lang/String;I)V
.limit stack 2
.limit locals 2
aload -1aload -1aload -1putfield card/suit Ljava/lang/String;
aload -1aload -1aload -1putfield card/value I
.end method

.method public transfer(Lplayer;Lplayer;)V
.limit stack 3
.limit locals 2
aload -1aload -1aload -1aload -1return
.end method

.method public <init>()V 
    aload_0
    invokenonvirtual java/lang/Object/<init>()V
    return
.end method
