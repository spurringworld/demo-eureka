package com.flomesh.poc.xbx.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.flomesh.poc.xbx.vo.TeacherVO;

 
@RestController
public class TeacherController {

	@Autowired
    LoadBalancerClient loadBalancerClient;

    @Autowired
    RestTemplate restTemplate;
 
	@Value("${service-g.url}")
	private String urlServiceG;


	@GetMapping("teachers")
	public List<TeacherVO> getTeachers(@RequestParam(value="schoolId", required=false, defaultValue="0") int schoolId ){
		System.out.println("======>>> get teachers");
		List<TeacherVO> list = new ArrayList<TeacherVO>();
		list.add(new TeacherVO(1, "孔子","特级",55,1));
		list.add(new TeacherVO(2, "莫言","高级",44,2));
		list.add(new TeacherVO(3, "钱学森","高级",39,1));
		list.add(new TeacherVO(4, "李淑婷","中级",32,2));
		if(schoolId > 0){
			return list.stream().filter(s->s.getSchoolId()==schoolId).collect(Collectors.toList());
		}
		return list;
	}


	// @GetMapping("teachers/{id}/subject")
	// public TeacherSubjectVO getClub4Student(@PathVariable(value="id") int id){
	// 	System.out.println("======>>> get teachers with subject");
	// 	TeacherSubjectVO result = new TeacherSubjectVO();
	// 	List<TeacherVO> list = this.getTeachers(0);
	// 	for (TeacherVO teacher : list) {
	// 		if(id == teacher.getId()) {
	// 			result.setTeacher(teacher);
	// 			// 获取科目
	// 			ServiceInstance serviceInstance = loadBalancerClient.choose("g-subject");
	// 			String url = "http://" + serviceInstance.getHost() + ":" 
	// 			            + serviceInstance.getPort() 
	// 						+ "teachers/" + id + "/subject";
	// 			System.out.println(url);
	// 			SubjectVO subject = restTemplate.getForObject(url, SubjectVO.class);
	// 			result.setSubject(subject);
	// 		}
	// 	}
	// 	return result;
	// }


	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("sayHello")
	public String sayHello(){
		System.out.println("======>>> sayHello");
		String url = this.urlServiceG + "/hello";
		System.out.println(url);
		String msgFromServiceG = restTemplate.getForObject(url, String.class);
		return msgFromServiceG;
	}

}