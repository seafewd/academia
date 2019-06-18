package ch.bfh.ti.soed.academia.persistence;

import ch.bfh.ti.soed.academia.backend.models.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import javax.ejb.embeddable.EJBContainer;
import javax.inject.Inject;
import javax.naming.NamingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * TestingClass - Tests all Methods in class Repository
 * @see ch.bfh.ti.soed.academia.persistence.Repository
 */
public class RepositoryTest {
    private static EJBContainer container;

    @Inject
    private Repository repository;
    /**
     * Start method, executed when this class is called
     */
    @BeforeAll
    public static void start() {
        container = EJBContainer.createEJBContainer();
    }

    /**
     * End method, executed when all tests are done
     */
    @AfterAll
    public static void stop() {
        container.close();
    }

    /**
     * StartTest method, executed right before each test
     * @throws NamingException namingException
     */
    // See: http://tomee.apache.org/developer/testing/other/index.html
    @BeforeEach
    public void inject() throws NamingException {
        container.getContext().bind("inject", this);
    }

    /**
     * EndTest method, executed right after each test
     * @throws NamingException namingException
     */
    @AfterEach
    public void reset() throws NamingException {
        container.getContext().unbind("inject");
    }

    //tests to be implemented:
    /**
     * Tests: isEmptyTest method
     * @throws InvalidKeySpecException invalidKeySpecException
     * @throws NoSuchAlgorithmException noSuchAlgorithmException
     */
    @Test
    public void isEmptyTest() throws InvalidKeySpecException, NoSuchAlgorithmException {
        repository.createEnrollment(new ModuleRun(), new Student(), Evaluation.A);
        repository.createStudent("Norbert","Haselbaum", StudentStatus.Enrolled);
        repository.createProfessor("Donald","Bernstein");
        repository.createModule("OOP", ModuleType.PE, DegreeProgramme.Microtechnology, "cooles Fach", new Professor());
        repository.createModuleRun(new Module(), Semester.FS2018);
        repository.isEmpty();
        assertFalse(repository.isEmpty());

    }

    /**
     *  Tests: findAllTests method
     */
    @Test
    public void findAllTest(){

    }
}
