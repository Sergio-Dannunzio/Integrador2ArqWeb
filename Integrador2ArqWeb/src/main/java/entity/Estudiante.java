package entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Estudiante {

    @Id
    private Long DNI;
    @Column(nullable=false)
    private String nombre;
    @Column(nullable=false)
    private String apellido;
    @Column(nullable=false)
    private int edad;
    @Column(nullable=false)
    private String genero;
    @Column(nullable=false)
    private String ciudad;
    @Column(unique=true)
    private long LU;
    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<CarreraEstudiante> carreras;
    
	public Estudiante() {
	}

	public Estudiante(Long dni, String nombre, String apellido, int edad, String genero, String ciudad, long lu) {
		super();
		DNI = dni;
		this.nombre = nombre;
		this.apellido = apellido;
		this.edad = edad;
		this.genero = genero;
		this.ciudad = ciudad;
		LU = lu;
        this.carreras = new ArrayList<CarreraEstudiante>();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public List<CarreraEstudiante> getCarreras() {
		return carreras;
	}

	public void setCarreras(List<CarreraEstudiante> carreras) {
		this.carreras = carreras;
	}

	public Long getDNI() {
		return DNI;
	}

	public int getEdad() {
		return edad;
	}

	public long getLU() {
		return LU;
	}
	
    public void addCarrera(Carrera c) {
        CarreraEstudiante cs = new CarreraEstudiante(this, c);
        carreras.add(cs);
        c.getEstudiantes().add(cs);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Estudiante estudiante = (Estudiante) o;
        return Objects.equals(nombre, estudiante.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }

	@Override
	public String toString() {
		return "Estudiante [DNI=" + DNI + ", nombre=" + nombre + ", apellido=" + apellido + ", edad=" + edad
				+ ", genero=" + genero + ", ciudad=" + ciudad + ", LU=" + LU  + "]";
	}
    
}
