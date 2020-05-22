.class card
.super java/lang/Object
.field public suit Ljava/lang/String;
.field public value I
.method public <init>(Ljava/lang/String;I)V
.limit stack 2
.limit locals 3
  aload 0
  invokenonvirtual java/lang/Object/<init>()V
	aload 0
	aload 1
	putfield card/suit Ljava/lang/String;
	aload 0
	iload 2
	putfield card/value I
	return
.end method

.method public transfer(Lplayer;Lplayer;)V
.limit stack 3
.limit locals 3
	aload 1
	getfield player/hand LList;
	aload 0
	invokevirtual List/remove(Ljava/lang/Object;)V
	aload 2
	getfield player/hand LList;
	aload 0
	invokevirtual List/add(Ljava/lang/Object;)V
	return
.end method

.method public <init>()V 
    aload_0
    invokenonvirtual java/lang/Object/<init>()V
    return
.end method
