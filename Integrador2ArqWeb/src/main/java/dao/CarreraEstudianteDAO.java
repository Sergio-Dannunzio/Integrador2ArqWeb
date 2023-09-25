package dao;

import dto.ReporteDTO;
import entity.Estudiante;

import javax.persistence.EntityManager;

import org.apache.commons.csv.CSVParser;

import java.util.List;

public interface CarreraEstudianteDAO {
    public void carreraEstudiantePersistence(EntityManager em, CSVParser parserCarreraEstudiante);
    public void addEstudiante(EntityManager em, long idEstudiante, long idCarrera);
    public List<Estudiante> getEstudianteXCarreraFiltroCiudad(EntityManager em, Long carrera_id, String ciudad);
    public List<ReporteDTO> getReporte(EntityManager em);
}
