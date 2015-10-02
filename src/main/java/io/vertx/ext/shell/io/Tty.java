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

package io.vertx.ext.shell.io;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Handler;

/**
 * Provide interactions with the Shell TTY.
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface Tty {

  /**
   * @return the current width, i.e the number of rows or {@literal -1} if unknown
   */
  int width();

  /**
   * @return the current height, i.e the number of columns or {@literal -1} if unknown
   */
  int height();

  /**
   * Set a stream on the standard input to read the data.
   *
   * @param stdin the standard input
   * @return this object
   */
  @Fluent
  Tty setStdin(Stream stdin);

  /**
   * Set an handler the standard input to read the data in String format.
   *
   * @param stdin the standard input
   * @return this object
   */
  @Fluent
  Tty setStdin(Handler<String> stdin);

  /**
   * @return the standard output for emitting data
   */
  Stream stdout();

  /**
   * Set an event handler to be notified by Shell events.
   *
   * @param eventType the event type
   * @param handler
   * @return
   */
  @Fluent
  Tty eventHandler(EventType eventType, Handler<Void> handler);

}
