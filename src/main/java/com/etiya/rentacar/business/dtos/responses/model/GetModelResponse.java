package com.etiya.rentacar.business.dtos.responses.model;

import com.etiya.rentacar.business.dtos.responses.brand.GetBrandResponse;
import com.etiya.rentacar.business.dtos.responses.fuel.GetFuelResponse;
import com.etiya.rentacar.business.dtos.responses.transmission.GetTranmissionResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetModelResponse {

    private int id;

    private String name;

    private GetBrandResponse brand;

    private GetFuelResponse fuel;

    private GetTranmissionResponse transmission;

}
