package br.com.alura.loja.testes;

import br.com.alura.loja.dao.CategoriaDao;
import br.com.alura.loja.dao.ClienteDao;
import br.com.alura.loja.dao.PedidoDao;
import br.com.alura.loja.dao.ProdutoDao;
import br.com.alura.loja.modelo.*;
import br.com.alura.loja.util.JPAUtil;
import br.com.alura.loja.vo.RelatorioDeVendasVo;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class PerformanceConsultas {
    public static void main(String[] args) {
        EntityManager em = JPAUtil.getEntityManager();

        Categoria categoria1 = new Categoria();
        Categoria categoria2 = new Categoria();
        Categoria categoria3 = new Categoria();
        Produto produto1 = new Produto();
        Produto produto2 = new Produto();
        Produto produto3 = new Produto();

        CategoriaDao categoriaDao = new CategoriaDao(em);
        ProdutoDao produtoDao =  new ProdutoDao(em);
        PedidoDao pedidoDaoComFetch = new PedidoDao(em);

        categoria1.setNome("NOTEBOOKS");
        categoria2.setNome("MOUSE");
        categoria3.setNome("TECLADO");

        produto1.setCategoria(categoria1);
        produto1.setPreco(BigDecimal.valueOf(3000));
        produto1.setNome("ACER");
        produto1.setDescricao("GAMER");

        produto2.setCategoria(categoria2);
        produto2.setPreco(BigDecimal.valueOf(1000));
        produto2.setNome("LOGITECH");
        produto2.setDescricao("MOUSE GAMER");

        produto3.setCategoria(categoria3);
        produto3.setPreco(BigDecimal.valueOf(2000));
        produto3.setNome("RYZER");
        produto3.setDescricao("TECLADO GAMER");

        em.getTransaction().begin();

        categoriaDao.cadastrar(categoria1);
        categoriaDao.cadastrar(categoria2);
        categoriaDao.cadastrar(categoria3);

        produtoDao.cadastrar(produto1);
        produtoDao.cadastrar(produto2);
        produtoDao.cadastrar(produto3);

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
        pedido.adicionarItem(new ItemPedido(3,produto1,pedido));
        pedido.adicionarItem(new ItemPedido(2,produto2,pedido));
        pedido.adicionarItem(new ItemPedido(1,produto3,pedido));

        pedidoDao.cadastrar(pedido);

        Pedido pedidoBuscado = pedidoDao.buscaPorId(1l);
        System.out.println("Valor total: R$ "+pedidoBuscado.getvalorTotal());

        List<RelatorioDeVendasVo> relatorio = pedidoDao.relatorioDeVendas();

        relatorio.forEach(System.out::println);

        Pedido pedidoComFetch = pedidoDaoComFetch.buscarPedidoComCliente(1l);


        em.getTransaction().commit();
        em.close();

        System.out.println(pedidoComFetch.getCliente().getNome());





    }
}
