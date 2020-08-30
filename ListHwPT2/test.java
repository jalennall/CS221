
public class test {

	public static void main(String[] args) {
	IUDoubleLinkedList<Integer> e = new IUDoubleLinkedList<Integer>();
	e.add(1);
	e.add(2);
	e.add(3);
	e.add(4);
	e.add(5);
	e.add(6);
	e.add(7);
	e.add(8);
	e.add(9);
	e.add(10);

	LinearNode<Integer> n = new LinearNode<Integer>(7);

	System.out.println("calcsize: " + e.CalcSize(n));
	}

}
