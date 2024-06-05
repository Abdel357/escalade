import com.example.jeeProjet.JeeProjetApplication;
import com.example.jeeProjet.model.Categorie;
import com.example.jeeProjet.model.Membre;
import com.example.jeeProjet.model.Sortie;
import com.example.jeeProjet.repo.CategorieRepository;
import com.example.jeeProjet.repo.MembreRepository;
import com.example.jeeProjet.repo.SortieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = JeeProjetApplication.class)
public class RepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CategorieRepository categorieRepository;

    @Autowired
    private SortieRepository sortieRepository;

    @Autowired
    private MembreRepository membreRepository;

    private Categorie testCategorie;
    private Membre testMembre;
    private Sortie testSortie;

    @BeforeEach
    public void setUp() {
        // Initialisation de la catégorie
        testCategorie = entityManager.persistAndFlush(new Categorie("Alpinisme de roche"));

        // Initialisation du membre
        testMembre = entityManager.persistAndFlush(new Membre("Maldonado", "Kevin", "kevin.maldonado@exemple.com", "motdepasse"));

        // Initialisation de la sortie
        testSortie = new Sortie("Sortie Mont Blanc", new Date(), testMembre, testCategorie);
        testSortie.setDescription("Ascension du Mont Blanc");
        testSortie.setSiteWeb("http://montblanc.com");
        testSortie = entityManager.persistAndFlush(testSortie);

        //Association de chaque relation
        testCategorie.addSortie(testSortie);
        testMembre.addSortie(testSortie);
    }

    // Test Pour Categorie
    @Test
    public void findAllCategoriesTest() {
        List<Categorie> categories = categorieRepository.findAll();

        // Assure que la liste des catégories n'est pas vide
        assertFalse(categories.isEmpty());

        // Vérifie qu'il existe une catégorie avec le bon nom
        assertTrue(categories.stream().anyMatch(c -> c.getNom().equals("Alpinisme de roche")));
    }

    @Test
    public void findCategorieBySortiesTest() {
        Optional<Categorie> foundCategorie = categorieRepository.findCategoriesBySorties(testCategorie.getId());

        // La catégorie (avec son id) est bien trouvé
        assertTrue(foundCategorie.isPresent(), "La catégorie devrait être trouvée.");

        // La catégorie trouvé a au moins une sortie associée
        assertFalse(foundCategorie.get().getSorties().isEmpty());
    }


    @Test
    public void createCategorieTest() {
        Categorie newCat = new Categorie(null, "Randonnée du vertige", new HashSet<>());
        Categorie savedCat = categorieRepository.save(newCat);

        // La catégorie créer a un ID
        assertNotNull(savedCat.getId());
    }

    //Test pour Sortie
    @Test
    public void findSortieByIdTest() {
        Optional<Sortie> foundSortie = sortieRepository.findById(testSortie.getId());

        // La sortie spécifiée par son ID a été trouvée
        assertTrue(foundSortie.isPresent());

        // Le nom de la sortie correspond bien
        assertEquals("Sortie Mont Blanc", foundSortie.get().getNom());
    }

    @Test
    public void findByNomContainingTest() {
        List<Sortie> foundSorties = sortieRepository.findByNomContaining("Mont Blanc");

        // La recherche par nom contenant "Mont Blanc" retourne au moins une sortie
        assertFalse(foundSorties.isEmpty());

        // Vérifie qu'il n'y a qu'une seule sortie
        assertEquals(1, foundSorties.size());
    }

    @Test
    public void createUpdateDeleteSortieTest() {
        Sortie newSortie = new Sortie(null, "Test Sortie", "Testing", "http://test.com", new Date(), testMembre, testCategorie);
        Sortie savedSortie = sortieRepository.save(newSortie);

        // La nouvelle sortie a été créer avec un ID
        assertNotNull(savedSortie.getId());

        savedSortie.setNom("Updated Test Sortie");
        Sortie updatedSortie = sortieRepository.save(savedSortie);

        // Vérifie que la mise à jour du nom de la sortie a bien été enregistrée
        assertEquals("Updated Test Sortie", updatedSortie.getNom());

        sortieRepository.delete(updatedSortie);
        Optional<Sortie> deletedSortie = sortieRepository.findById(savedSortie.getId());

        // Confirme que la sortie a bien été supprimée
        assertFalse(deletedSortie.isPresent());
    }

    //Test pour Membre
    @Test
    public void findAllMembresTest() {
        List<Membre> membres = membreRepository.findAll();

        // Assure que la liste des membres n'est pas vide
        assertFalse(membres.isEmpty());

        // Vérifie qu'il existe un membre avec l'email "kevin.maldonado@exemple.com"
        assertTrue(membres.stream().anyMatch(m -> m.getEmail().equals("kevin.maldonado@exemple.com")));
    }

    @Test
    public void findByIdTest() {
        Optional<Membre> foundMembre = membreRepository.findById(testMembre.getId());

        // Confirme que le membre spécifié par son ID a été trouvé
        assertTrue(foundMembre.isPresent());

        // Vérifie que l'email du membre correspond à "kevin.maldonado@exemple.com"
        assertEquals("kevin.maldonado@exemple.com", foundMembre.get().getEmail());
    }

    @Test
    public void createAndUpdateMembreTest() {
        Membre newMembre = new Membre("Smith", "Adam", "adam.smith@example.com", "securemotdepasse");
        Membre savedMembre = membreRepository.save(newMembre);

        // Confirme que le nouveau membre a été créer avec un ID
        assertNotNull(savedMembre.getId());

        savedMembre.setPrenom("Updated Adam");
        Membre updatedMembre = membreRepository.save(savedMembre);

        // Vérifie que la mise à jour du prénom du membre a été enregistrée
        assertEquals("Updated Adam", updatedMembre.getPrenom());
    }

    @Test
    public void findMembresBySortiesTest() {
        Optional<Membre> foundMembre = membreRepository.findMembresBySorties(testMembre.getId());

        // Le membre est bien trouvé
        assertTrue(foundMembre.isPresent(), "Le membre devrait être trouvé.");

        // Le membre trouvé a au moins une sortie associée
        assertFalse(foundMembre.get().getSorties().isEmpty());
    }

}
