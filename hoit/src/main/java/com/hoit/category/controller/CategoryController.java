package com.hoit.category.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoit.category.service.CategoryService;

@Controller
@RequestMapping(value = "/hoit/category")
public class CategoryController {

	@Autowired
	private CategoryService cateGoryService;
	
	@GetMapping(value = "/list.do")
	public String list(Model model) {
		Map<String, Object> map = new HashMap<>();
		model.addAttribute("list", cateGoryService.selectCategoryList(map));
		return "category/list";
	}
	
	@PostMapping(value = "/write_submit.do")
	@ResponseBody
	public void write(@RequestBody Map<String, Object> param) {
		cateGoryService.insertCategory(param);
	}
	
	@PostMapping(value = "/update_submit.do")
	@ResponseBody
	public void update(@RequestBody Map<String, Object> param) {
		cateGoryService.updateCategory(param);
	}
}
