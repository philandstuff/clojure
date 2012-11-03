package clojure.lang;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public abstract class ASequential extends Obj implements IPersistentCollection, Sequential, List, Serializable, IHashEq {
transient int _hash = -1;

protected ASequential(IPersistentMap meta) {
    super(meta);
}

protected ASequential() {
}

public boolean equiv(Object obj){

    if(!(obj instanceof Sequential || obj instanceof List))
        return false;
    ISeq ms = RT.seq(obj);
    for(ISeq s = seq(); s != null; s = s.next(), ms = ms.next())
        {
        if(ms == null || !Util.equiv(s.first(), ms.first()))
            return false;
        }
    return ms == null;

}

public boolean equals(Object obj){
    if(this == obj) return true;
    if(!(obj instanceof Sequential || obj instanceof List))
        return false;
    ISeq ms = RT.seq(obj);
    for(ISeq s = seq(); s != null; s = s.next(), ms = ms.next())
        {
        if(ms == null || !Util.equals(s.first(), ms.first()))
            return false;
        }
    return ms == null;

}

public int hashCode(){
    if(_hash == -1)
        {
        int hash = 1;
        for(ISeq s = seq(); s != null; s = s.next())
            {
            hash = 31 * hash + (s.first() == null ? 0 : s.first().hashCode());
            }
        this._hash = hash;
        }
    return _hash;
}

public int hasheq(){
    int hash = 1;
    for(ISeq s = seq(); s != null; s = s.next())
        {
        hash = 31 * hash + Util.hasheq(s.first());
        }
    return hash;
}

// java.util.Collection implementation

public Object[] toArray(){
    return RT.seqToArray(seq());
}

public boolean add(Object o){
    throw new UnsupportedOperationException();
}

public boolean remove(Object o){
    throw new UnsupportedOperationException();
}

public boolean addAll(Collection c){
    throw new UnsupportedOperationException();
}

public void clear(){
    throw new UnsupportedOperationException();
}

public boolean retainAll(Collection c){
    throw new UnsupportedOperationException();
}

public boolean removeAll(Collection c){
    throw new UnsupportedOperationException();
}

public boolean containsAll(Collection c){
    for(Object o : c)
        {
        if(!contains(o))
            return false;
        }
    return true;
}

public Object[] toArray(Object[] a){
    return RT.seqToPassedArray(seq(), a);
}

public int size(){
    return count();
}

public boolean isEmpty(){
    return seq() == null;
}

public boolean contains(Object o){
    for(ISeq s = seq(); s != null; s = s.next())
        {
        if(Util.equiv(s.first(), o))
            return true;
        }
    return false;
}

public Iterator iterator(){
    return new SeqIterator(seq());
}

//////////// List stuff /////////////////
private List reify(){
    return Collections.unmodifiableList(new ArrayList(this));
}

public List subList(int fromIndex, int toIndex){
    return reify().subList(fromIndex, toIndex);
}

public Object set(int index, Object element){
    throw new UnsupportedOperationException();
}

public Object remove(int index){
    throw new UnsupportedOperationException();
}

public int indexOf(Object o){
    ISeq s = seq();
    for(int i = 0; s != null; s = s.next(), i++)
        {
        if(Util.equiv(s.first(), o))
            return i;
        }
    return -1;
}

public int lastIndexOf(Object o){
    return reify().lastIndexOf(o);
}

public ListIterator listIterator(){
    return reify().listIterator();
}

public ListIterator listIterator(int index){
    return reify().listIterator(index);
}

public Object get(int index){
    return RT.nth(this, index);
}

public void add(int index, Object element){
    throw new UnsupportedOperationException();
}

public boolean addAll(int index, Collection c){
    throw new UnsupportedOperationException();
}
}
