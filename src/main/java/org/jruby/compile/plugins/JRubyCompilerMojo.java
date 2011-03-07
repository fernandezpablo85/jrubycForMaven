package org.jruby.compile.plugins;

import org.apache.maven.plugin.*;
import org.jruby.compile.plugins.exceptions.*;
import org.jruby.compile.plugins.util.*;

/**
 * Compiles ruby source files into .java files just before the compilation
 * target.
 * 
 * @goal jruby-compile
 * @phase generate-sources
 * @author Pablo Fernandez
 */
public class JRubyCompilerMojo extends AbstractMojo
{
  private static final String COMPILE_COMMAND = "jrubyc --java %s -t %s";

  private static final String UNEXPECTED_ERROR = "Unexpected exception: ";
  private static final String COMMAND_LINE_ERROR = "Error while executing a command line instruction";
  private static final String ERROR_CODE_MESSAGE = "Command line instruction returned with an error code";
  private static final String COMPILER_NOT_FOUND = "Couldn't find 'jrubyc' command. Please check that you have $JRUBY_HOME/bin included in your $PATH";
  
  /**
   * Ruby sources directory
   * 
   * @parameter expression = "${basedir}/src/main/ruby"
   * @required
   */
  protected String rubySourcesDirectory;
  
  /**
   * Java sources directory
   * 
   * @parameter expression = "${basedir}/src/main/java"
   * @required
   */
  protected String javaSourcesDirectory;

  public void execute() throws MojoExecutionException, MojoFailureException
  {
    if(!CommandLineUtils.isJRubyCompilerPresent())
    {
      getLog().error(COMPILER_NOT_FOUND);
      return;
    }
    
    // Create and log the command to be executed
    String command = String.format(COMPILE_COMMAND, rubySourcesDirectory, javaSourcesDirectory);
    getLog().info("Running command: " + command);
    
    // Execute the command and trap possible exceptions
    try
    {
      CommandLineUtils.runCommand(command);  
    }
    catch(NonZeroReturnException nze)
    {
      getLog().error(ERROR_CODE_MESSAGE, nze);
    }
    catch(CommandLineExecutionException cmle)
    {
      getLog().error(COMMAND_LINE_ERROR, cmle);
    }
    catch(Exception e)
    {
      getLog().error(UNEXPECTED_ERROR, e);
    }
  }
}