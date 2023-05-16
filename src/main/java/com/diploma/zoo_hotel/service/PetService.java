package com.diploma.zoo_hotel.service;

import com.diploma.zoo_hotel.dto.PetDto;
import com.diploma.zoo_hotel.entities.AnimalType;
import com.diploma.zoo_hotel.entities.Customer;
import com.diploma.zoo_hotel.entities.Employee;
import com.diploma.zoo_hotel.entities.Pet;
import com.diploma.zoo_hotel.repository.PetRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

import static java.util.Objects.nonNull;

@Service
@AllArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final CustomerService customerService;

    /**
     * Создание профиля
     *
     * @param dto объект с данными
     */
    public PetDto createPet(PetDto dto) {
        Customer customer = customerService.getCustomerById(dto.getOwnerId());
        Pet pet = new Pet();
        pet.setName(dto.getName());
        pet.setAnimalType(AnimalType.fromValue(dto.getAnimalType()));
        pet.setBirthDate(dto.getBirthDate());
        pet.setPhoto(UtilsService.convertPhotoToByte(dto.getPhoto()));
        pet.setCustomer(customer);
        pet.setDescription(dto.getDescription());
        pet.setHaveAllVaccinations(dto.getHaveAllVaccinations());
        pet.setWeight(dto.getWeight());
        pet.setIsFemale(dto.getIsFemale());
        return buildDto(petRepository.save(pet));
    }

    /**
     * Получение профиля по айди
     *
     * @param id айди нужного нам профиля
     * @return возвращаем найденный профиль
     */

    public PetDto getPetDtoById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Неверный идентификатор профиля");
        }
        return petRepository.findById(id)
                .map(PetService::buildDto)
                .orElse(null);
    }

    /**
     * Возвращает сущность Профиль
     *
     * @param id айди профиля
     * @return сущность Профиль
     */
    public Pet getPetById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Неверный идентификатор питомца");
        }
        return petRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Питомец с id = %s не найден", id)));
    }

    /**
     * Удалить профиль по его идентификатору
     *
     * @param petId айди записи
     */
    public void deletePet(Long petId) {
        if (petId == null) {
            throw new IllegalArgumentException("Неверный идентификатор питомца");
        }

        petRepository.deleteById(petId);
    }

    /**
     * Сохранить питомца
     *
     * @param pet сущность Питомец
     */
    public void savePet(Pet pet) {
        if (pet == null) {
            throw new IllegalArgumentException("Передан пустой профиль");
        }

        petRepository.save(pet);
    }

    /**
     * Редактировать профиль
     *
     * @param newProfile профиль
     * @return измененный профиль
     */
    public Pet editPet(PetDto newProfile) {
        Pet petRecord = getPetById(newProfile.getId());

        petRecord.setDescription(newProfile.getDescription());
        petRecord.setName(newProfile.getName());
        petRecord.setBirthDate(newProfile.getBirthDate());
        petRecord.setPhoto(UtilsService.convertPhotoToByte(newProfile.getPhoto()));
        petRecord.setHaveAllVaccinations(newProfile.getHaveAllVaccinations());
        petRecord.setIsFemale(newProfile.getIsFemale());
        petRecord.setWeight(newProfile.getWeight());

        return petRepository.save(petRecord);
    }

    public PetDto editPetDto(PetDto newProfile) {
        return buildDto(editPet(newProfile));
    }

    /**
     * Превратить сущность в ДТО
     *
     * @param pet сущность
     * @return ДТО с данными
     */
    private static PetDto buildDto(Pet pet) {
        return PetDto.builder()
                .id(pet.getId())
                .animalType(pet.getAnimalType().name())
                .description(pet.getDescription())
                .haveAllVaccinations(pet.getHaveAllVaccinations())
                .ownerId(pet.getCustomer().getId())
                .isFemale(pet.getIsFemale())
                .weight(pet.getWeight())
                .name(pet.getName())
                .birthDate(pet.getBirthDate())
                .photo(UtilsService.convertPhotoToString(pet.getPhoto()))
                .build();
    }
}
