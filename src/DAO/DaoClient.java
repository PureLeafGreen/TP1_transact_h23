package DAO;

import Models.Client;

public interface DaoClient {
    void save(Client client);
    Client getClient(int id);
}
