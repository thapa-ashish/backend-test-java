package app;

import app.config.DBConfig;
import app.controller.AuthController;
import app.controller.FruitController;
import app.entity.AppUser;
import app.repository.AppUserRepository;
import app.repository.FruitRepository;
import app.repository.FruitStandRepository;
import app.service.AppUserService;
import app.service.AuthService;
import app.service.FruitService;
import app.service.FruitStandService;
import io.jooby.*;
import io.jooby.exception.UnauthorizedException;
import io.jooby.hibernate.HibernateModule;
import io.jooby.hikari.HikariModule;
import io.jooby.json.GsonModule;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;

@OpenAPIDefinition(
        info = @Info(
                title = "Onye backend test API",
                description = "A small API used for code tests",
                license = @License(
                        name = "©2022 Onye LLT, all rights reserved",
                        url = "https://onyeone.com/"
                ),
                version = "1.0"
        )
)
public class App extends Jooby {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    {
        install(new GsonModule());
        install(new OpenAPIModule());

        install(new HikariModule(DBConfig.hikariDataSource()));
        install(new HibernateModule(AppUser.class));

        EntityManager entityManager = require(EntityManager.class);
        AppUser appUser = new AppUser("username", "password");
        entityManager.persist(appUser);

        AppUserRepository appUserRepository = new AppUserRepository(entityManager);
        AuthService authService = new AuthService(appUserRepository);
        AppUserService appUserService = new AppUserService(appUserRepository);

        FruitRepository fruitRepository = new FruitRepository(entityManager);
        FruitService fruitService = new FruitService(fruitRepository);

        FruitStandRepository fruitStandRepository = new FruitStandRepository(entityManager);
        FruitStandService fruitStandService = new FruitStandService(fruitStandRepository);

        Cors cors = new Cors();
        cors.allowOrigin("*");
        decorator(new CorsHandler(cors));
        mvc(new AuthController(authService,appUserService));
        mvc(new FruitController(fruitService,fruitStandService));

        error(UnauthorizedException.class, (ctx, cause, statusCode) -> {
            logger.error(String.format("unauthorized: %s", cause.getMessage()));
            ctx.send(StatusCode.UNAUTHORIZED);
        });
    }

    public static void main(final String[] args) {
        runApp(args, App::new);
    }

}
