package expertostech.autenticacao.jwt.controller;

import expertostech.autenticacao.jwt.model.Perfil;
import expertostech.autenticacao.jwt.model.UsuarioModel;
import expertostech.autenticacao.jwt.repository.UsuarioRepository;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping()
public class UsuarioController {

    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;

    public UsuarioController(UsuarioRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }


    @GetMapping("/listarTodos")
    public ResponseEntity<List<UsuarioModel>> listarTodos() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping("/salvar")
    public ResponseEntity<UsuarioModel> salvar(/*@RequestBody UsuarioModel usuario*/) {

        UsuarioModel model = new UsuarioModel("gio", "123");
        model.setPassword(encoder.encode(model.getPassword()));

        model.addPerfil(Perfil.ADMIN);
        return ResponseEntity.ok(repository.save(model));
    }

    @GetMapping("/validarSenha")
    public ResponseEntity<Boolean> validarSenha(@RequestParam String login,
                                                @RequestParam String password) {

        Optional<UsuarioModel> optUsuario = repository.findByLogin(login);
        if (optUsuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }

        UsuarioModel usuario = optUsuario.get();
        boolean valid = encoder.matches(password, usuario.getPassword());

        HttpStatus status = (valid) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status).body(valid);
    }


    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/user")
    public ResponseEntity<Boolean> roleUser() {

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<Boolean> roleAdmin() {

        return ResponseEntity.ok().build();
    }


}
