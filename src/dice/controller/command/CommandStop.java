/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dice.controller.command;

import dice.model.DiceModel;
import dice.view.DiceView;


/**
 * Class for command to stop the dice
 */
public class CommandStop implements CommandInterface
{

  private DiceView view;
  private DiceModel model;

  /**
   * Constructor which sets view and model to given parameters
   *
   * @param view
   * @param model
   */
  public CommandStop(DiceView view, DiceModel model)
  {
    this.view = view;
    this.model = model;
  }

  /**
   * Calls stop method in model
   */
  @Override
  public void execute()
  {
    model.stop();
  }
}