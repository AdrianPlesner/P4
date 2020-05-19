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
    hej:
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
    invokestatic examples/echo/bar()V
    return
.end method

.method public foo(I)V
    .limit locals 2
    .limit stack 10
    hej:
    getstatic java/lang/System/out Ljava/io/PrintStream;
    ldc "hej"
    iload 1
    invokestatic java/lang/String/valueOf(I)Ljava/lang/String;
    invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;
    invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
    return
.end method

.method public static bar()V
   .limit stack 10
   .limit locals 1
   new java/util/LinkedList
   dup
   invokespecial java/util/LinkedList/<init>()V
   dup
   astore 0
   new java/lang/Integer
   dup
   iconst_0
   invokespecial java/lang/Integer/<init>(I)V
   invokevirtual java/util/LinkedList/add(Ljava/lang/Object;)Z
   pop
   aload 0
   iconst_0
   invokevirtual java/util/LinkedList/get(I)Ljava/lang/Object;
   checkcast java/lang/Integer
   invokevirtual java/lang/Integer/intValue()I
   getstatic java/lang/System/out Ljava/io/PrintStream;
   swap
   invokevirtual java/io/PrintStream/println(I)V
   return
.end method