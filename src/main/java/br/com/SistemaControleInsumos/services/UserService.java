package br.com.SistemaControleInsumos.services;

import br.com.SistemaControleInsumos.dtos.user.ResponseUserDto;
import br.com.SistemaControleInsumos.dtos.user.UpdateUserDto;
import br.com.SistemaControleInsumos.entities.User;
import br.com.SistemaControleInsumos.enuns.UserRole;
import br.com.SistemaControleInsumos.repositories.UserRepository;
import br.com.SistemaControleInsumos.dtos.user.RequestUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.*;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public ResponseUserDto createUser(RequestUserDto userDto) {

        Optional<User> existingUser = userRepository.findByEmail(userDto.email());
        if (existingUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with this email already exists");
        }

        if (!userDto.password().equals(userDto.confirmPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The passwords is not equals.");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(userDto.password());

        User user = new User();
        user.setName(userDto.name());
        user.setAge(userDto.age());
        user.setEmail(userDto.email());
        user.setPassword(encryptedPassword);
        user.setRole(userDto.role() != null ? userDto.role() : UserRole.USER);

        User userSaved = this.userRepository.save(user);

        ResponseUserDto response = new ResponseUserDto(
                userSaved.getId(),
                userSaved.getCreateAt(),
                userSaved.getName(),
                userSaved.getEmail(),
                userSaved.getAge(),
                userSaved.getRole()
        );

        return response;

    }

    public List<ResponseUserDto> findAll() {
        try {
            List<User> users = this.userRepository.findAll();

            List<ResponseUserDto> responseDtos = new ArrayList<>();
            for (User user : users) {
                ResponseUserDto responseUserDto = new ResponseUserDto(
                        user.getId(),
                        user.getCreateAt(),
                        user.getName(),
                        user.getEmail(),
                        user.getAge(),
                        user.getRole()
                );
                responseDtos.add(responseUserDto);
            }
            return responseDtos;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "An error occurred while finding the users", e);
        }

    }

    public ResponseUserDto findById(UUID id) {
        try {
            Optional<User> userOptional = this.userRepository.findById(id);
            if (userOptional.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found!");
            }
            User user = userOptional.get();

            ResponseUserDto response = new ResponseUserDto(
                    user.getId(),
                    user.getCreateAt(),
                    user.getName(),
                    user.getEmail(),
                    user.getAge(),
                    user.getRole()
            );


            return response;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "An error occurred while finding the user", e);
        }

    }

    public ResponseUserDto update(UUID id, UpdateUserDto updateUserDto) {

        Optional<User> optionalUser = this.userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found!");
        }
        User userToUpdate = optionalUser.get();

        if (!passwordEncoder.matches(updateUserDto.oldPassword(), userToUpdate.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect old password.");
        }
        if (!updateUserDto.password().equals(updateUserDto.confirmPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The passwords not is equals.");
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(updateUserDto.password());

        User user = new User();
        user.setAge(updateUserDto.age());
        user.setName(updateUserDto.name());
        user.setId(userToUpdate.getId());
        user.setRole(updateUserDto.role() != null ? updateUserDto.role() : userToUpdate.getRole());
        user.setEmail(userToUpdate.getEmail());
        user.setPassword(encryptedPassword);
        user.setCreateAt(userToUpdate.getCreateAt());

        User userUpdated = this.userRepository.save(user);

        ResponseUserDto response = new ResponseUserDto(
                userUpdated.getId(),
                userUpdated.getCreateAt(),
                userUpdated.getName(),
                userUpdated.getEmail(),
                userUpdated.getAge(),
                userUpdated.getRole()
        );

        return response;
    }

    public Boolean delete(UUID id) {
        try {
            Optional<User> optionalUser = this.userRepository.findById(id);
            if (optionalUser.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            }
            this.userRepository.delete(optionalUser.get());
            return true;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "An error occurred while deleting the user", e);
        }
    }

    public List<ResponseUserDto> dateFilter(Timestamp initialDate, Timestamp finishDate) {
        try {
            List<User> users = this.userRepository.findByCreateAtBetween(
                    initialDate,
                    finishDate
            );
            List<ResponseUserDto> responseDtos = new ArrayList<>();

            for (User user : users) {
                ResponseUserDto responseUserDto = new ResponseUserDto(
                        user.getId(),
                        user.getCreateAt(),
                        user.getName(),
                        user.getEmail(),
                        user.getAge(),
                        user.getRole()
                );
                responseDtos.add(responseUserDto);
            }
            return responseDtos;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "An error occurred while finding the user by date", e);
        }
    }

}
