package projet.bananaspoker.metier;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GerantDeJoueur implements Runnable
{
    private Salle serv;
    private PrintWriter out;
    private BufferedReader in;

    public GerantDeJoueur(Socket so, Salle serv) throws IOException {
        this.serv = serv;
        this.out  = new PrintWriter(so.getOutputStream(), true);
        this.in   = new BufferedReader(new InputStreamReader(so.getInputStream()));
        this.serv.connection(this);
    }

    public PrintWriter getOut() {
        return this.out;
    }

    public void run() {
        try {
            if ( serv.getMotDePasse() != null) {
                this.out.println("Mot de passe : ");
                String mdp = this.in.readLine();
                if ( !mdp.equals(serv.getMotDePasse())) {
                    this.out.println("Mot de passe incorecte, des bisous");
                    this.serv.deconnection(this);
                }
            }
            else {
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
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
