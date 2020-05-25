.class Game/player
.super java/lang/Object
.field public name Ljava/lang/String;
.field public hand LGame/List;
.field public ai Z
.method public <init>()V 
    aload_0
    invokenonvirtual java/lang/Object/<init>()V
    return
.end method
.method public <init>(Ljava/lang/String;Z)V
.limit stack 2
.limit locals 3
  aload 0
  invokenonvirtual java/lang/Object/<init>()V
	aload 0
	aload 1
	putfield Game/player/name Ljava/lang/String;
	aload 0
	iload 2
	putfield Game/player/ai Z
	return
.end method

