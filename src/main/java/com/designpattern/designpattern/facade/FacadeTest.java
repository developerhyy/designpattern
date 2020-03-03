package com.designpattern.designpattern.facade;

/**
 * demo
 * @author joker
 */
public class FacadeTest {
    public static void main(String[] args) {
        Client1 client1= new Client1();
        Client2 client2 = new Client2();
        client1.doSomething1();
        client2.doSomething2();

    }
}

/**
 * 客户端1
 */
class Client1 {

    Facade facade=new Facade();

    public void doSomething1() {
        facade.doSomethingFacade();
    }

}

/**
 * 客户端2
 */
class Client2 {

    Facade facade=new Facade();

    public void doSomething2() {
        facade.doSomethingFacade();
    }

}


class Facade {

    SubSystem1 subSystem1=new SubSystem1();
    SubSystem2 subSystem2=new SubSystem2();
    SubSystem3 subSystem3=new SubSystem3();

    public void doSomethingFacade() {
        subSystem1.method1();
        subSystem2.method2();
        subSystem3.method3();
    }


}


class SubSystem1 {
    public void method1() {
        System.out.println( " SubSystem1.method1 executed. " );
    }
}

class SubSystem2 {
    public void method2() {
        System.out.println( " SubSystem2.method2 executed. " );
    }
}

class SubSystem3 {
    public void method3() {
        System.out.println( " SubSystem3.method3 executed. " );
    }
}
