package org.shavneva.familybudget.service;

import java.util.List;

public interface ICrudService<E> {
    E create(E newE);
    List<E> read();
    E update(E newE);
    void delete(int id);
}