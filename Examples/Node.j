.class Game/Node
.super java/lang/Object
.field public next LGame/Node;
.field public prev LGame/Node;
.field public value Ljava/lang/Object;

.method public static <init>(Ljava/lang/object)V
.limit stack 2
.limit locals 0
    aload 0
    dup
    invokenonvirtual java/lang/Object/<init>()V
    dup
    aconst_null
    putfield Game/Node/next LGame/node;
    dup
    aconst_null
    putfield Game/Node/prev LGame/node;
    dup
    aload 1
    putfield Game/Node/value Ljava/lang/Object;
    return
.end method