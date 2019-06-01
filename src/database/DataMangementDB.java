package database;

public interface DataMangementDB<T>{

	boolean save (T obj);
	boolean saveArray (T[] obj);
	boolean read (T obj);
	boolean readAll ();
	boolean update (T obj);
	boolean delete (T obj);
	boolean deleteAll ();
	
}
