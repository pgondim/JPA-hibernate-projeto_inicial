package br.com.alura.loja.dao;

import br.com.alura.loja.modelo.Categoria;
import br.com.alura.loja.modelo.Produto;

import javax.persistence.EntityManager;

public class CategoriaDao {
    private EntityManager em;

    public CategoriaDao(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(Categoria categoria){
        this.em.persist(categoria);//inclui já passando o objeto pra <MANAGED>
    }

    public void atualizar (Categoria categoria){
        this.em.merge(categoria);//o objeto já estará alterado, então só precisamos fazê-lo voltar pro estado <MANAGED> para que possa ser sincronizado no BD
    }

    public void excluir(Categoria categoria){
        categoria = this.em.merge(categoria);
        this.em.remove(categoria);
    }
}
