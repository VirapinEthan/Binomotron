import java.sql.*;
import java.util.*;

public class
Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Veuillez saisir le nom du projet:");
        String nom_projet = sc.nextLine();
        System.out.println("Veuillez saisir la date de création des groupes:");
        String date_creation = sc.nextLine();
        System.out.println("Veuillez saisir le numéro de projet (id):");
        int id_projet = sc.nextInt();
        System.out.println("Veuillez saisir le nombre d'apprenants par groupe:");
        int taille_groupe = sc.nextInt();


        List<String> liste_apprenants = new ArrayList<String>();
        List<String> liste_groupe = new ArrayList<>();
        int i = 0;
        int id_groupe = 1;

        String url = "jdbc:mysql://localhost:3306/binomotron";
        String user = "root";
        String pwd = "root";
        String driver = "com.mysql.jdbc.Driver";

        Connection con = null;
        Statement st = null;
        ResultSet rs1 = null;


        try {
            con = DriverManager.getConnection(url, user, pwd);
            st = con.createStatement();
            rs1 = st.executeQuery("SELECT * FROM apprenant;");

            while (rs1.next()) {
                liste_apprenants.add(rs1.getString("id_apprenant") + " " + rs1.getString("prenom_apprenant") + " " + (rs1.getString("nom_apprenant")));
            }

            System.out.println("Liste des apprenants avant mélange: " + liste_apprenants + "\n");

            Collections.shuffle(liste_apprenants);

            System.out.println("Liste des apprenants après mélange: " + liste_apprenants + "\n");

            for (i = 0; i < liste_apprenants.size(); i += taille_groupe) {
                liste_groupe.add(liste_apprenants.subList(i, Math.min(i + taille_groupe,
                        liste_apprenants.size())).toString());
                System.out.println("Groupe " + id_groupe + " : " + (liste_apprenants.subList(i, Math.min(i + taille_groupe,
                        liste_apprenants.size())).toString()) + "\n");
                id_groupe++;
            }
            System.out.println("Nous obtenons la liste de groupes suivante:" + liste_groupe + "\n");

            for (i = 0; i < liste_apprenants.size(); ) {
                String requete = "INSERT INTO apprenant_groupe_projet(id_apprenant, id_groupe, id_projet, date_creation) VALUES(id_apprenant, id_groupe, id_projet, date_creation)";
                st.executeUpdate(requete);
            }

        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());

        } finally {
            try {
                if (st != null) st.close();
                if (con != null) con.close();
            } catch (Exception e) {

            }
        }
    }
}