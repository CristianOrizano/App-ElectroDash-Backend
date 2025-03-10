package com.gestionventas.service;

import com.gestionventas.dto.boleta.BoletaDto;
import com.gestionventas.dto.boleta.BoletaFilterDto;
import com.gestionventas.dto.usuario.UsuarioDto;
import com.gestionventas.dto.usuario.UsuarioFilterDto;
import com.gestionventas.dto.usuario.UsuarioSaveDto;
import com.gestionventas.shared.page.PageResponse;
import com.gestionventas.shared.security.jwt.JWTAuthResonseDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface IUsuarioService {
    List<UsuarioDto> findAll();
    UsuarioDto findById(Long id);
    UsuarioDto create(UsuarioSaveDto usuarioBody);
    UsuarioDto update(Long id, UsuarioSaveDto usuarioBody) ;
    UsuarioDto disable(Long id) ;
    JWTAuthResonseDTO login (Authentication auth);
    PageResponse<UsuarioDto> findPaginated(UsuarioFilterDto filter);
}
