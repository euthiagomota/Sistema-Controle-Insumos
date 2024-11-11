package br.com.SistemaControleInsumos.Services;

import br.com.SistemaControleInsumos.Entities.User;
import br.com.SistemaControleInsumos.Repositories.UserRepository;
import br.com.SistemaControleInsumos.UserDTOs.RequestUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

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

        if (!userDto.password().equals(userDto.confirmpassword())) {
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
}
