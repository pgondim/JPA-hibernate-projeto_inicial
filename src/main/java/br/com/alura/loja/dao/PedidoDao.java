package br.com.alura.loja.dao;

import br.com.alura.loja.modelo.Pedido;


import javax.persistence.EntityManager;

public class PedidoDao {

    private EntityManager em;

    public PedidoDao(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(Pedido pedido){
        this.em.persist(pedido);
    }

    public Pedido buscaPorId(Long id){
        return this.em.find(Pedido.class, id);
    }

}