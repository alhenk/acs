package kz.trei.acs.dao;

import kz.trei.acs.office.rfid.RfidTag;

public interface RfidTagDao extends Dao<RfidTag> {
    public void createTable() throws DaoException;
}
