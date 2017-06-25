package com.dartin.project.net;

/**
 * Created by Martin on 25.06.2017.
 */
public class Timeout implements Runnable{
    private Thread requestedThread;

    public Timeout(Thread thread, int timeout){
        this.requestedThread = thread;
    }

    @Override
    public void run() {

    }
}
