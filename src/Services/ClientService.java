package Services;

import DAO.DaoClient;
import Models.Client;

public class ClientService {

    private final DaoClient daoClient;

    public ClientService(DaoClient daoClient) {
        this.daoClient = daoClient;
    }

    public void createClient(int id, String prenom) {
        daoClient.save(new Client(id, prenom));
    }

    public Client getClient(int clientId) {
        return daoClient.getClient(clientId);
    }
}
