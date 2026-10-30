package xyz.lcq12q.utils;

import java.util.List;

public class Page<T> {
	private int pageNum; // 当前页码
	private int pageSize; // 每页显示的记录数
	private int totalPage; // 总页数
	private int totalCount; // 总记录数

	private List<T> list; // 当前页的数据列表

	/**
	 * 获取当前页码
	 * @return 当前页码
	 */
	public int getPageNum() {
		return pageNum;
	}

	/**
	 * 设置当前页码
	 * @param pageNum 当前页码
	 */
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	/**
	 * 获取每页显示的记录数
	 * @return 每页显示的记录数
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每页显示的记录数
	 * @param pageSize 每页显示的记录数
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 获取总页数
	 * @return 总页数
	 */
	public int getTotalPage() {
		totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
//		totalPage = (int) Math.ceil((double) totalCount / pageSize);
		return totalPage;
	}

	/**
	 * 设置总页数
	 * @param totalPage 总页数
	 */
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	/**
	 * 获取总记录数
	 * @return 总记录数
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * 设置总记录数
	 * @param totalCount 总记录数
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 获取当前页的数据列表
	 * @return 当前页的数据列表
	 */
	public List<T> getList() {
		return list;
	}

	/**
	 * 设置当前页的数据列表
	 * @param list 当前页的数据列表
	 */
	public void setList(List<T> list) {
		this.list = list;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNum = pageNumber;
	}

	/**
	 * 获取当前页码（与 setPageNumber 配套的 getter，供 EL 的 ${p.pageNumber} 使用）
	 * @return 当前页码
	 */
	public int getPageNumber() {
		return pageNum;
	}
}
