package daobeans.implementation;

import daobeans.generic.GenericDaoLocal;

public interface AktDaoLocal extends GenericDaoLocal<Object, String> {

	public byte[] getPDFAkt(String uri);
}
