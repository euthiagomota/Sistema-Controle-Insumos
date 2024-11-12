package br.com.SistemaControleInsumos.Services;

import br.com.SistemaControleInsumos.Dtos.User.ResponseUserDto;
import br.com.SistemaControleInsumos.Dtos.User.UpdateUserDto;
import br.com.SistemaControleInsumos.Entities.User;
import br.com.SistemaControleInsumos.Repositories.UserRepository;
import br.com.SistemaControleInsumos.Dtos.User.RequestUserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.*;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    //@Autowired
    //private BCryptPasswordEncoder passwordEncoder;

    public User createUser(RequestUserDto userDto) {
        try {
            Optional<User> existingUser = userRepository.findByEmail(userDto.email());
            if (existingUser.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with this email already exists");
            }
            if (!userDto.password().equals(userDto.confirmPassword())) {
                throw new IllegalArgumentException("The passwords not is equals.");
            }
            User user = new User();
            user.setName(userDto.name());
            user.setAge(userDto.age());
            user.setEmail(userDto.email());
            user.setPassword(userDto.password());
            System.out.println(user);
            this.userRepository.save(user);
            return user;
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "An error occurred while creating the user", e);
        }

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
                        user.getPassword(),
                        user.getAge()
                );
                responseDtos.add(responseUserDto);
            }
            return responseDtos;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "An error occurred while finding the users", e);
        }

    }

    public Optional<User> findById(UUID id) {
        try {
            Optional<User> user = this.userRepository.findById(id);
            if (user.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found!");
            }
            return user;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "An error occurred while finding the user", e);
        }

    }

    public User update(UUID id, UpdateUserDto updateUserDto) {
        try {
            Optional<User> optionalUser = this.userRepository.findById(id);
            if (optionalUser.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found!");
            }
            User userToUpdate = optionalUser.get();

            if (!userToUpdate.getPassword().equals(updateUserDto.oldPassword())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect old password.");
            }
            if (!updateUserDto.password().equals(updateUserDto.confirmPassword())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The passwords not is equals.");
            }
            BeanUtils.copyProperties(updateUserDto, userToUpdate, "id", "createdAt");
            User userUpdated = this.userRepository.save(userToUpdate);
            return userUpdated;
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "An error occurred while updating the user", e);
        }
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

    public List<User> dateFilter(Timestamp initialDate, Timestamp finishDate) {
        try {
            List<User> users = this.userRepository.findByCreateAtBetween(
                    initialDate,
                    finishDate
            );
            return users;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "An error occurred while finding the user by date", e);
            }
        }

}
