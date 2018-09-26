package ca.ulaval.glo4002.quiz;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProduitMockManuelTest {
    private static final double PRIX = 22.22;
    private static final String NOM = "un produit";
    private static final double DELTA = 0.0001;
    private static final double TAUX_AUCUN_RABAIS = 0;

    private FactureMock factureMock;
    private CalculateurRabaisMock calculateurRabaisMock;

    private ProduitAvecInjection produit;

    @Before
    public void initialisation() {
        factureMock = new FactureMock();
        calculateurRabaisMock = new CalculateurRabaisMock();

        produit = new ProduitAvecInjection(NOM, PRIX);
    }

    @Test
    public void etantDonneUnRabais_lorsqueLeProduitEstFacture_alorsLeMontantInscritPourLeProduitContientLeRabais() {
        double tauxAvecRabais = 0.1;
        calculateurRabaisMock.forcerTaux(tauxAvecRabais);

        produit.facturerSur(factureMock, calculateurRabaisMock);

        factureMock.verifierProduitAvecPrix(NOM, PRIX * tauxAvecRabais);
    }

    @Test
    public void etantDonneAucunRabais_lorsqueLeProduitEstFacture_alorsAucunRabaisNestApplique() {
        calculateurRabaisMock.forcerAucunRabais();

        produit.facturerSur(factureMock, calculateurRabaisMock);

        factureMock.verifierProduitAvecPrix(NOM, PRIX);
    }

    // Classes servant de mock

    public class FactureMock implements Facture {

        private String dernierNomProduitEnregistre;
        private double dernierMontantEnregistre;

        public void inscrireArticle(String nom, double montant) {
            dernierNomProduitEnregistre = nom;
            dernierMontantEnregistre = montant;
        }

        public void verifierProduitAvecPrix(String nom, double montant) {
            assertEquals(dernierNomProduitEnregistre, nom);
            assertEquals(dernierMontantEnregistre, montant, DELTA);
        }
    }

    public class CalculateurRabaisMock extends CalculateurRabais {
        private double tauxCourant = 0;

        @Override
        public double taux() {
            return tauxCourant;
        }

        public void forcerTaux(double nouveauTaux) {
            tauxCourant = nouveauTaux;
        }

        public void forcerAucunRabais() {
            tauxCourant = TAUX_AUCUN_RABAIS;
        }
    }
}