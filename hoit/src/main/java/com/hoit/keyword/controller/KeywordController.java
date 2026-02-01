package com.hoit.keyword.controller;

import java.util.HashMap;
import java.util.Map;
import com.hoit.keyword.service.impl.KeywordServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoit.category.service.CategoryService;
import com.hoit.keyword.service.KeywordService;

@Controller
@RequestMapping(value = "/hoit/keyword")
public class KeywordController {

	@Autowired
	private KeywordService keywordService;
	@Autowired
	private CategoryService categoryService;

	@GetMapping(value = "/list.do")
	public String list(Model model) {
		Map<String, Object> map = new HashMap<>();
		map.put("USE_YN", "Y");
		model.addAttribute("list", keywordService.selectKeywordList());
		model.addAttribute("cg", categoryService.selectCategoryList(map));
		return "keyword/list";
	}
	
	@PostMapping(value = "/write_submit.do")
	@ResponseBody
	public void write(@RequestBody Map<String, Object> param) {
		keywordService.writeKeyword(param);
	}
	
	@PostMapping(value = "/delete_submit.do")
	@ResponseBody
	public void delete(@RequestBody Map<String, Object> param) {
		keywordService.deleteKeyword(param);
	}
}
