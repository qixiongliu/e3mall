package cn.e3mall.service;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbItem;

public interface ItemService {
	public TbItem getItemById(long itemId);
	public EasyUIDataGridResult getItemList(int page, int rows);
	public E3Result addItem(TbItem item, String desc);
	public E3Result getEditItemById(long id);
	public E3Result getEditItemDescById(long id);
}
