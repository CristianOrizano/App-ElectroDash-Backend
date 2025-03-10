package com.gestionventas.dto.boleta;

import com.gestionventas.dto.cliente.ClienteDto;
import com.gestionventas.dto.usuario.UsuarioDto;
import com.gestionventas.dto.usuario.UsuarioSimpleDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoletaDto {
    private Long id;
    private LocalDateTime fechaEmision;
    private ClienteDto cliente;
    private UsuarioSimpleDto usuario;
    private String tipoVenta;
    private BigDecimal total;

}
