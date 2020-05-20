.class card
.super java/Lang/Object
.field public suit Ljava/lang/String;
.field public value I
.method public <init>()V
.limit stack 2
.limit locals 2
	aload 2
	aload 0
	aload 0
	putfield card/suit Ljava/lang/String;
	aload 2
	aload 1
	aload 1
	putfield card/value I
.end method

.method public transfer()V
.limit stack 3
.limit locals 2
	aload 1
	aload 0
	aload 2
	aload 0
return
.end method

.method public <init>()V 
    aload_0
    invokenonvirtual java/lang/Object/<init>()V
    return
.end method
