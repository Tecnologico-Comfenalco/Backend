package com.tecno_comfenalco.pa.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tecno_comfenalco.pa.security.dto.requests.LoginRequestDto;
import com.tecno_comfenalco.pa.security.dto.responses.LoginResponseDto;
import com.tecno_comfenalco.pa.shared.utils.result.Result;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "/auth")
@PreAuthorize("permitAll()")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Value("${jwt.expiration-ms}")
    private Long expirationMs;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request, HttpServletResponse response) {
        // Delegate authentication logic to AuthenticationService and keep HTTP handling
        // here
        Result<LoginResponseDto, Exception> result = authenticationService.loginUser(request);

        if (result == null || !result.isOk()) {
            // AuthenticationService returns an error Result with an Exception
            return ResponseEntity.status(401).body(new LoginResponseDto("Authentication failed", null));
        }

        LoginResponseDto loginResp = result.getValue();

        // preserve cookie creation behavior from previous implementation
        long defaultExpiration = 60 * 60 * 1000L; // 1 hour default
        long configuredExpiration = expirationMs != null ? expirationMs.longValue() : defaultExpiration;
        long expirationTime = request.rememberMe() ? 7L * 24 * 60 * 60 * 1000 : configuredExpiration;
        String token = loginResp.token();

        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // ⚠️ usa 'true' solo si estás en HTTPS, sino no se envía en localhost
        cookie.setPath("/"); // permite que se envíe en todas las rutas
        cookie.setMaxAge((int) (expirationTime / 1000));

        // 💡 A partir de Java 11 puedes establecer SameSite manualmente:
        cookie.setAttribute("SameSite", "None"); // O "Lax" si estás sin HTTPS
        response.addCookie(cookie);

        return ResponseEntity.ok(new LoginResponseDto(loginResp.message(), token));
    }

    @GetMapping("/test")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Hello " + SecurityContextHolder.getContext().getAuthentication().getName());
    }

}
