package cn.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.service.ItemService;

/**
 * 商品管理Controller
 * @author qixiongliu
 *
 */
@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@RequestMapping(value="/item/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable Long itemId) {
		TbItem tbItem = itemService.getItemById(itemId);
		return tbItem;
	}
	
	@RequestMapping(value="/item/list")
	@ResponseBody
	public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
		EasyUIDataGridResult result = itemService.getItemList(page, rows);
		return result;
	}
	
	/**
	 * 商品添加
	 */
	@RequestMapping(value="/item/save", method=RequestMethod.POST)
	@ResponseBody
	public E3Result addItem(TbItem item, String desc) {
		E3Result e3Result = itemService.addItem(item, desc);
		return e3Result;
	}
	
	/**
	 * 商品编辑(未完成 还有bug  数据显示不出来   EasyUI的格式还没弄明白)
	 */
	@RequestMapping(value="/rest/item/query/item/desc/{id}")
	@ResponseBody
	public E3Result getEditItemDescById(@PathVariable Long id) {
		E3Result e3Result = itemService.getEditItemDescById(id);
		return e3Result;
	}
	@RequestMapping(value="/rest/item/param/item/query/{id}")
	@ResponseBody
	public E3Result getEditItemById(@PathVariable Long id) {
		E3Result e3Result = itemService.getEditItemById(id);
		return e3Result;
	}
}
