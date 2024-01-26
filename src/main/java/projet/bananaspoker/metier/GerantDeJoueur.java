package projet.bananaspoker.metier;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GerantDeJoueur implements Runnable
{
    private boolean accepte;
    private Salle serv;
    private PrintWriter out;
    private BufferedReader in;

    public GerantDeJoueur(Socket so, Salle serv) throws IOException {
        this.serv = serv;
        this.out = new PrintWriter(so.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(so.getInputStream()));
        this.accepte = true;
        if ( serv.getMotDePasse() != null ) {
            this.out.println("Mot de passe : ");
            String mdp = this.in.readLine();
            if ( !mdp.equals(serv.getMotDePasse()))
                this.accepte = false;
        }
    }

    public PrintWriter getOut() {
        return this.out;
    }

    public boolean estAccepte() { return this.accepte; }

    public void run() {
        try {
            this.out.println("Bonjour Bienvenue sur le serveur Poker de Mathys");
            while (true) {
                this.out.println("Entrez un message :");
                String message = in.readLine();
                this.serv.envoiMess(message, this);
                if (this.out.checkError()) {
                    this.serv.deconnection(this);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
