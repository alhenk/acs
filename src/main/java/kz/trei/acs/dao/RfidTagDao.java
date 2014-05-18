package kz.trei.acs.dao;

import kz.trei.acs.office.rfid.RfidTag;

/**
 * Created by Admin on 07.05.14.
 */
public interface RfidTagDao extends Dao<RfidTag> {
    public void createTable() throws DaoException;
}
