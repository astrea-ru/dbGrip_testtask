package ru.my.luxoft.dbgrip.testtask;


import org.junit.Test;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class KeyHandlerTest {

    private final  ExternalSystem exSystem = new ExternalSystem();
    private final  KeyHandler handler = new KeyHandler(exSystem);
    private final  List<Key> keys;
    private final static String KEY1 = "key1";
    private final static String KEY2 = "key2";
    private final static String KEY3 = "key3";
    {
        keys =  Arrays.asList(new Key(KEY1), new Key(KEY1), new Key(KEY3),
                        new Key(KEY2), new Key(KEY3), new Key(KEY2),
                        new Key(KEY1), new Key(KEY3));
    }

    @Test
    public void simpleTest() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(8);
        for (int i=0; i<8; i++){
            final int finalI = i;
            service.execute(new Runnable() {
                public void run() {
                    handler.handle(keys.get(finalI));
                }
            });
        }

        service.shutdown();
        service.awaitTermination(1, TimeUnit.MINUTES);

        Map<Key, Integer> results = exSystem.getCounters();
        assertTrue(results.get(new Key(KEY1)) == 3);
        assertTrue(results.get(new Key(KEY2)) == 2);
        assertTrue(results.get(new Key(KEY3)) == 3);
    }

}
