package org.anton.service;


import org.anton.entity.Gender;
import org.anton.repository.AddressRepository;

public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Integer countMales() {
        return addressRepository.getAllPerson().stream().filter(p -> p.gender() == Gender.MALE).toList().size();
    }
}
