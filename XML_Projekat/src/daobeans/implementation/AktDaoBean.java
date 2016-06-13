package daobeans.implementation;

import javax.ejb.Local;
import javax.ejb.Stateless;

import daobeans.generic.GenericDao;

@Stateless
@Local(AktDaoLocal.class)
public class AktDaoBean extends GenericDao<Object, String> implements AktDaoLocal {

}
