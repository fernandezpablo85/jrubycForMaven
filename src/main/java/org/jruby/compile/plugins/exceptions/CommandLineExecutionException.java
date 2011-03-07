package org.jruby.compile.plugins.exceptions;

public class CommandLineExecutionException extends Exception
{
  public CommandLineExecutionException(Exception e)
  {
    super(e);
  }
}
