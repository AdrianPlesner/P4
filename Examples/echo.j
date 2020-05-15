;The purpose of this is to test out jasmin and to make a program that prints the input.
.class public examples/echo
.super java/lang/Object

.method public <init>()V
    aload_0
    invokenonvirtual java/lang/Object/<init>()V
    return
.end method

.method public static main([Ljava/lang/String;)V
    .limit stack 10
    new java/util/LinkedList
    dup
    dup
    invokespecial java/util/LinkedList/<init>()V
    ldc "lolx du hader mig"
    invokevirtual java/util/LinkedList/add(Ljava/lang/Object;)Z
    pop
    getstatic java/lang/System/out Ljava/io/PrintStream;
    swap
    iconst_0
    invokevirtual java/util/LinkedList/get(I)Ljava/lang/Object;
    checkcast java/lang/String
    invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    invokestatic java/lang/System/console()Ljava/io/Console;
    invokevirtual java/io/Console/readLine()Ljava/lang/String;
    invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
    
    return
.end method

.method public foo(II)I
    .limit locals 3
    .limit stack 2
    iload 1
    iload 2
    iadd
    ireturn
.end method