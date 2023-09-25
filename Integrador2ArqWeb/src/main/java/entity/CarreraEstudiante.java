package entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "carrera_estudiante")
public class CarreraEstudiante {

	@EmbeddedId
    private CarreraEstudianteId id;
    @ManyToOne
    @MapsId("estudianteId")
    @JoinColumn(name = "estudiante_id")
    private Estudiante estudiante;
    @ManyToOne
    @MapsId("carreraId")
    @JoinColumn(name = "carrera_id")
    private Carrera carrera;
    @Column
    private Integer graduacion;
    @Column
    private int antiguedad;
    
	public CarreraEstudiante() {
	}
	
	public CarreraEstudiante(Estudiante estudiante, Carrera carrera) {
		this.estudiante = estudiante;
		this.carrera = carrera;
	}
	
	public CarreraEstudiante(Estudiante estudiante, Carrera carrera,Integer graduacion,int antiguedad) {
		this.estudiante = estudiante;
		this.carrera = carrera;
		this.graduacion = graduacion;
		this.antiguedad = antiguedad;
        this.id = new CarreraEstudianteId(estudiante.getDNI(), carrera.getId());
	}

	public Estudiante getEstudiante() {
		return estudiante;
	}
	
	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}
	
	public Carrera getCarrera() {
		return carrera;
	}
	
	public void setCarrera(Carrera carrera) {
		this.carrera = carrera;
	}
	
	public Integer getGraduacion() {
		return graduacion;
	}
	
	public void setGraduacion(Integer graduacion) {
		this.graduacion = graduacion;
	}
	
	public int getAntiguedad() {
		return antiguedad;
	}
	
	public void setAntiguedad(int antiguedad) {
		this.antiguedad = antiguedad;
	}
	
	public CarreraEstudianteId getId() {
		return id;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        CarreraEstudiante that = (CarreraEstudiante) o;
        return Objects.equals(estudiante, that.estudiante) &&
                Objects.equals(carrera, that.carrera);
    }

    @Override
    public int hashCode() {
        return Objects.hash(estudiante, carrera);
    }
	
	@Override
	public String toString() {
		return "CarreraEstudiante [id=" + id + ", estudiante=" + estudiante + ", carrera=" + carrera + ", graduacion="
				+ graduacion + ", antiguedad=" + antiguedad + "]";
	}
    
    
}
