package br.com.spakoapi.dao;

import br.com.spakoapi.entidade.Usuario;
import br.com.spakoapi.excecao.DAOException;
import br.com.spakoapi.excecao.ErrorCode;
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

    public Usuario getById(long id) {

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
            throw new DAOException("Usuário" + id + " não encontrado ", ErrorCode.NOT_FOUND.getCode());
        }

        return u;
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
        Usuario existente = null;

        if (!usuarioValido(u)) {
            throw new DAOException("Usuário inválido! ", ErrorCode.BAD_REQUEST.getCode());
        }

        if (u.getId() <= 0) {
            throw new DAOException("Usuário com id inválido! ", ErrorCode.BAD_REQUEST.getCode());
        }

        existente = getById(u.getId());

        if (existente == null) {
            throw new DAOException("Usuário não existe! ", ErrorCode.BAD_REQUEST.getCode());
        }

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
        Usuario usuario = null;

        usuario = getById(id);

        if (usuario == null) {
            throw new DAOException("Usuário não existe! ", ErrorCode.BAD_REQUEST.getCode());
        }

        try {
            em.getTransaction().begin();
            em.remove(usuario);
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
            u = em.createQuery("select u from Usuario u where u.nome like :nome", Usuario.class)
                    .setParameter("nome", "%" + nome + "%")
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
