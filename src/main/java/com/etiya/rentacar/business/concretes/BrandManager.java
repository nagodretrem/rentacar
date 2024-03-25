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

        Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);
        brandRepository.save(brand);

        return this.modelMapperService.forResponse().map(brand, CreatedBrandResponse.class);

    }

    @Override
    public UpdatedBrandResponse update(UpdateBrandRequest updateBrandRequest) {
        Brand existingBrand = brandRepository.findById(updateBrandRequest.getId()).orElseThrow(() -> new IllegalArgumentException("Brand not found"));

        modelMapperService.forRequest().map(updateBrandRequest, existingBrand);

        Brand updatedBrand = brandRepository.save(existingBrand);

        return this.modelMapperService.forResponse().map(updatedBrand, UpdatedBrandResponse.class);

    }

    @Override
    public List<GetBrandListResponse> getAll() {
        List<Brand> brands = brandRepository.findAll();

        return brands.stream()
                .map(brand -> this.modelMapperService.forResponse()
                        .map(brand, GetBrandListResponse.class)).collect(Collectors.toList());

    }

    @Override
    public GetBrandResponse getById(int id) {
        Brand brand = brandRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Brand not found"));

        return this.modelMapperService.forResponse().map(brand, GetBrandResponse.class);

    }



    @Override
    public void delete(int id) {
        brandRepository.deleteById(id);
    }
}
