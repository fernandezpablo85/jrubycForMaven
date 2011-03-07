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
    String command = String.format("jrubyc --java %s -t %s",rubySourcesDirectory, javaSourcesDirectory);
    getLog().info("Running command: " + command);
    
    // Execute the command and trap possible exceptions
    try
    {
      CommandLineUtils.runCommand(command);  
    }
    catch(NonZeroReturnException nze)
    {
      getLog().error("Command line instruction returned with an error code.", nze);
    }
    catch(CommandLineExecutionException cmle)
    {
      getLog().error("Error while executing a command line instruction", cmle);
    }
    catch(Exception e)
    {
      getLog().error("Unexpected exception: ", e);
    }
  }
}