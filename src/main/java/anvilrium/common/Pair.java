package anvilrium.common;

public class Pair<E1, E2> {
	
	private E1 first;
	private E2 second;
	
	public Pair(E1 first, E2 second) {
		this.setFirst(first);
		this.setSecond(second);
	}

	/**
	 * @return the first value
	 */
	public E1 getFirst() {
		return first;
	}

	/**
	 * @param first the first to set
	 */
	public void setFirst(E1 first) {
		this.first = first;
	}

	/**
	 * @return the second value
	 */
	public E2 getSecond() {
		return second;
	}

	/**
	 * @param second the second to set
	 */
	public void setSecond(E2 second) {
		this.second = second;
	}

}
