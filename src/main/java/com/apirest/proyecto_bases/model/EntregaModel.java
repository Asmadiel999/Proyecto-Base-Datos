package com.apirest.proyecto_bases.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 

public class EntregaModel {
    private Date fecha; 
    private boolean confirmacionRecepcionCliente; 

}
