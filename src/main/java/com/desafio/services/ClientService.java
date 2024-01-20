package com.desafio.services;

import com.desafio.dto.ClientDTO;
import com.desafio.entities.Client;
import com.desafio.repositories.ClientRepository;
import com.desafio.services.exceptions.ClientNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository repository;
    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public ClientDTO findById(Long id){
        Client client = repository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Cliente não existe!"));
        ClientDTO dto = modelMapper.map(client, ClientDTO.class);
        return dto;
    }




}
