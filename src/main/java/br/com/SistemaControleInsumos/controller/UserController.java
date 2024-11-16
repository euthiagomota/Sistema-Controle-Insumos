package br.com.SistemaControleInsumos.controller;

import br.com.SistemaControleInsumos.dtos.user.RequestUserDto;
import br.com.SistemaControleInsumos.dtos.user.ResponseUserDto;
import br.com.SistemaControleInsumos.dtos.user.UpdateUserDto;
import br.com.SistemaControleInsumos.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@Tag(name = "users", description = "this route is to do the control of users.")
public class UserController {

    @Autowired
    UserService userService;

    @Operation(summary = "create user", description = "This route is to create users")
    @ApiResponse(responseCode = "201", description = "Success to create user")
    @ApiResponse(responseCode = "400", description = "invalid data")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PostMapping("/register")
    public ResponseEntity<ResponseUserDto> createUser(@RequestBody RequestUserDto userDto) {
        ResponseUserDto user = this.userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping
    public ResponseEntity<List<ResponseUserDto>> findAllUsers() {
        List<ResponseUserDto> users = this.userService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseUserDto> findUserById(@PathVariable UUID id) {
        ResponseUserDto user = this.userService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseUserDto> updateUser(@PathVariable UUID id, UpdateUserDto updateUserDto) {
        ResponseUserDto user = this.userService.update(id, updateUserDto);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable UUID id) {
        Boolean isDeleted = this.userService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(isDeleted);
    }

    @GetMapping("/date-filter")
    public ResponseEntity<List<ResponseUserDto>> dateFilter(
            @RequestParam("initialDate") String initialDateStr,
            @RequestParam("finishDate") String finishDateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            Timestamp initialDate = new Timestamp(sdf.parse(initialDateStr).getTime());
            Timestamp finishDate = new Timestamp(sdf.parse(finishDateStr).getTime());
            List<ResponseUserDto> users = this.userService.dateFilter(initialDate, finishDate);
            return ResponseEntity.status(HttpStatus.OK).body(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}
