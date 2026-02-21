package com.hoit.accountbook.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import com.hoit.util.ExcelUpload;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


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
	
	@PostMapping(value = "/excelup.do")
	@ResponseBody
	public String excelup(MultipartHttpServletRequest request) throws Exception {
		
		MultipartFile excelFile = request.getFile("excelFile");
		if (excelFile == null || excelFile.isEmpty()) {
			return "파일이 없습니다.";
		}

		// 1. 파일 업로드
		String filePath = setExcelUrl(excelFile, request);
		File file = new File(filePath);

		// 2. 엑셀 읽기
		String[] cellTitle = {"거래일시", "입금금액", "출금금액", "적요내용"};
		ExcelUpload excelUpload = new ExcelUpload();
		List<Map<String, Object>> list = excelUpload.upExcel(filePath, cellTitle);
		System.out.println(list);
		
		return null;
//		int cnt = 0;
//		return cnt + "건 저장 완료";
	}
	
	public String setExcelUrl( MultipartFile item, HttpServletRequest request ) throws Exception{
		String uploadFileName = item.getOriginalFilename();								//업로드된 파일명
    	String savePath = request.getSession().getServletContext().getRealPath("/");	//저장경로

	    String fileName = com.hoit.util.UniqueKey.getKeyByDateFormat() +"."+ uploadFileName.substring(uploadFileName.lastIndexOf(".")+1); //파일명 생성
    	String filePath = "excel" + fileName;		//DB 저장용 파일경로
    	String fileFullPath = savePath + filePath;					//저장전체경로

    	File uploadFile = new File(fileFullPath);
    	item.transferTo(uploadFile);

    	return fileFullPath;
	}
}
