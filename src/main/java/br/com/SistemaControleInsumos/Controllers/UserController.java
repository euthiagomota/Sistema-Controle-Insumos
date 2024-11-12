package br.com.SistemaControleInsumos.Controllers;

import br.com.SistemaControleInsumos.Dtos.User.ResponseUserDto;
import br.com.SistemaControleInsumos.Dtos.User.UpdateUserDto;
import br.com.SistemaControleInsumos.Entities.User;
import br.com.SistemaControleInsumos.Services.UserService;
import br.com.SistemaControleInsumos.Dtos.User.RequestUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<ResponseUserDto> createUser(@RequestBody RequestUserDto userDto) {
        User user = this.userService.createUser(userDto);
        ResponseUserDto responseUserDto = new ResponseUserDto(
                user.getId(),
                user.getCreateAt(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getAge()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(responseUserDto);
    }

    @GetMapping
    public ResponseEntity<List<ResponseUserDto>> findAllUsers() {
        List<ResponseUserDto> users = this.userService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> findUserById(@PathVariable UUID id) {
    Optional<User> user = this.userService.findById(id);
    return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, UpdateUserDto updateUserDto) {
        User user = this.userService.update(id, updateUserDto);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable UUID id) {
       Boolean isDeleted = this.userService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(isDeleted);
    }

}
