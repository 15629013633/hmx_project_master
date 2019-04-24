package com.hmx.utils.result;

import java.io.Serializable;
import java.util.List;


/**
 * @Description 分页工具类
 * @Date 2016年6月24日-上午9:32:37
 */
@SuppressWarnings("serial")
public class PageBean<T> implements Serializable{
	
	private int pageNum;//当前页数(>0)  状态
	private int totalNum; // 总记录数(>=0)      读写
	private int totalPageNum; //总页数(>0)  只读

	private int pageSize; // 每页的记录数(>0)  初始化

	private List<T> page; // 当前页中存放的记录  读写

	public PageBean(int pageSize){
		this.pageNum = 1;
		this.pageSize = 10;
	}
	
	public PageBean(){
		this( 10 );
	}
	
	/**
	 * 获取任一页第一条数据在数据集的位置.
	 * 
	 * @param pageNo
	 *            从1开始的页号
	 * @param pageSize
	 *            每页记录条数
	 * @return 该页第一条数据
	 */
	public int getStartOfPage() {
		return (this.pageNum - 1) * pageSize;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public List<T> getPage() {
		return page;
	}
	
	public void setPage(List<T> page) {
		this.page = page;
	}
	
	public int getPageNum() {
		return pageNum;
	}
	
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	
	public int getTotalNum() {
		return totalNum;
	}
	
	/**
	 * @param totalNum 总页数
	 * @return true : 可以继续查询 false: 没有记录
	 */
	public boolean setTotalNum(int totalNum) {
		this.totalNum = totalNum;
		
		if(this.totalNum == 0){
			this.totalPageNum = 0;
			return false;
		}else{
			this.totalPageNum = this.totalNum / this.pageSize;
			if(this.totalNum % this.pageSize > 0){
				this.totalPageNum ++;
			}
		}
		this.pageNum = this.pageNum > this.totalPageNum ? this.totalPageNum:this.pageNum;
		return true;
	}
	
	public int getTotalPageNum() {
		return totalPageNum;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}

