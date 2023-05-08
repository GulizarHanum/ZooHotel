package com.diploma.zoo_hotel.service;

import com.diploma.zoo_hotel.dto.AuthDataRequestDto;
import com.diploma.zoo_hotel.dto.UserDto;
import com.diploma.zoo_hotel.dto.UserStatsDto;
import com.diploma.zoo_hotel.entities.User;
import com.diploma.zoo_hotel.exceptions.UserExistsException;
import com.diploma.zoo_hotel.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Получаем необходимые данные о пользователе для модуля аутентификации
     *
     * @param username никнейм пользователя
     *
     * @return интерфейс с данными пользователя
     *
     * @throws EntityNotFoundException пользователь не найден
     */
    @Override
    @Cacheable("userDetails")
    public UserDetails loadUserByUsername(String username) throws EntityNotFoundException {
        return findUserByEmail(username);
    }

    /**
     * Редактирование пользователя
     *
     * @param user пользователь
     *
     * @return возвращает ДТО пользователя
     */
    @CachePut(value = "findUserByUsername", key = "#user.username")
    public UserDto editUserDto(UserDto user) {
        User editedUser = this.findUserById(user.getId());

        editedUser.setEmail(user.getEmail());
        editedUser.setPassword(user.getPassword());

        return buildDto(repository.save(editedUser));
    }

    /**
     * Получение пользователя по его электронной почте
     *
     * @param email эл. почта пользователя
     *
     * @return данные о пользователе
     */
    @Cacheable(value = "findUserByEmail", key = "#email")
    public User findUserByEmail(String email) {
        return repository
                .findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Пользователь с email = %s не найден", email)));
    }

    /**
     * Получение пользователя по айди
     *
     * @param id айди пользователя
     *
     * @return возвращаем данные о пользователе
     */
    public User findUserById(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Пользователь с id = %s не найден", id)));
    }

    /**
     * Создание пользователя
     *
     * @param dto     данные пользователя для аутентификации
     * @param isAdmin признак администратора
     */
    @CachePut(value = "findUserByEmail", key = "#dto.email")
    public User createUser(AuthDataRequestDto dto, boolean isAdmin) {
        if (repository.findByEmail(dto.getEmail()).isEmpty()) {
            Set<User.Role> roles = new HashSet<>();
            if (isAdmin) {
                roles.add(User.Role.ADMIN);
            } else {
                roles.add(User.Role.USER);
            }

            User user = new User();
            user.setUsername(dto.getEmail());
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            user.setEmail(dto.getEmail());
            user.setPhoneNumber(dto.getPhoneNumber());
            user.setActive(true);
            user.setRoles(roles);

            return repository.save(user);
        } else {
            throw new UserExistsException(String.format("Пользователь с email %s уже существует!", dto.getEmail()));
        }
    }

    /**
     * Удаление пользователя по айди
     *
     * @param id айди пользователя
     */
    public void deleteUser(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Неверный идентификатор пользователя");
        }
        repository.deleteById(id);
    }

    /**
     * Вывести статистику о пользователях
     *
     * @return статистика о пользователях
     */
    public UserStatsDto getUsersStats() {
        long countAllUsers = repository.count();

        return UserStatsDto.builder()
                .allUsers(countAllUsers)
                .build();
    }

    /**
     * Превратить сущность в ДТО
     *
     * @param user сущность Пользователь
     *
     * @return ДТО с данными
     */
    private static UserDto buildDto(User user) {
        return UserDto.builder()
                .active(user.isActive())
                .email(user.getEmail())
                .id(user.getId())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .roles(user.getRoles())
                .username(user.getUsername())
                .build();
    }
}
