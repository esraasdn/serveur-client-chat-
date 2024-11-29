
import java.io.*;
import java.net.*;

public class serveur {

    @SuppressWarnings({"ConvertToTryWithResources", "CallToPrintStackTrace"})
    public static void main(String[] args) throws IOException {
        try {
            ServerSocket cs = new ServerSocket(1630); // Port 1234
            System.out.println("Serveur en attente de connexion...");
            while (true) {
                @SuppressWarnings("unused")
                Socket ss = cs.accept(); // Accepter la connexion
                System.out.println("Client connecté !");
                // creation des thread pour chaque client connecte 
                new clientinfo(ss).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class clientinfo extends Thread {

        @SuppressWarnings("FieldMayBeFinal")
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;

        public clientinfo(Socket socket) {
            this.socket = socket;
        }

        @Override
        @SuppressWarnings("CallToPrintStackTrace")
        public void run() {
            try {
                // Initialisation des flux d'entrée et de sortie pour ce client
                in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Flux d'entrée
                out = new PrintWriter(socket.getOutputStream(), true);  // Flux de sortie

                // Ajout du client à la liste des clients
                // clients.add(out);
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("Message reçu : " + message);  // Afficher le message du client

                    // Diffuser le message à tous les clients
                    out.println(message);  // Envoie le message à tous les clients

                    // Si le message est "bye", déconnecter ce client
                    if (message.equals("bye")) {
                        break;  // Sort de la boucle et arrête le thread
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (socket != null) {
                        socket.close();  // Fermer la connexion avec le client
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}






















/* 
        PrintWriter out = new PrintWriter(ss.getOutputStream(), true);

        String message = in.readLine(); // Lire le message du client
        System.out.println("Client dit : " + message);

        out.println("Message reçu : " + message); // Répondre au client

        ss.close();
        cs.close(); */
