package br.com.alura.loja.testes;

import br.com.alura.loja.dao.CategoriaDao;
import br.com.alura.loja.dao.ClienteDao;
import br.com.alura.loja.dao.PedidoDao;
import br.com.alura.loja.dao.ProdutoDao;
import br.com.alura.loja.modelo.*;
import br.com.alura.loja.util.JPAUtil;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CadastroDePedido {

    public static void main(String[] args) {
        EntityManager em = JPAUtil.getEntityManager();

        Categoria categoria = new Categoria();
        Produto produto = new Produto();

        CategoriaDao categoriaDao = new CategoriaDao(em);
        ProdutoDao produtoDao =  new ProdutoDao(em);

        categoria.setNome("NOTEBOOKS");

        produto.setCategoria(categoria);
        produto.setPreco(BigDecimal.valueOf(3000));
        produto.setNome("ACER");
        produto.setDescricao("GAMER");

        em.getTransaction().begin();

        categoriaDao.cadastrar(categoria);

        produtoDao.cadastrar(produto);

        //Cadastro cliente
        Cliente cliente = new Cliente();
        cliente.setNome("Pedro Gondim");
        cliente.setCpf("123.123.123-12");

        ClienteDao clienteDao = new ClienteDao(em);
        clienteDao.cadastrar(cliente);


        //Cadastro do pedido
        Pedido pedido = new Pedido();
        PedidoDao pedidoDao = new PedidoDao(em);

        //primeiro devemos saber quais produtos vamos cadastrar
        //para isso, devemos fazer a busca no BD
        Produto produtoPedido = produtoDao.buscaPorId(1l);

        pedido.setCliente(cliente);
        pedido.adicionarItem(new ItemPedido(3,produtoPedido,pedido));

        pedidoDao.cadastrar(pedido);

        Pedido pedidoBuscado = pedidoDao.buscaPorId(1l);
        System.out.println("Valor total: "+pedidoBuscado.getvalorTotal());




        em.getTransaction().commit();
        em.close();



    }

}
