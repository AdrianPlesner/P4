.class List
.super java/lang/Object
.field public length I
.field private list Ljava/util/LinkedList;

.method public <init>()V
    .limit locals 1
    .limit stack 4
    aload 0
    dup
    invokenonvirtual java/lang/Object/<init>()V
    dup
    new java/util/LinkedList
    dup
    invokespecial java/util/LinkedList/<init>()V
    putfield List/list Ljava/util/LinkedList;
    iconst_0
    putfield List/length I
    return
.end method

.method public add(Ljava/lang/Object;)V
    .limit locals 2
    .limit stack 3
    aload 0
    getfield List/list Ljava/util/LinkedList;
    aload 1
    invokevirtual java/util/LinkedList/add(Ljava/lang/Object)Z
    pop
    aload 0
    dup
    getfield List/length I
    iconst_1
    iadd
    putfield List/length I
    return
.end method

.method public remove(Ljava/lang/Object;)V
    .limit locals 2
    .limit stack 3
    aload 0
    getfield List/list Ljava/util/LinkedList;
    aload 1
    invokevirtual java/util/LinkedList/remove(Ljava/lang/Object)Z
    pop
    aload 0
    dup
    getfield List/length I
    iconst_1
    isub
    putfield List/length I
    return
.end method

.method public clear()V
    .limit locals 1
    .limit stack 2
    aload 0
    dup
    getfield List/list Ljava/util/LinkedList;
    invokevirtual java/util/LinkedList/clear()V
    iconst_0
    putfield List/length I
    return
.end method

.method public take(I)LList;
    .limit locals 4
    .limit stack 5
    new List
    dup
    invokespecial List/<init>()V
    astore 2
    aload 0
    getfield List/list Ljava/util/LinkedList;
    iconst_0
    istore 3
loop:
    iload 3
    iload 1
    if_icmpge done
    dup
    invokevirtual Ljava/util/LinkedList/removeFirst()Ljava/lang/Object;
    aload 2
    swap
    invokevirtual List/add(Ljava/lang/Object)V
    iload 3
    iconst_1
    iadd
    istore 3
    goto loop
done:
    dup
    getfield List/length I
    iload 1
    isub
    putfield List/length I
    return
.end method

.method public index(I)Ljava/lang/Object;
    .limit locals 2
    .limit stack 2
    aload 0
    getfield List/list Ljava/util/LinkedList;
    iload 1
    invokevirtual java/util/LinkedList/get(I)Ljava/lang/Object;
    areturn
.end method