package br.com.alura.loja.testes;

import br.com.alura.loja.dao.CategoriaDao;
import br.com.alura.loja.dao.ProdutoDao;
import br.com.alura.loja.modelo.Categoria;
import br.com.alura.loja.modelo.Cliente;
import br.com.alura.loja.modelo.Pedido;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.List;

public class CadastroDeProduto {
    public static void main(String[] args) {
        cadastrarProduto();
        Long id = 1l;

        Cliente cliente = new Cliente();
        Pedido pedido = new Pedido();

        EntityManager em = JPAUtil.getEntityManager();
        ProdutoDao produtoDao = new ProdutoDao(em);

        Produto produtoBuscado = produtoDao.buscaPorId(1l);

        List<Produto> todos = produtoDao.buscarPorCategoria("APARELHOS CELULARES");
        todos.forEach(x -> System.out.println(x.getNome()));

        String precoProduto = produtoDao.buscarPorUmAtributoComNome("categoria.nome", "Iphone");
        System.out.println(precoProduto);




    }

    private static void cadastrarProduto() {
        Produto celular = new Produto();
        Categoria celulares = new Categoria();
        EntityManager em = JPAUtil.getEntityManager();
        ProdutoDao produtoDao = new ProdutoDao(em);
        CategoriaDao categoriaDao = new CategoriaDao(em);


        celulares.setNome("CELULARES");

        celular.setNome("Iphone");
        celular.setDescricao("Caro");
        celular.setPreco(new BigDecimal("800"));
        celular.setCategoria(celulares);


        em.getTransaction().begin();//Começa as operações no BD

        categoriaDao.cadastrar(celulares);
        produtoDao.cadastrar(celular);
        celulares.setNome("APARELHOS CELULARES");
        categoriaDao.atualizar(celulares);


        em.getTransaction().commit();//Enviar as operações realizadas no sistema para o BD
        em.close();//Fechar o BD
    }
}
