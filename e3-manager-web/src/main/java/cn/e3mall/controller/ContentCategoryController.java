package cn.e3mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.content.service.ContentCategoryService;

@Controller
public class ContentCategoryController {
	
	@Autowired
	private ContentCategoryService contentCategoryService;
	
	@RequestMapping(value="/content/category/list")
	@ResponseBody
	public List<EasyUITreeNode> getContentCategoryList(@RequestParam(value="id", defaultValue="0") Long parentId) {
		List<EasyUITreeNode> resultList = contentCategoryService.getContentCategoryList(parentId);
		return resultList;
	}
	
	@RequestMapping(value="/content/category/create", method=RequestMethod.POST)
	@ResponseBody
	public E3Result addContentCategory(Long parentId, String name) {
		E3Result e3Result = contentCategoryService.addContentCategory(parentId, name);
		return e3Result;
	}
	
	@RequestMapping(value="/content/category/update", method=RequestMethod.POST)
	@ResponseBody
	public E3Result renameContentCategory(Long id, String name) {
		E3Result e3Result = contentCategoryService.renameContentCategory(id, name);
		return e3Result;
	}
	
	@RequestMapping(value="/content/category/delete/", method=RequestMethod.POST)
	@ResponseBody
	public E3Result deleteContentCategory(Long id) {
		E3Result e3Result = contentCategoryService.deleteContentCategory(id);
		return e3Result;
	}
	
}
