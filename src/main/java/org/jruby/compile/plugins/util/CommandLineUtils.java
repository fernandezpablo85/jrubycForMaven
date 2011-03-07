package org.jruby.compile.plugins.util;

import org.jruby.compile.plugins.exceptions.*;

public class CommandLineUtils
{
  /**
   * Checks if the jrubyc command is present in the machine
   * 
   * @return true if present, false otherwise
   */
  public static boolean isJRubyCompilerPresent()
  {
    try
    {
      CommandLineUtils.runCommand("jrubyc --help");
      return true;
    }
    catch(Exception e)
    {
      return false;
    }
  }
  
  /**
   * Runs a command line instruction.
   * 
   * @param command command line instruction to execute
   * @throws CommandLineExecutionException if there is an unexpected error while executing
   * @throws NonZeroReturnException if the operation returns an error code
   */
  public static void runCommand(String command) throws CommandLineExecutionException, NonZeroReturnException
  {
    Runtime r = Runtime.getRuntime();
    try
    {
      Process p = r.exec(command);
      int result = p.waitFor();
      if(result !=0)
      {
        throw new NonZeroReturnException();
      }
    }
    catch(Exception e)
    {
      throw new CommandLineExecutionException(e);
    }
  }
}
