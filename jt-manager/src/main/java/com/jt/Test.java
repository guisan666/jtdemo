package com.jt;

public class Test {

    String str=new String("tarena");
    char[]ch={'a','b','c'};

    public static void main(String args[]){
        Test ex=new Test();
        ex.change(ex.str,ex.ch);
        System.out.print(ex.str+" and ");
        System.out.print(ex.ch);
    }
    public void change(String str,char ch[]){
        //引用类型变量，传递的是地址，属于引用传递。
        str="test ok";
        ch[0]='g';
    }
}
