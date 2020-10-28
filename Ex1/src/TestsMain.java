pacakge org.junit.jupiter.api;

import org.junit.jupiter.api.Test;

public class TestsMain {
    public static void main(String[] args) throws InterruptedException {

        System.out.println("----------- Evyatar Tests Start -------------");
        new tester_v5();
        System.out.println("----------- Evyatar Tests Ended -------------\n");
        Thread.sleep(1000);
        System.out.println("----------- Golan Tests Start -------------");
        new TestGolan();
        System.out.println("----------- Golan Tests Ended -------------\n");
        Thread.sleep(1000);
        System.out.println("----------- Adi Tests Start -------------");
        new TestAdi();
        System.out.println("----------- Adi Tests Ended -------------\n");
        Thread.sleep(1000);
        System.out.println("----------- Rina Tests Start -------------");
        new TestRina();
        System.out.println("----------- Rina Tests Ended -------------\n");
        Thread.sleep(1000);
        System.out.println("----------- All Tested ended -------------");
    }
}