package com.hitpixel.paymentgwinvoice.services;

import com.hitpixel.paymentgwinvoice.enums.CrudStatus;
import com.hitpixel.paymentgwinvoice.models.Client;
import com.hitpixel.paymentgwinvoice.models.enums.FeeType;
import com.hitpixel.paymentgwinvoice.repository.ClientPageRepo;
import com.hitpixel.paymentgwinvoice.repository.ClientRepository;
import com.hitpixel.paymentgwinvoice.requestdto.ClientCreateRequest;
import com.hitpixel.paymentgwinvoice.responsedto.ClientCreateResponse;
import com.hitpixel.paymentgwinvoice.responsedto.ClientResponse;
import com.hitpixel.paymentgwinvoice.responsedto.GenericResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientPageRepo clientPageRepo;

    public Client save(Client client){
        return clientRepository.save(client);
    }

    public ClientCreateResponse create(ClientCreateRequest request){
        Client client = new Client(request);
        try {
            client = clientRepository.save(client);
            return new ClientCreateResponse(true, client.getId(), CrudStatus.CREATED);
        }catch (Exception exp){
            log.error("error occurred while creating client on DB, error message is: "+ exp.getLocalizedMessage());
            return new ClientCreateResponse(false, null, CrudStatus.FAILED);
        }
    }

    public Optional<List<ClientResponse>> getAllClients(){
        List<ClientResponse> clients = clientRepository.findAll().stream().map(ClientResponse::new).collect(Collectors.toList());
        return Optional.of(clients);
    }

    public List<ClientResponse> getAll(Integer pageNo, Integer pageSize, String sortBy){
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Client> pageResult = clientPageRepo.findAll(paging);
        if(pageResult.hasContent()) {
            return pageResult.getContent().stream().map(ClientResponse::new).collect(Collectors.toList());
        } else {
            return null;
        }
    }

    public Optional<Client> get(String clientId){
        return clientRepository.findById(clientId);
    }

    public GenericResponse delete(String clientId){
        if(this.clientRepository.existsById(clientId)) {
            try {
                clientRepository.deleteById(clientId);
                return new GenericResponse(true, "success");
            } catch (Exception exp) {
                log.error("error occurred while deleting client, exp is: " + exp.getLocalizedMessage());
                return new GenericResponse(false, "failed");
            }
        }
        return new GenericResponse(false, "client does not exist");
    }

    public Client getByName(String clientName){
        try{
            return clientRepository.findByClient(clientName);
        }catch (Exception exp){
            log.error("error while fetching client by name, error message is: "+exp.getLocalizedMessage());
            return null;
        }
    }

    public Double calculateFeeForClient(String clientId, Double txnAmount){
        if(clientId == null || txnAmount == null)
            return null;
        Optional<Client> optionalClient = this.get(clientId);
        if(optionalClient.isEmpty())
            return null;
        Client client = optionalClient.get();
        FeeType feeType = client.getFeesType();
        Double fees = null;
        if (feeType.getFeeType().equals(FeeType.FLAT_FEE.getFeeType())) {
            fees = Double.valueOf(client.getFees());
        }
        else {
            double percent = Double.parseDouble(client.getFees());
            fees = txnAmount * (percent/100.0f);
        }
        return fees;
    }
}
