package br.com.spakoapi.entidade;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UsuarioComum.class)
public abstract class UsuarioComum_ {

	public static volatile SingularAttribute<UsuarioComum, String> telefone;
	public static volatile SingularAttribute<UsuarioComum, Cidade> cidade;
	public static volatile SingularAttribute<UsuarioComum, String> endereco;
	public static volatile SingularAttribute<UsuarioComum, String> numero;
	public static volatile SingularAttribute<UsuarioComum, String> bairro;
	public static volatile SingularAttribute<UsuarioComum, String> celular;
	public static volatile SingularAttribute<UsuarioComum, Usuario> usuario;
	public static volatile SingularAttribute<UsuarioComum, Long> id;
	public static volatile SingularAttribute<UsuarioComum, String> cep;

}

