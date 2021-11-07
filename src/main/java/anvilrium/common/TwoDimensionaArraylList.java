package anvilrium.common;

import java.io.PrintStream;
import java.util.ArrayList;

public class TwoDimensionaArraylList<E> {
	
	private ArrayList<ArrayList<E>> values;
	
	private int rowCount;
	private int columnCount;
	
	public TwoDimensionaArraylList(int inRowCount, int inColumnCount) {
		rowCount = inRowCount;
		columnCount = inColumnCount;
		values = new ArrayList<>(rowCount);
		for (int i = 0; i < rowCount; i++) {
			ArrayList<E> values2 = new ArrayList<>(columnCount);
			for (int j = 0; j < columnCount; j++) {
				values2.add(null);
			}
			values.add(values2);
		}
	}
	
	public void set(int row, int column, E value) {
		values.get(row).set(column, value);
	}
	
	public E get(int row, int column) {
		return values.get(row).get(column);
	}
	
	public void addRow(int index) {
		ArrayList<E> values2 = new ArrayList<>(rowCount);
		for (int j = 0; j < columnCount; j++) {
			values2.add(null);
		}
		values.add(index, values2);
		rowCount++;
	}
	
	public void addRow() {
		addRow(rowCount);
	}
	
	public void addColumn(int index) {
		for (ArrayList<E> arrayList : values) {
			arrayList.add(index, null);
		}
		columnCount++;
	}
	
	public void addColumn() {
		addColumn(columnCount);
	}
	
	/**
	 * @return the rowCount
	 */
	public int getRowCount() {
		return rowCount;
	}

	/**
	 * @return the columnCount
	 */
	public int getColumnCount() {
		return columnCount;
	}

	@Override
	public String toString() {
		return values.toString();
	}
	
	public ArrayList<E> toArrayList() {
		ArrayList<E> list = new ArrayList<>();
		for (ArrayList<E> list1 : values) {
			list.addAll(list1);
		}
		return list;
	}
	
	public static void main(String[] args) {
		TwoDimensionaArraylList<String> testList = new TwoDimensionaArraylList<>(5, 7);
		testList.set(3, 4, "test");
		PrintStream out = System.out;
		out.println(testList);
		testList.addRow(2);
		testList.addColumn(2);
		out.println(testList);
	}

}
