package expertostech.autenticacao.jwt.services;

import expertostech.autenticacao.jwt.data.DetalheUsuarioData;
import expertostech.autenticacao.jwt.model.UsuarioModel;
import expertostech.autenticacao.jwt.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class DetalheUsuarioServiceImpl implements UserDetailsService {

    private final UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UsuarioModel> usuarioModel = Optional.of(repository.findByLogin(username))
                .orElseThrow(() -> new UsernameNotFoundException("Username [" + username + "] não encontrado."));

        return new DetalheUsuarioData(usuarioModel);
    }
}
