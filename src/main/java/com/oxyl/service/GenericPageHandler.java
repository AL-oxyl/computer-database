package com.oxyl.service;

import java.util.List;
import java.util.Optional;

import com.oxyl.ui.Pagination;

public interface GenericPageHandler<T> {
	public int getPageIndex();
	public List<Optional<T>> getPageList();
	public void setPageList(List<Optional<T>> pageList);
	public void handlePage(int result);
	public void updateInfo(int entry);
	public boolean testLeft();
	public boolean testRight();
	public Pagination getPageState();
}
