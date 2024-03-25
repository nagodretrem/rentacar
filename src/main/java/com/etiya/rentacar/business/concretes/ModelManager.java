package com.etiya.rentacar.business.concretes;

import com.etiya.rentacar.business.abstracts.BrandService;
import com.etiya.rentacar.business.abstracts.FuelService;
import com.etiya.rentacar.business.abstracts.ModelService;
import com.etiya.rentacar.business.abstracts.TransmissionService;
import com.etiya.rentacar.business.dtos.requests.model.CreateModelRequest;
import com.etiya.rentacar.business.dtos.requests.model.UpdateModelRequest;
import com.etiya.rentacar.business.dtos.responses.model.CreatedModelResponse;
import com.etiya.rentacar.business.dtos.responses.model.GetModelListResponse;
import com.etiya.rentacar.business.dtos.responses.model.GetModelResponse;
import com.etiya.rentacar.business.dtos.responses.model.UpdatedModelResponse;
import com.etiya.rentacar.core.utilities.mapping.ModelMapperService;
import com.etiya.rentacar.dataAccess.abstracts.BrandRepository;
import com.etiya.rentacar.dataAccess.abstracts.FuelRepository;
import com.etiya.rentacar.dataAccess.abstracts.ModelRepository;
import com.etiya.rentacar.dataAccess.abstracts.TransmissionRepository;
import com.etiya.rentacar.entities.Brand;
import com.etiya.rentacar.entities.Fuel;
import com.etiya.rentacar.entities.Model;
import com.etiya.rentacar.entities.Transmission;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ModelManager implements ModelService {

    private ModelRepository modelRepository;
    private ModelMapperService modelMapperService;


    @Override
    public CreatedModelResponse add(CreateModelRequest createModelRequest) {
        Model model = this.modelMapperService.forRequest().map(createModelRequest, Model.class);

        modelRepository.save(model);

        return this.modelMapperService.forResponse().map(model, CreatedModelResponse.class);
    }

    @Override
    public UpdatedModelResponse update(UpdateModelRequest updateModelRequest) {
        Model existingModel = modelRepository.findById(updateModelRequest.getId()).orElseThrow(() -> new IllegalArgumentException("Model not found"));

        modelMapperService.forRequest().map(updateModelRequest, existingModel);

        Model updatedModel = modelRepository.save(existingModel);

        return this.modelMapperService.forResponse().map(updatedModel, UpdatedModelResponse.class);
    }

    @Override
    public GetModelResponse getById(int id) {

        Model model = modelRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Model not found"));

        return this.modelMapperService.forResponse().map(model, GetModelResponse.class);
    }

    @Override
    public List<GetModelListResponse> getAll() {

        List<Model> models = modelRepository.findAll();
        return models.stream()
                .map(model -> this.modelMapperService.forResponse()
                        .map(model, GetModelListResponse.class)).collect(Collectors.toList());
    }



    @Override
    public void delete(int id) {

        modelRepository.deleteById(id);

    }
}
