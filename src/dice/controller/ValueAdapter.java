/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dice.controller;

import dice.model.DiceModel;
import dice.view.DiceView;
import java.util.concurrent.Flow;

/**
 * Value Adapter (Subscriber publisher design pattern)
 * @author marco
 */
public class ValueAdapter implements Flow.Subscriber<Integer[]>
{
  private DiceView view;
  private DiceModel model;
  private Flow.Subscription subscription;
  
  /**
   * Constructor des Valueadapter
   * @param model : DiceModel Objekt
   * @param view  : DiceView Objekt
   */
  public ValueAdapter(DiceModel model, DiceView view)
  {
    this.view = view;
    this.model = model;
  }
  
  /**
   * Methode um beim model zu subscriben
   */
  public void subscribe()
  {
    model.addValueSubscription(this);
  }
  
  
  /**
   * 
   * Setzt die subscription und fragt an beim publisher
   * @param subscription : Subscription Objekt
   */
  @Override
  public void onSubscribe(Flow.Subscription subscription)
  {
    this.subscription = subscription;
    this.subscription.request(1);
  }

  /**
   * Setzt die Werte in den Labels der View und fragt beim publisher an
   * @param item : Integer 
   */
  @Override
  public void onNext(Integer[] item)
  {
    view.getLbNumber1().setText(String.valueOf(item[0]));
    view.getLbNumber2().setText(String.valueOf(item[1]));
    view.getLbNumber3().setText(String.valueOf(item[2]));
    
    subscription.request(1);
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
  
