package br.com.alura.loja.dao;

import br.com.alura.loja.modelo.Produto;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class ProdutoDao {
    private EntityManager em;

    public ProdutoDao(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(Produto produto){
        this.em.persist(produto);
    }

    public Produto buscaPorId(Long id){
        return this.em.find(Produto.class, id);
    }

    public List<Produto> buscarTodos(){
        String jpql = "SELECT p FROM Produto AS p";
        return this.em.createQuery(jpql,Produto.class).getResultList();
    }

    public List<Produto> buscarPorNome(String nome){
        String jpql = "SELECT p FROM Produto AS p WHERE p.nome = :nome";
        return this.em.createQuery(jpql,Produto.class)
                .setParameter("nome", nome)
                .getResultList();
    }

    public List<Produto> buscarPorCategoria(String nomeCategoria){
        String jpql = "SELECT p FROM Produto AS p WHERE p.categoria.nome = :categoria";
        return this.em.createQuery(jpql,Produto.class)
                .setParameter("categoria", nomeCategoria)
                .getResultList();
    }

    public String buscarPorUmAtributoComNome(String atributo, String nome){
        String jpql = "SELECT p."+atributo+" FROM Produto AS p WHERE p.nome = :nome";
        return this.em.createQuery(jpql)
                .setParameter("nome", nome)
                .getSingleResult().toString();
    }

}
