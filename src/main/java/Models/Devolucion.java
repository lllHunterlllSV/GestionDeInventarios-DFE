package Models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "devoluciones")
public class Devolucion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "devolucion_id")
    private Integer devolucionId;

    @ManyToOne
    @JoinColumn(name = "venta_id", nullable = false)
    private Venta venta;

    @ManyToOne
    @JoinColumn(name = "detalleVenta_id", nullable = false)
    private DetalleVenta detalleVenta;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "motivo")
    private String motivo;

    @Column(name = "fecha_devolucion")
    private Date fechaDevolucion;

    @Column(name = "estado")
    private String estado; // "Solicitada", "Aprobada", "Rechazada"

    // Constructor vacío
    public Devolucion() {
    }

    // Constructor con parámetros
    public Devolucion(Venta venta, DetalleVenta detalleVenta, Usuario usuario, Integer cantidad, String motivo, Date fechaDevolucion, String estado) {
        this.venta = venta;
        this.detalleVenta = detalleVenta;
        this.usuario = usuario;
        this.cantidad = cantidad;
        this.motivo = motivo;
        this.fechaDevolucion = fechaDevolucion;
        this.estado = estado;
    }

    // Getters y Setters
    public Integer getDevolucionId() {
        return devolucionId;
    }

    public void setDevolucionId(Integer devolucionId) {
        this.devolucionId = devolucionId;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public DetalleVenta getDetalleVenta() {
        return detalleVenta;
    }

    public void setDetalleVenta(DetalleVenta detalleVenta) {
        this.detalleVenta = detalleVenta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}