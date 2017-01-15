package ru.my.luxoft.dbgrip.testtask;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainApp {

    private final static KeyHandler handler = new KeyHandler();
    private final static List<Key> keys;
    static {
        keys = Collections.synchronizedList(Arrays.asList(new Key("key1"), new Key("key1"), new Key("key3")));
    }

    public static void main(String... args) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(8);
        long start = System.currentTimeMillis();
        for (int i=0; i<8; i++){
            service.execute(new RunHandler());
        }

        service.shutdown();
        service.awaitTermination(1, TimeUnit.MINUTES);

        long stop = System.currentTimeMillis();

        System.out.println("Time: "+(stop - start));
    }

    private static class RunHandler implements Runnable{

        public void run() {
            handler.handle(keys.get(randInt()));
        }

        int randInt() {
            Random rand = new Random();
            return rand.nextInt(3);
        }
    }
}
