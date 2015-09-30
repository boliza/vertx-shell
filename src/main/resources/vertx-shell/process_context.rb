require 'vertx-shell/session'
require 'vertx-shell/tty'
require 'vertx/util/utils.rb'
# Generated from io.vertx.ext.shell.process.ProcessContext
module VertxShell
  #  @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
  class ProcessContext
    # @private
    # @param j_del [::VertxShell::ProcessContext] the java delegate
    def initialize(j_del)
      @j_del = j_del
    end
    # @private
    # @return [::VertxShell::ProcessContext] the underlying java delegate
    def j_del
      @j_del
    end
    #  @return the tty assocated with this process
    # @return [::VertxShell::Tty]
    def tty
      if !block_given?
        return ::Vertx::Util::Utils.safe_create(@j_del.java_method(:tty, []).call(),::VertxShell::Tty)
      end
      raise ArgumentError, "Invalid arguments when calling tty()"
    end
    #  @return the shell session
    # @return [::VertxShell::Session]
    def session
      if !block_given?
        return ::Vertx::Util::Utils.safe_create(@j_del.java_method(:session, []).call(),::VertxShell::Session)
      end
      raise ArgumentError, "Invalid arguments when calling session()"
    end
    #  End the process.
    # @param [Fixnum] status the termination status
    # @return [void]
    def end(status=nil)
      if status.class == Fixnum && !block_given?
        return @j_del.java_method(:end, [Java::int.java_class]).call(status)
      end
      raise ArgumentError, "Invalid arguments when calling end(status)"
    end
  end
end