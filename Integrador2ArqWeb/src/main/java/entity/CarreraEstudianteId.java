package entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class CarreraEstudianteId implements Serializable{

    @Column(name = "estudiante_id")
    private Long estudianteId;
    @Column(name = "carrera_id")
    private Long carreraId;
    
	public CarreraEstudianteId(Long estudianteId, Long carreraId) {
		this.estudianteId = estudianteId;
		this.carreraId = carreraId;
	}

	public Long getEstudianteId() {
		return estudianteId;
	}

	public void setEstudianteId(Long estudianteId) {
		this.estudianteId = estudianteId;
	}

	public Long getCarreraId() {
		return carreraId;
	}

	public void setCarreraId(Long carreraId) {
		this.carreraId = carreraId;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        CarreraEstudianteId that = (CarreraEstudianteId) o;
        return Objects.equals(carreraId, that.carreraId) &&
                Objects.equals(estudianteId, that.estudianteId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carreraId, estudianteId);
    }

	@Override
	public String toString() {
		return "CarreraEstudianteId [estudianteId=" + estudianteId + ", carreraId=" + carreraId + "]";
	}
    
}
