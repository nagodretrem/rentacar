package com.etiya.rentacar.business.dtos.responses.car;

import com.etiya.rentacar.business.dtos.responses.model.GetModelResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCarResponse {

    private int id;
    private int modelYear;
    private String plate;
    private int state;
    private double dailyPrice;
    private GetModelResponse model;
}
