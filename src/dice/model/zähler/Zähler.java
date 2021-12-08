/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dice.model.zähler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author marco
 * 
 * Klasse in der die Threads implementiert werden
 */

public class Zähler implements Runnable
{
  private AtomicBoolean stop;
  private Integer[] value;
  private ExecutorService eService;
  private SubmissionPublisher<Integer[]> iPublisher;
  private int id;
  
  /**
   * Konstruktor der Zähler-Klasse
   * Stop wir mit "true initialisiert", Value ist zunächst 0,
   * SingleThreadExecutor und SubmissionPublisher wird erstellt bzw. initialisiert
   * 
   * @param id : ID des Zählerobjekts
   */
  
  public Zähler(int id)
  {
    stop = new AtomicBoolean(true);
    value = new Integer[2];
    value[0] = id;
    value[1] = 0;
    this.id = id;
    eService = Executors.newSingleThreadExecutor();
    iPublisher = new SubmissionPublisher<>();
  }
  
  /**
   * Publisher subscribed beim übergebenen Parameter
   * @param subscriber : Subscriber
   */
  public void addValueSubscription(Flow.Subscriber<Integer[]> subscriber)
  {
    iPublisher.subscribe(subscriber);
  }
  
  /**
   * Start Methode:
   * stop wird auf "false" gesetzt,
   * Das Objekt wird dem Thread übergeben
   * mit notifyAll() werden alle Threads zu runnable geändert
   */
  public synchronized void start()
  {
    //lg.info("Start");
    //stop = false;
    stop.set(false);
    
    try
    {
      this.notifyAll();
    }
    catch(Exception ex)
    {
      System.err.println(ex.toString());
    }
    eService.submit(this);
  }
  
  /**
   * Stop Methode:
   * stop wird auf "true" gesetzt
   */
  public void stop()
  {
    stop.set(true);
  }
  
  /**
   * Zufallszahlen zwischen 0 und 9 werden erzeugt und übergeben
   * mit wait() werden die Threads gestoppt und können in start() dann wieder gestartet werden
   */
  @Override
  public void run()
  {
    while (true)
    {
      try
      {
        Thread.sleep(10);
      }
      catch (InterruptedException ex)
      {
        System.err.print(ex.toString());
      }
      value[1] = (int) (0 + 9 * Math.random());
      //System.err.print(value[1]);
      iPublisher.submit(value);
      while (stop.get())
      {
        System.err.print(value[1] + " [" + id + "] ");
        synchronized (this)
        {
          try
          {
            this.wait();
          }
          catch (InterruptedException ex)
          {
            System.err.println(ex.toString());
          }
        }
      }
    }
  }
  
  
  
  //  @Override
//  public void run()
//  {
//    while (true)
//    {
//      try
//      {
//        Thread.sleep(10);
//      }
//      catch (InterruptedException ex)
//      {
//        System.err.print(ex.toString());
//      }
//      value = (int) (1 + 6 * Math.random());
//      if (!stop)
//      {
//        iPublisher.submit(value);
//      }
//    }
//  }
  
//  @Override
//  public void run()
//  {
//    while (!stop)
//    {
//      try
//      {
//        Thread.sleep(10);
//      }
//      catch (InterruptedException ex)
//      {
//        System.err.print("ALARM!");
//      }
//      value = (int) (1 + 6 * Math.random());
//      iPublisher.submit(value);
//    }
//  }
}
