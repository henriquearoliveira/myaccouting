package br.com.contability.business.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import br.com.contability.business.UploadImage;
import br.com.contability.business.Usuario;

@Component
public class PerfilServices {

	@Autowired
	private UploadImageServices services;

	@Autowired
	private UsuarioServices usuarioServices;

	public void atualizaPerfil(Usuario usuario, MultipartFile file) {

		usuarioServices.preenche(usuario);

		if (file.getSize() != 0) {
			// System.out.println("entrei");
			updateWithImage(usuario, file);
		} else
			justupdate(usuario);

	}

	private void justupdate(Usuario usuario) {

		usuarioServices.atualiza(usuario, false);

	}

	private void updateWithImage(Usuario usuario, MultipartFile file) {

		UploadImage uploadImage = services.uploadImageCloudinary(file);

		UploadImage uploadRetorno = services.insere(uploadImage, false);

		usuario.setUploadImage(uploadRetorno);

		usuarioServices.atualiza(usuario, false);
	}

}
