package com.apirest.proyecto_bases.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 

public class RecepcionModel {
    private String problemaReportado; 
    private String condicionesObservadas; 
    private List<String> accesorios; 
    private List<FotografiasModel> fotografias; 
}
