package br.com.alura.loja.modelo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cliente cliente;

    private BigDecimal valorTotal = BigDecimal.ZERO;
    private LocalDate data = LocalDate.now();

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)//informa que esse relacionamento já foi mapeado em outro lugar
    private List<ItemPedido> itens = new ArrayList<>();

    public Pedido() {}

    public Pedido(Cliente cliente) {
        this.cliente = cliente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public BigDecimal getvalorTotal() {
        return valorTotal;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void adicionarItem(ItemPedido item){
        item.setPedido(this);
        this.itens.add(item);
        this.valorTotal = this.valorTotal.add(item.getValor());
    }

    public BigDecimal valorTotalVendido(EntityManager em){
        String jpql = "SELECT SUM(p.valorTotal) FROM Pedido AS p";
        this.valorTotal = em.createQuery(jpql, BigDecimal.class)
                .getSingleResult();
        return em.createQuery(jpql, BigDecimal.class)
                .getSingleResult();
    }

}
