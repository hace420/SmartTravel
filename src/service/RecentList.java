package service;
import java.util.LinkedList;

public class RecentList <T>{
    private LinkedList<T> list = new LinkedList<>();
    private final int MAX_SIZE = 10;

    public void addRecent(T item){
        if (item == null){
        System.out.println("Invalid entry!");return;
        } 
        list.remove(item);
        list.addFirst(item);

        // trims size incase it grows over 10 incase item is not duplicate 
        while (list.size() >  MAX_SIZE){
            list.removeLast();
        }

    }

    public void printRecent(int maxToShow){
        if (maxToShow > MAX_SIZE){
            System.out.println("Number to large");
            return;
        }
        for (int i =0;i<maxToShow;i++){
            System.out.println((i+1)+"."+list.get(i));
        }

    }

    public int size(){
        return list.size();
    }
    public boolean isEmpty(){
        return list.isEmpty();
    
    }
}
