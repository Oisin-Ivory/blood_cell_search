import BloodCellSearch.DisjointSetNode;

import static BloodCellSearch.DisjointSetNode.union;

public class main {
    public static void main(String[] args){
        DisjointSetNode a = new DisjointSetNode(1);
        DisjointSetNode b = new DisjointSetNode(2);
        DisjointSetNode c = new DisjointSetNode(3);
        DisjointSetNode d = new DisjointSetNode(4);
        DisjointSetNode e = new DisjointSetNode(5);

        b.setParent(a);
        c.setParent(b);
        e.setParent(d);
        System.out.println("--------------------------------------------");
        System.out.println(a.toString());
        System.out.println(b.toString());
        System.out.println(c.toString());
        System.out.println(d.toString());
        System.out.println(e.toString());
        System.out.println("--------------------UNION-------------------");
        union(a,d);
        System.out.println(a.toString());
        System.out.println(b.toString());
        System.out.println(c.toString());
        System.out.println(d.toString());
        System.out.println(e.toString());
    }
}
