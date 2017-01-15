package ru.my.luxoft.dbgrip.testtask;

import java.util.HashMap;
import java.util.Map;

/*
As far as I understand the task, the main problem in common resources.
When we start two different threads with the same key, they try to change something common.
By this reason I took current simple example.
I didn't use concurrency package for Map, because, as I understand the task,
resolving problem should be in KeyHandler class.
 */
public class ExternalSystem {

    private Map<Key, Integer> counters = new HashMap();

    //External system can process different keys at the same time just fine,
    // but if it's called with equal keys at the same time it will break.
    public void process(Key key){
        Integer counter = counters.get(key);
        System.out.println(String.format("%s: Process key %s %d ",
                Thread.currentThread().getName() ,key.getKey(), counter));
        if (counter == null){
            counters.put(key, 1);
        } else {
            counters.put(key, ++counter);
        }
        System.out.println(String.format("%s: New counter for %s is %d ",
                Thread.currentThread().getName(), key.getKey(), counters.get(key)));
    }

    //Only for test
    public Map<Key, Integer> getCounters() {
        return counters;
    }
}
