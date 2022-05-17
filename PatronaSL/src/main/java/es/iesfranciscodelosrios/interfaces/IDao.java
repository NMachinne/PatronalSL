package es.iesfranciscodelosrios.interfaces;

import java.util.Collection;

public interface IDao<T, K> {
	boolean insert(T ob);

	T get(K id);

	Collection<T> getALL();

	int update(T ob);

	int delete(T ob);

}
