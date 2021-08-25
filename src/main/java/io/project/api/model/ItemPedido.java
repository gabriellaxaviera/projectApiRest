package io.project.api.model;

import javax.persistence.*;

@Entity
@Table
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @ManyToOne //muitos item pedido pra um pedido
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne //muitos item pedido pra um produto
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @Column
    private Integer quantidade;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
