/**
 *   Copyright (c) Rich Hickey. All rights reserved.
 *   The use and distribution terms for this software are covered by the
 *   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
 *   which can be found in the file epl-v10.html at the root of this distribution.
 *   By using this software in any fashion, you are agreeing to be bound by
 * 	 the terms of this license.
 *   You must not remove this notice, or any other, from this software.
 **/

package clojure.lang;

public abstract class ASeq extends ASequential implements ISeq {

public String toString(){
	return RT.printString(this);
}

public IPersistentCollection empty(){
	return PersistentList.EMPTY;
}

protected ASeq(IPersistentMap meta){
	super(meta);
}


protected ASeq(){
}

//public Object reduce(IFn f) {
//	Object ret = first();
//	for(ISeq s = rest(); s != null; s = s.rest())
//		ret = f.invoke(ret, s.first());
//	return ret;
//}
//
//public Object reduce(IFn f, Object start) {
//	Object ret = f.invoke(start, first());
//	for(ISeq s = rest(); s != null; s = s.rest())
//		ret = f.invoke(ret, s.first());
//	return ret;
//}

//public Object peek(){
//	return first();
//}
//
//public IPersistentList pop(){
//	return rest();
//}

public int count(){
	int i = 1;
	for(ISeq s = next(); s != null; s = s.next(), i++)
		if(s instanceof Counted)
			return i + s.count();
	return i;
}

final public ISeq seq(){
	return this;
}

public ISeq cons(Object o){
	return new Cons(o, this);
}

public ISeq more(){
    ISeq s = next();
    if(s == null)
        return PersistentList.EMPTY;
    return s;
}

//final public ISeq rest(){
//    Seqable m = more();
//    if(m == null)
//        return null;
//    return m.seq();
//}

}
