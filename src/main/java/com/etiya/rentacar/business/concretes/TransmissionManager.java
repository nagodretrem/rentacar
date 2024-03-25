package com.etiya.rentacar.business.concretes;

import com.etiya.rentacar.business.abstracts.TransmissionService;
import com.etiya.rentacar.business.dtos.requests.transmission.CreateTranmissionRequest;
import com.etiya.rentacar.business.dtos.requests.transmission.UpdateTransmissionRequest;
import com.etiya.rentacar.business.dtos.responses.transmission.CreatedTransmissionResponse;
import com.etiya.rentacar.business.dtos.responses.transmission.GetTranmissionListResponse;
import com.etiya.rentacar.business.dtos.responses.transmission.GetTranmissionResponse;
import com.etiya.rentacar.business.dtos.responses.transmission.UpdatedTransmissionResponse;
import com.etiya.rentacar.core.utilities.mapping.ModelMapperService;
import com.etiya.rentacar.dataAccess.abstracts.TransmissionRepository;
import com.etiya.rentacar.entities.Transmission;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TransmissionManager implements TransmissionService {
    private TransmissionRepository transmissionRepository;
    private ModelMapperService modelMapperService;
    @Override
    public CreatedTransmissionResponse add(CreateTranmissionRequest createTransmissionRequest) {
        Transmission transmission = this.modelMapperService.forRequest().map(createTransmissionRequest, Transmission.class);
        transmissionRepository.save(transmission);

        return this.modelMapperService.forResponse().map(transmission, CreatedTransmissionResponse.class);

    }

    @Override
    public UpdatedTransmissionResponse update(UpdateTransmissionRequest updateTransmissionRequest) {
        Transmission existingTransmission = transmissionRepository.findById(updateTransmissionRequest.getId()).orElseThrow(() -> new IllegalArgumentException("Transmission not found"));

        modelMapperService.forRequest().map(updateTransmissionRequest, existingTransmission);

        Transmission updatedTransmission =this.transmissionRepository.save(existingTransmission);

        return this.modelMapperService.forResponse().map(updatedTransmission, UpdatedTransmissionResponse.class);
    }

    @Override
    public List<GetTranmissionListResponse> getAll() {
        List<Transmission> transmissions = transmissionRepository.findAll();

        return transmissions.stream()
                .map(transmission -> this.modelMapperService.forResponse()
                        .map(transmission, GetTranmissionListResponse.class)).collect(Collectors.toList());
    }

    @Override
    public GetTranmissionResponse getById(int id) {

        Transmission transmission = transmissionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Transmission not found"));
        return this.modelMapperService.forResponse().map(transmission, GetTranmissionResponse.class);
    }


    @Override
    public void delete(int id) {
        transmissionRepository.deleteById(id);
    }
}
