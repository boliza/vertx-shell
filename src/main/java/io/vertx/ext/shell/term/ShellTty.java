package io.vertx.ext.shell.term;

import io.termd.core.readline.KeyDecoder;
import io.termd.core.readline.Keymap;
import io.termd.core.readline.Readline;
import io.termd.core.telnet.TelnetTtyConnection;
import io.termd.core.util.Helper;
import io.vertx.ext.shell.Job;
import io.vertx.ext.shell.Shell;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Writer;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class ShellTty {

  final TelnetTtyConnection conn;
  final Shell shell;

  public ShellTty(TelnetTtyConnection conn, Shell shell) {
    this.conn = conn;
    this.shell = shell;
  }

  public void init() {
    InputStream inputrc = KeyDecoder.class.getResourceAsStream("inputrc");
    Keymap keymap = new Keymap(inputrc);
    Readline readline = new Readline(keymap);
    for (io.termd.core.readline.Function function : Helper.loadServices(Thread.currentThread().getContextClassLoader(), io.termd.core.readline.Function.class)) {
      readline.addFunction(function);
    }
    conn.write("Welcome to vertx-shell\r\n\r\n");
    read(readline);
  }

  public void read(Readline readline) {
    readline.readline(conn, "% ", line -> {
      shell.createJob(line, ar -> {
        if (ar.succeeded()) {
          Job job = ar.result();
          job.setStdout(conn::write);
          job.endHandler(code -> {
            read(readline);
          });
          job.run();
        } else {
          ar.cause().printStackTrace(new PrintWriter(new Writer() {
            @Override
            public void write(char[] cbuf, int off, int len) throws IOException {
              conn.write(new String(cbuf, off, len));
            }
            @Override
            public void flush() throws IOException {
            }
            @Override
            public void close() throws IOException {
            }
          }));
          read(readline);
        }
      });
    });
  }

  public void close() {
    //
  }
}