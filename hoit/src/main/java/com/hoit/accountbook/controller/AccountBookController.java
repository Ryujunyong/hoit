package com.hoit.accountbook.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hoit.accountbook.service.AccountBookService;
import com.hoit.category.service.CategoryService;
import com.hoit.common.CursorResponse;
import com.hoit.common.PageVO;
import com.hoit.common.PagingSetting;



@Controller
@RequestMapping(value = "/hoit/accountBook")
public class AccountBookController {

	@Autowired
	private AccountBookService accountBookService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private PagingSetting pageingSetting;

	@GetMapping(value = "/list.do")
	public String list(Model model, @RequestParam Map<String, Object> param) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date date = new Date();
		String currentDate = sdf.format(date);
		param.put("ASSET_AT", currentDate);
		Map<String, Object> map = new HashMap<>();
		map.put("USE_YN", "Y");
		Map<String, Object> commonPaging = pageingSetting.CommonPaging(new PageVO(), accountBookService.accountBookCnt());
		
		model.addAttribute("list", accountBookService.accountBookList(commonPaging));
		model.addAttribute("cb", accountBookService.getCurrentMoney());
		model.addAttribute("cg", categoryService.selectCategoryList(map));
		model.addAttribute("totalPageCnt", commonPaging.get("totalPageCnt"));
		model.addAttribute("pageVO", commonPaging.get("pageVO"));
		
		return "accountBook/list";
	}
	
	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/scrollList.do")
	@ResponseBody
	public CursorResponse scrollList(@RequestBody Map<String, Object> param) {
		return accountBookService.getScrollList(param);
	}
	
	@PostMapping(value = "/write_submit.do")
	@ResponseBody
	public void write(@RequestBody Map<String, Object> param) {
		System.out.println(param);
		accountBookService.writeAccountBook(param);
	}
	
	@PostMapping(value = "/chargeCash_submit.do")
	@ResponseBody
	public Map<String, Object> saveAsset(@RequestBody Map<String, Object> param) {
		accountBookService.saveAsset(param);
		return param;
	}
	
	@PostMapping(value = "/deleteAccountBook_submit.do")
	@ResponseBody
	public void deleteAccountBook(@RequestBody Map<String, Object> param) {
		accountBookService.deleteAccountBook(param);
	}
	
	@GetMapping(value = "/stats.do")
	public String stats() {
		return "accountBook/stats";
	}
	
	@PostMapping(value = "/getMonthlyAmount.do")
	@ResponseBody
	public Map<String, Object> getMonthlyAmount(@RequestBody Map<String, Object> param) {
		return accountBookService.getMonthlyAmount(param);
	}
	@PostMapping(value = "/getCategoryMonthlyAmount.do")
	@ResponseBody
	public List<Map<String, Object>> getCategoryMonthlyAmount(@RequestBody Map<String, Object> param) {
		return accountBookService.getCategoryMonthlyAmount(param);
	}
	
	@PostMapping(value = "/updateCategory_submit.do")
	@ResponseBody
	public void updateCategory(@RequestBody Map<String, Object> param) {
		accountBookService.editCategory(param);
	}
	
	@PostMapping(value = "/excelup.do")
	@ResponseBody
	public String excelup(MultipartHttpServletRequest request) throws Exception {
		MultipartFile excelFile = request.getFile("excelFile");
		if (excelFile == null || excelFile.isEmpty()) {
			return "파일이 없습니다.";
		}
		int cnt = accountBookService.excelUpload(request, excelFile);
		return cnt + "건의 카테고리가 등록되지 않았습니다. 카테고리 등록 바랍니다.";
	}
	

}
