package service;
import java.util.List;

// <T> poate fi Client, Locatie etc.
public interface GenericService<T> {
    void adauga(T entity); // create
    List<T> citeste(); // read
    void actualizeaza(T entity); // update
    void sterge(int id); // delete
}
