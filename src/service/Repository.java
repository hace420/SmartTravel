//------------------------------------------
// Assignment 3)
// Question: ()
// Written by: (Christian Buckley 40329967)
//------------------------------------------
package service;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import exceptions.EntityNotFoundException;
import interfaces.*;


public class Repository<T extends Identifiable & Comparable<? super T>>{
    private List<T> items = new ArrayList<>();

    public void add(T item){
        items.add(item);
    }

    public T findById(String id) throws EntityNotFoundException{
        for (T item : items){
            if (item.getId().equals(id)){
                return item;
            } 
        }
    throw new EntityNotFoundException("Item with ID " + id + " not found in repository");
    }

    public void removeById(String id) throws EntityNotFoundException {
    for (int i = 0; i < items.size(); i++) {
        if (items.get(i).getId().equals(id)) {
            items.remove(i);
            return;
        }
    }
    throw new EntityNotFoundException("Item with ID " + id + " not found in repository");
}
    

    public List<T> filter(Predicate<T> predicate){
        List<T> results = new ArrayList<>();
        for (T item : items){
            if (predicate.test(item)){
                results.add(item);
            }
        }
        return results;
    }

    public List<T> getSorted(){
        List<T> sorted = new ArrayList<>(items);
        sorted.sort(null);
        return sorted;
    }
    
}
