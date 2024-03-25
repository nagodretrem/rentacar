package com.etiya.rentacar.business.concretes;

import com.etiya.rentacar.business.abstracts.BrandService;
import com.etiya.rentacar.business.dtos.requests.brand.CreateBrandRequest;
import com.etiya.rentacar.business.dtos.requests.brand.UpdateBrandRequest;
import com.etiya.rentacar.business.dtos.responses.brand.CreatedBrandResponse;
import com.etiya.rentacar.business.dtos.responses.brand.GetBrandListResponse;
import com.etiya.rentacar.business.dtos.responses.brand.GetBrandResponse;
import com.etiya.rentacar.business.dtos.responses.brand.UpdatedBrandResponse;
import com.etiya.rentacar.core.utilities.mapping.ModelMapperService;
import com.etiya.rentacar.dataAccess.abstracts.BrandRepository;
import com.etiya.rentacar.entities.Brand;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
public class BrandManager implements BrandService {
    private final BrandRepository brandRepository;
    private final ModelMapperService modelMapperService;



    @Override
    public CreatedBrandResponse add(CreateBrandRequest createBrandRequest) {
        //todo: business rules

        Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);

        brandRepository.save(brand);

        CreatedBrandResponse response = this.modelMapperService.forResponse().map(brand, CreatedBrandResponse.class);

        return response;

    }

    @Override
    public UpdatedBrandResponse update(UpdateBrandRequest brand) {
        Brand brandToUpdate = brandRepository.findById(brand.getId()).orElseThrow(() -> new IllegalArgumentException("Brand not found"));
        brandToUpdate.setName(brand.getName());



        brandRepository.save(brandToUpdate);

        UpdatedBrandResponse response = modelMapperService.forResponse().map(brandToUpdate, UpdatedBrandResponse.class);

        return response;

    }

    @Override
    public List<GetBrandListResponse> getAll() {
        List<Brand> brands = brandRepository.findAll();

        List<GetBrandListResponse> response = brands.stream().map(brand ->
                this.modelMapperService.forResponse()
                        .map(brand, GetBrandListResponse.class)).collect(Collectors.toList());

        return response;

    }

    @Override
    public GetBrandResponse getById(int id) {
        Brand brand = brandRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Brand not found"));
        GetBrandResponse response = this.modelMapperService.forResponse().map(brand, GetBrandResponse.class);
        return response;

    }



    @Override
    public void delete(int id) {
        brandRepository.deleteById(id);
    }
}
