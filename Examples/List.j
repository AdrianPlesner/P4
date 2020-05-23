.class Game/List
.super java/lang/Object
.field public length I
.field private first LGame/Node;
.field private last LGame/Node;

.method public <init>()V
    .limit locals 1
    .limit stack 4
    aload 0
    dup
    invokenonvirtual java/lang/Object/<init>()V
    dup
    aconst_null
    putfield Game/List/first LGame/Node;
    dup
    aconst_null
    putfield Game/List/last LGame/Node;
    dup
    iconst_0
    putfield Game/List/length I
    return
.end method

.method public add(Ljava/lang/Object;)V
    .limit locals 4
    .limit stack 3
    new Game/Node
    dup
    aload 1
    invokespecial Game/Node/<init>(Ljava/lang/Object)V
    astore 2
    aload 0
    getfield Game/List/length I
    ifeq empty
    aload 0
    getfield Game/List/last LGame/Node;
    astore 3
    aload 3
    aload 2
    setfield Game/Node/next LGame/Node;
    aload 2
    aload 3
    setfield Game/Node/prev LGame/Node;
    aload 0
    aload 2
    putfield Game/Node/Last LGame/Node;
    goto added
empty:
    aload 0
    aload 2
    dup2
    putfield Game/List/first LGame/Node;
    putfield Game/List/last LGame/Node;
added:
    aload 0
    dup
    getfield Game/List/length I
    iconst_1
    iadd
    putfield Game/List/length I
    return
.end method

.method public remove(Ljava/lang/Object;)V
    .limit locals 2
    .limit stack 3
    aload 0
    getfield Game/List/list Ljava/util/LinkedList;
    aload 1
    invokevirtual java/util/LinkedList/remove(Ljava/lang/Object;)Z
    pop
    aload 0
    dup
    getfield Game/List/length I
    iconst_1
    isub
    putfield Game/List/length I
    return
.end method

.method public clear()V
    .limit locals 1
    .limit stack 2
    aload 0
    dup
    getfield GameList/list Ljava/util/LinkedList;
    invokevirtual java/util/LinkedList/clear()V
    iconst_0
    putfield Game/List/length I
    return
.end method

.method public take(I)LList;
    .limit locals 4
    .limit stack 5
    new Game/List
    dup
    invokespecial Game/List/<init>()V
    astore 2
    aload 0
    getfield Game/List/list Ljava/util/LinkedList;
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
    invokevirtual Game/List/add(Ljava/lang/Object;)V
    iload 3
    iconst_1
    iadd
    istore 3
    goto loop
done:
    dup
    getfield Game/List/length I
    iload 1
    isub
    putfield Game/List/length I
    return
.end method

.method public index(I)Ljava/lang/Object;
    .limit locals 2
    .limit stack 2
    aload 0
    getfield Game/List/list Ljava/util/LinkedList;
    iload 1
    invokevirtual java/util/LinkedList/get(I)Ljava/lang/Object;
    areturn
.end method