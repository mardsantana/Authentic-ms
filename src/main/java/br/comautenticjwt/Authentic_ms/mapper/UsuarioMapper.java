package br.comautenticjwt.Authentic_ms.mapper;


import br.comautenticjwt.Authentic_ms.app.model.Usuario;
import br.comautenticjwt.Authentic_ms.vo.UsuarioRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toUsuario(UsuarioRequest usuarioRequest);
}
