/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.htlgrieskirchen.pos3.pcp;

import java.util.concurrent.ArrayBlockingQueue;

public class Storage {

    private final ArrayBlockingQueue<Integer> queue;

    private int fetchedCounter;
    private int storedCounter;
    private int underflowCounter;
    private int overflowCounter;
    private boolean productionComplete;

    public Storage() {
        this.queue = new ArrayBlockingQueue<>(10);
        this.fetchedCounter = 0;
        this.storedCounter = 0;
        this.underflowCounter = 0;
        this.overflowCounter = 0;
        this.productionComplete = false;
    }

    public synchronized boolean put(Integer data) {
        boolean offer = queue.offer(data);
        if (offer) {
            storedCounter++;
            return true;
        } else {
            overflowCounter++;
            return false;
        }
    }

    public synchronized Integer get() {
        if (queue.isEmpty()) {
            underflowCounter++;
            return null;
        } else {
            fetchedCounter++;
            return queue.poll(); //queue.peek() wenn nicht removed werden soll
        }
    }

    public boolean isProductionComplete() {
        return this.productionComplete;
    }

    public void setProductionComplete() {
        this.productionComplete = true;
    }

    public int getFetchedCounter() {
        return this.fetchedCounter;
    }

    public int getStoredCounter() {
        return this.storedCounter;
    }

    public int getUnderflowCounter() {
        return this.underflowCounter;
    }

    public int getOverflowCounter() {
        return this.overflowCounter;
    }
}
