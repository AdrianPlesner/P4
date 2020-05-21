.class player
.super java/lang/Object
.field public name Ljava/lang/String;
.field public hand LList;
.field public ai Z
.field public score I
.method public <init>()V 
    aload_0
    invokenonvirtual java/lang/Object/<init>()V
    return
.end method
.method public <init>(Ljava/lang/String;Z)V
.limit stack 2
.limit locals 2
	aload 0
	aload 1
	putfield player/name Ljava/lang/String;
	aload 0
	aload 2
	putfield player/ai Z
.end method

