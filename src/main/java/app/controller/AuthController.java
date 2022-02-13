package app.controller;

import app.dto.LoginRequest;
import app.dto.Response;
import app.dto.SignupRequest;
import app.entity.AppUser;
import app.repository.AppUserRepository;
import app.service.AppUserService;
import app.service.AuthService;
import com.google.gson.Gson;
import io.jooby.Context;
import io.jooby.MediaType;
import io.jooby.annotations.*;
import io.jooby.exception.UnauthorizedException;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Tag(name = "Controller", description = "Controller for user login and logout")
@Path("/")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private static final Gson gson = new Gson();
    private AuthService authService;
    private AppUserService appUserService;

    public AuthController(AuthService authService, AppUserService appUserService) {
        this.authService = authService;
        this.appUserService = appUserService;
    }


    /**
     * Endpoint for logging in
     *
     * @param loginRequest model object, username and password
     * @param context      the jooby HTTP request context,
     *                     lets you interact with and manipulate the HTTP request and HTTP response
     * @return response object
     */
    @POST("/auth/login")
    @Produces(MediaType.JSON)
    @Consumes(MediaType.JSON)
    public Response login(@RequestBody LoginRequest loginRequest, @ContextParam Context context) {
        logger.info("login request received");
        var user = authService.getUserByUsername(loginRequest.getUsername());
        if (user.getPassword().equals(loginRequest.getPassword())) {
            var cookie = authService.generateCookie(user);
            context.setResponseCookie(cookie);
            return new Response("login success");
        }
        return new Response("login failed");
    }


    /**
     * Endpoint for logout
     *
     * @param jwt     the jwt cookie obtained when logged in
     * @param context the jooby HTTP request context,
     *                lets you interact with and manipulate the HTTP request and HTTP response
     * @return response object
     */
    @POST("/auth/logout")
    @Produces(MediaType.JSON)
    public Response logout(@CookieParam("JWT_COOKIE") String jwt, @ContextParam Context context) {
        logger.info("logout request received");
        var cookie = authService.generateExpiredCookie();
        context.setResponseCookie(cookie);
        return new Response("logout success");
    }


    /**
     * Endpoint for getting user information when user is logged in
     *
     * @param jwt the jwt cookie obtained when logged in
     * @return user information JSON
     */
    @GET("/home")
    @Produces(MediaType.JSON)
    public String getUserInfo(@CookieParam("JWT_COOKIE") String jwt) {
        if (authService.isTokenValid(jwt)) {
            var username = authService.getUsernameClaim(jwt);
            var user = authService.getUserByUsername(username);
            return gson.toJson(user);
        }
        throw new UnauthorizedException("not authorized to access user info");
    }

    /**
     * Endpoint for logging in
     *
     * @param signupRequest model object, username and password
     * @param context       the jooby HTTP request context,
     *                      lets you interact with and manipulate the HTTP request and HTTP response
     * @return response object
     */
    @POST("/auth/signup")
    @Produces(MediaType.JSON)
    @Consumes(MediaType.JSON)
    public Response signUp(@RequestBody SignupRequest signupRequest, @ContextParam Context context) throws Exception {
        logger.info("signup request received");
        var user = authService.getUserByUsername(signupRequest.getUsername());
        if (user != null) {
            throw new Exception("user " + user.getUsername() + " is already taken");
        }
        AppUser appUser = new AppUser(signupRequest.getUsername(), signupRequest.getPassword());
        appUserService.saveAppUser(appUser);
        return new Response("User Registered");
    }


}
