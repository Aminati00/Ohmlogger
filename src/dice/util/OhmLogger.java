/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dice.util;

import java.time.LocalDateTime;
import java.util.logging.*;

/**
 *
 * @author le
 */
public class OhmLogger 
{
  private static Logger lg = null;
  
  private OhmLogger()
  {
  }
  
  public static Logger getLogger()
  {
    if (lg == null)
    {
      lg = Logger.getLogger("OhmLogger");
      initLogger();
    }
    return lg;
  }
  
  private static void initLogger()
  {
    //FileHandler fh = new FileHandler();
    // Einstellungen in Properties-Datei LOG_FILE="/tmp/log.txt"
    ConsoleHandler ch = new ConsoleHandler();
    ch.setFormatter(new OhmFormatter());
    //lg.addHandler(ch);
  }
}

class OhmFormatter extends SimpleFormatter
{
  @Override
  public String format(LogRecord record)
  {
    
    
    String logLine = "";
    LocalDateTime ldt = LocalDateTime.now();
    logLine += ldt.toString();
    logLine += "|";
    logLine += "\n";
    return logLine;
    
    //    logLine += ";" + record.getMessage();
  }
}
