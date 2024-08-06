package br.comautenticjwt.Authentic_ms.app.model;

import br.comautenticjwt.Authentic_ms.vo.UsuarioRequest;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String email;
    private String nome;
    private String senha;
    private boolean ativo;

    public Usuario(UsuarioRequest usuarioRequest) {
        this.email = usuarioRequest.getEmail();
        this.nome = usuarioRequest.getNome();
        this.senha = usuarioRequest.getSenha();
        this.ativo = true;

    }
}
