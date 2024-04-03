package br.com.contability.business.services;

import br.com.contability.business.UploadImage;
import br.com.contability.business.Usuario;
import br.com.contability.comum.SessaoServices;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class PerfilServices {

    private final SessaoServices sessaoServices;
    private final UploadImageServices services;
    private final UsuarioServices usuarioServices;

    public PerfilServices(SessaoServices sessaoServices, UploadImageServices services, UsuarioServices usuarioServices) {
        this.sessaoServices = sessaoServices;
        this.services = services;
        this.usuarioServices = usuarioServices;
    }

    public void atualizaPerfil(Usuario usuario, MultipartFile file, HttpSession session) {

        usuarioServices.preenche(usuario);

        if (file.getSize() != 0) {
            updateWithImage(usuario, file);
        } else
            justupdate(usuario);

        sessaoServices.configuraSessionComUsuario(usuario, session);
    }

    private void justupdate(Usuario usuario) {
        usuarioServices.atualiza(usuario, false);
    }

    private void updateWithImage(Usuario usuario, MultipartFile file) {

        final UploadImage uploadImage = services.uploadImageCloudinary(file);
        final UploadImage uploadRetorno = services.insere(uploadImage, false);

        usuario.setUploadImage(uploadRetorno);
        usuarioServices.atualiza(usuario, false);
    }

}
