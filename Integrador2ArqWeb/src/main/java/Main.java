import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import dao.*;
import dao.impl.*;
import dto.ReporteDTO;
import entity.*;

public class Main {
    public static void main(String[] args) throws  IOException {
        final String CSV_CAREER = "./src/main/resources/csv/career.csv";
        final String CSV_STUDENT = "./src/main/resources/csv/student.csv";
        final String CSV_CAREER_STUDENT = "./src/main/resources/csv/career_student.csv";

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Integ02");
        EntityManager em = emf.createEntityManager();

        CSVParser parserCareer = CSVFormat.DEFAULT.withHeader().parse(new FileReader(CSV_CAREER));
        CSVParser parserStudent = CSVFormat.DEFAULT.withHeader().parse(new FileReader(CSV_STUDENT));
        CSVParser parserCareerStudent = CSVFormat.DEFAULT.withHeader().parse(new FileReader(CSV_CAREER_STUDENT));

        CarreraDAO carrera = new CarreraDAOImpl();
        EstudianteDAO estudiante = new EstudianteDAOImpl();
        CarreraEstudianteDAO carreraEstudiante = new CarreraEstudianteDAOImpl();

        estudiante.estudiantePersistence(em, parserStudent);
        carrera.carreraPersistence(em, parserCareer);
        carreraEstudiante.carreraEstudiantePersistence(em, parserCareerStudent);

        System.out.println(" Recuperar todos los estudiantes, y especificar algun criterio de ordenamiento simple.");
        List<Estudiante> estudiantesOrderBy = estudiante.getEstudiantesConOrderBy(em);
        estudiantesOrderBy.forEach(s -> System.out.println(s));
        System.out.println("Recuperar un estudiante, en base a su numero de libreta universitaria.");
        System.out.println(" Numero de libreta universitaria: "+ 258406);
        Estudiante estudianteXLU = estudiante.getEstudianteXLU(em, 258406L);
        System.out.println(estudianteXLU.toString());
        System.out.println("Recuperar todos los estudiantes, en base a su genero.");
        String[] generos = {"Male","Agender","Female","Bigender","Genderfluid","Polygender","Genderqueer","Non-binary"};
        for(String genero : generos){
            System.out.println("Genero: "+genero);
            List<Estudiante> estudiantesXGenero = estudiante.getEstudiantesXGenero(em, genero);
            estudiantesXGenero.forEach(s -> System.out.println(s));
        }
        System.out.println("Recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos.");
        List<Carrera> carreras = carrera.getCarrerasOrderByEstudiantes(em);
        carreras.forEach(c -> System.out.println(c));
        System.out.println("Recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia. ");
        System.out.println("Identificador de la carrera: 17");
        System.out.println("Ciudad: Dzaoudzi");
        List<Estudiante> estudianteXCarreraFiltroCiudad = carreraEstudiante.getEstudianteXCarreraFiltroCiudad(em, 17L, "Dzaoudzi");
        estudianteXCarreraFiltroCiudad.forEach(s -> System.out.println(s));
        System.out.println("Reporte de las carreras con informacion de los inscriptos y egresados por anio, ordenando las carreras alfabeticamente y los anios de manera cronologica.\n");
        List<ReporteDTO> reportes = carreraEstudiante.getReporte(em);
        reportes.forEach(s -> System.out.println(s));
    }
}
