package expertostech.autenticacao.jwt.services;

import expertostech.autenticacao.jwt.model.UsuarioModel;
import expertostech.autenticacao.jwt.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DBService {

    private UsuarioRepository repository;

    private PasswordEncoder bCryptPasswordEncoder;

    public void InstanciaDB() {
        UsuarioModel model = new UsuarioModel("teste", bCryptPasswordEncoder.encode("123"));

        repository.save(model);

    }


}
