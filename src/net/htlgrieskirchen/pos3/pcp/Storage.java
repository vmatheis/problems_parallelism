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

    public Storage(ArrayBlockingQueue<Integer> queue, int fetchedCounter, int storedCounter, int underflowCounter, int overflowCounter, boolean productionComplete) {
        this.queue = queue;
        this.fetchedCounter = fetchedCounter;
        this.storedCounter = storedCounter;
        this.underflowCounter = underflowCounter;
        this.overflowCounter = overflowCounter;
        this.productionComplete = productionComplete;
    }

    public synchronized boolean put(Integer data) throws InterruptedException {
//        queue.put(data);
        if (storedCounter < 11) {
//            queue.put(data);
            storedCounter++;
            return true;
        } else {
            overflowCounter++;
            return false;
        }
    }

    public synchronized Integer get() {        
        if(queue.isEmpty()){
            underflowCounter++;
            return null;
        } else{
            fetchedCounter++;
            return queue.poll();
        }
    }

    public boolean isProductionComplete() {
        return productionComplete;
    }

    public void setProductionComplete() {
        productionComplete = true;
    }

    public int getFetchedCounter() {
        return fetchedCounter;
    }

    public int getStoredCounter() {
        return storedCounter;
    }

    public int getUnderflowCounter() {
        return underflowCounter;
    }

    public int getOverflowCounter() {
        return overflowCounter;
    }
}
