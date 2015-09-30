/*
 * Copyright 2014 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package io.vertx.rxjava.ext.shell.command;

import java.util.Map;
import io.vertx.lang.rxjava.InternalHelper;
import rx.Observable;
import java.util.List;
import io.vertx.rxjava.core.cli.CommandLine;
import io.vertx.rxjava.ext.shell.Session;
import io.vertx.rxjava.ext.shell.io.Stream;
import io.vertx.rxjava.ext.shell.io.Tty;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.ext.shell.cli.CliToken;
import io.vertx.core.Handler;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 *
 * <p/>
 * NOTE: This class has been automatically generated from the {@link io.vertx.ext.shell.command.CommandProcess original} non RX-ified interface using Vert.x codegen.
 */

public class CommandProcess extends Tty {

  final io.vertx.ext.shell.command.CommandProcess delegate;

  public CommandProcess(io.vertx.ext.shell.command.CommandProcess delegate) {
    super(delegate);
    this.delegate = delegate;
  }

  public Object getDelegate() {
    return delegate;
  }

  /**
   * @return the current Vert.x instance
   * @return 
   */
  public Vertx vertx() { 
    Vertx ret= Vertx.newInstance(this.delegate.vertx());
    return ret;
  }

  /**
   * @return the unparsed arguments tokens
   * @return 
   */
  public List<CliToken> argsTokens() { 
    List<CliToken> ret = this.delegate.argsTokens().stream().map(CliToken::newInstance).collect(java.util.stream.Collectors.toList());
    return ret;
  }

  /**
   * @return the actual string arguments of the command
   * @return 
   */
  public List<String> args() { 
    List<String> ret = this.delegate.args();
;
    return ret;
  }

  /**
   * @return the command line object or null
   * @return 
   */
  public CommandLine commandLine() { 
    CommandLine ret= CommandLine.newInstance(this.delegate.commandLine());
    return ret;
  }

  /**
   * @return the shell session
   * @return 
   */
  public Session session() { 
    Session ret= Session.newInstance(this.delegate.session());
    return ret;
  }

  public CommandProcess setStdin(Stream stdin) { 
    ( /* Work around for https://jira.codehaus.org/browse/GROOVY-6970 */ (io.vertx.ext.shell.io.Tty) delegate).setStdin((io.vertx.ext.shell.io.Stream) stdin.getDelegate());
    return this;
  }

  public CommandProcess eventHandler(String event, Handler<Void> handler) { 
    ( /* Work around for https://jira.codehaus.org/browse/GROOVY-6970 */ (io.vertx.ext.shell.io.Tty) delegate).eventHandler(event, handler);
    return this;
  }

  public CommandProcess write(String text) { 
    this.delegate.write(text);
    return this;
  }

  /**
   * End the process with the exit status 
   */
  public void end() { 
    this.delegate.end();
  }

  /**
   * End the process.
   * @param status the exit status.
   */
  public void end(int status) { 
    this.delegate.end(status);
  }


  public static CommandProcess newInstance(io.vertx.ext.shell.command.CommandProcess arg) {
    return arg != null ? new CommandProcess(arg) : null;
  }
}