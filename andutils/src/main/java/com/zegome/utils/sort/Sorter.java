package com.zegome.utils.sort;

import java.util.Comparator;
import java.util.List;

/**
 * (C) 2013 ZeroTeam
 * @author QuanLT
 */

public abstract class Sorter<T> {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	public abstract void sort(final T[] pArray, final int pStart, final int pEnd, final Comparator<T> pComparator);
	public abstract void sort(final List<T> pList, final int pStart, final int pEnd, final Comparator<T> pComparator);
	
	// ===========================================================
	// Methods
	// ===========================================================

	public final void sort(final T[] pArray, final Comparator<T> pComparator){
		this.sort(pArray, 0, pArray.length, pComparator);
	}

	public final void sort(final List<T> pList, final Comparator<T> pComparator){
		this.sort(pList, 0, pList.size(), pComparator);
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
