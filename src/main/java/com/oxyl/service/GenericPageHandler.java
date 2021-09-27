package com.oxyl.service;

import java.util.ArrayList;

import com.oxyl.ui.Pagination;

public interface GenericPageHandler<T> {
	public int getPageIndex();
	public ArrayList<T> getPageList();
	public void setPageList(ArrayList<T> pageList);
	public int getNumberPage();
	public void handlePage(int result);
	public void updateInfo(int entry);
	public boolean testLeft();
	public boolean testRight();
	public Pagination getPageState();
}
