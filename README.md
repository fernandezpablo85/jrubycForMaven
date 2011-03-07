# JRuby's compiler (jrubyc) maven plugin #

### Why? ###

JRuby is cool. 

Documentation on how to add JRuby to your existing java projects is not cool.

Let's try to change that (a little bit at least).

### Add ruby to your maven-powered Java projects ###

If you are using maven as a build tool, this plugin will help you add ruby to your project very easily.

### Installation ###

You need to have [JRuby installed](http://jruby.org/getting-started)

Then just add this to your pom.xml:

> <plugin>
>  <groupId>org.jruby.compile.plugin</groupId>
>  <artifactId>maven-jruby-plugin</artifactId>
>  <version>1.0</version>
>  <executions>
>    <execution>
>      <goals>
>        <goal>jruby-compile</goal>
>      </goals>
>    </execution>
>  </executions>
> </plugin>

And that's it :D

### How it works ###

This plugin will fire in the `generate-sources` phase. 

It will search for ruby files in the folder:

> src/main/ruby

And "compile" them to java files in:

> src/main/java

Then the maven `compile` phase will kick in and take care of the rest. Smooth.

### What now? ###

For now, this is it. I'm going to add a tutorial on how to invoke a ruby class implementing a java interface (something that is strangely missing in the internet at least at this moment) using the plugin so hopefully I can inspire you to start giving JRuby a try. It's worth it.

### About the author ###

My name is Pablo Fernandez. I live in Buenos Aires argentina.

You can follow me on twitter at @fernandezpablo

Or ping me (gtalk) / email me at fernandezpablo85 at gmail dot com

For professional contact, this is my [LinkedIn Profile](http://www.linkedin.com/in/fernandezpablo85)