package ca.ulaval.glo4002.quiz;

public class ProduitAvecInjection {
    private String nom;
    private final double prixUnitaire;

    public ProduitAvecInjection(String nom, double prix) {
        this.nom = nom;
        this.prixUnitaire = prix;
    }

    public void facturerSur(Facture facture, CalculateurRabais calculateurRabais) {
        double montant = prixUnitaire;
        if (calculateurRabais.taux() > 0) {
            montant = montant * calculateurRabais.taux();
        }

        facture.inscrireArticle(nom, montant);
    }
}

