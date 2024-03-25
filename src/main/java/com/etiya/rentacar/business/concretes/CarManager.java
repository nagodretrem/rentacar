package com.etiya.rentacar.business.concretes;

import com.etiya.rentacar.business.abstracts.CarService;
import com.etiya.rentacar.business.abstracts.ModelService;
import com.etiya.rentacar.business.dtos.requests.car.CreateCarRequest;
import com.etiya.rentacar.business.dtos.requests.car.UpdateCarRequest;
import com.etiya.rentacar.business.dtos.responses.car.CreatedCarResponse;
import com.etiya.rentacar.business.dtos.responses.car.GetCarListResponse;
import com.etiya.rentacar.business.dtos.responses.car.GetCarResponse;
import com.etiya.rentacar.business.dtos.responses.car.UpdatedCarResponse;
import com.etiya.rentacar.core.utilities.mapping.ModelMapperService;
import com.etiya.rentacar.dataAccess.abstracts.CarRepository;
import com.etiya.rentacar.entities.Car;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CarManager implements CarService {

    private final CarRepository carRepository;
    private ModelMapperService modelMapperService;

    @Override
    public CreatedCarResponse add(CreateCarRequest createCarRequest) {
        Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class);

        carRepository.save(car);

        return this.modelMapperService.forResponse().map(car, CreatedCarResponse.class);
    }

    @Override
    public UpdatedCarResponse update(UpdateCarRequest updateCarRequest) {
        Car existingCar = carRepository.findById(updateCarRequest.getId()).orElseThrow(() -> new IllegalArgumentException("Car not found"));

        modelMapperService.forRequest().map(updateCarRequest, existingCar);

        Car updatedCar = carRepository.save(existingCar);

        return this.modelMapperService.forResponse().map(updatedCar, UpdatedCarResponse.class);

    }

    @Override
    public GetCarResponse getById(int id) {
        Car car = carRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Car not found"));

        return this.modelMapperService.forResponse().map(car, GetCarResponse.class);
    }

    @Override
    public List<GetCarListResponse> getAll() {
        List<Car> cars = carRepository.findAll();
        return cars.stream()
                .map(car -> this.modelMapperService.forResponse()
                        .map(car, GetCarListResponse.class)).collect(Collectors.toList());
    }

    @Override
    public void delete(int id) {

        carRepository.deleteById(id);

    }
}
