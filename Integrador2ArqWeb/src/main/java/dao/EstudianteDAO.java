package dao;

import entity.Estudiante;
import org.apache.commons.csv.CSVParser;

import javax.persistence.EntityManager;
import java.util.List;

public interface EstudianteDAO {
    public void estudiantePersistence(EntityManager em, CSVParser parserEstudiante);
    public void insertEstudiante(EntityManager em, long DNI, String nombre, String apellido, int edad, String genero, int LU, String ciudad);
    public List<Estudiante> getEstudiantesConOrderBy(EntityManager em);
    public Estudiante getEstudianteXLU(EntityManager em, Long LU);
    public List<Estudiante> getEstudiantesXGenero(EntityManager em, String genero);
}

