package Models;



import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

@Entity
public class Medidas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Esto indica que el medida_id es autoincremental
    private Integer medidaId;

    @Column(nullable = false, length = 30)
    private String medida;

    // Getters y setters

    public Integer getMedidaId() {
        return medidaId;
    }

    public void setMedidaId(Integer medidaId) {
        this.medidaId = medidaId;
    }

    public String getMedida() {
        return medida;
    }

    public void setMedida(String medida) {
        this.medida = medida;
    }

    // Constructor sin parámetros
    public Medidas() {
    }

    // Constructor con parámetros
    public Medidas(Integer medidaId, String medida) {
        this.medidaId = medidaId;
        this.medida = medida;
    }
}
