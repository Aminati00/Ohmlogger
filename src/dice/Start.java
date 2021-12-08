/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dice;



import dice.controller.CommandController;
import dice.controller.ValueAdapter;
import dice.controller.command.CommandInvoker;
import dice.model.DiceModel;
import dice.view.DiceView;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
*
* @author marco
*/

public class Start
{
  public Start()
  {
    DiceView view = new DiceView();
    
    DiceModel model = new DiceModel();
    model.subscribe();
    
    CommandInvoker invoker = new CommandInvoker();
    
    CommandController commandctrl = new CommandController(view, model, invoker);
    commandctrl.registerCommands();
    commandctrl.registerEvents();
    
    ValueAdapter adapter = new ValueAdapter(model, view);
    adapter.subscribe();
    
    view.setVisible(true);
}



  public static void main(String[] args)
  {
  try
  {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
  }
  catch (Exception ex)
  {
    JOptionPane.showMessageDialog(null, ex.toString());
  }
  
  new Start();
  }



}


