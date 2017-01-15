package ru.my.luxoft.dbgrip.testtask;


import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class KeyHandler {

    private ExternalSystem  externalSystem;
    private final ConcurrentMap <Key, Key> keysForLock = new ConcurrentHashMap();


    public KeyHandler(){
        externalSystem = new ExternalSystem();
    }

    public KeyHandler(ExternalSystem  externalSystem){
        this.externalSystem = externalSystem;
    }

    /* Task:
    delegates key processing to some external system
    Method handle(Key key) is called from multiple threads (8 threads).
    Implement method handle() to prevent simultaneous processing of equal keys.
     All keys should be processed eventually, even if some of them are equal.
    */

    /*
    One of decision: mark method synchronized. It's mean that only one thread can execute this method at one time.
    public synchronized void handle(Key key) {
        externalSystem.process(key);
    }
    It's bad decision, because our application will not be multithreading in fact.
    All actions will execute as standard application in one thread (if we will see on time)
     */

    /*
    Second decision: synchronized by key.
    public void handle(Key key) {
        synchronized(key) {
            externalSystem.process(key);
        }
    }
    We can do it only if we sure, that if key1 equals key2 it's mean, that key1 and key2 it the same object
    For example: new Key("key1"), new Key("key1"). For our application they are equal keys, but for Java it is two different objects!

    But idea is good. We make synchronization only for the same object!
     */

    /*
    Result. Base on second idea I create list of object for locking in ConcurrentHashMap.
     We receive key, find locking object and make synchronize.
     */

    public void handle(Key key)  {
        keysForLock.putIfAbsent(key, key);
        synchronized (keysForLock.get(key)) {
            externalSystem.process(key);
        }
    }

}
