package app;

import decorators.AuthenticateDec;
import domain.User;
import io.jooby.AccessLogHandler;
import io.jooby.Jooby;
import io.jooby.ModelAndView;
import io.jooby.handlebars.HandlebarsModule;
import io.jooby.hikari.HikariModule;
import io.jooby.jdbi.JdbiModule;
import io.jooby.json.JacksonModule;
import mappers.UserMapper;
import org.jdbi.v3.core.Handle;



public class App extends Jooby {


  {

  }


  {
    install(new HikariModule());
    install(new JdbiModule());
    install(new JacksonModule());
    install(new HandlebarsModule());


  }

  {
    decorator(new AccessLogHandler());
    decorator(new AuthenticateDec());
    get("/users", ctx -> {
      try (Handle handle = require(Handle.class)) {
        return handle.inTransaction(h -> h.createQuery("select * from users")
                .registerRowMapper(new UserMapper()).mapTo(User.class).list());
      }

    });
  }


  {

    get("/login",ctx -> new ModelAndView("index.html"));
  }


  {

    get("/users2", ctx -> {
      try (Handle handle = require(Handle.class)) {
        return handle.inTransaction(h -> h.createQuery("select * from users")
                .map(new UserMapper()).list());
      }

    });
  }

  {
    decorator(new AccessLogHandler());
    get("/", ctx -> "Welcome to Jooby!");
  }





  public static void main(final String[] args) {
    runApp(args, App::new);
  }

}
