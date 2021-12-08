/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dice.model;

import dice.model.zähler.Zähler;
import dice.util.OhmLogger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.logging.Logger;

/**
 * DiceModel übergibt ein Integerarray zum Valuadapter und subscribed bei 
 * drei Zähler-Objekten, welche Integerarrays mit deren ID und Nummer übergeben
 */
public class DiceModel implements Flow.Subscriber<Integer[]>
{
  private static Logger lg = OhmLogger.getLogger();
  private Flow.Subscription[] subscription;
  //private ExecutorService eService;
  private SubmissionPublisher<Integer[]> iPublisher;
  private int counter;
  private Integer[] values;
  private Zähler[] zähler;
  //private boolean stop;
  //private int value;
  
  
  /**
   * Konstruktor
   */
  public DiceModel()
  {
    //stop = false;
    //value = 0;
    //eService = Executors.newScheduledThreadPool(3); //3 Threads erstellen für 3 Zufallszahlen
    
    counter = 0;
    iPublisher = new SubmissionPublisher<>();
    subscription = new Flow.Subscription[3];
    zähler = new Zähler[3];
    zähler[0] = new Zähler(0);
    zähler[1] = new Zähler(1);
    zähler[2] = new Zähler(2);
    values = new Integer[]{0,0,0};
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
   * subscribe-Methode:
   * Hier wird bei den drei Zähler-Objekten subscribed
   */
  public void subscribe()
  {
    for (Zähler z : zähler)
    {
      z.addValueSubscription(this);
    }
  }
  
  /**
   * start-Methode:
   * Jeder Zähler wird hier einzeln gestartet
   */
  public void start()
  {
    for (Zähler z : zähler)
    {
      z.start();
    }
  }

  /**
   * stop-Methode:
   * jeder Zähler wird hier einzeln gestoppt
   */
  public void stop()
  {
    
    for (Zähler z : zähler)
    {
      z.stop();
    }
    System.err.print("\n");
  }

  
  /**
   * gibt den Wert zu jeder subscription
   * @param subscription : Subscribtion-Objekt
   */
  @Override
  public void onSubscribe(Flow.Subscription subscription)
  {
    this.subscription[counter] = subscription;
    this.subscription[counter].request(1);
    counter += 1;
    
  }

  /**
   * setzt die Werte in "values" auf die gegebenen Werte
   * @param item : Integerarray mit ID und Wert
   */
  @Override
  public void onNext(Integer[] item)
  {
    values[item[0]] = item[1];
    iPublisher.submit(values);
    //lg.info("Nachricht übermittelt");
    subscription[item[0]].request(1);
    
  }

  
  @Override
  public void onError(Throwable throwable)
  {
  }

  @Override
  public void onComplete()
  {
  }
          
  
  
  
  
  


  
  
}
