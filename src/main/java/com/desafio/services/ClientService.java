package com.desafio.services;

import com.desafio.dto.ClientDTO;
import com.desafio.entities.Client;
import com.desafio.repositories.ClientRepository;
import com.desafio.services.exceptions.ClientNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    @Transactional(readOnly = true)
    public Page<ClientDTO> findAll(Pageable pageable){
        Page<Client> result = repository.findAll(pageable);
        return result.map(x -> modelMapper.map(x, ClientDTO.class));
    }

    public ClientDTO insert(ClientDTO dto) {
        Client client = modelMapper.map(dto, Client.class);
        client = repository.save(client);
        return modelMapper.map(client, ClientDTO.class);
    }
    @Transactional
    public ClientDTO update(Long id, ClientDTO dto) {
        try{
            Client client = repository.getReferenceById(id);
            copyDtoToEntity(dto, client);
            client = repository.save(client);
            return modelMapper.map(client, ClientDTO.class);
        }
        catch (EntityNotFoundException e){
            throw new ClientNotFoundException("Cliente não encontrado!");
        }
    }

    private void copyDtoToEntity(ClientDTO dto, Client entity) {
        entity.setName(dto.getName());
        entity.setCpf(dto.getCpf());
        entity.setIncome(dto.getIncome());
        entity.setBirthDate(dto.getBirthDate());
        entity.setChildren(dto.getChildren());
    }
}
