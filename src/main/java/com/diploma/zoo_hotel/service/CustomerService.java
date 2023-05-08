package com.diploma.zoo_hotel.service;

import com.diploma.zoo_hotel.dto.CustomerDto;
import com.diploma.zoo_hotel.entities.City;
import com.diploma.zoo_hotel.entities.Customer;
import com.diploma.zoo_hotel.entities.User;
import com.diploma.zoo_hotel.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

/**
 * Логика для работы с профилем
 */
@Service
@AllArgsConstructor
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final UserService userService;

    /**
     * Создание профиля
     *
     * @param dto объект с данными
     */
    public void createCustomer(CustomerDto dto) {
        User user = userService.findUserById(dto.getUserId());

        Customer customer = new Customer();
        customer.setLastName(dto.getLastName());
        customer.setFirstName(dto.getFirstName());
        customer.setPhoto(UtilsService.convertPhotoToByte(dto.getPhoto()));
        customer.setCity(City.fromValue(dto.getCity()));
        customer.setCreationDateTime(LocalDateTime.now());
        customer.setBirthDate(dto.getBirthDate());
        customer.setUser(user);

        customerRepository.save(customer);
    }

    /**
     * Получение профиля по айди
     *
     * @param id айди нужного нам профиля
     *
     * @return возвращаем найденный профиль
     */

    public CustomerDto getCustomerDtoById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Неверный идентификатор профиля");
        }
        return customerRepository.findById(id)
                .map(CustomerService::buildDto)
                .orElse(null);
    }

    /**
     * Возвращает сущность Профиль
     *
     * @param id айди профиля
     *
     * @return сущность Профиль
     */
    public Customer getCustomerById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Неверный идентификатор профиля");
        }
        return customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Профиль с id = %s не найден", id)));
    }

    /**
     * Удалить профиль по его идентификатору
     *
     * @param customerId айди записи
     */
    public void deleteCustomer(Long customerId) {
        if (customerId == null) {
            throw new IllegalArgumentException("Неверный идентификатор профиля");
        }

        customerRepository.deleteById(customerId);
    }

    /**
     * Сохранить профиль
     *
     * @param customer сущность Профиль
     */
    public void saveCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Передан пустой профиль");
        }

        customerRepository.save(customer);
    }

    /**
     * Редактировать профиль
     *
     * @param newProfile профиль
     *
     * @return измененный профиль
     */
    public CustomerDto editCustomer(CustomerDto newProfile) {
        Customer customerRecord = getCustomerById(newProfile.getId());

        customerRecord.setLastName(newProfile.getLastName());
        customerRecord.setFirstName(newProfile.getFirstName());
        customerRecord.setCity(City.fromValue(newProfile.getCity()));
        customerRecord.setPhoto(UtilsService.convertPhotoToByte(newProfile.getPhoto()));

        return buildDto(customerRepository.save(customerRecord));
    }

    /**
     * Превратить сущность в ДТО
     *
     * @param customer сущность
     *
     * @return ДТО с данными
     */
    private static CustomerDto buildDto(Customer customer) {
        return CustomerDto.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .city(customer.getCity().name())
                .birthDate(customer.getBirthDate())
                .photo(UtilsService.convertPhotoToString(customer.getPhoto()))
                .userId(customer.getUser().getId())
                .build();
    }

}
