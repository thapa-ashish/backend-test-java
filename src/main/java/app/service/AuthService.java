package app.service;

import app.entity.AppUser;
import app.repository.AppUserRepository;
import com.google.gson.Gson;
import io.jooby.Cookie;
import io.jooby.exception.UnauthorizedException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

public class AuthService {

    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private final AppUserRepository appUserRepository;

    public AuthService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    /**
     * Method to generate a cookie once the user has been authenticated
     *
     * @param appUser the user object
     * @return cookie
     */
    public Cookie generateCookie(AppUser appUser) {
        var header = Jwts.header().setType("JWT");
        var jwt = Jwts.builder()
                .setHeader((Map<String, Object>) header)
                .setIssuer("backend-test")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // 30 minutes
                .setSubject("backend-test")
                .claim("username", appUser.getUsername())
                .signWith(key)
                .compact();

        var cookie = new Cookie("JWT_COOKIE", jwt);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        return cookie;
    }


    /**
     * Check if a token is valid (not expired and encoded with the correct key)
     *
     * @param jwt the jwt cookie obtained when logged in
     * @return true if token is valid, otherwise false
     */
    public boolean isTokenValid(String jwt) {
        try {
            var body = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
            return Timestamp.valueOf(LocalDateTime.now())
                    .before(Timestamp.from(Instant.ofEpochSecond((Integer) body.get("exp"))));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get the claim from token containing the username of the logged in user
     *
     * @param jwt the jwt cookie obtained when logged in
     * @return The username if username claim present otherwise throws exception
     * @throws UnauthorizedException if no username claim in token
     */
    public String getUsernameClaim(String jwt) throws UnauthorizedException {
        try {
            var body = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
            return appUserRepository.getUserByUsername((String) body.get("username")).getUsername();
        } catch (Exception e) {
            throw new UnauthorizedException("not authorized to access user info");
        }
    }

    /**
     * Method to generate a cookie that is instantly expired, "overwrites" the cookie obtained when logged in
     *
     * @return the expired cookie
     */
    public Cookie generateExpiredCookie() {
        var header = Jwts.header().setType("JWT");
        header.setType("JWT");
        var now = new Date(System.currentTimeMillis());
        var jwt = Jwts.builder()
                .setHeader((Map<String, Object>) header)
                .setIssuer("backend-test")
                .setIssuedAt(now)
                .setExpiration(now)
                .setSubject("backend-test")
                .signWith(key)
                .compact();

        var cookie = new Cookie("JWT_COOKIE", jwt);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        return cookie;
    }


    public AppUser getUserByUsername(String username) {
        return appUserRepository.getUserByUsername(username);
    }
}
