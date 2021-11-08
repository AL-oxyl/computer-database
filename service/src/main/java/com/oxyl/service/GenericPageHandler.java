package com.oxyl.service;

import java.util.List;

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
