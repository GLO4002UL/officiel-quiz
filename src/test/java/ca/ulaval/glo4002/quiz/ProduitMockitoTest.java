package ca.ulaval.glo4002.quiz;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

public class ProduitMockitoTest {
    private static final double PRIX = 22.22;
    private static final String NOM = "un produit";
    private static final double TAUX_AUCUN_RABAIS = 0;

    private Facture factureMock;
    private CalculateurRabais calculateurRabaisMock;

    private ProduitAvecInjection produit;

    @Before
    public void initialisation() {
        factureMock = mock(Facture.class);
        calculateurRabaisMock = mock(CalculateurRabais.class);

        produit = new ProduitAvecInjection(NOM, PRIX);
    }

    @Test
    public void etantDonneUnRabais_lorsqueLeProduitEstFacture_alorsLeMontantInscritPourLeProduitContientLeRabais() {
        double tauxAvecRabais = 0.1;
        willReturn(tauxAvecRabais).given(calculateurRabaisMock).taux();

        produit.facturerSur(factureMock, calculateurRabaisMock);

        then(factureMock).should().inscrireArticle(NOM, PRIX * tauxAvecRabais);
    }

    @Test
    public void etantDonneAucunRabais_lorsqueLeProduitEstFacture_alorsAucunRabaisNestApplique() {
        willReturn(TAUX_AUCUN_RABAIS).given(calculateurRabaisMock).taux();

        produit.facturerSur(factureMock, calculateurRabaisMock);

        then(factureMock).should().inscrireArticle(NOM, PRIX);
    }
}