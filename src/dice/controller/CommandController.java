/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dice.controller;

import dice.controller.command.CommandInvoker;
import dice.controller.command.CommandStart;
import dice.controller.command.CommandStop;
import dice.model.DiceModel;
import dice.view.DiceView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Command controller for command design pattern
 */
public class CommandController implements ActionListener
{

  private DiceView view;
  private DiceModel model;
  private CommandInvoker invoker;

  /**
   * Constructor for CommandController, sets view, model and invoker to given
   * parameters
   *
   * @param view
   * @param model
   * @param invoker
   */
  public CommandController(DiceView view, DiceModel model, CommandInvoker invoker)
  {
    this.view = view;
    this.model = model;
    this.invoker = invoker;
  }

  /**
   * Registers the events for all UI elements
   */
  public void registerEvents()
  {
    view.getBtnStart().addActionListener(this);
    view.getBtnStop().addActionListener(this);
  }

  /**
   * Registers commands for UI elements
   */
  public void registerCommands()
  {
    CommandStart cmdStart = new CommandStart(view, model);
    CommandStop cmdStop = new CommandStop(view, model);

    invoker.addCommand(view.getBtnStart(), cmdStart);
    invoker.addCommand(view.getBtnStop(), cmdStop);
  }

  /**
   * Executes or the commands
   *
   * @param evt
   */
  @Override
  public void actionPerformed(ActionEvent evt)
  {
    Object key = evt.getSource();
    invoker.executeCommand(key);
  }
}
