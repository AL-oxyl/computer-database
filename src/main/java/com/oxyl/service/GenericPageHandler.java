package com.oxyl.service;

import java.util.List;

import com.oxyl.ui.Pagination;

public interface GenericPageHandler<T> {
	public Long getPageIndex();
	public List<T> getPageList();
	public void setPageList(List<T> pageList);
	public void handlePage(int result);
	public void updateInfo(Long entry);
	public boolean testLeft();
	public boolean testRight();
	public Pagination getPageState();
}
