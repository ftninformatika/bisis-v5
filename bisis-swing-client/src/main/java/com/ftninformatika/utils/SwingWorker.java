package com.ftninformatika.utils;

import javax.swing.*;

public abstract class SwingWorker
{
    private Object value;
    private Thread thread;
    private ThreadVar threadVar;

    private static class ThreadVar
    {
        private Thread thread;

        ThreadVar(Thread t) { thread = t; }
        synchronized Thread get() { return thread; }
        synchronized void clear() { thread = null; }
    }


    protected synchronized Object getValue()
    {
        return value;
    }



    private synchronized void setValue(Object x)
    {
        value = x;
    }





    public abstract Object construct();





    public void finished() {}




    public void interrupt()
    {
        Thread t = threadVar.get();
        if (t != null) {
            t.interrupt();
        }
        threadVar.clear();
    }






    public Object get()
    {
        for (;;)
        {
            Thread t = threadVar.get();
            if (t == null) {
                return getValue();
            }
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
    }





    public SwingWorker()
    {
        final Runnable doFinished = new Runnable() {
            public void run() { finished();
            }
        };
        Runnable doConstruct = new Runnable() {
            public void run() {
                try {
                    SwingWorker.this.setValue(construct());
                } finally {
                    threadVar.clear();
                }
                SwingUtilities.invokeLater(doFinished);
            }

        };
        Thread t = new Thread(doConstruct);
        threadVar = new ThreadVar(t);
    }



    public void start()
    {
        Thread t = threadVar.get();
        if (t != null) {
            t.start();
        }
    }
}