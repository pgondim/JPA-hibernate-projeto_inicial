package br.com.alura.loja.dao;

import br.com.alura.loja.modelo.Pedido;
import br.com.alura.loja.vo.RelatorioDeVendasVo;


import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

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

    public List<RelatorioDeVendasVo> relatorioDeVendas(){
        String jpql = "SELECT new br.com.alura.loja.vo.RelatorioDeVendasVo(" +
                " prod.nome, " +
                "SUM(i.quantidade), " +
                "MAX(p.data)) " +
                "FROM Pedido AS p " +
                "JOIN p.itens AS i " +
                "JOIN i.produto AS prod " +
                "GROUP BY prod.nome " +
                "ORDER BY i.quantidade DESC";
        return em.createQuery(jpql, RelatorioDeVendasVo.class)
                .getResultList();
    }

    public Pedido buscarPedidoComCliente(Long id){
        String jpql = "SELECT p FROM Pedido AS p JOIN FETCH p.cliente WHERE p.id = :id";
        return em.createQuery(jpql, Pedido.class)
                .setParameter("id", id)
                .getSingleResult();
    }

}
