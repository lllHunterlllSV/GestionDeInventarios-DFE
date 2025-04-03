package Models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "productos")
public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "producto_id")
    private Integer productoId;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToOne
    @JoinColumn(name= "categoria_id", nullable = false)
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "medida_id", nullable = false)
    private Medidas medidas;

    @ManyToOne
    @JoinColumn(name = "proveedor_id", nullable = false)
    private Proveedores proveedores;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    private List<DetalleOrdenCompra> detalleOrdenCompras;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    private List<DetalleVenta> detalleVentas;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    private List<Lote> lotes;

    public Producto(){

    }

    public Producto(String nombre, String descripcion, Categoria categoria, Medidas medidas, Proveedores proveedores){
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.medidas = medidas;
        this.proveedores = proveedores;
    }


    //GETTERS
    public Integer getProductoId() {
        return productoId;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Medidas getMedidas() {
        return medidas;
    }

    public Proveedores getProveedores() {
        return proveedores;
    }

    public List<DetalleOrdenCompra> getDetalleOrdenCompras() {
        return detalleOrdenCompras;
    }

    public List<DetalleVenta> getDetalleVentas() {
        return detalleVentas;
    }

    public List<Lote> getLotes() {
        return lotes;
    }

    //SETTERS
    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void setMedidas(Medidas medidas) {
        this.medidas = medidas;
    }

    public void setProveedores(Proveedores proveedores) {
        this.proveedores = proveedores;
    }

    public void setDetalleOrdenCompras(List<DetalleOrdenCompra> detalleOrdenCompras) {
        this.detalleOrdenCompras = detalleOrdenCompras;
    }

    public void setDetalleVentas(List<DetalleVenta> detalleVentas) {
        this.detalleVentas = detalleVentas;
    }

    public void setLotes(List<Lote> lotes) {
        this.lotes = lotes;
    }

    
}
