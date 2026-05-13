package com.upc.presulisto.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class AporteMetaDTO {

    private Long id;
    private BigDecimal monto;
    private LocalDate fecha;
    private String nota;
    private Long metaId;

}
