package br.com.spakoapi.entidade;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Produto.class)
public abstract class Produto_ {

	public static volatile SingularAttribute<Produto, byte[]> img4;
	public static volatile SingularAttribute<Produto, byte[]> img3;
	public static volatile SingularAttribute<Produto, BigDecimal> desconto;
	public static volatile SingularAttribute<Produto, Integer> qnt;
	public static volatile SingularAttribute<Produto, Categoria> categoria;
	public static volatile SingularAttribute<Produto, BigDecimal> valor;
	public static volatile SingularAttribute<Produto, String> nome;
	public static volatile SingularAttribute<Produto, Long> id;
	public static volatile SingularAttribute<Produto, UsuarioFornecedor> fornecedor;
	public static volatile SingularAttribute<Produto, byte[]> img2;
	public static volatile SingularAttribute<Produto, String> descricao;
	public static volatile SingularAttribute<Produto, byte[]> img1;

}

