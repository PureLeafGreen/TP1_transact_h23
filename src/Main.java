import DAO.DaoClient;
import DAO.DaoClientImplH2;
import Models.Client;
import Services.ClientService;
import Utils.JDBCclass;

import java.sql.SQLOutput;

public class Main {
    public static void main(String[] args) {
        JDBCclass.createDatabase();
        final DaoClient daoClient = new DaoClientImplH2();
        ClientService clientService = new ClientService(daoClient);

        final Client c1 = new Client(1,"Christopher-william");

        clientService.createClient(c1.getId(), c1.getPrenom());
        final Client client = clientService.getClient(c1.getId());
        System.out.println("Mon client, id : " + client.getId() + ", prenom : " + client.getPrenom());
        //Selection de tous les records
        JDBCclass.selectRecords();
    }
}