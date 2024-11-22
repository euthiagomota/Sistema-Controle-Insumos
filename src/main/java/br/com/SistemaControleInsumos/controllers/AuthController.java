package br.com.SistemaControleInsumos.controllers;


import br.com.SistemaControleInsumos.dtos.user.AuthenticationDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "auth", description = "this route is to do user authentication.")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Operation(summary = "authentication user", description = "This route is to do user authentication")
    @ApiResponse(responseCode = "200", description = "Success to authentication user")
    @ApiResponse(responseCode = "400", description = "invalid data")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid AuthenticationDto data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        this.authenticationManager.authenticate(usernamePassword);

        return ResponseEntity.status(HttpStatus.OK).body("successful login!");
    }
}
