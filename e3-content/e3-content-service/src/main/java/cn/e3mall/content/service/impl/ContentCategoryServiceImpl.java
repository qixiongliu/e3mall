package cn.e3mall.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.content.service.ContentCategoryService;
import cn.e3mall.mapper.TbContentCategoryMapper;
import cn.e3mall.pojo.TbContentCategory;
import cn.e3mall.pojo.TbContentCategoryExample;
import cn.e3mall.pojo.TbContentCategoryExample.Criteria;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
	
	@Autowired
	private TbContentCategoryMapper tbContentCategoryMapper;
	
	@Override
	public List<EasyUITreeNode> getContentCategoryList(long parentId) {
		// 去查询参数ID，parentId
		// 根据parentId查询tb_content_category，查询子节点列表
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		// 设置查询条件
		List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
		// 把列表转成List<EasyUITreeNode>
		List<EasyUITreeNode> nodeList = new ArrayList<>();
		for (TbContentCategory cat: list) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(cat.getId());
			node.setText(cat.getName());
			node.setState(cat.getIsParent()?"closed":"open");
			nodeList.add(node);
		}
		return nodeList;
	}

	@Override
	public E3Result addContentCategory(long parentId, String name) {
		// 1、接收两个参数：parentId、name
		// 2、向tb_content_category表中插入数据。
		// a)创建一个TbContentCategory对象
		TbContentCategory tbContentCategory = new TbContentCategory();
		tbContentCategory.setParentId(parentId);
		tbContentCategory.setName(name);
		// b)补全TbContentCategory对象的属性
		tbContentCategory.setIsParent(false);
		Date date = new Date();
		tbContentCategory.setCreated(date);
		tbContentCategory.setUpdated(date);
		//排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数
		tbContentCategory.setSortOrder(1);
		//状态。可选值:1(正常),2(删除)
		tbContentCategory.setStatus(1);
		// c)向tb_content_category表中插入数据
		tbContentCategoryMapper.insert(tbContentCategory);
		// 3、判断父节点的isparent是否为true，不是true需要改为true。
		TbContentCategory parentNode = tbContentCategoryMapper.selectByPrimaryKey(parentId);
		if (!parentNode.getIsParent()) {
			parentNode.setIsParent(true);
			//更新父节点
			tbContentCategoryMapper.updateByPrimaryKey(parentNode);
		}
		// 4、需要主键返回。
		// 5、返回E3Result，其中包装TbContentCategory对象

		return E3Result.ok(tbContentCategory);
	}

	@Override
	public E3Result renameContentCategory(long id, String name) {
		TbContentCategory node = tbContentCategoryMapper.selectByPrimaryKey(id);
		node.setName(name);
		tbContentCategoryMapper.updateByPrimaryKey(node);
		return E3Result.ok();
	}
	
	/**
	 * 删除节点
	 */
	@Override
	public E3Result deleteContentCategory(long id) {
		TbContentCategory node = tbContentCategoryMapper.selectByPrimaryKey(id);
		
		// 判断父节点有几个子节点
		long parentId = node.getParentId();
		TbContentCategory parent = tbContentCategoryMapper.selectByPrimaryKey(parentId);
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
		if (list.size() <= 1) {
			parent.setIsParent(false);
		}
		
		deleteHandler(node);
		
		return E3Result.ok();
	}
	
	public void deleteHandler(TbContentCategory node) {
		// 出口
		if (!node.getIsParent()) {
			tbContentCategoryMapper.deleteByPrimaryKey(node.getId());
			return;
		}
		
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(node.getId());
		List<TbContentCategory> childrens = tbContentCategoryMapper.selectByExample(example);
		for (TbContentCategory children: childrens) {
			deleteHandler(children);
		}
		
		tbContentCategoryMapper.deleteByPrimaryKey(node.getId());
		
		return;
	}

}
