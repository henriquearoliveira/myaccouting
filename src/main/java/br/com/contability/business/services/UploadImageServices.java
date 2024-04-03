package br.com.contability.business.services;

import br.com.contability.business.UploadImage;
import br.com.contability.business.repository.UploadImageRepository;
import br.com.contability.comum.ServicesAbstract;
import br.com.contability.utilitario.CaixaDeFerramentas;
import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class UploadImageServices extends ServicesAbstract<UploadImage, UploadImageRepository> {

    @Value("${cloudinary.cloudname}")
    private String cloudName;

    @Value("${cloudinary.apikey}")
    private String apiKey;

    @Value("${cloudinary.apisecret}")
    private String apiSecret;

    private final ConfiguraArquivosServices arquivosServices;

    public UploadImageServices(ConfiguraArquivosServices arquivosServices) {
        this.arquivosServices = arquivosServices;
    }

    public UploadImage uploadImageCloudinary(MultipartFile file) {

        // OBTEM A AUTENTICAÇÃO NECESSÁRIA PARA O UPLOAD
        final Map<String, String> configuracao = getAuthentication();
        final Cloudinary cloudinary = new Cloudinary(configuracao);
        final File imagem = arquivosServices.configuraArquivo(file);
        final Map<String, Object> resultado = uploadImagem(cloudinary, imagem);

        return converterToUploadImage(resultado);

        /*
         * KEYSET É O CONJUNTO DE KEY, JÁ O ENTRYSET É O CONJUNTO DE KEY X VALOR for
         * (String strings : resultado.keySet()) { } resultado.keySet().forEach(action);
         */
    }

    private UploadImage converterToUploadImage(Map<String, Object> resultado) {

        final UploadImage uploadImage = new UploadImage();
        uploadImage.setPublicID((String) resultado.get("public_id"));
        uploadImage.setSignature((String) resultado.get("signature"));
        uploadImage.setWidth((long) resultado.get("width"));
        uploadImage.setHeight((long) resultado.get("height"));
        uploadImage.setFormat((String) resultado.get("format"));
        uploadImage.setResourceType((String) resultado.get("resource_type"));
        uploadImage.setCreateAt(CaixaDeFerramentas.stringToLocalDateTime((String) resultado.get("created_at")));
        uploadImage.setBytes((long) (resultado.get("bytes")));
        uploadImage.setUrl((String) resultado.get("url"));
        uploadImage.setSecureUrl((String) resultado.get("secure_url"));

        return uploadImage;

    }

    private Map<String, Object> uploadImagem(Cloudinary cloudinary, File imagem) {

        final Map<String, Object> mapVazioCloudinary = new HashMap<>();
        final Map<String, Object> resultado;
        try {
            resultado = cloudinary.uploader().upload(imagem, mapVazioCloudinary);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return resultado;
    }


    /**
     * @return
     */
    private Map<String, String> getAuthentication() {

        final Map<String, String> configuracao = new HashMap<>();
        configuracao.put("cloud_name", cloudName);
        configuracao.put("api_key", apiKey);
        configuracao.put("api_secret", apiSecret);

        return configuracao;
    }

}
