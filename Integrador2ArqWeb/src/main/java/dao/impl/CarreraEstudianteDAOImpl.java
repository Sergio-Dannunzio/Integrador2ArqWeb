package dao.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import dao.CarreraEstudianteDAO;
import dto.ReporteDTO;
import entity.Carrera;
import entity.CarreraEstudiante;
import entity.Estudiante;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CarreraEstudianteDAOImpl implements CarreraEstudianteDAO{
	
    final static String ANTIGUEDAD = "antiquity";
    final static String GRADUACION = "graduation";
    final static String CARRERA = "career";
    final static String ESTUDIANTE = "student";
    final static String CIUDAD = "city";

	@Override
	public void carreraEstudiantePersistence(EntityManager em, CSVParser parserCarreraEstudiante) {
        for(CSVRecord row: parserCarreraEstudiante) {
            em.getTransaction().begin();

            int antiquity = Integer.parseInt(row.get(ANTIGUEDAD));
            Integer graduation = null;
            if(!row.get(GRADUACION).equals("")){
                graduation = Integer.parseInt(row.get(GRADUACION));
            }
            Long career_id = Long.parseLong(row.get(CARRERA));
            Long student_DNI = Long.parseLong(row.get(ESTUDIANTE));

            Estudiante student = em.find(Estudiante.class, student_DNI);
            Carrera career = em.find(Carrera.class, career_id);
            student.addCarrera(career);
            career.addEstudiante(student);

            CarreraEstudiante insert = new CarreraEstudiante(student, career, graduation, antiquity);
            em.persist(insert);
            em.getTransaction().commit();
        }
	}

	@Override
	public void addEstudiante(EntityManager em, long idEstudiante, long idCarrera) {
        Query query = em.createNativeQuery("INSERT INTO carrera_estudiante (carrera_id, estudiante_id, antiguedad, graduacion) "
                + "VALUES (:carrera, :estudiante, :antiguedad, :graduacion)");

        em.getTransaction().begin();

        query.setParameter(CARRERA, idCarrera);
        query.setParameter(ESTUDIANTE, idEstudiante);
        query.setParameter(ANTIGUEDAD, 0);
        query.setParameter(GRADUACION, null);

        Estudiante estudiante = em.find(Estudiante.class, idEstudiante);
        Carrera carrera = em.find(Carrera.class, idCarrera);

        carrera.addEstudiante(estudiante);
        estudiante.addCarrera(carrera);

        query.executeUpdate();
        em.getTransaction().commit();
		
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Estudiante> getEstudianteXCarreraFiltroCiudad(EntityManager em, Long carrera_id, String ciudad) {
        em.getTransaction().begin();

        List<Estudiante> estudiantes = em.createQuery("SELECT DISTINCT(s) FROM Estudiante s, CarreraEstudiante cs "
                        + "WHERE cs.carrera.id = :carrera "
                        + "AND s.ciudad = :ciudad")
                .setParameter("carrera", carrera_id)
                .setParameter("ciudad", ciudad)
                .getResultList();

        em.getTransaction().commit();

        return estudiantes;
	}
	
    @SuppressWarnings("unchecked")
	@Override
	public List<ReporteDTO> getReporte(EntityManager em) {
        em.getTransaction().begin();

        List<Object[]> query = em.createNativeQuery("SELECT nombre, graduacion anios, NULL enrolled, cs.estudiante_id graduado "
                        + "FROM carrera c "
                        + "INNER JOIN  carrera_estudiante cs "
                        + "ON c.id= cs.carrera_id "
                        + "WHERE cs.graduacion IS NOT NULL "
                        + "UNION ALL "
                        + "SELECT nombre, YEAR(CURDATE()) - cs.antiguedad anios, cs.estudiante_id enrolled, NULL graduado "
                        + "FROM carrera c INNER JOIN  carrera_estudiante cs "
                        + "ON c.id= cs.carrera_id "
                        + "WHERE cs.graduacion IS NULL "
                        + "UNION ALL "
                        + "SELECT nombre, (graduacion - cs.antiguedad) anios, cs.estudiante_id enrolled, NULL graduado "
                        + "FROM carrera c "
                        + "INNER JOIN  carrera_estudiante cs "
                        + "ON c.id= cs.carrera_id "
                        + "WHERE cs.graduacion IS NOT NULL "
                        + "ORDER BY nombre, anios, enrolled DESC")
                .getResultList();

        List<ReporteDTO> reportes = query.stream().map(o -> new ReporteDTO((String)o[0], (BigInteger)o[1], (BigInteger)o[2], (BigInteger)o[3])).collect(Collectors.toList());

        em.getTransaction().commit();
        return reportes;
	}

}
