package br.com.spakoapi.recurso;

import br.com.spakoapi.entidade.Usuario;
import br.com.spakoapi.servico.UsuarioServico;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/usuario")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class UsuarioRecurso {

    private final UsuarioServico servico = new UsuarioServico();

    @GET
    public List<Usuario> getUsuarios(
            @QueryParam("nome") String nome,
            @QueryParam("offset") int offset,
            @QueryParam("limit") int limit,
            @QueryParam("id") int id
    ) {
        if ((offset > 0) && (limit > 0)) {
            return servico.getUsuariosPaginacao(offset, limit);
        }
        if (nome != null) {
            return servico.getUsuarioPorNome(nome);
        }
        if (id > 0) {
            return servico.getUsuarioPorId(id);
        }
        return servico.getUsuarios();
    }

    @POST
    public Response novoUsuario(Usuario u) {
        servico.salvarUsuario(u);
        return Response.status(Status.CREATED).build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") long id) {
        servico.apagarUsuario(id);
        return Response.status(Status.OK).build();
    }

    @PUT
    public Response update(Usuario u) {
        servico.alterarUsuario(u);
        return Response.status(Status.OK).build();
    }

}
