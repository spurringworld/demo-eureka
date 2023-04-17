package com.flomesh.poc.xbx.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.flomesh.poc.xbx.vo.ClubVO;
 
@RestController
public class ClubController {

 
	@GetMapping("clubs")
	public List<ClubVO> getClubs(){
		System.out.println("===> get clubs");
		List<ClubVO> list = new ArrayList<ClubVO>();
		list.add(new ClubVO(1, "Basketball-篮球社",
		    "无论你是看篮球还是打篮球，无论你是对篮球一知半解还是从未涉及，只要你关注篮球相关的，对篮球充满兴趣，篮球社将非常欢迎你们的加入",
			new int[]{1}));
		list.add(new ClubVO(2, "Weiqi-围棋社",
		    "中国文化人历来推崇艺术素养的琴、棋、书、画。棋在其中占据了独特的地位。棋作为体育项目之一，也是随着国运的变动而起起伏伏。陈毅元帅说过:“国运昌，棋运盛。”",
			new int[]{2,3}));
		list.add(new ClubVO(3, "Quyi-曲艺社",
		    "社团宗旨为继承和发展曲艺艺术，并将时代主题与传统文化、校园文化相融合，不断开拓曲艺表演途径，不断开拓曲艺受众群体，让欢声笑语洒满校园的每个角落。",
			new int[]{4}));
		return list;
	}

	@GetMapping("clubs/{id}")
	public ClubVO getOneClub(@PathVariable(value="id") int id){
		System.out.println("===> get a club");
		List<ClubVO> list = this.getClubs();
		for (ClubVO clubVO : list) {
			if(clubVO.getId() == id){
				return clubVO;
			}
		}
		return null;
	}

	@GetMapping("students/{id}/club")
	public ClubVO getClubBystId(@PathVariable(value="id") int id){
		System.out.println("===> get a club");
		List<ClubVO> list = this.getClubs();
		for (ClubVO clubVO : list) {
			int[] stIds = clubVO.getStudentIds();
			for (int stId : stIds) {
				if(stId == id) {
					return clubVO;
				}
			}
		}
		return null;
	}

}