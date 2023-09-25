package dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import dao.EstudianteDAO;
import entity.Estudiante;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class EstudianteDAOImpl implements EstudianteDAO{
    final static String ID = "DNI";
    final static String NOMBRE = "name";
    final static String APELLIDO = "lastName";
    final static String EDAD = "age";
    final static String GENERO = "gender";
    final static String CIUDAD = "city";
    final static String LIBRETA_UNIVERSITARIA = "LU";

	@Override
	public void estudiantePersistence(EntityManager em, CSVParser parserEstudiante) {
        for(CSVRecord row: parserEstudiante) {
            em.getTransaction().begin();

            Long DNI = Long.parseLong(row.get(ID));
            String nombre = row.get(NOMBRE);
            String apellido = row.get(APELLIDO);
            int edad = Integer.parseInt(row.get(EDAD));
            String genero = row.get(GENERO);
            String ciudad = row.get(CIUDAD);
            long LU = Long.parseLong(row.get(LIBRETA_UNIVERSITARIA));

            Estudiante insert = new Estudiante(DNI, nombre, apellido, edad, genero, ciudad, LU);
            em.persist(insert);
            em.getTransaction().commit();
        }
	}

	@Override
	public void insertEstudiante(EntityManager em, long DNI, String nombre, String apellido, int edad, String genero,
			int LU, String ciudad) {
		Estudiante s = em.find(Estudiante.class, DNI);
        Query query = null;
        if(s != null) {
            query = em.createNativeQuery("UPDATE estudiante "
                    + "SET nombre = :nombre, apellido = :apellido, "
                    + "edad = :edad, genero = :genero, LU = :LU, ciudad = :ciudad "
                    + "WHERE DNI = :DNI");
        }
        else {
            query = em.createNativeQuery("INSERT INTO Estudiante (DNI, nombre, apellido, edad, genero, ciudad, LU) "
                    + "VALUES (:DNI, :nombre, :apellido, :edad, :genero, :ciudad, :LU)");
        }

        em.getTransaction().begin();

        query.setParameter(ID, DNI);
        query.setParameter(NOMBRE, nombre);
        query.setParameter(APELLIDO, apellido);
        query.setParameter(EDAD, edad);
        query.setParameter(GENERO, genero);
        query.setParameter(CIUDAD, ciudad);
        query.setParameter(LIBRETA_UNIVERSITARIA, LU);

        query.executeUpdate();
        em.getTransaction().commit();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Estudiante> getEstudiantesConOrderBy(EntityManager em) {
        em.getTransaction().begin();
        List<Estudiante> estudiantes = em.createQuery("SELECT s FROM Estudiante s ORDER BY apellido").getResultList();
        em.getTransaction().commit();
        return estudiantes;
	}

	@Override
	public Estudiante getEstudianteXLU(EntityManager em, Long LU) {
        em.getTransaction().begin();
        Estudiante s = (Estudiante) em.createQuery("SELECT s FROM Estudiante s WHERE s.LU = :LU").
                setParameter(LIBRETA_UNIVERSITARIA, LU).getSingleResult();
        em.getTransaction().commit();
        return s;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Estudiante> getEstudiantesXGenero(EntityManager em, String genero) {
        em.getTransaction().begin();

        List<Estudiante> estudiante = em.createQuery("SELECT s FROM Estudiante s WHERE s.genero = :genero").setParameter("genero", genero).getResultList();
        em.getTransaction().commit();

        return estudiante;
	}

}
