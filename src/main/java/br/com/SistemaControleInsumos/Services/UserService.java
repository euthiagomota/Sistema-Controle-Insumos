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

import java.beans.Beans;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    //@Autowired
    //private BCryptPasswordEncoder passwordEncoder;

    public User createUser(RequestUserDto userDto) {

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

    public List<ResponseUserDto> findAll() {
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
    }

    public Optional<User> findById(UUID id) {
       Optional<User> user = this.userRepository.findById(id);
       if (user.isEmpty()) {
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found!");
       }
       return user;
    }

    public User update(UUID id, UpdateUserDto updateUserDto) {
        Optional<User> optionalUser = this.userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found!");
        }
        User userToUpdate = optionalUser.get();

        if (!userToUpdate.getPassword().equals(updateUserDto.oldPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect old password.");
        }
        if (!updateUserDto.password().equals(updateUserDto.confirmPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The passwords not is empty.");
        }
        BeanUtils.copyProperties(updateUserDto, userToUpdate, "id", "createdAt");

        User userUpdated = this.userRepository.save(userToUpdate);

        return userUpdated;

    }
}
