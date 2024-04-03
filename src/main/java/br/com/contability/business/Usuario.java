package br.com.contability.business;

import br.com.contability.business.dto.CadastroDTO;
import br.com.contability.comum.BeanIdentificavel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(indexes = {@Index(name = "index_usuario", columnList = "id"),
        @Index(name = "unique_email", columnList = "email", unique = true)})
public class Usuario extends BeanIdentificavel implements UserDetails {

    @Column(nullable = false, unique = true)
    @NotEmpty(message = "O email não pode ser vazio")
    @NotNull(message = "O email não pode ser null")
    private String email;

    @Column(length = 4000, nullable = false)
    @NotEmpty(message = "A senha não pode ser vazio")
    @NotNull(message = "A senha não pode ser null")
    private String senha;

    @Column(length = 4000)
    @JsonInclude(Include.NON_NULL)
    private String senhaMaster;

    @Column(nullable = false)
    @NotEmpty(message = "O nome não pode ser vazio")
    @NotNull(message = "O nome não pode ser null")
    private String nome;

    @Column
    private boolean ativo = true;

    @Column
    private boolean administrador = true;

    @ManyToOne
    private UploadImage uploadImage;

    //
    @JsonIgnore
    @OneToMany(mappedBy = "usuario", targetEntity = Categoria.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Categoria> categorias;

    @JsonIgnore
    @OneToMany(mappedBy = "usuario", targetEntity = Conta.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Conta> contas;

    @JsonIgnore
    @OneToMany(mappedBy = "usuario", targetEntity = Lancamento.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Lancamento> lancamentos;

    @JsonIgnore
    @OneToMany(mappedBy = "usuario", targetEntity = Saldo.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Lancamento> saldos;

    @JsonIgnore
    @OneToMany(mappedBy = "usuario", targetEntity = CodigoUsuario.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CodigoUsuario> codigoUsuarios;

    //

    /**
     * @param cadastro
     * @return this instance
     */
    public Usuario getUsuarioByCadastro(CadastroDTO cadastro, boolean ativaUsuario) {

        this.setAdministrador(true);
        this.setAtivo(ativaUsuario);
        this.setEmail(cadastro.getEmail());
        this.setNome(cadastro.getNome());
        this.setSenha(new BCryptPasswordEncoder().encode(cadastro.getSenha()));
        this.setSenhaMaster(null);

        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        return getSenha();
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAtivo();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
