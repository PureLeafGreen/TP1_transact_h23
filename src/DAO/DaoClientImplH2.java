package DAO;

import Models.Client;
import Utils.JDBCclass;



public class DaoClientImplH2 implements DaoClient{

    @Override
    public void save(Client client) {
        JDBCclass.save(client);
    }

    @Override
    public Client getClient(int id) {
        return  JDBCclass.getClient(id);
    }
}
