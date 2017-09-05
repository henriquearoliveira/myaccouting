package br.com.contability.business.resources;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cloudinary.Cloudinary;

import br.com.contability.business.Usuario;

@Controller
@RequestMapping("/perfil")
public class PerfilResources {

	@GetMapping
	public ModelAndView perfil(Usuario usuarioModel, Model model) {

		ModelAndView mv = new ModelAndView("perfil/Perfil");

		return mv;

	}

	@SuppressWarnings("unchecked")
	@PostMapping
	public ModelAndView perfilEdit(Usuario usuario, @RequestParam(value = "file") MultipartFile file) {

		System.out.println(file.getName());
		System.out.println(file.getOriginalFilename());
		System.out.println(file.getSize());
		System.out.println(usuario.getNome());

		Map<String, String> configuracao = new HashMap<>();
		configuracao.put("cloud_name", "dznde0ht5");
		configuracao.put("api_key", "538862271882227");
		configuracao.put("api_secret", "UKxwkpSI-SQp2gzZ65AMzCj8Vkc");

		Cloudinary cloudinary = new Cloudinary(configuracao);

		Map<String, String> vazio = new HashMap<>();

		Map<String, String> resultado = new HashMap<>();

		File f = null;
		try {
			f = Files.createTempFile("temp", file.getOriginalFilename()).toFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			file.transferTo(f);
		} catch (IllegalStateException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			resultado = cloudinary.uploader().upload(f, vazio);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(resultado);

		/*
		 * KEYSET É O CONJUNTO DE KEY, JÁ O ENTRYSET É O CONJUNTO DE KEY X VALOR
		 * for (String strings : resultado.keySet()) {
		 * 
		 * }
		 * 
		 * resultado.keySet().forEach(action);
		 */

		return null;
	}

}
