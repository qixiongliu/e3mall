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
	public E3Result editItem(TbItem item, String desc);
	public E3Result deleteItems(String ids);
	public E3Result backOrderItems(String ids);
	public E3Result newStockItems(String ids);
}
