package daobeans.implementation;

import javax.ejb.Local;
import javax.ejb.Stateless;

import daobeans.generic.GenericDao;

@Stateless
@Local(AmandmanDaoLocal.class)
public class AmandmanDaoBean extends GenericDao<Object, String> implements AmandmanDaoLocal {

}
