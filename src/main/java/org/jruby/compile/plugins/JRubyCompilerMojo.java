package org.jruby.compile.plugins;

import java.io.*;
import java.util.*;

import org.apache.maven.plugin.*;

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
  /**
   * Ruby sources directory
   * 
   * @parameter expression = "${basedir}/src/main/ruby"
   * @required
   */
  protected String rubyBaseDirectory;
  
  /**
   * Ruby target directory
   * 
   * @parameter expression = "${basedir}/src/main/java"
   * @required
   */
  protected String javaBaseDirectory;

  public void execute() throws MojoExecutionException, MojoFailureException
  {
    List<File> rubySources = getRubySources();
    getLog().info("Converting " + rubySources.size() + " ruby source files to java in " + javaBaseDirectory);
    Runtime r = Runtime.getRuntime();
    try
    {
      System.out.println("jrubyc --java " + rubyBaseDirectory + " -t " + javaBaseDirectory);
      Process p = r.exec("jrubyc --java " + rubyBaseDirectory + " -t " + javaBaseDirectory);
      int result = p.waitFor();
      if(result !=0)
      {
        getLog().error("jrubyc exited with result code != 0");
      }
      else
      {
        getLog().info("Ruby compilation successful");
      }
    }
    catch(Exception e)
    {
      getLog().error(e.getMessage());
    }
  }
  
  private List<File> getRubySources()
  {
    File root = new File(rubyBaseDirectory);
    if(!root.exists())
    {
      getLog().warn("Seems that " + rubyBaseDirectory + " does not exist");
      return Arrays.asList();
    }
    List<File> rubySources = new ArrayList<File>();
    parseDirectory(root, rubySources);
    return rubySources;
  }
  
  private void parseDirectory(File directory, List<File> all)
  {
    for(File file : directory.listFiles())
    {
      if(file.isDirectory())
      {
        parseDirectory(file, all);
      }
      else
      {
        if(file.getName().endsWith(".rb"))
        {
          all.add(file);
        }
      }
    }
  }
  
  public static class RubyFileFilter implements FileFilter
  {
    public boolean accept(File file)
    {
      if(file.isDirectory())
      {
        return false;
      }
      else
      {
        return file.getName().endsWith(".rb");
      }
    }
  }
}