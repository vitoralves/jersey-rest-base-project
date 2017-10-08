package br.com.spakoapi.dao;

import br.com.spakoapi.entidade.Usuario;
import br.com.spakoapi.excecao.DAOException;
import br.com.spakoapi.excecao.ErrorCode;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;

public class UsuarioDAO {

    public List<Usuario> getAll() {
        EntityManager em = JPAUtil.getEntityManager();
        List<Usuario> usuarios = null;

        try {
            usuarios = em.createQuery("select u from Usuario u", Usuario.class).getResultList();
        } catch (RuntimeException ex) {
            throw new DAOException("Erro ao recuperar usuários " + ex.getMessage(), ErrorCode.SERVER_ERROR.getCode());
        } finally {
            em.close();
        }

        return usuarios;
    }

    public List<Usuario> getById(long id) {

        if (id <= 0) {
            throw new DAOException("O id deve ser maior que 0", ErrorCode.BAD_REQUEST.getCode());
        }

        EntityManager em = JPAUtil.getEntityManager();
        Usuario u = null;

        try {
            u = em.find(Usuario.class, id);
        } catch (RuntimeException ex) {
            throw new DAOException("Erro ao buscar usuario por id " + ex.getMessage(), ErrorCode.BAD_REQUEST.getCode());
        } finally {
            em.close();
        }

        if (u == null) {
            throw new DAOException("Usuário " + id + " não encontrado ", ErrorCode.NOT_FOUND.getCode());
        }

        List<Usuario> lista = new ArrayList<>();
        lista.add(u);
        return lista;
    }

    public void save(Usuario u) {
        EntityManager em = JPAUtil.getEntityManager();

        if (!usuarioValido(u)) {
            throw new DAOException("Usuário inválido! ", ErrorCode.BAD_REQUEST.getCode());
        }

        try {
            em.getTransaction().begin();
            em.persist(u);
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            em.getTransaction().rollback();
            throw new DAOException("Erro ao salvar usuário " + ex.getMessage(), ErrorCode.SERVER_ERROR.getCode());
        } finally {

            em.close();
        }

    }

    public void update(Usuario u) {
        EntityManager em = JPAUtil.getEntityManager();
        List<Usuario> existente = null;

        if (!usuarioValido(u)) {
            throw new DAOException("Usuário inválido! ", ErrorCode.BAD_REQUEST.getCode());
        }

        if (u.getId() == null) {
            throw new DAOException("Usuário com id inválido! ", ErrorCode.BAD_REQUEST.getCode());
        }

        // se não existir ele retorna uma exceção
        existente = getById(u.getId());

        try {
            em.getTransaction().begin();
            em.merge(u);
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            em.getTransaction().rollback();
            throw new DAOException("Erro ao atualizar usuário " + ex.getMessage(), ErrorCode.SERVER_ERROR.getCode());
        } finally {
            em.close();
        }
    }

    public void delete(long id) {
        EntityManager em = JPAUtil.getEntityManager();
        List<Usuario> usuario = null;

        //se não existir retorna exceção
        usuario = getById(id);

        // quando procura-se o objeto por id o entity manager é fechado e o objeto fica detached, portanto é necessário realizar um merge para ele voltar como reattach
        Usuario u = em.merge(usuario.get(0));
        try {
            em.getTransaction().begin();
            em.remove(u);
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            em.getTransaction().rollback();
            throw new DAOException("Erro ao excluir usuário " + ex.getMessage(), ErrorCode.SERVER_ERROR.getCode());
        } finally {
            em.close();
        }
    }

    public List<Usuario> getByPagination(int first, int max) {
        List<Usuario> u;
        EntityManager em = JPAUtil.getEntityManager();

        try {
            u = em.createQuery("select u from Usuario u", Usuario.class)
                    .setFirstResult(first - 1)
                    .setMaxResults(max)
                    .getResultList();
        } catch (RuntimeException ex) {
            throw new DAOException("Erro ao recuperar usuários com paginação " + ex.getMessage(), ErrorCode.SERVER_ERROR.getCode());
        } finally {
            em.close();
        }

        if (u.isEmpty()) {
            throw new DAOException("Página sem usuários ", ErrorCode.NOT_FOUND.getCode());
        }

        return u;
    }

    public List<Usuario> getByNome(String nome) {
        List<Usuario> u;
        EntityManager em = JPAUtil.getEntityManager();

        try {
            u = em.createQuery("select u from Usuario u where UPPER(u.nome) like :nome", Usuario.class)
                    .setParameter("nome", "%" + nome.toUpperCase() + "%")
                    .getResultList();
        } catch (RuntimeException ex) {
            throw new DAOException("Erro ao recuperar usuários por nome " + ex.getMessage(), ErrorCode.SERVER_ERROR.getCode());
        } finally {
            em.close();
        }

        if (u.isEmpty()) {
            throw new DAOException("Nenhum usuário encontrado ", ErrorCode.NOT_FOUND.getCode());
        }

        return u;
    }

    private boolean usuarioValido(Usuario u) {

        if (u.getCpf() == null) {
            return false;
        } else if (u.getEmail() == null) {
            return false;
        } else if (u.getNome() == null) {
            return false;
        } else if (u.getSenha() == null) {
            return false;
        } else if (u.getSobrenome() == null) {
            return false;
        } else if (u.getTipo() < 0) {
            return false;
        } else {
            return true;
        }

    }
}
