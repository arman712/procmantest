package decorators;

import domain.User;
import exceptions.AuthenticateException;
import io.jooby.Route;
import io.jooby.StatusCode;
import io.jooby.exception.StatusCodeException;

import javax.annotation.Nonnull;

public class AuthenticateDec implements Route.Decorator {
    @Nonnull
    @Override
    public Route.Handler apply(@Nonnull Route.Handler next) {

        return ctx -> {
           if(!ctx.getRequestPath().equals("/login") && ctx.headerMap().get("auth") == null){
               throw new StatusCodeException(StatusCode.UNAUTHORIZED);
           }
           return  next.apply(ctx);

        };
    }
}
