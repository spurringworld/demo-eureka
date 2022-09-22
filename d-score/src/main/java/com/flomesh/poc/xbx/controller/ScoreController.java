package com.flomesh.poc.xbx.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flomesh.poc.xbx.vo.ScoreVO;
 
@RestController
public class ScoreController {
 
	@GetMapping("scores")
	public List<ScoreVO> getSores(@RequestParam(value="studentId", required=false, defaultValue="0") int studentId ){
		System.out.println("===> get scores");
		List<ScoreVO> list = new ArrayList<ScoreVO>();
		list.add(new ScoreVO(1, "语文", 95));
		list.add(new ScoreVO(1, "数学", 98));
		list.add(new ScoreVO(2, "语文", 88));
		list.add(new ScoreVO(2, "数学", 92));
		list.add(new ScoreVO(3, "语文", 89));
		list.add(new ScoreVO(3, "数学", 100));
		list.add(new ScoreVO(4, "语文", 93));
		list.add(new ScoreVO(4, "数学", 86));
		if(studentId > 0){
			return list.stream().filter(s->s.getStudentId()==studentId).collect(Collectors.toList());
		}
		return list;
	}

}