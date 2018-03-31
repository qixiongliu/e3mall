package cn.e3mall.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.IDUtils;
import cn.e3mall.mapper.TbItemDescMapper;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.pojo.TbItemExample;
import cn.e3mall.pojo.TbItemExample.Criteria;
import cn.e3mall.service.ItemService;

/**
 * 商品管理Service
 * @author qixiongliu
 *
 */
@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private TbItemMapper tbItemMapper;
	@Autowired
	private TbItemDescMapper tbItemDescMapper;

	@Override
	public TbItem getItemById(long itemId) {
		// 根据主键查询
//		TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);
		TbItemExample example = new TbItemExample();
		// 设置查询条件
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(itemId);
		// 执行查询
		List<TbItem> tbItemList = tbItemMapper.selectByExample(example);
		if (tbItemList != null && tbItemList.size() > 0) {
			return tbItemList.get(0);
		}
		return null;
	}

	@Override
	public EasyUIDataGridResult getItemList(int page, int rows) {
		PageHelper.startPage(page, rows);
		TbItemExample example = new TbItemExample();
		List<TbItem> list = tbItemMapper.selectByExample(example);
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setTotal(pageInfo.getTotal());
		result.setRows(list);
		
		return result;
	}

	@Override
	public E3Result addItem(TbItem item, String desc) {
		// 1、生成商品id
		long itemId = IDUtils.genItemId();
		// 2、补全TbItem对象的属性
		item.setId(itemId);
		//商品状态，1-正常，2-下架，3-删除
		item.setStatus((byte) 1);
		Date date = new Date();
		item.setCreated(date);
		item.setUpdated(date);
		// 3、向商品表插入数据
		tbItemMapper.insert(item);
		// 4、创建一个TbItemDesc对象
		TbItemDesc itemDesc = new TbItemDesc(); 
		// 5、补全TbItemDesc的属性
		itemDesc.setItemId(itemId);
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		itemDesc.setItemDesc(desc);
		// 6、向商品描述表插入数据
		tbItemDescMapper.insert(itemDesc);
		// 7、E3Result.ok()
		return E3Result.ok();
	}

	@Override
	public E3Result getEditItemById(long id) {
		TbItem item = tbItemMapper.selectByPrimaryKey(id);
		return E3Result.ok(item);
	}

	@Override
	public E3Result getEditItemDescById(long id) {
		TbItemDesc itemDesc = tbItemDescMapper.selectByPrimaryKey(id);
		return E3Result.ok(itemDesc);
	}
	
}
