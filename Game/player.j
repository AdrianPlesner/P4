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
.limit locals 3
  aload 0
  invokenonvirtual java/lang/Object/<init>()V
	aload 0
	aload 1
	putfield player/name Ljava/lang/String;
	aload 0
	iload 2
	putfield player/ai Z
	return
.end method

.method public whatevs()V
.limit stack 6
.limit locals 3
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
	astore 1
	iconst_0
loop1:
	dup
	aload 1
	dup2
	getfield List/length I
	isub
	ifgt done1
	swap
	invokevirtual List/index(I)Ljava/lang/Object;
	checkcast java/lang/Integer
	invokevirtual java/lang/Integer/intValue()I
	istore 2
	; begin loop body
	iload 2
	ldc 1
	iadd
	istore 2
	; end loop body
	iconst_1
	iadd
	goto loop1
done1:
	pop2
	pop
	return
.end method

