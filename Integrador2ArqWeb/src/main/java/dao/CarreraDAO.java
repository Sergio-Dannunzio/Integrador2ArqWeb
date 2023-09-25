package dao;

import entity.Carrera;

import javax.persistence.EntityManager;

import org.apache.commons.csv.CSVParser;

import java.util.List;

public interface CarreraDAO {
    public void carreraPersistence(EntityManager em, CSVParser parserCarrera);
    public List<Carrera> getCarrerasOrderByEstudiantes(EntityManager em);
}
