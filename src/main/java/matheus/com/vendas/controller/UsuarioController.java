package matheus.com.vendas.controller;

import matheus.com.vendas.entity.Usuario;
import matheus.com.vendas.service.impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    private UsuarioServiceImpl usuarioService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioController(PasswordEncoder passwordEncoder,
                             UsuarioServiceImpl usuarioService) {

        this.passwordEncoder = passwordEncoder;
        this.usuarioService = usuarioService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario cadastrar(@Valid @RequestBody Usuario usuario){
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        return usuarioService.salvar(usuario);
    }


}
