package com.etiya.rentacar.business.concretes;

import com.etiya.rentacar.business.abstracts.FuelService;
import com.etiya.rentacar.business.dtos.requests.fuel.CreateFuelRequest;
import com.etiya.rentacar.business.dtos.requests.fuel.UpdateFuelRequest;
import com.etiya.rentacar.business.dtos.responses.fuel.CreatedFuelResponse;
import com.etiya.rentacar.business.dtos.responses.fuel.GetFuelListResponse;
import com.etiya.rentacar.business.dtos.responses.fuel.GetFuelResponse;
import com.etiya.rentacar.business.dtos.responses.fuel.UpdatedFuelResponse;
import com.etiya.rentacar.core.utilities.mapping.ModelMapperService;
import com.etiya.rentacar.dataAccess.abstracts.FuelRepository;
import com.etiya.rentacar.entities.Fuel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class FuelManager implements FuelService {
    private FuelRepository fuelRepository;
    private ModelMapperService modelMapperService;
    @Override
    public CreatedFuelResponse add(CreateFuelRequest createFuelRequest) {
        Fuel fuel = this.modelMapperService.forRequest().map(createFuelRequest, Fuel.class);
        fuelRepository.save(fuel);

        return this.modelMapperService.forResponse().map(fuel, CreatedFuelResponse.class);


    }

    @Override
    public UpdatedFuelResponse update(UpdateFuelRequest updateFuelRequest) {
        Fuel existingFuel = fuelRepository.findById(updateFuelRequest.getId()).orElseThrow(() -> new IllegalArgumentException("Fuel not found"));

        modelMapperService.forRequest().map(updateFuelRequest, existingFuel);
        Fuel updatedFuel = fuelRepository.save(existingFuel);

        return this.modelMapperService.forResponse().map(updatedFuel, UpdatedFuelResponse.class);
    }

    @Override
    public GetFuelResponse getById(int id) {
        Fuel fuel = fuelRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Fuel not found"));

        return this.modelMapperService.forResponse().map(fuel, GetFuelResponse.class);
    }

    @Override
    public List<GetFuelListResponse> getAll() {
        List<Fuel> fuels = fuelRepository.findAll();
        return fuels.stream()
                .map(fuel -> this.modelMapperService.forResponse()
                        .map(fuel, GetFuelListResponse.class)).collect(Collectors.toList());
    }


    @Override
    public void delete(int id) {

        fuelRepository.deleteById(id);

    }


}
