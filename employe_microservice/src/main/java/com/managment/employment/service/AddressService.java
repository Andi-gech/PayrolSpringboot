package com.managment.employment.service;

import com.managment.employment.model.Address;
import com.managment.employment.repository.AddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AddressService {


    private AddressRepository addressRepository;


    public Address addAddress(Address address) {
        return addressRepository.save(address);
    }


    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }


    public Optional<Address> getAddressById(Long id) {
        return addressRepository.findById(id);
    }


    public boolean deleteAddress(Long id) {
        if (addressRepository.existsById(id)) {
            addressRepository.deleteById(id);
            return true;
        }
        return false;
    }
}


