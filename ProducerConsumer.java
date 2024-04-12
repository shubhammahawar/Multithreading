/*package whatever //do not write package name here */

Write a program  the producer and the consumer, which share a common, fixed-size buffer used as a queue. 

/* Output -

  producer produced value 0
producer produced value 1
consumer consumed value 0
consumer consumed value 1
producer produced value 2
consumer consumed value 2
producer produced value 3
producer produced value 4
consumer consumed value 3
consumer consumed value 4

  */



import java.io.*;
import java.util.*;


public  class ProducerConsumerThread{
    
    public static void main(String args[]) throws InterruptedException{
    
    PC pc = new PC();
    
    Thread t1 = new Thread (new Runnable() {
        
        @Override
        public void run(){
            try{
                for(int i=0;i<5;i++)
                pc.produce();
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    });
    
    
    Thread t2 = new Thread (new Runnable() {
        
        @Override
        public void run(){
            try {
                for(int i=0;i<5;i++)
                pc.consume();
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    });
    
    t1.start();
    t2.start();
    
    t1.join();
    t2.join();
    
}


public static class PC {
    
    LinkedList<Integer> list = new LinkedList<>();
    int capacity = 5;
     int value=0;
    
    public void produce() throws InterruptedException{
       
        synchronized(this){
            
            while(list.size()==capacity)
            wait();
            
            System.out.println("producer produced value "+value);
            list.add(value++);
            
            notify();
            Thread.sleep(1000);
        }
    }
    
    public void consume() throws InterruptedException{
        synchronized(this){
            while(list.size()==0)
            wait();
            
            int value = list.removeFirst();
            System.out.println("consumer consumed value "+value);
            
            notify();
            Thread.sleep(1000);
        }
    }
    
}
}
