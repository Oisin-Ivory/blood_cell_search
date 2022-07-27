package BloodCellSearch;

public class DisjointSetNode<E> {
    public DisjointSetNode<?> parent = null;
    public E data;

    public static void union(DisjointSetNode<?> node1, DisjointSetNode<?> node2) {
        if(node2.getParent()==null) {
            node2.setParent(node1);
        }else {
            findHead(node2).setParent(node1);
        }
    }

    public DisjointSetNode(E data){
        this.data = data;
    }

    public static DisjointSetNode<?> findHead(DisjointSetNode<?> node){
       if(node.getParent() == null){
             return node;
        }else{
           return findHead(node.getParent());
       }
    }


    public DisjointSetNode<?> getParent() {
        return parent;
    }

    public void setParent(DisjointSetNode<?> parent) {
        this.parent = parent;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }


//    @Override
//    public String toString(){
//        if(this.parent!=null) {
//            return data + " head: "+findHead(this).getData();
//        }else{
//            return data + " head: null";
//        }
//    }
}
