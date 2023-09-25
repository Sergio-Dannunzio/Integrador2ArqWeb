package entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

@Entity
@NaturalIdCache
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Carrera {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @Column(nullable=false)
    @NaturalId
    private String nombre;
    @Column
    private int cantidad_anios;
    @OneToMany(mappedBy = "carrera", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<CarreraEstudiante> estudiantes;
    
	public Carrera() {
	}

	public Carrera(String nombre, int cantidad_anios) {
		this.nombre = nombre;
		this.cantidad_anios = cantidad_anios;
        this.estudiantes = new ArrayList<CarreraEstudiante>();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getLength() {
		return cantidad_anios;
	}

	public void setLength(int cantidad_anios) {
		this.cantidad_anios = cantidad_anios;
	}

	public List<CarreraEstudiante> getEstudiantes() {
		return estudiantes;
	}

	public void setEstudiantes(List<CarreraEstudiante> estudiantes) {
		this.estudiantes = estudiantes;
	}

	public Long getId() {
		return id;
	}
	
    public void addEstudiante(Estudiante s) {
        CarreraEstudiante cs = new CarreraEstudiante(s, this);
        estudiantes.add(cs);
        s.getCarreras().add(cs);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carrera career = (Carrera) o;
        return Objects.equals(nombre, career.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }

	@Override
	public String toString() {
		return "Carrera [id=" + id + ", nombre=" + nombre + ", cantidad_anios=" + cantidad_anios +  "]";
	}
    
}
