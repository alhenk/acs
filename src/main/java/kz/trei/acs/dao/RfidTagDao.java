package kz.trei.acs.dao;

import kz.trei.acs.office.rfid.RfidTag;
import kz.trei.acs.user.User;

import java.util.List;

/**
 * Created by Admin on 07.05.14.
 */
public interface RfidTagDao extends AbstractDao<RfidTag>{
    public void createTable() throws DaoException;
}
