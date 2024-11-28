package br.com.SistemaControleInsumos.services;

import br.com.SistemaControleInsumos.dtos.user.ResponseUserDto;
import br.com.SistemaControleInsumos.dtos.user.UpdateUserDto;
import br.com.SistemaControleInsumos.entities.User;
import br.com.SistemaControleInsumos.enuns.UserRole;
import br.com.SistemaControleInsumos.repositories.UserRepository;
import br.com.SistemaControleInsumos.dtos.user.RequestUserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.*;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    @Autowired
    UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public ResponseUserDto createUser(RequestUserDto userDto) {
        log.info("Iniciando criação de usuário. Email: {}", userDto.email());

        Optional<User> existingUser = userRepository.findByEmail(userDto.email());
        if (existingUser.isPresent()) {
            log.warn("Usuário com o email {} já existe!", userDto.email());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with this email already exists");
        }

        if (!userDto.password().equals(userDto.confirmPassword())) {
            log.warn("As senhas não coincidem para o usuário com email: {}", userDto.email());
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

        log.info("Usuário criado com sucesso! ID: {}, Email: {}", userSaved.getId(), userSaved.getEmail());
        return response;

    }

    public List<ResponseUserDto> findAll() {
        log.info("Iniciando busca por todos os usuários");
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
            log.info("Lista de usuários resgatada com sucesso!");
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

            log.info("Usuário encontrado com sucesso!");
            return response;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "An error occurred while finding the user", e);
        }

    }

    public ResponseUserDto update(UUID id, UpdateUserDto updateUserDto) {
        log.info("Iniciando atualização do usuário");

        Optional<User> optionalUser = this.userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            log.warn("Usuário não encontrado");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found!");
        }
        User userToUpdate = optionalUser.get();

        if (updateUserDto.password() != null && updateUserDto.confirmPassword() != null) {
            if (!passwordEncoder.matches(updateUserDto.oldPassword(), userToUpdate.getPassword())) {
                log.warn("Senha atual incorreta");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect old password.");
            }
            if (!updateUserDto.password().equals(updateUserDto.confirmPassword())) {
                log.warn("Senhas e confirmação imcompativeis");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwords do not match.");
            }
            userToUpdate.setPassword(passwordEncoder.encode(updateUserDto.password()));
        }

        if (updateUserDto.name() != null) {
            userToUpdate.setName(updateUserDto.name());
        }
        if (updateUserDto.age() != null) {
            userToUpdate.setAge(updateUserDto.age());
        }
        if (updateUserDto.role() != null) {
            userToUpdate.setRole(updateUserDto.role());
        }

        User userUpdated = this.userRepository.save(userToUpdate);

        log.info("Usuário atualizado com sucesso!");
        return new ResponseUserDto(
                userUpdated.getId(),
                userUpdated.getCreateAt(),
                userUpdated.getName(),
                userUpdated.getEmail(),
                userUpdated.getAge(),
                userUpdated.getRole()
        );
    }


    public Boolean delete(UUID id) {
        log.info("Iniciando exlusão do usuário");

        try {
            Optional<User> optionalUser = this.userRepository.findById(id);
            if (optionalUser.isEmpty()) {
                log.warn("Usuário não encontrado");
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            }
            this.userRepository.delete(optionalUser.get());
            log.info("Usuário deletado com sucesso!");
            return true;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "An error occurred while deleting the user", e);
        }
    }

    public List<ResponseUserDto> dateFilter(Timestamp initialDate, Timestamp finishDate) {
        log.info("Iniciando busca usuario por filtro de data");
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

            log.info("Uuários encontrado por data com sucesso!");
            return responseDtos;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "An error occurred while finding the user by date", e);
        }
    }

}
