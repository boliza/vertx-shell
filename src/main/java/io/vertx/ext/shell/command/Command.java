/*
 * Copyright 2014 Red Hat, Inc.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  and Apache License v2.0 which accompanies this distribution.
 *
 *  The Eclipse Public License is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  The Apache License v2.0 is available at
 *  http://www.opensource.org/licenses/apache2.0.php
 *
 *  You may elect to redistribute this code under either of these licenses.
 *
 *
 * Copyright (c) 2011-2013 The original author or authors
 * ------------------------------------------------------
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 *
 *     The Eclipse Public License is available at
 *     http://www.eclipse.org/legal/epl-v10.html
 *
 *     The Apache License v2.0 is available at
 *     http://www.opensource.org/licenses/apache2.0.php
 *
 * You may elect to redistribute this code under either of these licenses.
 *
 */

package io.vertx.ext.shell.command;

import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.cli.CLI;
import io.vertx.core.cli.Option;
import io.vertx.core.cli.annotations.CLIConfigurator;
import io.vertx.ext.shell.cli.Completion;
import io.vertx.ext.shell.command.impl.CommandBuilderImpl;

import java.util.Collections;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface Command {

  @GenIgnore
  static Command create(Class<? extends Command> clazz) {
    CLI cli = CLIConfigurator.define(clazz);
    cli.addOption(new Option().setArgName("help").setFlag(true).setShortName("h").setLongName("help"));

    boolean tmp = false;
    try {
      clazz.getDeclaredMethod("name");
      tmp = true;
    } catch (NoSuchMethodException ignore) {
    }
    boolean overridesName = tmp;

    tmp = false;
    try {
      clazz.getDeclaredMethod("cli");
      tmp = true;
    } catch (NoSuchMethodException ignore) {
    }
    boolean overridesCli = tmp;

    return new Command() {

      @Override
      public String name() {
        if (overridesName) {
          try {
            return clazz.newInstance().name();
          } catch (Exception ignore) {
            // Use cli.getName() instead
          }
        }
        return cli.getName();
      }

      @Override
      public CLI cli() {
        if (overridesCli) {
          try {
            return clazz.newInstance().cli();
          } catch (Exception ignore) {
            // Use cli instead
          }
        }
        return cli;
      }

      @Override
      public void process(CommandProcess process) {
        Command instance;
        try {
          instance = clazz.newInstance();
        } catch (Exception e) {
          process.end();
          return;
        }
        CLIConfigurator.inject(process.commandLine(), instance);
        instance.process(process);
      }

      @Override
      public void complete(Completion completion) {
        Command instance;
        try {
          instance = clazz.newInstance();
        } catch (Exception e) {
          Command.super.complete(completion);
          return;
        }
        instance.complete(completion);
      }
    };
  }

  /**
   * Create a new commmand, the command is responsible for managing the options and arguments via the
   * {@link CommandProcess#args() arguments}.
   *
   * @param name the command name
   * @return the command
   */
  static CommandBuilder builder(String name) {
    return new CommandBuilderImpl(name, null);
  }

  /**
   * Create a new commmand with its {@link io.vertx.core.cli.CLI} descriptor. This command can then retrieve the parsed
   * {@link CommandProcess#commandLine()} when it executes to know get the command arguments and options.
   *
   * @param cli the cli to use
   * @return the command
   */
  static CommandBuilder builder(CLI cli) {
    return new CommandBuilderImpl(cli.getName(), cli);
  }

  /**
   * @return the command name
   */
  default String name() {
    return null;
  }

  /**
   * @return the command line interface, can be null
   */
  default CLI cli() {
    return null;
  }

  void process(CommandProcess process);

  default void complete(Completion completion) {
    completion.complete(Collections.emptyList());
  }
}
