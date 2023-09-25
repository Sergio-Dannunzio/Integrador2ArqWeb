package dao.impl;

import dao.CarreraDAO;
import entity.Carrera;
import lombok.NoArgsConstructor;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import javax.persistence.EntityManager;
import java.util.List;

@NoArgsConstructor
public class CarreraDAOImpl implements CarreraDAO {
    final static String NOMBRE = "name";
    final static String LENGTH = "length";

    @Override
    public void carreraPersistence(EntityManager em, CSVParser parserCarrera) {
    	for(CSVRecord row: parserCarrera) {
    		em.getTransaction().begin();
    		
    		String nombre = row.get(NOMBRE);
    		int cantidad_anios = Integer.parseInt(row.get(LENGTH));
    		
    		Carrera insert = new Carrera(nombre, cantidad_anios);
    		em.persist(insert);
    		em.getTransaction().commit();
    	}
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<Carrera> getCarrerasOrderByEstudiantes(EntityManager em) {
    	em.getTransaction().begin();
    	List<Carrera> carreras = em.createQuery("SELECT DISTINCT c FROM Carrera c JOIN c.estudiantes s WHERE size(s) > 0 ORDER BY size(s)").getResultList();
    	em.getTransaction().commit();
    	return carreras;
    }
	
}