package br.com.SistemaControleInsumos.Controllers;

import br.com.SistemaControleInsumos.Entities.User;
import br.com.SistemaControleInsumos.Services.UserService;
import br.com.SistemaControleInsumos.UserDTOs.RequestUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody RequestUserDto userDto) {
        User user = this.userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
