package br.com.leodean.Cadastro.domain.mapper;

import br.com.leodean.Cadastro.domain.Customer;
import br.com.leodean.Cadastro.domain.databaseDomain.CustomerDataBase;
import br.com.leodean.Cadastro.domain.dto.CustomerDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class CustomerMapper {


    public static List<CustomerDTO> mappToResponse(List<CustomerDataBase> customerDatabase) {
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        for (CustomerDataBase customerDataBase : customerDatabase) {
            customerDTOList.add(CustomerDTO.builder().customerID(customerDataBase.getCustomerID())
                    .name(customerDataBase.getName())
                    .cell(customerDataBase.getCell())
                    .email(customerDataBase.getEmail()).build());
        }
        return customerDTOList;
    }

    public static void customerIDRequestCreated(Customer customer, String customerIDCreated) {
        customer.setCustomerID(customerIDCreated);
    }

    public static CustomerDataBase mappToDataBase(Customer request) {
        return CustomerDataBase.builder()
                .customerID(UUID.randomUUID().toString())
                .name(request.getName())
                .email(request.getEmail())
                .cell(request.getCell())
                .password(new BCryptPasswordEncoder().encode(request.getPassword()))
                .CPF(request.getCPF())
                .dataRegistro(LocalDateTime.now())
                .build();
    }

    public static CustomerDTO mappToResponse(CustomerDataBase customerDataBase) {
        return CustomerDTO.builder()
                .customerID(customerDataBase.getCustomerID())
                .name(customerDataBase.getName())
                .email(customerDataBase.getEmail())
                .cell(customerDataBase.getCell())
                .build();
    }


}
