package br.com.spakoapi.servico;

import br.com.spakoapi.dao.UsuarioDAO;
import br.com.spakoapi.entidade.Usuario;
import java.util.List;

public class UsuarioServico {

    private final UsuarioDAO dao = new UsuarioDAO();

    public List<Usuario> getUsuarios() {
        return dao.getAll();
    }

    public Usuario getUsuarioPorId(long id) {
        return dao.getById(id);
    }

    public void salvarUsuario(Usuario u) {
        dao.save(u);
    }

    public void alterarUsuario(Usuario u) {
        dao.update(u);
    }

    public void apagarUsuario(long id) {
        dao.delete(id);
    }

    public List<Usuario> getUsuariosPaginacao(int inicio, int maximo) {
        return dao.getByPagination(inicio, maximo);
    }

    public List<Usuario> getUsuarioPorNome(String nome) {
        return dao.getByNome(nome);
    }

}
